/* 
 * File:   libvtplayer-thread.h
 * Author: godeau
 *
 * Created on March 1, 2013, 12:25 PM
 */

#ifndef LIBVTPLAYER_THREAD_H
#define	LIBVTPLAYER_THREAD_H

#define NOT_SET_CONFIG          -1
#define SET_CONFIG              0

#include "libvtplayer.h"

#ifdef	__cplusplus
extern "C" {
#endif

    /**
     * Cette fonction permet d'initialiser le sous-processus.
     * @return une valeur négative en cas d'erreur. 
     */
    int init_thread();

#ifdef __linux
    /**
     * Cette fonction permet d'initialiser les valeurs des picots.
     * @return une valeur négative en cas d'erreur.
     */
    int init_data();
#elif _WIN32
    /**
     * Cette fonction permet d'initialiser les valeurs des picots.
     */
    void init_data();
#endif

    /**
     * Vous n'avez pas la permission d'utiliser cette fonction si vous n'avez pas initialisé le sous-processus.
     * @param vtplayer_handle
     * @param b1_1 ... b4_1
     * 
     * @param b1_2 ... b4_2
     * @param ecart duré total de fonctionnement.
     */
    void vtplayer_set_thread(BYTE b1, BYTE b2, BYTE b3, BYTE b4, BYTE b5, BYTE b6, BYTE b7, BYTE b8, int ecart);

    /**
     * Cette fonction tue le sous-processus.
     */
    void stop_thread();


#ifdef	__cplusplus
}
#endif

#endif	/* LIBVTPLAYER_THREAD_H */

