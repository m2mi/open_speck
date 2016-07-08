# IoT Security Suite | open-speck

The IoT Security Suite, commissioned by the Department of Homeland Security (DHS) Science and Technology Directorate (S&T) under the Silicon Valley Innovation Program (SVIP), is developed by  Machine-to-Machine Intelligence  (M2Mi) Corporation and    available as open source under the Apache License. The IoT Security Suite provides an implementation of fast encryption ciphers that use minimal resources capable of operating in small IoT devices and sensors that have limited or constrained resources. In order to meet this requirement, the  IoT Security Suite uses lightweight block ciphers publicly released by the National Security Agency (NSA) in June 2013 which are part of the SIMON & SPECK family of ciphers designed for both hardware and software implementations.

This open source project provides an implementation of the Speck block cipher to be used by IoT developpers.

Speck is a family of lightweight block ciphers publicly released by the National Security Agency (NSA) in June 2013. Speck has been optimized for performance in software implementations, while its sister algorithm, Simon, has been optimized for hardware implementations. Speck is an add-rotate-xor (ARX) cipher.

See https://eprint.iacr.org/2013/404.pdf for the original Simon & Speck paper.

The project provides:

1. A native C implementation of the Speck cipher.
2. A Java public API for the Speck cipher that leverages the C implementation through JNI.
3. A small Java client listening on port 9000 that can be used to test and manage the Java Speck API remotely.
4. A GUI to test the performances of the Java Speck API. 

## Code Example

```Java
byte[] key, iv, plaintext, ciphertext;

// Initialize the cipher
SpeckCipher speck = Speck.getInstance(mode, blockSize, keySize).init(key, iv);
// Encryption
ciphertext = speck.encrypt(plaintext);
// Decryption
plaintext = speck.decrypt(ciphertext);
```

## Installation

1. Generate the native C library using the Makefile.
2. Generate the open-speck and open-speck-client JARs using Maven.
3. Run the open-speck-client JAR and set the java.library.path to the directory containing the native library.
4. Load the test UI by running the index.html file in a browser.

## Contribute

Contact us at info@m2mi.com.

## License

Licensed under the Apache License, Version 2.0 (the "License"). You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

