package com.vpnclient.controller;

import java.net.*;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import com.vpnclient.model.VPNConfig;
public class VPNProtocol {
    private VPNConfig config;
    private byte[] key;
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public VPNProtocol() {
        this.config = new VPNConfig();
        generateKey();
    }

    public void negotiateConnection() throws Exception {
        Socket socket = new Socket(this.config.getServerAddress(), 6000);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(this.key);
        outputStream.flush();
        socket.close();
    }

    private void generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(this.config.getEncryptionAlgorithm());
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            this.key = secretKey.getEncoded();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


    public byte[] encrypt(String input) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(this.key, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            System.out.println(e);
            return new byte[32];
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

    public void handleProtocolError() {

    }
}