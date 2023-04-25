package com.vpnserver.model;

import jakarta.persistence.Entity;
import javax.crypto.*;
import javax.crypto.spec.*;
@Entity
public class VPNCipher {
    private byte[] key;
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public VPNCipher(byte[] key) {
        this.key = key;
    }

    public byte[] encrypt(String input) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(this.key, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            System.out.println(e);
            byte[] ba1 = new byte[18];
            return ba1;
        }
    }

    public String decrypt(byte[] cipherText) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(this.key, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }
}