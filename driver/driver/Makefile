ifneq ($(KERNELRELEASE),)
obj-m	:=	vtplayer.o
else
KDIR	:= /lib/modules/$(shell uname -r)/build
PWD	:= $(shell pwd)

default:
	$(MAKE) -C $(KDIR) SUBDIRS=$(PWD) modules
endif

device:
	mknod /dev/vtplayer0 c 180 192

clean:
	rm vtplayer.ko vtplayer.o vtplayer.mod.o vtplayer.mod.c *~ .*
