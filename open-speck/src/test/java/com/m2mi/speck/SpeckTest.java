package com.m2mi.speck;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.m2mi.speck.jni.SpeckJNI;
import com.m2mi.speck.jni.SpeckTesterJNI;

public class SpeckTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test 
	public void testExpandKey32_64() {

		byte[] key = new byte[8];
		Arrays.fill(key, (byte)0);
		
		SpeckJNI speck = new SpeckJNI();
		byte [] result = speck.expandKey32_64(key); 
		System.out.println(String.format("result is %d bytes long.", result.length));

		Assert.assertTrue(result.length > 0);
		
	}
	
	@Test 
	public void testEncryptionDecryptionOfString() {
		
		byte[] pt, ct;
		
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		byte[] iv = new byte[8];
		Arrays.fill(key1, (byte)0);
		Arrays.fill(key2, (byte)0);
		Arrays.fill(iv, (byte)0);
		
		String plaintext = "This Speck cipher is really fast!!!";
		System.out.println("plain text: " + plaintext);

		SpeckJNI speck = new SpeckJNI();
		
		try {
			pt = plaintext.getBytes("UTF-8");
			// encryption
			ct = speck.encryptCBC64_128(key1, key2, iv, pt);
			System.out.println("encrypted text: " + new String(ct,"UTF-8"));
			
			// decryption
			pt = speck.decryptCBC64_128(key1, key2, iv, ct);
			System.out.println("decrypted text: " + new String(pt,"UTF-8"));
			
			Assert.assertTrue(plaintext.equals(new String(pt,"UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testCSpeed() {
		SpeckTesterJNI tester = new SpeckTesterJNI();
		long[] result = tester.testCBCSpeed(128, 64, 128);
		System.out.println("encryption time 128: " + result[0]);
		System.out.println("decryption time 128: " + result[1]);
		
		result = tester.testCBCSpeed(128, 128, 256);
		System.out.println("encryption time 256: " + result[0]);
		System.out.println("decryption time 256: " + result[1]);
		
		Assert.assertTrue(result[0] > 0);
	}

}
