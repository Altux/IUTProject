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


#include <stdlib.h>
#include <stdio.h>

#include "libvtplayer.h"

#ifdef __linux
#define START 1
#elif _WIN32
#define START 0
#endif

int main(int argc, char **argv) {
    unsigned int b1 = 0, b2 = 0, b3 = 0, b4 = 0; /* Four bytes */
    int ret;

#ifdef __linux
    if (argc != 5 + 1) {
        fprintf(stderr, "Expecting 5 arguments, but found %d instead.\n", argc);
        fprintf(stderr, "Usage: %s <device_node> <xx> <yy> <zz> <tt>\n", argv[0]);
        return EXIT_FAILURE;
    }
#elif _WIN32
    if (argc != 4 + 1) {
        fprintf(stderr, "Expecting 4 arguments, but found %d instead.\n", argc);
        fprintf(stderr, "Usage: %s <xx> <yy> <zz> <tt>\n", argv[0]);
        return EXIT_FAILURE;
    }
#endif

    //b1 = (BYTE)strtol(argv[2], NULL, 16);
    //printf("%d\n\n", strtol(argv[2], NULL, 16));
    //sscanf(argv[2], "%x", &b1);
    //if(sscanf(argv[2], "%x", &b1) <0) printf("NON\n");
    //else printf("%d\n", b1);

    if (
            sscanf(argv[START + 1], "%x", &b1) != 1 ||
            sscanf(argv[START + 2], "%x", &b2) != 1 ||
            sscanf(argv[START + 3], "%x", &b3) != 1 ||
            sscanf(argv[START + 4], "%x", &b4) != 1) {
        fprintf(stderr, "Error: could not parse hexadecimal number!\n");
        return EXIT_FAILURE;
    }

#ifdef __linux
    if (vtplayer_open(argv[1]) != -1) {
        fprintf(stderr, "VTPlayer device opened: %s.\n", argv[1]);
    } else {
        fprintf(stderr, "Failed to open VTPlayer device '%s'.\n", argv[1]);
        return EXIT_FAILURE;
    }
#elif _WIN32
    switch (vtplayer_open()) {
        case VTP_ERROR_OPENING:
            fprintf(stderr, "Failed to open VTPlayer device.\n");
            return EXIT_FAILURE;

        case VTP_ERROR_SETTING_CONFIGURATION:
            fprintf(stderr, "Failed to set the configuration on the VTPlayer device.\n");
            return EXIT_FAILURE;

        case VTP_ERROR_CLAIMING_INTERFACE:
            fprintf(stderr, "Failed to claim the VTPlayer device.\n");
            break;

        case VTP_ERROR_SETTING_ALTERNATE_INTERFACE:
            fprintf(stderr, "Failed to set alternate interface on the VTPlayer device.\n");
            break;
    }
#endif

    if ((ret = vtplayer_set(b1, b2, b3, b4)) >= 0) {
        fprintf(stderr, "Pad configuration set.\n");
    } else {
        fprintf(stderr, "Failed to sed pad configuration: %d\n", ret);
        return EXIT_FAILURE;
    }

    if ((ret = vtplayer_close()) >= 0) {
        fprintf(stderr, "Device closed.\n");
    } else {
        fprintf(stderr, "Failed to close device: %d.\n", ret);
        return EXIT_FAILURE;
    }


    return EXIT_SUCCESS;
}
