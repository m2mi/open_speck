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
package com.m2mi.speck;

import java.security.InvalidParameterException;

/**
 * Abstract class for a Speck Cipher.
 * <p>
 * Implementations may vary in their block size, key size and mode of operation.
 * 
 * @author Julien Niset
 *
 */
public abstract class SpeckCipher {

	/* The size of the block */
	protected int blockSize;
	/* The size of the key */
	protected int keySize;
	/* The key */
	protected byte[][] key;
	/* The initialization vector */
	protected byte[][] iv;
	
	public SpeckCipher(int blockSize, int keySize) {
		this.blockSize = blockSize;
		this.keySize = keySize;
		this.key = new byte[keySize/8][8];
		this.iv = new byte[blockSize/8][8];
	}
	
	/**
	 * Initializes the instance with a key an IV.
	 * 
	 * @param key
	 * 				- The encryption/decryption key.
	 * @param iv
	 * 				- The initialization vector.
	 * 
	 * @return the current instance.
	 * 
	 * @throws InvalidParameterException if the key or IV size is not valid.
	 */
	public SpeckCipher init(byte[] key, byte[] iv) throws InvalidParameterException {
		return this.setKey(key).setIv(iv);
	}
	
	/**
	 * Sets the encryption/decrytion key.
	 * <p>
	 * The key is split in chunks of 64 bits.
	 * 
	 * @param key
	 * 				- The key.
	 * 
	 * @return the current instance.
	 * 
	 * @throws InvalidParameterException if the size of the provided key is not valid.
	 */
	public SpeckCipher setKey(byte[] key) throws InvalidParameterException {

		if(key.length*8 < this.keySize) {
			throw new InvalidParameterException("Invalid key size.");
		}
		
		int j = 0;
		for(int i = 0; i < this.keySize/8; i+=8) {
			System.arraycopy(key, i, this.key[j], 0, 8);
			j++;
		}
		
		return this;
		
	}
	
	/**
	 * Sets the initialization vector (IV).
	 * <p>
	 * The IV is split in chunks of 64 bits.
	 * 
	 * @param iv
	 * 				- The initialization vector.
	 * 
	 * @return the current instance.
	 * 
	 * @throws InvalidParameterException if the size of the provided IV is not valid.
	 */
	public SpeckCipher setIv(byte[] iv) throws InvalidParameterException {
		
		if(iv.length*8 < this.blockSize) {
			throw new InvalidParameterException("Invalid IV size.");
		}
		
		int j = 0;
		for(int i = 0; i < this.blockSize/8; i+=8) {
			System.arraycopy(iv, i, this.iv[j], 0, 8);
			j++;
		}
		
		return this;
		
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
	public abstract byte[] encrypt(byte[] plaintext) throws IllegalStateException;
	
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
	public abstract byte[] decrypt(byte[] ciphertext) throws IllegalStateException;
}
