package com.udacity.jwdnd.course1.cloudstorage.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Service for hashing passwords using PBKDF2 with HMAC SHA-1.
 */
@Slf4j
@Component
public class HashService {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 5000;
    private static final int KEY_LENGTH = 128;

    /**
     * Generates a hashed value for the given data and salt.
     *
     * @param data the data to hash
     * @param salt the salt to use for hashing
     * @return Base64-encoded hashed value
     */
    public String getHashedValue(final String data, final String salt) {
        byte[] hashedValue = null;

        final KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
        try {
            final var factory = SecretKeyFactory.getInstance(ALGORITHM);
            hashedValue = factory.generateSecret(spec).getEncoded();
        } catch (final InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("Error hashing value: {}", e.getMessage(), e);
        }

        return Base64.getEncoder().encodeToString(hashedValue);
    }
}
