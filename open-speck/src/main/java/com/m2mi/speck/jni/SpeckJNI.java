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
package com.m2mi.speck.jni;

/**
 * JNI wrapper around the native C implementation of the Speck cipher.
 * 
 * @author Julien Niset
 *
 */
public class SpeckJNI {
	
	private static final boolean PROD = true;
	
	static {
		try {
			if(PROD)
				System.loadLibrary("speck");
			else
				System.load(System.getProperty("user.dir") + "/src/main/c/libspeck.dylib");
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Key expansion for 32bit block size, 64bit key.
	 * 
	 * @param key
	 * 				- The key to expand. Must be 8 bytes.
	 * @return
	 */
	public native byte[] expandKey32_64(byte[] key); 
	
	/**
	 * Key expansion for 64bit block size, 128bit key.
	 * <p>
	 * The key is split in chunks of 64bit.
	 * 
	 * @param key1
	 * 				- The first part of the key (8 bytes).
	 * @param key2
	 * 				- The second part of the key (8 bytes).
	 * @return
	 */
	public native byte[] expandKey64_128(byte[] key1, byte[] key2);
	
	/**
	 * Key expansion for 128bit block size, 256bit key.
	 * <p>
	 * The key is split in chunks of 64bit.
	 * 
	 * @param key1
	 * 				- The first part of the key (8 bytes).
	 * @param key2
	 * 				- The second part of the key (8 bytes).
	 * @param key3
	 * 				- The third part of the key (8 bytes).
	 * @param key4
	 * 				- The fourth part of the key (8 bytes).
	 * 
	 * @return A byte array containing the expanded key.
	 */
	public native byte[] expandKey128_256(byte[] key1, byte[] key2, byte[] key3, byte[] key4);
	
	/**
	 * Encryption for 64bit block size, 128bit key and CBC mode.
	 * <p>
	 * The key is provided as 2 chunks of 64bit and the method starts with key expansion.
	 * 
	 * @param key1
	 * 				- The first part of the key (8 bytes).
	 * @param key2
	 * 				- The second part of the key (8 bytes).
	 * @param iv
	 * 				- The Initialization Vector (8 bytes).
	 * @param plaintext
	 * 				- The plain text to encrypt.
	 * 
	 * @return A byte array containing the resulting cipher text.
	 */
	public native byte[] encryptCBC64_128(byte[] key1, byte[] key2, byte[] iv, byte[] plaintext);
	
	/**
	 * Decryption for 64bit block size, 128bit key and CBC mode.
	 * <p>
	 * The key is provided as 2 chunks of 64bit and the method starts with key expansion.
	 * 
	 * @param key1
	 * 				- The first part of the key (8 bytes).
	 * @param key2
	 * 				- The second part of the key (8 bytes).
	 * @param iv
	 * 				- The Initialization Vector (8 bytes).
	 * @param ciphertext
	 * 				- The cipher text to decrypt.
	 * 
	 * @return A byte array containing the resulting plain text.
	 */
	public native byte[] decryptCBC64_128(byte[] key1, byte[] key2, byte[] iv, byte[] ciphertext);
	
	/**
	 * Encryption for 128bit block size, 256bit key and CBC mode.
	 * <p>
	 * The key is provided as 4 chunks of 64bit and the method starts with key expansion.
	 * <p>
	 * The IV is provided as 2 chunks of 64 bits.
	 * 
	 * @param key1
	 * 				- The first part of the key (8 bytes).
	 * @param key2
	 * 				- The second part of the key (8 bytes).
	 * @param key3
	 * 				- The third part of the key (8 bytes).
	 * @param key4
	 * 				- The fourth part of the key (8 bytes).
	 * @param iv1
	 * 				- The first part of the Initialization Vector (8 bytes).
	 * @param iv2
	 * 				- The second part of the Initialization Vector (8 bytes).
	 * @param plaintext
	 * 				- The plain text to encrypt.
	 * 
	 * @return A byte array containing the resulting cipher text.
	 */
	public native byte[] encryptCBC128_256(byte[] key1, byte[] key2, byte[] key3, byte[] key4, byte[] iv1, byte[] iv2, byte[] plaintext);
	
	/**
	 * Decryption for 128bit block size, 256bit key and CBC mode.
	 * <p>
	 * The key is provided as 4 chunks of 64bit and the method starts with key expansion.
	 * <p>
	 * The IV is provided as 2 chunks of 64 bits.
	 * 
	 * @param key1
	 * 				- The first part of the key (8 bytes).
	 * @param key2
	 * 				- The second part of the key (8 bytes).
	 * @param key3
	 * 				- The third part of the key (8 bytes).
	 * @param key4
	 * 				- The fourth part of the key (8 bytes).
	 * @param iv1
	 * 				- The first part of the Initialization Vector (8 bytes).
	 * @param iv2
	 * 				- The second part of the Initialization Vector (8 bytes).
	 * @param ciphertext
	 * 				- The cipher text to decrypt.
	 * 
	 * @return A byte array containing the resulting plain text.
	 */
	public native byte[] decryptCBC128_256(byte[] key1, byte[] key2, byte[] key3, byte[] key4, byte[] iv1, byte[] iv2, byte[] ciphertext);
	
}
