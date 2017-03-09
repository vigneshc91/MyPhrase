package com.phrase.my.myphrase;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.Cipher;
import javax.crypto.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by vignesh on 7/3/17.
 */

public class Encryption {

    public static SecretKey generateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String key = AppConstants.ENCRYPTION_SECRET_KEY + password;
        SecretKey secret = new SecretKeySpec(key.getBytes(), "AES");
        return secret;
    }

    public static String encryptPassword(String password, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeySpecException {
        /* Encrypt the message. */
        SecretKey secretKey = generateKey(key);
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = cipher.doFinal(password.getBytes("UTF-8"));
        return cipherText.toString();
    }

    public static String decryptPassword(String cipherPassword, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        SecretKey secretKey = generateKey(key);
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] cipherBytes = cipherPassword.getBytes("UTF-8");
        String decryptString = new String(cipher.doFinal(cipherBytes), "UTF-8");
        return decryptString;
    }
}
