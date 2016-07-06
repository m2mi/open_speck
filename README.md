# open-speck

Open source implementation of the Speck block cipher. The library contains both a native C implementation as well as Java classes that leverage the C implementation through JNI.

A small client is provided that publishes some basic API to validate and test the performances of the library.

Speck is a family of lightweight block ciphers publicly released by the National Security Agency (NSA) in June 2013. Speck has been optimized for performance in software implementations, while its sister algorithm, Simon, has been optimized for hardware implementations. Speck is an add-rotate-xor (ARX) cipher.

See https://eprint.iacr.org/2013/404.pdf for the original Simon & Speck paper.

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

1. Compile the native C code using the Makefile.
2. Generate the open-speck and open-speck-client JARs using Maven.

## Contribute

Contact us at info@m2mi.com.

## License

Licensed under the Apache License, Version 2.0 (the "License"). You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

