cmake_minimum_required(VERSION 2.8)
project (VTPlayer)

find_package (JNI REQUIRED)
include_directories (${JNI_INCLUDE_DIRS})

#option (x86 "Forcer la compilation en 32 bits" OFF)


##################################################################
## configuration des dépendance selon le système d'exploitation ##
##################################################################
if(${CMAKE_SYSTEM_NAME} MATCHES "Linux")
	set(VTP_LIB ../driver/vtplayer.h)
	set(VTP_LIB_THREAD mutex.h mutex.c)
else(${CMAKE_SYSTEM_NAME} MATCHES "Linux")
	set(VTP_LIB control_request.h usb.h libusb_dyn.c)
	set(VTP_LIB_THREAD gettimeofday.h gettimeofday.c)
endif(${CMAKE_SYSTEM_NAME} MATCHES "Linux")


##################################################################
############### création des différente librairie ################
##################################################################
add_library (
	_vtplayer
	SHARED
	${VTP_LIB}
	libvtplayer.h
	libvtplayer.c)
	
#----------------------------------------------------------------#

add_library (
	_vtplayer_thread
	SHARED
	${VTP_LIB_THREAD}
	libvtplayer-thread.h
	libvtplayer-thread.c)

target_link_libraries (
	_vtplayer_thread
	_vtplayer)

#----------------------------------------------------------------#

add_library (
	_vtplayer_jni
	SHARED
	libJNI.h
	libJNI.c)

target_link_libraries (
	_vtplayer_jni
	_vtplayer_thread)
	
#----------------------------------------------------------------#

add_library (
	_vtplayer_jni_standalone
	SHARED
	${VTP_LIB}
	libvtplayer.h
	libvtplayer.c

	${VTP_LIB_THREAD}
	libvtplayer-thread.c
	libvtplayer-thread.h)

	#message(${DIR_LIB_INSTALL})
#install (TARGETS _vtplayer _vtplayer_thread _vtplayer_jni _vtplayer_jni_standalone DESTINATION "${DIR_LIB_INSTALL}")

##################################################################
################# création des executables test ##################
##################################################################

add_executable(
	vtp_test
	vtp_test.c)

target_link_libraries(
	vtp_test
	_vtplayer)

#----------------------------------------------------------------#

add_executable(
	vtp_test_thread
	vtp_test_thread.c)

target_link_libraries(
	vtp_test_thread
	_vtplayer_thread)
