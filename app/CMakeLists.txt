cmake_minimum_required(VERSION 3.6)

set(BUILD_SHARED_LIBS OFF
    CACHE BOOL "Build shared libraries" FORCE)
set(CMAKE_CXX_FLAGS "-std=c++17 ${CMAKE_CXX_FLAGS}")

# Other required build files
add_subdirectory(src/main/cpp/fost-android-ndk)
add_subdirectory(src/main/cpp/fost-android-java)

add_library(native-lib SHARED src/main/cpp/native-lib.cpp)

target_link_libraries(
        native-lib
        fost-test
        -Wl,--whole-archive
            fost-android-java
            beanbag-smoke
            beanbag-views-smoke
            fost-core-smoke
            fost-crypto-smoke
            fost-datetime-smoke
            fost-inet-smoke
            fost-jsondb-smoke
            fost-s3-smoke
            fost-sinks-smoke
            fost-urlhandler-smoke
        -Wl,--no-whole-archive
    )
