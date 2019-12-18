project(test-android)
cmake_minimum_required(VERSION 3.10)

set(BUILD_SHARED_LIBS OFF
    CACHE BOOL "Build shared libraries" FORCE)
set(CMAKE_CXX_FLAGS "-std=c++17 ${CMAKE_CXX_FLAGS}")

add_custom_target(check)
add_custom_target(stress)

# Other required build files
add_subdirectory(src/main/cpp/fost-mobile)
add_subdirectory(src/main/cpp/fost-mobile/fost/beanbag)
add_subdirectory(src/main/cpp/fost-mobile/fost/makham)
add_subdirectory(src/main/cpp/fost-mobile/mdca-platform/mdca-common)
add_subdirectory(src/main/cpp/fost-mobile/mdca-platform/mdca-android)
add_subdirectory(src/main/cpp/fost-android-java)

add_library(native-lib SHARED src/main/cpp/native-lib.cpp)

# Extra libraries containing tests we want to use
add_subdirectory(src/main/cpp/fost-mobile/fost/web/Cpp/fost-urlhandler-test)


target_link_libraries(
        native-lib
        fost-test
        mdca-android

        # The linker will want to throw away symbols not used in
        # the actual code. The below are needed explicitly
        # so that the linker retains everything
        -Wl,--whole-archive
            # If any part of the following libraries are needed
            # then they must be loaded in this way here so that
            # all code they contain will be included
            f5-json-schema
            beanbag-views
            fost-android-java
            fost-urlhandler
            mdca-common

            # The following are needed so that all tests are kept
            beanbag-smoke
            beanbag-views-smoke
            f5-makham-test
            fost-core-smoke
            fost-crypto-smoke
            fost-datetime-smoke
            fost-inet-smoke
            fost-jsondb-smoke
            fost-s3-smoke
            fost-sinks-smoke
            fost-urlhandler-smoke
            fost-urlhandler-test

            mdca-common-smoke
        -Wl,--no-whole-archive
    )
