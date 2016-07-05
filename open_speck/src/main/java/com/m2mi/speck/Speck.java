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

import java.security.NoSuchAlgorithmException;

import com.m2mi.speck.impl.SpeckCBC128_256;
import com.m2mi.speck.impl.SpeckCBC64_128;

/**
 * Factory class that returns a Speck cipher implementation. 
 * 
 * @author Julien Niset
 *
 */
public class Speck {

	private static final int CBC_PKCS7 = 0;
	
	/**
	 * Factory method to return a SpeckCipher instance.
	 * 
	 * @param mode
	 * 						- The block cipher mode of operation.
	 * @param blockSize
	 * 						- The size of the block.
	 * @param keySize
	 * 						- The size of the key.
	 * 
	 * @throws NoSuchAlgorithmException if the mode, block size or key size is not supported.
	 */
	public static SpeckCipher getInstance(int mode, int blockSize, int keySize) throws NoSuchAlgorithmException {
		
		SpeckCipher cipher = null;
		
		if(mode == Speck.CBC_PKCS7) {
			if(blockSize == 64) {
				if(keySize == 128) {
					cipher = new SpeckCBC64_128(blockSize, keySize);
				}
				else {
					throw new NoSuchAlgorithmException("Unsupported key size.");
				}
			}
			else if(blockSize == 128) {
				if(keySize == 256) {
					cipher = new SpeckCBC128_256(blockSize, keySize);
				}
				else {
					throw new NoSuchAlgorithmException("Unsupported key size.");
				}
			}
			else {
				throw new NoSuchAlgorithmException("Unsupported block size.");
			}
		}
		else {
			throw new NoSuchAlgorithmException("Unsupported mode.");
		}
		
		return cipher;
	}
	
}
