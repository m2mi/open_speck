# IoT Security Suite | open-speck

The IoT Security Suite, commissioned by the Department of Homeland Security (DHS) Science and Technology Directorate (S&T) under the Silicon Valley Innovation Program (SVIP), is developed by  Machine-to-Machine Intelligence  (M2Mi) Corporation and available as open source under the Apache License. The IoT Security Suite provides an implementation of fast encryption ciphers that use minimal resources capable of operating in small IoT devices and sensors that have limited or constrained capabilities. In order to meet this requirement, the IoT Security Suite uses lightweight block ciphers publicly released by the National Security Agency (NSA) in June 2013 which are part of the SIMON & SPECK family of ciphers designed for both hardware and software implementations.

Speck has been optimized for performance in software implementations, while its sister algorithm, Simon, has been optimized for hardware implementations. Speck is an add-rotate-xor (ARX) cipher.

This open source project provides an implementation of the Speck block cipher to be used by IoT developers.

See https://eprint.iacr.org/2013/404.pdf for the original Simon & Speck paper.

The project provides:

1. A native C implementation of the Speck cipher.
2. A Java public API for the Speck cipher that leverages the C implementation through JNI.
3. A small Java client (Lightweight Speck Client) listening on port 9000 that can be used to test and manage the Java Speck API.
4. A GUI to test the performances of the Java Speck API. 

You can see a video of the test GUI running on one of M2Mi's servers here:  https://youtu.be/MIapwQ2GBYw 

The Speck ciphers can be used directly from C or can be called from C++ or you can use the Java Lightweight Speck Client.  

## Code Example using C, C++ and Python  

The Speck code is written in C and exposed as a dynamically linked library libspeck.so. This library can be linked to dynamically by C and C++ code by using the LD_LIBRARY_PATH to indicate the libraries location. For example:

export LD_LIBRARY_PATH=/home/dev/open-speck/open-speck/src/main/c

where the directory /home/dev/open-speck/open-speck/src/main/c contains the file libspeck.so.

The library has a number of functions that can be called to perform encryption and decryption. For example:

size_t speck_64_128_cbc_encrypt(uint64_t k1, uint64_t k2, uint64_t iv, void * plaintext, void * ciphertext, size_t length)

size_t speck_64_128_cbc_decrypt(uint64_t k1, uint64_t k2, uint64_t iv, void * ciphertext, void * plaintext, size_t length)

These functions would be called from programs to use the Speck code. If you are not using C, they can still be called from other languages. For example:

Python: https://docs.python.org/2/extending/extending.html
C++: http://www.thegeekstuff.com/2013/01/mix-c-and-cpp/


## Code Example Using the Java Lightweight Speck Client

```Java
byte[] key, iv, plaintext, ciphertext;

// Initialize the cipher
SpeckCipher speck = Speck.getInstance(mode, blockSize, keySize).init(key, iv);
// Encryption
ciphertext = speck.encrypt(plaintext);
// Decryption
plaintext = speck.decrypt(ciphertext);
```

## Installation Using the Java Lightweight Speck Client

This project relies on having Maven, Java, C and Make available for compiling and building the code in the project.

1. Check out the code from github. For example:

   git clone https://github.com/team-m2mi/open-speck.git

   After downloading the code, change directory into the downloaded project.

2. Set the JAVA_HOME environment variable to point to your JAVA JDK/JRE directory. For example:

   export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_73
   
   In this example the JAVA jdk is installed in the /usr/lib/jvm/jdk1.8.0_73 directory. Yours likely will be in a different location.

3. Generate the native C library using the Makefile. The makefile can be found in open-speck/src/main/c directory.

   cd open-speck/src/main/c
   make
   
   Afterwards set the LD_LIBRARY_PATH to point to the newly created runtime library. This variable is used to indicate where runtime libraries can be found that are not part of the operating system. For example:
   
   export LD_LIBRARY_PATH=/home/wbathurs/open-speck/open-speck/src/main/c
   
4. Generate the open-speck and open-speck-client JARs using Maven. Move back up to the top level directory of open-speck and issue the package command:

   mvn package
5. Run the open-speck-client JAR and set the java.library.path to the directory containing the native library.

   java -Djava.library.path=$LD_LIBRARY_PATH -jar ./open-speck-client/target/open_speck-client-1.0.SNAPSHOT.jar 
   
6. Load the test UI by running the index.html file in a browser.

   The open-speck-ui directory will need to be copied into your web servers content directory. For example, in LINUX this directory is normally /var/www/html (Apache). Then one would copy it over to that directory:
   
   cp -r open-speck-ui /var/www/html
   
   Make sure you have permissions to copy the files to the target directory.

## Contribute

Contact us at info@m2mi.com.

## License

Licensed under the Apache License, Version 2.0 (the "License"). You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

