/*
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
 *     Louis-Philippe Lamoureux
 *     William Bathurst
 *     Geoff Barnard
 *     Andrew Whaley    
 */
#include <jni.h>
#include <stdio.h>
#include <string.h>
 #include <sys/time.h>
 #include "include/speck.h"
#include "include/SpeckTesterJNI.h"

long timestamp()
{
    struct timeval  tv;
    gettimeofday(&tv, NULL);
    
    double time_in_mill = (tv.tv_sec) * 1000 + (tv.tv_usec) / 1000 ;
    return (long) time_in_mill;
}

/*
 * Class:     com_m2mi_speck_jni_SpeckTesterJNI
 * Method:    testCBCDecryptionSpeed
 * Signature: (III)I
 */
JNIEXPORT jlongArray JNICALL Java_com_m2mi_speck_jni_SpeckTesterJNI_testCBCSpeed
  (JNIEnv *env, jobject obj, jint mbs, jint blockSize, jint keySize) 
  {

  	long result[2];
  	size_t b;
  	long start, end;

  	size_t plain_text_size = (mbs * 1024 * 1024) + 3;
  	uint8_t * pt_buffer = (uint8_t *) malloc((mbs * 1024 * 1024) + 3);
   	uint8_t * ct_buffer = (uint8_t *) malloc((mbs * 1024 * 1024) + 16);
    
   	// Initialise plain text to 0xdd
   	memset(pt_buffer, 0xdd, plain_text_size);

  	if(blockSize == 64 && keySize == 128) {
    	// Encrypt
    	start = timestamp();
    	b = speck_64_128_cbc_encrypt(0x1b1a191813121110, 0x0b0a090803020100, 0x0, pt_buffer, ct_buffer, plain_text_size);
    	end = timestamp();
    	result[0] = end - start;

    	// Clear plain text
     	memset(pt_buffer, 0x0, plain_text_size);
    
    	// Decrypt
    	start = timestamp();
    	plain_text_size = speck_64_128_cbc_decrypt(0x1b1a191813121110, 0x0b0a090803020100, 0x0, ct_buffer, pt_buffer, b);
    	end = timestamp();
    	result[1] = end - start;
  	}
  	else if(blockSize == 128 && keySize == 256) {
    	// Encrypt
    	start = timestamp();
    	b = speck_128_256_cbc_encrypt(0x1f1e1d1c1b1a1918, 0x1716151413121110, 0x0f0e0d0c0b0a0908, 0x0706050403020100, 0x0, 0x0, pt_buffer, ct_buffer, plain_text_size);
    	end = timestamp();
    	result[0] = end - start;
    
    	// Clear plain text
    	memset(pt_buffer, 0x0, plain_text_size);
    
    	// Decrypt
    	start = timestamp();
    	plain_text_size = speck_128_256_cbc_decrypt(0x1f1e1d1c1b1a1918, 0x1716151413121110, 0x0f0e0d0c0b0a0908, 0x0706050403020100, 0x0, 0x0, ct_buffer, pt_buffer, b);
    	end = timestamp();
    	result[1] = end - start;
  	}
  	else {
  		result[0] = -1;
  		result[1] = -1;
  	}

  	// Check buffer contains original data
    for (size_t i = 0; i<plain_text_size; i++) if (pt_buffer[i] != 0xdd)
    {
        result[0] = -1;
  		result[1] = -1;
    }
    
    free(pt_buffer);

    jlongArray toReturn = (*env)->NewLongArray(env, 2);
    (*env)->SetLongArrayRegion(env, toReturn, 0, 2, result);

    return toReturn;
  }
