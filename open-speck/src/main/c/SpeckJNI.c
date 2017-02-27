/*
 * (C) Copyright ${year} Machine-to-Machine Intelligence (M2Mi) Corporation, all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Julien Niset
 *     Louis-Philippe Lamoureux
 *     William Bathurst
 *     Peter Havart-Simkin
 *     Geoff Barnard
 *     Andrew Whaley
 */
#include <jni.h>
#include <stdio.h>
#include <string.h>
#include "include/speck.h"
#include "include/SpeckJNI.h"

/*
 * Key expansion for 32bit block size, 64bit key
*/
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_expandKey32_164
  (JNIEnv *env, jobject obj, jbyteArray key)
  {
    int SIZE = sizeof(uint16_t) * 22;

    if((*env)->GetArrayLength(env, key) != 8) {
      return NULL;
    }

    jbyte * keyPtr = (*env)->GetByteArrayElements(env, key, NULL);
    jbyte * expandedPtr = (jbyte *)speck_expand_key_32_64(*keyPtr);
    jbyteArray result = (*env)->NewByteArray(env, SIZE);
    (*env)->SetByteArrayRegion(env, result, 0, SIZE, expandedPtr);
    (*env)->ReleaseByteArrayElements(env, key, keyPtr, 0);

    free(expandedPtr);

    return result;
  }

/*
 * Key expansion for 64bit block size, 128bit key
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_expandKey64_1128
  (JNIEnv *env, jobject obj, jbyteArray key1, jbyteArray key2)
  {
    int SIZE = sizeof(uint32_t) * 27;

    if((*env)->GetArrayLength(env, key1) != 8 && (*env)->GetArrayLength(env, key2) != 8) {
      return NULL;
    }

    jbyte * key1Ptr = (*env)->GetByteArrayElements(env, key1, NULL);
    jbyte * key2Ptr = (*env)->GetByteArrayElements(env, key2, NULL);
    jbyte * expandedPtr = (jbyte *)speck_expand_key_64_128(*key1Ptr, *key2Ptr);
    jbyteArray result = (*env)->NewByteArray(env, SIZE);
    (*env)->SetByteArrayRegion(env, result, 0, SIZE, expandedPtr);
    (*env)->ReleaseByteArrayElements(env, key1, key1Ptr, 0);
    (*env)->ReleaseByteArrayElements(env, key2, key2Ptr, 0);

    free(expandedPtr);

    return result;
  }

/*
 * Key expansion for 128bit block size, 256bit key
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_expandKey128_1256
  (JNIEnv *env, jobject obj, jbyteArray key1, jbyteArray key2, jbyteArray key3, jbyteArray key4)
  {
    int SIZE = sizeof(uint64_t) * 34;

    if((*env)->GetArrayLength(env, key1) != 8 || (*env)->GetArrayLength(env, key2) != 8 ||
      (*env)->GetArrayLength(env, key3) != 8 || (*env)->GetArrayLength(env, key4) != 8) {
      return NULL;
    }

    jbyte * key1Ptr = (*env)->GetByteArrayElements(env, key1, NULL);
    jbyte * key2Ptr = (*env)->GetByteArrayElements(env, key2, NULL);
    jbyte * key3Ptr = (*env)->GetByteArrayElements(env, key3, NULL);
    jbyte * key4Ptr = (*env)->GetByteArrayElements(env, key4, NULL);
    jbyte * expandedPtr = (jbyte *)speck_expand_key_128_256(*key1Ptr, *key2Ptr, *key3Ptr, *key4Ptr);
    jbyteArray result = (*env)->NewByteArray(env, SIZE);
    (*env)->SetByteArrayRegion(env, result, 0, SIZE, expandedPtr);
    (*env)->ReleaseByteArrayElements(env, key1, key1Ptr, 0);
    (*env)->ReleaseByteArrayElements(env, key2, key2Ptr, 0);
    (*env)->ReleaseByteArrayElements(env, key3, key3Ptr, 0);
    (*env)->ReleaseByteArrayElements(env, key4, key4Ptr, 0);

    free(expandedPtr);

    return result;
  }

/*
 * Class:     com_m2mi_speck_SpeckJNI
 * Method:    encryptCBC64_128
 * Signature: ([B[B[B[B[B)I
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_encryptCBC64_1128
  (JNIEnv *env, jobject obj, jbyteArray key1, jbyteArray key2, jbyteArray iv, jbyteArray plaintext)
  {

     jint length = (*env)->GetArrayLength(env, plaintext);

     jbyte key1_buf[8];
     jbyte key2_buf[8];
     jbyte iv_buf[8];
     jbyte plain_buf[length];

     (*env) -> GetByteArrayRegion(env,key1,0,8,key1_buf);
     (*env) -> GetByteArrayRegion(env,key2,0,8,key2_buf);
     (*env) -> GetByteArrayRegion(env,iv,0,8,iv_buf);
     (*env) -> GetByteArrayRegion(env,plaintext,0,length,plain_buf);

      int block_size = sizeof(uint64_t);
      size_t blocks = (length + block_size) / block_size;
      void *ct = malloc(blocks * block_size);

      size_t size = speck_64_128_cbc_encrypt(*((uint64_t *)&key1_buf), *((uint64_t *)&key2_buf), *((uint64_t *)&iv_buf), &plain_buf, ct, length);

      jbyteArray ciphertext = (*env)->NewByteArray(env, size);
      (*env)->SetByteArrayRegion(env,ciphertext,0,size,ct);

      free(ct);

      return ciphertext;
  }

/*
 * Class:     com_m2mi_speck_SpeckJNI
 * Method:    decryptCBC64_128
 * Signature: ([B[B[B[B[B)I
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_decryptCBC64_1128
  (JNIEnv *env, jobject obj, jbyteArray key1, jbyteArray key2, jbyteArray iv, jbyteArray ciphertext)
  {
     jint length = (*env)->GetArrayLength(env, ciphertext);

     jbyte key1_buf[8];
     jbyte key2_buf[8];
     jbyte iv_buf[8];
     jbyte cipher_buf[length];

     (*env) -> GetByteArrayRegion(env,key1,0,8,key1_buf);
     (*env) -> GetByteArrayRegion(env,key2,0,8,key2_buf);
     (*env) -> GetByteArrayRegion(env,iv,0,8,iv_buf);
     (*env) -> GetByteArrayRegion(env,ciphertext,0,length,cipher_buf);

      void *pt = malloc(length);

      size_t size = speck_64_128_cbc_decrypt(*((uint64_t *)&key1_buf), *((uint64_t *)&key2_buf), *((uint64_t *)&iv_buf), &cipher_buf, pt, length);

      jbyteArray plaintext = (*env)->NewByteArray(env, size);
      (*env)->SetByteArrayRegion(env,plaintext,0,size,pt);

      free(pt);

     return plaintext;
  }

  /*
 * Class:     com_m2mi_speck_SpeckJNI
 * Method:    encryptCBC128_256
 * Signature: ([B[B[B[B[B[B[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_encryptCBC128_1256
  (JNIEnv *env, jobject obj, jbyteArray key1, jbyteArray key2, jbyteArray key3, jbyteArray key4, jbyteArray iv1, jbyteArray iv2, jbyteArray plaintext)
  {
     jint length = (*env)->GetArrayLength(env, plaintext);

     jbyte key1_buf[8];
     jbyte key2_buf[8];
     jbyte key3_buf[8];
     jbyte key4_buf[8];
     jbyte iv1_buf[8];
     jbyte iv2_buf[8];
     jbyte plain_buf[length];

     (*env) -> GetByteArrayRegion(env,key1,0,8,key1_buf);
     (*env) -> GetByteArrayRegion(env,key2,0,8,key2_buf);
     (*env) -> GetByteArrayRegion(env,key3,0,8,key3_buf);
     (*env) -> GetByteArrayRegion(env,key4,0,8,key4_buf);
     (*env) -> GetByteArrayRegion(env,iv1,0,8,iv1_buf);
     (*env) -> GetByteArrayRegion(env,iv2,0,8,iv2_buf);
     (*env) -> GetByteArrayRegion(env,plaintext,0,length,plain_buf);

      int block_size = sizeof(uint64_t) * 2;
      size_t blocks = (length + block_size) / block_size;
      void *ct = malloc(blocks * block_size);

      size_t size = speck_128_256_cbc_encrypt(*((uint64_t *)&key1_buf), *((uint64_t *)&key2_buf), *((uint64_t *)&key3_buf), *((uint64_t *)&key4_buf), *((uint64_t *)&iv1_buf), *((uint64_t *)&iv2_buf), &plain_buf, ct, length);

      jbyteArray ciphertext = (*env)->NewByteArray(env, size);
      (*env)->SetByteArrayRegion(env,ciphertext,0,size,ct);

      free(ct);

      return ciphertext;
  }

/*
 * Class:     com_m2mi_speck_SpeckJNI
 * Method:    decryptCBC128_256
 * Signature: ([B[B[B[B[B[B[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_m2mi_speck_jni_SpeckJNI_decryptCBC128_1256
  (JNIEnv *env, jobject obj, jbyteArray key1, jbyteArray key2, jbyteArray key3, jbyteArray key4, jbyteArray iv1, jbyteArray iv2, jbyteArray ciphertext)
  {
     jint length = (*env)->GetArrayLength(env, ciphertext);

     jbyte key1_buf[8];
     jbyte key2_buf[8];
     jbyte key3_buf[8];
     jbyte key4_buf[8];
     jbyte iv1_buf[8];
     jbyte iv2_buf[8];
     jbyte cipher_buf[length];

     (*env) -> GetByteArrayRegion(env,key1,0,8,key1_buf);
     (*env) -> GetByteArrayRegion(env,key2,0,8,key2_buf);
     (*env) -> GetByteArrayRegion(env,key3,0,8,key3_buf);
     (*env) -> GetByteArrayRegion(env,key4,0,8,key4_buf);
     (*env) -> GetByteArrayRegion(env,iv1,0,8,iv1_buf);
     (*env) -> GetByteArrayRegion(env,iv2,0,8,iv2_buf);
     (*env) -> GetByteArrayRegion(env,ciphertext,0,length,cipher_buf);

     int block_size = sizeof(uint64_t) * 2;
     size_t blocks = (length + block_size) / block_size;
     void *pt = malloc(blocks * block_size);

     size_t size = speck_128_256_cbc_decrypt(*((uint64_t *)&key1_buf), *((uint64_t *)&key2_buf), *((uint64_t *)&key3_buf), *((uint64_t *)&key4_buf), *((uint64_t *)&iv1_buf), *((uint64_t *)&iv2_buf), &cipher_buf, pt, length);

     jbyteArray plaintext = (*env)->NewByteArray(env, size);
     (*env)->SetByteArrayRegion(env,plaintext,0,size,pt);

     free(pt);

     return plaintext;
  }
