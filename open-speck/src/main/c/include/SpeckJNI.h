/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_m2mi_speck_jni_SpeckJNI */

#ifndef _Included_com_m2mi_speck_jni_SpeckJNI
#define _Included_com_m2mi_speck_jni_SpeckJNI
#ifdef __cplusplus
extern "C" {
#endif
#undef com_m2mi_speck_jni_SpeckJNI_PROD
#define com_m2mi_speck_jni_SpeckJNI_PROD 0L
/*
 * Class:     com_m2mi_speck_jni_SpeckJNI
 * Method:    expandKey32_64
 * Signature: ([B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_expandKey32_164
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_m2mi_speck_jni_SpeckJNI
 * Method:    expandKey64_128
 * Signature: ([B[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_expandKey64_1128
  (JNIEnv *, jobject, jbyteArray, jbyteArray);

/*
 * Class:     com_m2mi_speck_jni_SpeckJNI
 * Method:    expandKey128_256
 * Signature: ([B[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_expandKey128_1256
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     com_m2mi_speck_jni_SpeckJNI
 * Method:    encryptCBC64_128
 * Signature: ([B[B[B[B)B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_encryptCBC64_1128
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     com_m2mi_speck_jni_SpeckJNI
 * Method:    decryptCBC64_128
 * Signature: ([B[B[B[B)B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_decryptCBC64_1128
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     com_m2mi_speck_jni_SpeckJNI
 * Method:    encryptCBC128_256
 * Signature: ([B[B[B[B[B[B[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_encryptCBC128_1256
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jbyteArray, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     com_m2mi_speck_jni_SpeckJNI
 * Method:    decryptCBC128_256
 * Signature: ([B[B[B[B[B[B[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_decryptCBC128_1256
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jbyteArray, jbyteArray, jbyteArray, jbyteArray);

#ifdef __cplusplus
}
#endif
#endif
