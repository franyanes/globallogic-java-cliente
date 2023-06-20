package com.fyanes.bci.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.fyanes.bci.exceptions.PasswordEncryptionErrorException;

public class PasswordEncryption {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1234567890123456"; // We will use this insecure method for simplicity.
//  private static final String KEY = System.getenv("SECRET_KEY"); // This should be the correct way for security reasons.

    public static String encrypt(String password) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new PasswordEncryptionErrorException("An error occurred while trying to encrypt the password");
        }
    }

    public static String decrypt(String encryptedPassword) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new PasswordEncryptionErrorException("An error occurred while trying to decrypt the password");
        }
    }
}
