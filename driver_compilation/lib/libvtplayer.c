/*
 * Copyright (C) 2004-2007 Christophe Jacquet <jacquetc@free.fr>
 *
 * See <vtplayer.c> and the associated documentation for more information.
 *
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

#include "libvtplayer.h"
#ifdef __linux
#include <sys/ioctl.h>
#include <fcntl.h>

#define STRLEN 1024
#elif _WIN32
#include "usb.h"
#include "control_request.h"

// Vendir ID and Product ID (see lsusb)
int VID = 0x1100;
int PID = 0x0001;

#define TIMEOUT 100
#endif

VTPLAYER vtplayer_handler;

#ifdef __linux

int vtplayer_open(const char *device) {
    vtplayer_handler = open(device, O_WRONLY);
    return vtplayer_handler;
}
#elif _WIN32

int vtplayer_open() {
    struct usb_bus * bus;
    struct usb_device * dev;
#ifdef __linux
    char buf[STRLEN];
#endif
#ifdef DEBUG
    printf("Init usb\n");
#endif
    usb_init();
#ifdef DEBUG
    printf("Find busses\n");
#endif
    usb_find_busses();
#ifdef DEBUG
    printf("Find devices\n");
#endif
    usb_find_devices();

#ifdef DEBUG
    printf("Explore busses\n");
#endif
    for (bus = usb_get_busses(); bus; bus = bus->next) {
#ifdef DEBUG
        printf("Parsing usb bus %s.\n", bus->dirname);
#endif
        for (dev = bus->devices; dev; dev = dev->next) {
#ifdef DEBUG
            printf("  Parsing usb device [%04X:%04X] %s\n", dev->descriptor.idVendor, dev->descriptor.idProduct, dev->filename);
#endif

            if ((dev->descriptor.idVendor == VID) && (dev->descriptor.idProduct == PID)) {
                if (vtplayer_handler) {
                    printf("Another vtplayer is connected. Please remove one.\n");
                    //return -1;
                }

#ifdef DEBUG
                printf("Found vtplayer. Opening interface.\n");
#endif
                if (!(vtplayer_handler = usb_open(dev))) {
                    perror("vtplayer");
                    return VTP_ERROR_OPENING;
                }
#ifdef __linux__
                if (usb_get_driver_np(vtplayer_handler, 0, buf, STRLEN) == 0) {
#ifdef DEBUG
                    printf("Done. Interface is bound to driver %s, detaching.\n", buf);
#endif
                    if (usb_detach_kernel_driver_np(vtplayer_handler, 0) < 0) {
                        perror("vtplayer");
                        return -1;
                    }
                }
#endif

#ifdef DEBUG
                printf("Done. Setting configuration.\n");
#endif
                if (usb_set_configuration(vtplayer_handler, dev->config[0].bConfigurationValue) < 0) {
                    perror("vtplayer");
                    return VTP_ERROR_SETTING_CONFIGURATION;
                }

                int val = 0;
#ifdef DEBUG
                printf("Done. Claiming interface.\n");
#endif
                if ((val = usb_claim_interface(vtplayer_handler, 0)) < 0) {
                    perror("vtplayer");
#ifdef DEBUG
                    printf("%d\n\n", val);
#endif
                    return VTP_ERROR_CLAIMING_INTERFACE;
                }

#ifdef DEBUG
                printf("Done. Setting alternate interface.\n");
#endif
                if (usb_set_altinterface(vtplayer_handler, 0) < 0) {
                    perror("vtplayer");
                    return VTP_ERROR_SETTING_ALTERNATE_INTERFACE;
                }
#ifdef DEBUG
                printf("Done.\n");
#endif
            }
        }
    }

    return (vtplayer_handler == NULL) ? VTP_NOT_FOUND : 0;
}
#endif

int vtplayer_buffer_set(void *buffer) {
#ifdef __linux
    return ioctl(vtplayer_handler, VTP_CMD_SET_PADS, buffer);
#elif _WIN32
    if (vtplayer_handler) {
        int ret = usb_control_msg(vtplayer_handler, 0x21, 0x09, 0x0200, 0x0000, buffer, VTP_DATA_LENGTH, TIMEOUT);
        if (ret < 0) {
#ifdef DEBUG
            switch (-ret) {
                case EBADF: //9
                    printf("USB ERROR: EBADF %d\n", ret);
                    break;

                case ENXIO: //6
                    printf("USB ERROR: ENXIO %d\n", ret);
                    break;

                case EBUSY: //19
                    printf("USB ERROR: EBUSY %d\n", ret);
                    break;

                    //case (LUSBDARWINSTALL):
                    //  printf("USB ERROR: LUSBDARWINSTALL %d\n", ret);
                    //  break;

                case EINVAL: //22
                    printf("USB ERROR: EINVAL %d\n", ret);
                    break;

#ifdef __linux__
                case ETIMEDOUT: //110
                    printf("USB ERROR: ETIMEDOUT %d\n", ret);
                    break;
#endif

                case EIO: //5
                    printf("USB ERROR: EIO %d\n", ret);
                    break;

                default:
                    printf("USB ERROR: %d\n", ret);
                    break;
            }
#endif
            //Dirty fix: if the vtplayer crashes for any reasonwe reload it
            //seems that this makes the program crashes anyway in certain circumstances
            //This occured only once, when there was a bug in the USB driver in the Linux kernel
            vtplayer_close();
            vtplayer_open();
        }
        return ret;
    }
    return 0;
#endif
}

int vtplayer_set(BYTE b1, BYTE b2, BYTE b3, BYTE b4) {
    BYTE buffer[VTP_DATA_LENGTH];

    buffer[0] = b1;
    buffer[1] = b2;
    buffer[2] = b3;
    buffer[3] = b4;

    return vtplayer_buffer_set(buffer);
}

int vtplayer_close() {
#ifdef __linux
    return close(vtplayer_handler);
#elif _WIN32
    if (vtplayer_handler) {
        if (usb_release_interface(vtplayer_handler, 0) < 0) {
            perror("usb_release");
            return VTP_ERROR_USB_RELEASE;
        }

        if (usb_close(vtplayer_handler) < 0) {
            perror("usb_close");
            return VTP_ERROR_USB_CLOSE;
        }
    }
    return 0;
#endif
}

#ifdef _WIN32

int vtplayer_get_status(char * data) {
    return usb_interrupt_read(vtplayer_handler, 0x81, data, 4, TIMEOUT);
}
#endif

/**
 * We change the bit order so that the 4 bytes set to the VTPlayer are
 * easy to read.
 * Each digit represent one line, each of its bits represent one pin.
 * => 4 digits for the left cell and 4 for the right cell.
 */
u32 reorder(u32 pad) {
#define B(z, y) (((pad & (1 << (y - 1))) ? 1 : 0) << (z - 1))
    return ~(B(8, 1) | B(7, 2) | B(16, 3) | B(15, 4) |
            B(24, 17) | B(23, 18) | B(32, 19) | B(31, 20) |
            B(6, 5) | B(5, 6) | B(14, 7) | B(13, 8) |
            B(22, 21) | B(21, 22) | B(30, 23) | B(29, 24) |
            B(2, 9) | B(1, 10) | B(10, 11) | B(9, 12) |
            B(18, 25) | B(17, 26) | B(26, 27) | B(25, 28) |
            B(4, 13) | B(3, 14) | B(12, 15) | B(11, 16) |
            B(20, 29) | B(19, 30) | B(28, 31) | B(27, 32));
}