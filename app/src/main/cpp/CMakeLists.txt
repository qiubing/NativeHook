# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
#set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})

#set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

set(XHOOK_INCLUDE ${PROJECT_SOURCE_DIR}/../../../../xhook/src/main/cpp/)
include_directories(${XHOOK_INCLUDE})

add_library(xhook SHARED IMPORTED)
set_target_properties(
        xhook
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/../../../../xhook/src/main/jniLibs/${ANDROID_ABI}/libxhook.so
)

add_library(native-hook SHARED nativeHook.cpp)
#include_directories(${CMAKE_CURRENT_LIST_DIR}/src/main/cpp/includes)
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        native-hook
        xhook
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})