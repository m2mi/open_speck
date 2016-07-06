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
package com.m2mi.speck.client.rs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import com.m2mi.speck.Speck;
import com.m2mi.speck.SpeckCipher;
import com.m2mi.speck.jni.SpeckTesterJNI;

/**
 * REST API to test the Speck implementation.
 * 
 * @author Julien Niset
 *
 */
@Path("v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpeckRS {

	@GET
	@Path("hello")
	public Response sayHello(@Context HttpServletRequest hsr) { 
		return Response.status(200).entity("Hello Speck!").build();
	}
	
	/** Tests the encryption and decryption speed on the local machine.
	 * 					
	 * @param blockSize
	 * 					- The block size.
	 * @param keySize
	 * 					- The key size.
	 * @param mode
	 * 					- The mode of operation
	 * @param mb
	 * 					- The number of MB to encrypt and decrypt.
	 * 
	 * @return the time in milliseconds for the encryption and decryption.
	 */
	@GET
	@Path("{blockSize: [0-9]*}/{keySize: [0-9]*}/test")
	public Response testSpeed(
			@Context HttpServletRequest hsr, 
			@PathParam("blockSize") int blockSize,
			@PathParam("keySize") int keySize,
			@DefaultValue("cbc") @QueryParam("mode") String mode,
			@DefaultValue("1") @QueryParam("mb") int mb) { 
		
		long[] result = null;
		int code = 200;
		Map<String,Object> outcome = new HashMap<String,Object>();
		
		SpeckTesterJNI tester = new SpeckTesterJNI();
		if("cbc".equals(mode.toLowerCase())) {
			result = tester.testCBCSpeed(mb, blockSize, keySize);
			if(result[0] == -1) {
				code = 500;
				outcome.put("success", false);
				outcome.put("error", "Unexpected error.");
			}
			else {
				outcome.put("success", true);
				outcome.put("mb", mb);
				outcome.put("encryption", result[0]);
				outcome.put("decryption", result[1]);
			}
		}
		else {
			code = 400;
			outcome.put("success", false);
			outcome.put("error", "Unknown mode.");
		}
		
		return Response.status(code).entity(outcome).build();
	}
	
	/**
	 * Encrypts the provided plaintext.
	 * 
	 * @param blockSize
	 * 					- The block size.
	 * @param keySize
	 * 					- The key size.
	 * @param mode
	 * 					- The mode of operation
	 * @param key
	 * 					- The secret key.
	 * @param iv
	 * 					- The initialization vector.
	 * @param plaintext
	 * 					- The plaintext to encrypt.
	 * 
	 * @return the ciphertext in hexadecimal representation.
	 */
	@POST
	@Path("{blockSize: [0-9]*}/{keySize: [0-9]*}/encrypted")
	public Response encrypt(
			@Context HttpServletRequest hsr, 
			@PathParam("blockSize") int blockSize,
			@PathParam("keySize") int keySize,
			@DefaultValue("cbc") @QueryParam("mode") String mode,
			@QueryParam("key") String key,
			@QueryParam("iv") String iv,
			String plaintext) { 
		
		int code = 200;
		int m = 0;
		String ciphertext = null;
		
		if("cbc".equals(mode.toLowerCase())) {
			m = 0;
		}
		else {
			return Response.status(400).entity("Unkown mode").build();
		}
		
		try {
			SpeckCipher speck = Speck.getInstance(m, blockSize, keySize).init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));
			ciphertext = byteToHex(speck.encrypt(plaintext.getBytes("UTF-8")));
		} catch (Exception e) {
			code = 400;
			ciphertext = e.getMessage();
		}
		
		return Response.status(code).entity(ciphertext).build();
	}
	
	/**
	 * Decrypts the provided ciphertext.
	 * 
	 * @param blockSize
	 * 					- The block size.
	 * @param keySize
	 * 					- The key size.
	 * @param mode
	 * 					- The mode of operation
	 * @param key
	 * 					- The secret key.
	 * @param iv
	 * 					- The initialization vector.
	 * @param ciphertext
	 * 					- The ciphertext to decrypt in hexadecimal representation.
	 * 
	 * @return the plaintext.
	 */
	@POST
	@Path("{blockSize: [0-9]*}/{keySize: [0-9]*}/decrypted")
	public Response decrypt(
			@Context HttpServletRequest hsr, 
			@PathParam("blockSize") int blockSize,
			@PathParam("keySize") int keySize,
			@DefaultValue("cbc") @QueryParam("mode") String mode,
			@QueryParam("key") String key,
			@QueryParam("iv") String iv,
			String ciphertext) { 
		
		int code = 200;
		int m = 0;
		String plaintext = null;
		
		if("cbc".equals(mode.toLowerCase())) {
			m = 0;
		}
		else {
			return Response.status(400).entity("Unkown mode").build();
		}
		
		try {
			SpeckCipher speck = Speck.getInstance(m, blockSize, keySize).init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));
			plaintext = new String(speck.decrypt(hexToByte(ciphertext)),"UTF-8");
		} catch (Exception e) {
			code = 400;
			plaintext = e.getMessage();
		}
		
		return Response.status(code).entity(plaintext).build();
	}
	
    /**
     * Converts an array of bytes to an hexadecimal String.
     * 
     * @param array
     * 				- The array of bytes.
     * @return
     */
    private String byteToHex(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    /**
     * Converts an hexadecimal String to an array of bytes.
     * 
     * @param s
     * 				- The hexadecimal String.
     * @return
     */
    private byte[] hexToByte(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
