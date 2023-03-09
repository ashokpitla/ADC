/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.management.openmbean.InvalidKeyException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class Encryption {

    public static String encryptText(String plainText, SecretKey secKey) throws java.security.InvalidKeyException {
        String output;
        String CipherText;

        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
            byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
            CipherText = bytesToBase64(byteCipherText);

            output = CipherText.replace("+", "-");
            output = output.replace("/", "_");

        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("encryptText - Error : " + e.toString());
            output = plainText;
        }
        return output;
    }

    public static String decryptText(String CipherText, SecretKey secKey, String code, String ip) throws java.security.InvalidKeyException {
        String output;
        String inputText;
        byte[] byteCipherText;
        try {
            inputText = CipherText.replace("-", "+");
            inputText = inputText.replace("_", "/");
            byteCipherText = Base64ToBytes(inputText);
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secKey);
            byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
            output = new String(bytePlainText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("decryptText - Error [ " + code + " ] : " + e.toString() + " , " + ip);
            output = "";
        }
        return output;
    }

    public static SecretKey getSecretEncryptionKey() {
        SecretKey secKey = null;
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            secKey = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("getSecretEncryptionKey - Error : " + e.toString());
        }
        return secKey;
    }

    private static String bytesToBase64(byte[] hash) {
        return DatatypeConverter.printBase64Binary(hash);
    }

    private static byte[] Base64ToBytes(String hash) {
        return DatatypeConverter.parseBase64Binary(hash);
    }
}
