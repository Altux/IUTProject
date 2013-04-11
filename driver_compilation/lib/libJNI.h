/* 
 * File:   libJNI.h
 * Author: godeau
 * 
 * Ne pas modifier ce fichier
 * 
 * Created on January 29, 2013, 7:18 PM
 */
#include <jni.h>

#ifndef LIBJNI_H
#define	LIBJNI_H

#ifdef	__cplusplus
extern "C" {
#endif

#ifdef __linux

/*
 * Class:     vtplayer_linux_VTPlayer
 * Method:    vtplayer_init_mouse
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1init_1mouse(JNIEnv *, jclass, jstring);

/*
 * Class:     vtplayer_linux_VTPlayer
 * Method:    vtplayer_init_thread
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1init_1thread(JNIEnv *, jclass);

/*
 * Class:     vtplayer_linux_VTPlayer
 * Method:    vtplayer_init_data
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1init_1data(JNIEnv *, jclass);

/*
 * Class:     vtplayer_linux_VTPlayer
 * Method:    vtplayer_set
 * Signature: (BBBB)V
 */
JNIEXPORT void JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1set__BBBB(JNIEnv *, jclass, jbyte, jbyte, jbyte, jbyte);

/*
 * Class:     vtplayer_linux_VTPlayer
 * Method:    vtplayer_set
 * Signature: (BBBBBBBBJ)V
 */
JNIEXPORT void JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1set__BBBBBBBBJ(JNIEnv *, jclass, jbyte, jbyte, jbyte, jbyte, jbyte, jbyte, jbyte, jbyte, jint);

/*
 * Class:     vtplayer_linux_VTPlayer
 * Method:    vtplayer_release_mouse
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1release_1mouse(JNIEnv *, jclass);

/*
 * Class:     vtplayer_linux_VTPlayer
 * Method:    vtplayer_stop_thread
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1stop_1thread(JNIEnv *, jclass);
	
#elif _WIN32
    /*
     * Class:     vtplayer_windows_VTPlayer
     * Method:    vtplayer_init_mouse
     * Signature: ()I
     */
    JNIEXPORT jint JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1init_1mouse(JNIEnv *, jclass);

    /*
     * Class:     vtplayer_windows_VTPlayer
     * Method:    vtplayer_init_thread
     * Signature: ()I
     */
    JNIEXPORT jint JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1init_1thread(JNIEnv *, jclass);

    /*
     * Class:     vtplayer_windows_VTPlayer
     * Method:    vtplayer_init_data
     * Signature: ()V
     */
    JNIEXPORT void JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1init_1data(JNIEnv *, jclass);

    /*
     * Class:     vtplayer_windows_VTPlayer
     * Method:    vtplayer_set
     * Signature: (BBBB)I
     */
    JNIEXPORT void JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1set__BBBB(JNIEnv *, jclass, jbyte, jbyte, jbyte, jbyte);

    /*
     * Class:     vtplayer_windows_VTPlayer
     * Method:    vtplayer_set
     * Signature: (BBBBBBBBJ)I
     */
    JNIEXPORT void JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1set__BBBBBBBBJ(JNIEnv *, jclass, jbyte, jbyte, jbyte, jbyte, jbyte, jbyte, jbyte, jbyte, jint);

    /*
     * Class:     vtplayer_windows_VTPlayer
     * Method:    vtplayer_release_mouse
     * Signature: ()I
     */
    JNIEXPORT jint JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1release_1mouse(JNIEnv *, jclass);

    /*
     * Class:     vtplayer_windows_VTPlayer
     * Method:    vtplayer_stop_thread
     */
    JNIEXPORT void JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1stop_1thread(JNIEnv *, jclass);
#else
#error unknown operating systems
#endif

#ifdef	__cplusplus
}
#endif

#endif	/* LIBJNI_H */

