package com.m2mi.speck.jni;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SpeckJNITest {


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

		System.out.println("testExpandKey32_64:");
		
		byte[] key = new byte[8];
		Arrays.fill(key, (byte)0);
		
		SpeckJNI speck = new SpeckJNI();
		byte [] result = speck.expandKey32_64(key); 
		System.out.println(String.format("result is %d bytes long.", result.length));

		Assert.assertTrue(result.length > 0);
	}
	
	@Test 
	public void testExpandKey64_128() {

		System.out.println("testExpandKey64_128:");
		
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		Arrays.fill(key1, (byte)0);
		Arrays.fill(key2, (byte)0);
		
		SpeckJNI speck = new SpeckJNI();
		byte [] result = speck.expandKey64_128(key1, key2); 
		System.out.println(String.format("result is %d bytes long.", result.length));

		Assert.assertTrue(result.length > 0);
	}
	
	@Test 
	public void testExpandKey128_256() {

		System.out.println("testExpandKey128_256:");
		
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		byte[] key3 = new byte[8];
		byte[] key4 = new byte[8];
		Arrays.fill(key1, (byte)0);
		Arrays.fill(key2, (byte)0);
		Arrays.fill(key3, (byte)0);
		Arrays.fill(key4, (byte)0);
		
		SpeckJNI speck = new SpeckJNI();
		byte [] result = speck.expandKey128_256(key1, key2, key3, key4); 
		System.out.println(String.format("result is %d bytes long.", result.length));

		Assert.assertTrue(result.length > 0);
	}
	
	@Test 
	public void testCBC64_128() {
		
		System.out.println("testCBC64_128:");
		
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
	public void testCBC128_256() {
		
		System.out.println("testCBC128_256:");
		
		byte[] pt, ct;
		
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		byte[] key3 = new byte[8];
		byte[] key4 = new byte[8];
		byte[] iv1 = new byte[8];
		byte[] iv2 = new byte[8];
		Arrays.fill(key1, (byte)0);
		Arrays.fill(key2, (byte)0);
		Arrays.fill(key3, (byte)0);
		Arrays.fill(key4, (byte)0);
		Arrays.fill(iv1, (byte)0);
		Arrays.fill(iv2, (byte)0);
		
		String plaintext = "This Speck cipher is really slow!!!";
		System.out.println("plain text: " + plaintext);

		SpeckJNI speck = new SpeckJNI();
		
		try {
			pt = plaintext.getBytes("UTF-8");
			// encryption
			ct = speck.encryptCBC128_256(key1, key2, key3, key4, iv1, iv2, pt);
			System.out.println("encrypted text: " + new String(ct,"UTF-8"));

			// decryption
			pt = speck.decryptCBC128_256(key1, key2, key3, key4, iv1, iv2, ct);
			System.out.println("decrypted text: " + new String(pt,"UTF-8"));
			
			Assert.assertTrue(plaintext.equals(new String(pt,"UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testCSpeed() {
		
		System.out.println("testCSpeed:");
		
		SpeckTesterJNI tester = new SpeckTesterJNI();
		long[] result = tester.testCBCSpeed(128, 64, 128);
		System.out.println("encryption time 64/128: " + result[0]);
		System.out.println("decryption time 64/128: " + result[1]);
		
		result = tester.testCBCSpeed(128, 128, 256);
		System.out.println("encryption time 128/256: " + result[0]);
		System.out.println("decryption time 128/256: " + result[1]);
		
		Assert.assertTrue(result[0] > 0);
	}
	
}
