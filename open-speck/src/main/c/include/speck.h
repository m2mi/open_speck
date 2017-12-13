//
//  speck.h
//  Lightweight Encryption
//
//  Created by Andrew Whaley
//

#ifndef speck_h
#define speck_h

#include <stdlib.h>
#include <stdint.h>

// Key Expansion Functions - remember to free the expanded key pointer once finished
// 32 bit block size, 64 bit key size
uint16_t * speck_expand_key_32_64(uint64_t key);

// 64 bit block size, 128 bit key size
uint32_t * speck_expand_key_64_128(uint64_t k1, uint64_t k2);

// 128 bit block size, 256 bit key size (only practical on 64-bit hardware)
uint64_t * speck_expand_key_128_256(uint64_t k1, uint64_t k2, uint64_t k3, uint64_t k4);

// Encryption / Decryption Functions
// Best option on 16-bit machines but note that the key size is too small
uint32_t speck_encrypt_32_64(uint16_t * k, uint32_t plaintext);
uint32_t speck_decrypt_32_64(uint16_t * k, uint32_t ciphertext);

// Best option on 32-bit machines, key size is 128bit and considered secure
uint64_t speck_encrypt_64_128(uint32_t * k, uint64_t plaintext);
uint64_t speck_decrypt_64_128(uint32_t * k, uint64_t ciphertext);

// Best option on 64-bit machines, key size is 256bit
// For 128 bit blocks the uint64_t * should point to two values.
int speck_encrypt_128_256(uint64_t * k, uint64_t * plaintext, uint64_t * ciphertext);
int speck_decrypt_128_256(uint64_t * k, uint64_t * ciphertext, uint64_t * plaintext);


// CBC Mode with PKCS7 padding for bulk encryption
size_t speck_64_128_cbc_encrypt(uint64_t k1, uint64_t k2, uint64_t iv, void * plaintext, void * ciphertext, size_t length);
size_t speck_64_128_cbc_decrypt(uint64_t k1, uint64_t k2, uint64_t iv, void * ciphertext, void * plaintext, size_t length);

size_t speck_128_256_cbc_encrypt(uint64_t k1, uint64_t k2, uint64_t k3, uint64_t k4, uint64_t iv1, uint64_t iv2, void * plaintext, void * ciphertext, size_t length);
size_t speck_128_256_cbc_decrypt(uint64_t k1, uint64_t k2, uint64_t k3, uint64_t k4, uint64_t iv1, uint64_t iv2, void * ciphertext, void * plaintext, size_t length);

#endif /* speck_h */
