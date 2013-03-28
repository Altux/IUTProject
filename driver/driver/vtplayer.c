/*
 * VTPlayer mouse driver - 0.5.0
 *
 * Copyright (c) 2004-2007 Christophe Jacquet <jacquetc@free.fr>
 *
 *	This program is free software; you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as
 *	published by the Free Software Foundation, version 2.
 *
 * This driver is based on:
 *  - the usb-skeleton driver, version 1.1
 *    copyright (c) 2001-2003 Greg Kroah-Hartman <greg@kroah.com>
 *
 *  - the usbmouse driver, version 1.15
 *    copyright (c) 1999-2001 Vojtech Pavlik <vojtech@ucw.cz>
 *
 *
 * History:
 * 2013-02-06 - 0.5.0 - adapted to change in kernel APIs (ok with 3.5.0)
 * 2007-10-07 - 0.4.1 - adapted to SPARC (big-endian) architecture (ok with 2.6.22)
 * 2007-02-25 - 0.4.0 - adapted to changes in kernel APIs (ok with 2.6.15)
 * 2004-12-02 - 0.3.2 - changed device file display to "/dev/usb/vtplayer%d"
 * 2004-03-06 - 0.3.1 - lots of code cleanup - first public release
 * 2004-01-26 - 0.3.0 - merge of 0.1 and 0.2 - full support
 * 2004-01-26 - 0.2.0 - tactile cell support, written from usb-skeleton.c
 * 2004-01-25 - 0.1.0 - mouse support, based on usbmouse.c adapted to
 *                      VTPlayer's specific protocol
 *
 */

/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

#include <linux/kernel.h>
#include <asm/signal.h>
#include <linux/errno.h>
#include <linux/init.h>
#include <linux/slab.h>
#include <linux/module.h>
#include <linux/kref.h>
#include <linux/uaccess.h>
#include <linux/usb.h>
#include <linux/mutex.h>
#include <linux/usb/input.h>
#include <linux/hid.h>
#include <linux/fs.h>
#include "vtplayer.h"

/* Use our own dbg macro */
#undef dbg
#define dbg(format, arg...) do { if (debug) printk(KERN_DEBUG __FILE__ ": " format "\n" , ## arg); } while (0)

/* Version Information */
#define DRIVER_VERSION "v0.5.0"
#define DRIVER_AUTHOR "Christophe Jacquet <jacquetc@free.fr>"
#define DRIVER_DESC "VirTouch VTPlayer Tactile Mouse Driver"
#define DRIVER_LICENSE "GPL"
#define DRIVER_NAME "vtplayer"

/* Default minor base */
#define VTP_MINOR_BASE	192

/* Module parameters */
/* Debug: print out additional debugging messages - off by default */
static int debug = 0;
module_param(debug, int, 0);
MODULE_PARM_DESC(debug, "Debug enabled or not");

/* VTPlayer vendor & product IDs */
#define VTP_VENDOR_ID	0x1100
#define VTP_PRODUCT_ID	0x0001

/* table of devices that work with this driver */
static struct usb_device_id vtp_table[] = {
    { USB_DEVICE(VTP_VENDOR_ID, VTP_PRODUCT_ID)},
    {} /* Terminating entry */
};

MODULE_DEVICE_TABLE(usb, vtp_table);

/* Structure to hold all of our device specific stuff */
struct vtplayer {
    struct usb_device *udev;            /* the usb device for this device */
    struct usb_interface *interface;    /* the interface for this device */
    unsigned char minor;                /* the starting minor number for this device */
    int open;                           /* if the port is open or not */
    int present;                        /* if the device is not disconnected */
    struct semaphore sem;               /* limiting the number of writes in progress */
    __u8 int_in_endpointAddr;           /* the address of the bulk in endpoint */

    /* These fields are specifically for the input device */
    struct input_dev *mouse_dev; /* associated input device */
    struct urb *mouse_irq;       /* URB used for IRQ transferts */
    int mouse_open;              /* if the input device is open or not */
    signed char *mouse_data;     /* the buffer to receive data */
    dma_addr_t mouse_data_dma;   /* DMA address */
    char mouse_name[128];        /* device name */
    char mouse_phys[64];         /* physical name */
};

/* prevent races between open() and disconnect() */
static DEFINE_SEMAPHORE(disconnect_sem);

/* local function prototypes */
static int vtp_ioctl(struct file *file, unsigned int cmd, unsigned long arg);
static int vtp_open(struct inode *inode, struct file *file);
static int vtp_release(struct inode *inode, struct file *file);

static int vtp_probe(struct usb_interface *interface, const struct usb_device_id *id);
static void vtp_disconnect(struct usb_interface *interface);


#define VTP_DATA_LENGTH 4


/************* I N P U T   D E V I C E   ( M O U S E ) ***********************/
static void vtp_mouse_irq(struct urb *urb) {
    struct vtplayer *mouse = urb->context;
    signed char *data = mouse->mouse_data;
    struct input_dev *dev = mouse->mouse_dev;
    int status;

    switch (urb->status) {
        case 0: /* success */
            break;
        case -ECONNRESET: /* unlink */
        case -ENOENT:
        case -ESHUTDOWN:
            return;
            /* -EPIPE:  should clear the halt */
        default: /* error */
            goto resubmit;
    }

    /*
     * VTPlayer protocol (as reverse-engineered on 2004-01-22):
     *	byte 0		X motion
     *	byte 1		Y motion
     *	byte 2		unused (?)
     *	byte 3		button:
     *				1- right top
     *				2- right bottom
     *				4- left bottom
     *				8- left top
     */

    input_report_key(dev, BTN_LEFT, data[3] & 0x08);
    input_report_key(dev, BTN_RIGHT, data[3] & 0x01);
    input_report_key(dev, BTN_MIDDLE, data[3] & 0x04);
    input_report_key(dev, BTN_SIDE, data[3] & 0x02);

    input_report_rel(dev, REL_X, data[0]);
    input_report_rel(dev, REL_Y, data[1]);

    input_sync(dev);

resubmit:
    status = usb_submit_urb(urb, GFP_ATOMIC);
    if (status) {
        dev_err(&mouse->udev->dev, "can't resubmit intr, %s-%s/input0, status %d\n", mouse->udev->bus->bus_name, mouse->udev->devpath, status);
    }
}

static int vtp_mouse_open(struct input_dev *dev) {
    struct vtplayer *mouse = input_get_drvdata(dev);

    dbg("%s", __FUNCTION__);
    
    if (mouse->mouse_open++) {
        return 0;
    }

    mouse->mouse_irq->dev = mouse->udev;
    if (usb_submit_urb(mouse->mouse_irq, GFP_KERNEL)) {
        mouse->mouse_open--;
        return -EIO;
    }

    return 0;
}

static void vtp_mouse_close(struct input_dev *dev) {
    struct vtplayer *mouse = input_get_drvdata(dev);
    
    dbg("%s", __FUNCTION__);

    if (!--mouse->mouse_open) {
        usb_unlink_urb(mouse->mouse_irq);
    }
}

/********************** T A C T I L E   P A D S ******************************/

/*
 * File operations needed when we register this driver.
 * This assumes that this driver NEEDS file operations,
 * of course, which means that the driver is expected
 * to have a node in the /dev directory. If the USB
 * device were for a network interface then the driver
 * would use "struct net_driver" instead, and a serial
 * device would use "struct tty_driver".
 */
static struct file_operations vtplayer_fops = {
    /*
     * The owner field is part of the module-locking
     * mechanism. The idea is that the kernel knows
     * which module to increment the use-counter of
     * BEFORE it calls the device's open() function.
     * This also means that the kernel can decrement
     * the use-counter again before calling release()
     * or should the open() function fail.
     */
    .owner = THIS_MODULE,
    .unlocked_ioctl = vtp_ioctl,
    .open = vtp_open,
    .release = vtp_release,
};

/*
 * usb class driver info in order to get a minor number from the usb core,
 * and to have the device registered with the driver core
 */
static struct usb_class_driver vtplayer_class = {
    .name = "vtplayer%d",
    .fops = &vtplayer_fops,
    .minor_base = VTP_MINOR_BASE,
};

static struct usb_driver vtplayer_driver = {
    .name = "vtplayer",
    .probe = vtp_probe,
    .disconnect = vtp_disconnect,
    .id_table = vtp_table,
};

/**
 *	vtp_delete
 */
static inline void vtp_delete(struct vtplayer *dev) {
    kfree(dev);
}

/**
 *	vtp_open
 */
static int vtp_open(struct inode *inode, struct file *file) {
    struct vtplayer *dev = NULL;
    struct usb_interface *interface;
    int subminor;
    int retval = 0;
    
    dbg("%s", __FUNCTION__);

    subminor = iminor(inode);

    /* prevent disconnects */
    down(&disconnect_sem);

    interface = usb_find_interface(&vtplayer_driver, subminor);
    if (!interface) {
        pr_err("%s - error, can't find device for minor %d\n", __func__, subminor);
        retval = -ENODEV;
        goto exit;
    }

    dev = usb_get_intfdata(interface);
    if (!dev) {
        retval = -ENODEV;
        goto exit;
    }

    /* lock this device */
    down(&dev->sem);

    /* increment our usage count for the driver */
    ++dev->open;

    // save our object in the file's private structure
    file->private_data = dev;
    
    /* unlock this device */
    up(&dev->sem);
    

exit:
    up(&disconnect_sem);
    return retval;
}

/**
 *	vtp_release
 */
static int vtp_release(struct inode *inode, struct file *file) {
    struct vtplayer *dev;
    int retval = 0;

    dev = (struct vtplayer *) file->private_data;
    if (dev == NULL) {
        dbg("%s: object is NULL", __FUNCTION__);
        return -ENODEV;
    }
    
    dbg("%s: minor %d", __FUNCTION__, dev->minor);

    /* lock our device */
    down(&dev->sem);

    if (dev->open <= 0) {
        dbg("%s: device not opened", __FUNCTION__);
        retval = -ENODEV;
        goto exit_not_opened;
    }

    --dev->open;

    if (!dev->present) {
        /* the device was unplugged before the file was released */
        up(&dev->sem);
        vtp_delete(dev);
        return 0;
    }

exit_not_opened:
    up(&dev->sem);

    return retval;
}

/**
 *	vtp_ioctl
 *
 *	The only supported command is VTP_CMD_SET_PADS, which sets the
 *	configuration of the tactile pads.
 */
static int vtp_ioctl(struct file *file, unsigned int cmd, unsigned long arg) {
    struct vtplayer *dev;
    int retval = 0;
    unsigned char *buffer;

    dev = (struct vtplayer *) file->private_data;
    
    dbg("%s(%X, %lX)", __FUNCTION__, cmd, arg);

    switch (cmd) {
        case VTP_CMD_SET_PADS: /* Set pad configuration */
            
            /* lock this object */
            down(&dev->sem);

            // verify that the device wasn't unplugged
            if (!dev->present) {
                retval = -ENODEV;
                goto exit;
            }

            /* allocate buffer */
            buffer = kmalloc(VTP_DATA_LENGTH, GFP_KERNEL);

            /* copy the data from userspace into our transfer buffer;
             * this is the only copy required.
             */
            if (copy_from_user(buffer, (unsigned char *) arg, VTP_DATA_LENGTH)) {
                retval = -EFAULT;
                goto exit;
            }

            retval = usb_control_msg(
                    dev->udev,
                    usb_sndctrlpipe(dev->udev, 0), /* Default control pipe */
                    0x09, /* bRequest */
                    0x21, /* bmRequestType */
                    0x0200, /* wValue */
                    0x0000, /* wIndex */
                    buffer, /* data */
                    VTP_DATA_LENGTH, /* wLength */
                    HZ * 10); /* timeout */

            /* we can deallocate "buffer" now because the call is synchronous */
            kfree(buffer);
            if (retval != VTP_DATA_LENGTH) {
                pr_err("%s - failed submitting write urb, error %d", __FUNCTION__, retval);
                retval = -1;
            } else {
                retval = 0;
            }

exit:
            /* unlock the device */
            up(&dev->sem);

            break;

        default:
            retval = -ENOTTY;
    }

    return retval;
}

/**
 *	vtp_probe
 *
 *	Called by the usb core when a new device is connected that it thinks
 *	this driver might be interested in.
 */
static int vtp_probe(struct usb_interface *interface, const struct usb_device_id *id) {
    struct usb_device *udev = interface_to_usbdev(interface);
    struct vtplayer *dev = NULL;
    struct usb_host_interface *iface_desc;
    struct usb_endpoint_descriptor *endpoint;
    int i;
    int retval = -ENOMEM;
    char path[64];
    char *buf;
    int maxp = 0;
    int irq_pipe = 0;
    int pollInterval = 0;
    
    dbg("probing: %04X:%04X", le16_to_cpu(udev->descriptor.idVendor), le16_to_cpu(udev->descriptor.idProduct));

    /* See if the device offered us matches what we can accept */
    if ((le16_to_cpu(udev->descriptor.idVendor) != VTP_VENDOR_ID) ||
            (le16_to_cpu(udev->descriptor.idProduct) != VTP_PRODUCT_ID)) {
        return -ENODEV;
    }

    /* allocate memory for our device state and initialize it */
    dev = kmalloc(sizeof (struct vtplayer), GFP_KERNEL);
    if (dev == NULL) {
        dev_err(&interface->dev, "Out of memory\n");
        goto error;
    }
    memset(dev, 0x00, sizeof (*dev));

    //mutex_init(&dev->sem);
    // cf: https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=3&ved=0CEAQFjAC&url=https%3A%2F%2Fcours.etsmtl.ca%2Fele784%2Fcours%2FELE784-Cours2-Synchronisation.pdf&ei=hPMUUcL0A-rY0QWZjYCgAw&usg=AFQjCNHnO1DJrmW0AI1K8224GEkK_zt-NA&cad=rja
    sema_init(&dev->sem, 1);
    
    dev->udev = udev;
    dev->interface = interface;

    /* set up the endpoint information */
    /* check out the endpoints */
    /* use the first Interrupt endpoint */
    /* (normally, the VTP-1 has only one endpoint (interrupt one), */
    /* at 0x81) */
    iface_desc = &interface->altsetting[0];
    for (i = 0; i < iface_desc->desc.bNumEndpoints; ++i) {
        endpoint = &iface_desc->endpoint[i].desc;

        if ((endpoint->bEndpointAddress & USB_DIR_IN) &&
                ((endpoint->bmAttributes & USB_ENDPOINT_XFERTYPE_MASK) == USB_ENDPOINT_XFER_INT)) {
            /* we found an interrupt in endpoint */
            dev->int_in_endpointAddr = endpoint->bEndpointAddress;
            pollInterval = endpoint->bInterval;
            dbg("%s: found interrupt-in endpoint at 0x%02X (mouse function)\n", __FUNCTION__, dev->int_in_endpointAddr);
            irq_pipe = usb_rcvintpipe(udev, dev->int_in_endpointAddr);
            maxp = usb_maxpacket(udev, irq_pipe, usb_pipeout(irq_pipe));
            break;
        }
    }
    if (!(dev->int_in_endpointAddr)) {
        pr_err("Couldn't find interrupt-in endpoint.\n");
        goto error;
    }

    /* allow device read, write and ioctl */
    dev->present = 1;

    /********************** MOUSE INITIALIZATION *************************/
    dev->mouse_data = usb_alloc_coherent(udev, 8, GFP_ATOMIC, &dev->mouse_data_dma);
    if (!dev->mouse_data) {
        kfree(dev);
        return -ENOMEM;
    }

    dev->mouse_irq = usb_alloc_urb(0, GFP_KERNEL);
    if (!dev->mouse_irq) {
        usb_free_coherent(udev, 8, dev->mouse_data, dev->mouse_data_dma);
        kfree(dev);
        return -ENODEV;
    }

    dev->udev = udev;

    dev->mouse_dev = input_allocate_device();

    dev->mouse_dev->evbit[0] = BIT_MASK(EV_KEY) | BIT_MASK(EV_REL);
    dev->mouse_dev->keybit[BIT_WORD(BTN_MOUSE)] = BIT_MASK(BTN_LEFT) | BIT_MASK(BTN_RIGHT) | BIT_MASK(BTN_MIDDLE);
    dev->mouse_dev->relbit[0] = BIT_MASK(REL_X) | BIT_MASK(REL_Y);
    dev->mouse_dev->keybit[BIT_WORD(BTN_MOUSE)] |= BIT_MASK(BTN_SIDE) | BIT_MASK(BTN_EXTRA);

    input_set_drvdata(dev->mouse_dev, dev);
    
    dev->mouse_dev->open = vtp_mouse_open;
    dev->mouse_dev->close = vtp_mouse_close;

    usb_make_path(udev, path, 64);
    sprintf(dev->mouse_phys, "%s/input0", path);

    dev->mouse_dev->name = dev->mouse_name;
    dev->mouse_dev->phys = dev->mouse_phys;
    dev->mouse_dev->id.bustype = BUS_USB;
    dev->mouse_dev->id.vendor = udev->descriptor.idVendor;
    dev->mouse_dev->id.product = udev->descriptor.idProduct;
    dev->mouse_dev->id.version = udev->descriptor.bcdDevice;

    if (!(buf = kmalloc(63, GFP_KERNEL))) {
        usb_free_coherent(udev, 8, dev->mouse_data, dev->mouse_data_dma);
        kfree(dev);
        return -ENOMEM;
    }

    if (udev->descriptor.iManufacturer &&
            usb_string(udev, udev->descriptor.iManufacturer, buf, 63) > 0)
        strcat(dev->mouse_name, buf);
    if (udev->descriptor.iProduct &&
            usb_string(udev, udev->descriptor.iProduct, buf, 63) > 0)
        sprintf(dev->mouse_name, "%s %s", dev->mouse_name, buf);

    if (!strlen(dev->mouse_name))
        sprintf(dev->mouse_name, "USB VTPlayer Mouse %04x:%04x",
            dev->mouse_dev->id.vendor, dev->mouse_dev->id.product);

    kfree(buf);

    dbg("%s: maxp = %i\n", __FUNCTION__, maxp);
    
    usb_fill_int_urb(dev->mouse_irq, udev, irq_pipe, dev->mouse_data,
            (maxp > 8 ? 8 : maxp),
            vtp_mouse_irq, dev, pollInterval);
    dev->mouse_irq->transfer_dma = dev->mouse_data_dma;
    dev->mouse_irq->transfer_flags |= URB_NO_TRANSFER_DMA_MAP;

    input_register_device(dev->mouse_dev);
    printk(KERN_INFO "input: %s on %s\n", dev->mouse_name, path);

    usb_set_intfdata(interface, dev);

    /* we can register the device now, as it is ready */
    retval = usb_register_dev(interface, &vtplayer_class);
    if (retval) {
        /* something prevented us from registering this driver */
        pr_err("Not able to get a minor for this device.");
        usb_set_intfdata(interface, NULL);
        goto error;
    }

    dev->minor = interface->minor;
    
    //pr_info("test info %d", interface->minor);

    /* let the user know what node this device is now attached to */
    pr_info("VTPlayer now attached to /dev/vtplayer%d (180,%d)", dev->minor /*- VTP_MINOR_BASE*/, dev->minor);

    return 0;

error:
    vtp_delete(dev);
    return retval;
}

/**
 *	vtp_disconnect
 *
 *	Called by the usb core when the device is removed from the system.
 *
 *	This routine guarantees that the driver will not submit any more urbs
 *	by clearing dev->udev.  It is also supposed to terminate any currently
 *	active urbs.  Unfortunately, usb_bulk_msg(), used in skel_read(), does
 *	not provide any way to do this.  But at least we can cancel an active
 *	write.
 */
static void vtp_disconnect(struct usb_interface *interface) {
    struct vtplayer *dev;
    int minor;

    /* prevent races with open() */
    down(&disconnect_sem);

    dev = usb_get_intfdata(interface);
    usb_set_intfdata(interface, NULL);

    down(&dev->sem);

    minor = dev->minor;

    /* give back our minor */
    usb_deregister_dev(interface, &vtplayer_class);

    /* Disconnecting the mouse part of the driver */
    usb_unlink_urb(dev->mouse_irq);
    input_unregister_device(dev->mouse_dev);
    usb_free_urb(dev->mouse_irq);
    usb_free_coherent(interface_to_usbdev(interface), 8, dev->mouse_data, dev->mouse_data_dma);


    /* prevent device read, write and ioctl */
    dev->present = 0;
    up(&dev->sem);

    /* if the device is opened, vtp_release will clean this up */
    if (!dev->open)
        vtp_delete(dev);
    
    up(&disconnect_sem);

    dev_info(&interface->dev, "VTPlayer vtplayer%d now disconnected", minor /*- VTP_MINOR_BASE*/);
}

/**
 *	vtp_init
 */
static int __init vtp_init(void) {
    int result;

    /* register this driver with the USB subsystem */
    result = usb_register(&vtplayer_driver);
    if (result) {
        pr_err("usb_register failed. Error number %d", result);
        return result;
    }

    pr_info(DRIVER_DESC " " DRIVER_VERSION);
    return 0;
}

static void __exit vtp_exit(void) {
    /* deregister this driver with the USB subsystem */
    usb_deregister(&vtplayer_driver);
}

module_init(vtp_init);
module_exit(vtp_exit);

MODULE_AUTHOR(DRIVER_AUTHOR);
MODULE_DESCRIPTION(DRIVER_DESC);
MODULE_VERSION(DRIVER_VERSION);
MODULE_LICENSE(DRIVER_LICENSE);

