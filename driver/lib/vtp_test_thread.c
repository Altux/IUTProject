/* 
 * File:   vtp_test_thread.c
 * Author: godeau
 *
 * Created on March 1, 2013, 8:53 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#ifdef _WIN32
#include <windows.h>
#endif
#include "libvtplayer-thread.h"

#ifdef __linux
#define START 1
#elif _WIN32
#define START 0
#endif

/*
 * 
 */
int main(int argc, char** argv) {

    unsigned int b1, b2, b3, b4, b5, b6, b7, b8; /* 8 bytes */
    int ret;

#ifdef __linux
    if (argc != 9 + 1) {
        fprintf(stderr, "Expecting 9 arguments, but found %d instead.\n", argc);
        fprintf(stderr, "Usage: %s <device_node> <xx> <yy> <zz> <tt> <uu> <vv> <ww> <ll>\n", argv[0]);
        return EXIT_FAILURE;
    }
#elif _WIN32
    if (argc != 8 + 1) {
        fprintf(stderr, "Expecting 8 arguments, but found %d instead.\n", argc);
        fprintf(stderr, "Usage: %s <xx> <yy> <zz> <tt> <uu> <vv> <ww> <ll>\n", argv[0]);
        return EXIT_FAILURE;
    }
#endif

    if (
            sscanf(argv[START + 1], "%x", &b1) != 1 ||
            sscanf(argv[START + 2], "%x", &b2) != 1 ||
            sscanf(argv[START + 3], "%x", &b3) != 1 ||
            sscanf(argv[START + 4], "%x", &b4) != 1 ||

            sscanf(argv[START + 5], "%x", &b5) != 1 ||
            sscanf(argv[START + 6], "%x", &b6) != 1 ||
            sscanf(argv[START + 7], "%x", &b7) != 1 ||
            sscanf(argv[START + 8], "%x", &b8) != 1) {
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

#ifdef __linux
    if ((ret = init_data()) != -1) {

    } else {
        fprintf(stderr, "Failed to initialise the share memory : %d\n", ret);
        return EXIT_FAILURE;
    }
#elif _WIN32
    init_data();
#endif

    if ((ret = init_thread()) >= 0) {
        fprintf(stderr, "The second thread have start\n");
    } else {
        fprintf(stderr, "Failed to start the second thread: %d\n", ret);
        return EXIT_FAILURE;
    }

    fprintf(stdout, "Set the configuration\n");
    vtplayer_set_thread(b1, b2, b3, b4, b5, b6, b7, b8, 1000);

#ifdef __linux
    sleep(10);
#elif _WIN32
    Sleep(10000);
#endif

    stop_thread();

    if ((ret = vtplayer_close()) >= 0) {
        fprintf(stderr, "Device closed.\n");
    } else {
        fprintf(stderr, "Failed to close device: %d.\n", ret);
        return EXIT_FAILURE;
    }

    return (EXIT_SUCCESS);
}

