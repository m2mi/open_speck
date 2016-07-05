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
package com.m2mi.speck.impl;

import com.m2mi.speck.SpeckCipher;
import com.m2mi.speck.jni.SpeckJNI;

/**
 * Implementation of the Speck cipher with 64 bit block, 128 bit key and CBC mode of operation.
 *  * <p>
 * The encryption and decryption is performed in a native C and accessed through a JNI wrapper.
 * 
 * @author Julien Niset
 *
 */
public class SpeckCBC64_128 extends SpeckCipher {

	/* The JNI wrapper */
	private SpeckJNI jni;
	
	public SpeckCBC64_128(int blockSize, int keySize) {
		super(blockSize,keySize);
		this.jni = new SpeckJNI();
	}
	
	/**
	 * Encrypts data.
	 * 
	 * @param plaintext
	 * 						- The data to encrypt.
	 * 
	 * @return the encrypted data.
	 * 
	 * @throws IllegalStateException if the instance has not been initialized.
	 */
	@Override
	public byte[] encrypt(byte[] plaintext) throws IllegalStateException {
		if(this.key == null || this.iv == null) {
			throw new IllegalStateException("The instance must be initialized with a key and IV.");
		}
		return jni.encryptCBC64_128(this.key[0], this.key[1], this.iv[0], plaintext);
	}

	/**
	 * Decrypts data.
	 * 
	 * @param ciphertext
	 * 						- The data to decrypt.
	 * 
	 * @return the decrypted data.
	 * 
	 * @throws IllegalStateException if the instance has not been initialized.
	 */
	@Override
	public byte[] decrypt(byte[] ciphertext) throws IllegalStateException {
		if(this.key == null || this.iv == null) {
			throw new IllegalStateException("The instance must be initialized with a key and IV.");
		}
		return jni.decryptCBC64_128(this.key[0], this.key[1], this.iv[0], ciphertext);
	}

}
