#include <jni.h>
#include <string>

#define TAG "android_jni"

#include <android/log.h>
#include <pthread.h>

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_blood_nativedemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

static int jniRegisterNativeMethods(JNIEnv *env, const char *className,
                                    const JNINativeMethod *gMethods, int numMethods);

static void func1(JNIEnv *env, jclass jclazz, jint arg);

void attachCurrentThread(JavaVM *vm);

static const char *g_className = "com/blood/nativedemo/jni/JniActivity";

static JNINativeMethod g_Methods[] = {
        {"nativeFunc1", "(I)V", (void *) func1},
};

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = nullptr;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return JNI_ERR;
    }

    LOGI(">> JNI_OnLoad JNIEnv %p", env);

    jniRegisterNativeMethods(env, g_className, g_Methods,
                             sizeof(g_Methods) / sizeof(JNINativeMethod));

    attachCurrentThread(vm);

    return JNI_VERSION_1_4;
}

void attachCurrentThread(JavaVM *vm) {
    JNIEnv *env = nullptr;
    char threadName[64];
    snprintf(threadName, sizeof(threadName), "heron_runtime_%ld", pthread_self());
    JavaVMAttachArgs args;
    args.version = JNI_VERSION_1_4;
    args.name = threadName;
    args.group = nullptr;
    vm->AttachCurrentThread(&env, &args);
    LOGI(">> attachCurrentThread JNIEnv %p", env);
}

void JNI_OnUnload(JavaVM *vm, void *reserved) {

}

static int jniRegisterNativeMethods(JNIEnv *env, const char *className,
                                    const JNINativeMethod *gMethods, int numMethods) {
    jclass clazz = env->FindClass(className);
    if (clazz == nullptr) {
        return -1;
    }

    int result = env->RegisterNatives(clazz, gMethods, numMethods);
    if (result != JNI_OK) {
    }

    env->DeleteLocalRef(clazz);
    return result;
}


#define EXTENSION_DECLARE(extensionName)                \
    namespace extension {                               \
    }

namespace {
    EXTENSION_DECLARE(DialogExtension);
}


#define JSAPI_DECLARE "aaa"
#define HE(arg) arg##_DECLARE
#define me(arg) HE(arg) HE(arg)

#define BBB(arg) arg
#define AAA(arg) arg(123)

char *str = nullptr;
char *s = nullptr;

void func1(JNIEnv *env, jclass jclazz, jint arg) {
    LOGI(">> func1 %d", arg);

    s = me(JSAPI);
    LOGI(">> s >>> %s", s);

    int num = AAA(BBB);
}


