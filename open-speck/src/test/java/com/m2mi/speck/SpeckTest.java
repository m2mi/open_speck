package com.m2mi.speck;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void testSpeck64_128() {
		
		int blockSize = 64;
		int keySize = 128;
		
		String key = "This is a random key that must remain private";
		String iv = "This is a random IV and it should be unique";
		
		try {
			String plaintext = "This Speck cipher is really fast!!!";
			
			SpeckCipher speck = Speck.getInstance(0, blockSize, keySize).init(key.getBytes(), iv.getBytes());
			byte[] ciphertext = speck.encrypt(plaintext.getBytes("UTF-8"));
			String decryptedtext = new String(speck.decrypt(ciphertext),"UTF-8"); 

			Assert.assertTrue(plaintext.equals(decryptedtext));
			
		} catch(Exception e) {
			e.getStackTrace();
			Assert.assertTrue(false);
		}	
	}
	
	@Test 
	public void testSpeck128_256() {
				
		int blockSize = 128;
		int keySize = 256;
		
		String key = "This is a random key that must remain private";
		String iv = "This is a random IV and it should be unique";
		
		try {
			String plaintext = "This Speck cipher is really fast!!!";
			
			SpeckCipher speck = Speck.getInstance(0, blockSize, keySize).init(key.getBytes(), iv.getBytes());
			byte[] ciphertext = speck.encrypt(plaintext.getBytes("UTF-8"));
			String decryptedtext = new String(speck.decrypt(ciphertext),"UTF-8"); 

			Assert.assertTrue(plaintext.equals(decryptedtext));
			
		} catch(Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		
	}
	
	@Test
	public void testUnsupportedConfig() {
		
		int blockSize = 128;
		int keySize = 512;
		
		try {
			@SuppressWarnings("unused")
			SpeckCipher speck = Speck.getInstance(0, blockSize, keySize);
		} catch(NoSuchAlgorithmException n) {
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			Assert.assertTrue(false);
			return;
		}
		Assert.assertTrue(false);
	}

	
}
