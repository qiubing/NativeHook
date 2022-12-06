cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

aux_source_directory(. DIR_LIB_SOURCE)

#set(src_xhook
#        src/main/cpp/xhook/xh_core.c
#        src/main/cpp/xhook/xh_elf.c
#        src/main/cpp/xhook/xh_jni.c
#        src/main/cpp/xhook/xh_log.c
#        src/main/cpp/xhook/xh_util.c
#        src/main/cpp/xhook/xh_version.c
#        src/main/cpp/xhook/xhook.c
#        )

add_library(xhook SHARED ${DIR_LIB_SOURCE})
include_directories(.)
find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)
target_link_libraries(xhook ${log-lib})