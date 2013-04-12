/* 
 * File:   libJNI.h
 * Author: godeau
 * 
 * Ne pas modifier ce fichier
 * 
 * Created on January 29, 2013, 7:18 PM
 */

#include <jni.h>
#include "libJNI.h"
#include "libvtplayer-thread.h"

#ifdef __linux

JNIEXPORT jint JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1init_1mouse(JNIEnv *_env, jclass _ignore, jstring _device) {
    //  on transforme la jstring en char *
    const char *str = (*_env)->GetStringUTFChars(_env, _device, 0);
    int ret = vtplayer_open(str);
    // On libére la mémoire
    (*_env)->ReleaseStringUTFChars(_env, _device, str);

    return ret;
}

JNIEXPORT jint JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1init_1thread(JNIEnv *_env, jclass _ignore){
	return init_thread();
}

JNIEXPORT jint JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1init_1data(JNIEnv *_env, jclass _ignore){
	return init_data();
}

JNIEXPORT void JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1set__BBBB(JNIEnv *_env, jclass _ignore, jbyte _b1, jbyte _b2, jbyte _b3, jbyte _b4){
	vtplayer_set_thread(_b1, _b2, _b3, _b4, NULL_BYTE, NULL_BYTE, NULL_BYTE, NULL_BYTE, NOT_SET_CONFIG);
}

JNIEXPORT void JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1set__BBBBBBBBJ(JNIEnv *_env, jclass _ignore, jbyte _b1, jbyte _b2, jbyte _b3, jbyte _b4, jbyte _b5, jbyte _b6, jbyte _b7, jbyte _b8, jlong _ecart){
	vtplayer_set_thread (_b1, _b2, _b3, _b4, _b5, _b6, _b7, _b8, _ecart);
}

JNIEXPORT jint JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1release_1mouse(JNIEnv *_env, jclass _ignore){
	return vtplayer_close();
}

JNIEXPORT void JNICALL Java_vtplayer_linux_VTPlayer_vtplayer_1stop_1thread(JNIEnv *_env, jclass _ignore){
	stop_thread();
}

#elif _WIN32

JNIEXPORT jint JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1init_1mouse(JNIEnv *_env, jclass _ignore) {
    return vtplayer_open();// init_vtplayer();
}

JNIEXPORT void JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1init_1data(JNIEnv *_env, jclass _ignore) {
    init_data();
}

JNIEXPORT jint JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1init_1thread(JNIEnv *_env, jclass _ignore) {
    return init_thread();
}

JNIEXPORT void JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1set__BBBB(JNIEnv *_env, jclass _ignore, jbyte _b1, jbyte _b2, jbyte _b3, jbyte _b4) {
    vtplayer_set_thread(_b1, _b2, _b3, _b4, NULL_BYTE, NULL_BYTE, NULL_BYTE, NULL_BYTE, NOT_SET_CONFIG);
}

JNIEXPORT void JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1set__BBBBBBBBJ(JNIEnv *_env, jclass _ignore, jbyte _b1, jbyte _b2, jbyte _b3, jbyte _b4, jbyte _b5, jbyte _b6, jbyte _b7, jbyte _b8, jlong _ecart) {
    vtplayer_set_thread(_b1, _b2, _b3, _b4, _b5, _b6, _b7, _b8, _ecart);
}

JNIEXPORT jint JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1release_1mouse(JNIEnv *_env, jclass _ignore) {
    return vtplayer_close();//release_vtplayer();
}

JNIEXPORT void JNICALL Java_vtplayer_windows_VTPlayer_vtplayer_1stop_1thread(JNIEnv *_env, jclass _ignore) {
    stop_thread();
}
#endif
