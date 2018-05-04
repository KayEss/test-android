#include <jni.h>
#include <fost/test>


namespace {
    const fostlib::setting<bool> c_verbose(
            "native-lib.cpp",
            "Tests", "Display test names", true);
    std::string g_results("No tests have been run yet\nPress the run button...");
}


extern "C" JNIEXPORT jstring
JNICALL
Java_com_felspar_android_fosttester_Tester_stringFromJNI(
        JNIEnv *env, jobject /* this */
) {
    return env->NewStringUTF(g_results.c_str());
}


extern "C" JNIEXPORT jboolean
Java_com_felspar_android_fosttester_Tester_runTests(
        JNIEnv *env, jobject /* this */
) {
    std::stringstream ss;
    try {
        fostlib::test::suite::execute(ss);
        g_results = ss.str();
        return jboolean{true};
    } catch ( std::exception &e ) {
        ss << e.what() << '\n';
        g_results = ss.str();
        return jboolean{false};
    }
}

