cmake_minimum_required(VERSION 2.8)
project (VTPlayer)

##################################################################
############# création du driver || patch selon l'os #############
##################################################################

if(${CMAKE_SYSTEM_NAME} MATCHES "Linux")
	option(DRIVER "compile module" ON)

	if(${DRIVER})
		set (MODULE_NAME vtplayer)
		set (MODULE_SOURCE_FILES vtplayer.h vtplayer.c)
		set (MODULE_FILE ${MODULE_NAME}.ko)
		execute_process (
			COMMAND uname -r
			OUTPUT_VARIABLE os_release
			OUTPUT_STRIP_TRAILING_WHITESPACE)
		set (KERNEL_DIR "/lib/modules/${os_release}/build")
		set (KBUILD_COMMAND ${CMAKE_MAKE_PROGRAM} -C ${KERNEL_DIR} M=${VTPlayer_BINARY_DIR}/${DRIVER_OUTPUT_PATH} modules)
		file (COPY Kbuild DESTINATION ${DRIVER_OUTPUT_PATH})

		foreach( MODULE_SOURCE_FILE ${MODULE_SOURCE_FILES} )
			file (COPY ${MODULE_SOURCE_FILE} DESTINATION ${DRIVER_OUTPUT_PATH})
		endforeach (MODULE_SOURCE_FILE)

		add_custom_command (
			OUTPUT ${DRIVER_OUTPUT_PATH}/${MODULE_FILE}
			COMMAND ${KBUILD_COMMAND}
			DEPENDS ${MODULE_SOURCE_FILES} Kbuild
			WORKING_DIRECTORY ${DRIVER_OUTPUT_PATH}
			COMMENT "Building vtplayer.ko")

		add_custom_target (
			${MODULE_NAME}
			ALL
			DEPENDS ${DRIVER_OUTPUT_PATH}/${MODULE_FILE})
	endif(${DRIVER})
else(${CMAKE_SYSTEM_NAME} MATCHES "Linux")
	if(NOT ${DEBUG})
		# -mwindows : enleve l'affichage du terminal lors du lancement de l'executable
		SET(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -mwindows")
	endif(NOT ${DEBUG})

	add_executable(
		vtpServer
		vtpServer.c)

	target_link_libraries (
		vtpServer
		_vtplayer)
		
	#install (TARGETS vtpServer DESTINATION "${CMAKE_INSTALL_PREFIX}")

endif(${CMAKE_SYSTEM_NAME} MATCHES "Linux")
