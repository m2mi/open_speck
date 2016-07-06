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
 * JNI wrapper to test the performances of the native C implementation of the Speck cipher.
 * 
 * @author Julien Niset
 *
 */
public class SpeckTesterJNI {

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
	 * Test the encryption and decryption speed of the Speck cipher in CBC mode.
	 * 
	 * @param mb
	 * 						- The number of MB to encrypt and decrypt.
	 * @param blockSize
	 * 						- The size of the block.
	 * @param keySize
	 * 						- The size of the key.
	 * 
	 * @return	the number of milliseconds taken to encrypt and decrypt 'mb' MB of data.
	 */
	public native long[] testCBCSpeed(int mb, int blockSize, int keySize);
	
}
