package com.phrase.my.myphrase;

import com.scottyab.aescrypt.AESCrypt;

import java.security.*;


/**
 * Created by vignesh on 7/3/17.
 */

public class Encryption {

    public static String getSecretKey(String password)
    {
        String key = AppConstants.ENCRYPTION_SECRET_KEY + password;
        return key;
    }

    public static String encryptPassword(String password, String key) throws GeneralSecurityException {
        /* Encrypt the message. */
        String secretKey = getSecretKey(key);
        String encryptedString = AESCrypt.encrypt(secretKey, password);
        return encryptedString;
    }

    public static String decryptPassword(String cipherPassword, String key) throws GeneralSecurityException {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        String secretKey = getSecretKey(key);
        String decryptedString = AESCrypt.decrypt(secretKey, cipherPassword);
        return decryptedString;
    }
}
