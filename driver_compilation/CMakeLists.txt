cmake_minimum_required(VERSION 2.8)
project (VTPlayer)

set (EXECUTABLE_OUTPUT_PATH dist/${CMAKE_BUILD_TYPE})
set (LIBRARY_OUTPUT_PATH dist/${CMAKE_BUILD_TYPE})
set (DRIVER_OUTPUT_PATH dist/${CMAKE_BUILD_TYPE})

option (DEBUG "Afficher les lignes de debugage" OFF)
if(${DEBUG})
	add_definitions (-DDEBUG)
endif(${DEBUG})

if(${CMAKE_SYSTEM_NAME} MATCHES "Linux")
	message(STATUS "Linus system detected")
	
else(${CMAKE_SYSTEM_NAME} MATCHES "Linux")
	message(STATUS "Windows sytem detected")
	#set (DIR_LIB_INSTALL "$ENV{SYSTEMROOT}/System32/vtplayer") #CACHE STRING "directory where copy lib files")
endif(${CMAKE_SYSTEM_NAME} MATCHES "Linux")

add_subdirectory (lib)
add_subdirectory (driver)
