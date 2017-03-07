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
        SecretKey secret = new SecretKeySpec(password.getBytes(), "AES");
        return secret;
    }

    public static String encryptPassword(String password, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeySpecException {
        /* Encrypt the message. */
        SecretKey secretKey = generateKey(key);
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = cipher.doFinal(password.getBytes("UTF-8"));
        return new String(cipherText, "UTF-8");
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
