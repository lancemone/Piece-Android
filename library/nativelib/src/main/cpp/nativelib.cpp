#include <jni.h>
#include <string>
#include <android/log.h>
#include "AES.h"
#include "Base64.h"
#include "randomUtils.h"

#define  LOG_TAG  "nativeLibs"
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_timothy_nativelib_NativeLib_strEncrypt(
        JNIEnv *env,
        jobject contextObject,
        jstring plainText
) {
    LOGE("start Encrypt");
    const std::string sourceStr = std::string(env->GetStringUTFChars(plainText, 0));
    string key = rand_str(BLOCK_SIZE);
    LOGE("key: %s", key.c_str());
    string result;
    AES aes;
    result = aes.Encrypt_CBC_Zeros(key.c_str(), sourceStr);
    LOGE("result: %s", result.c_str());
    string header = rand_str(6);
    LOGE("header: %s", header.c_str());
    string footer = rand_str(8);
    LOGE("footer: %s", footer.c_str());
    header.append(result).append(key).append(footer);
    return env->NewStringUTF(header.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_timothy_nativelib_NativeLib_strDecrypt(
        JNIEnv *env,
        jobject contextObject,
        jstring encryptText
) {
    LOGE("start Decrypt");
    const std::string strSrc = std::string(env->GetStringUTFChars(encryptText, 0));
    char *key = new char [BLOCK_SIZE];
    size_t endSize = BLOCK_SIZE + 8;    // key长度+结尾长度
    size_t startSize = 6;
    size_t length = strSrc.length();
    string keyStr = strSrc.substr(length-24, BLOCK_SIZE);
    memcpy(key, keyStr.c_str(), BLOCK_SIZE);
    LOGE("key: %s", key);
    string encrypt = strSrc.substr(startSize, length-startSize-endSize);
    LOGE("encrypt: %s", encrypt.c_str());
    AES aes;
    string strDest;
    strDest = aes.Decrypt_CBC_Zeros(key, encrypt);
    return env->NewStringUTF(strDest.c_str());
}