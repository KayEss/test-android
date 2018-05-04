cmake_minimum_required(VERSION 3.6)

set(BUILD_SHARED_LIBS OFF
    CACHE BOOL "Build shared libraries" FORCE)

# Other required build files
add_subdirectory(src/main/cpp/fost-android-ndk)

add_library(native-lib SHARED src/main/cpp/native-lib.cpp)

target_link_libraries(
        native-lib

        fost-test
        -Wl,--whole-archive
            fost-core-smoke
            fost-crypto-smoke
        -Wl,--no-whole-archive
    )
