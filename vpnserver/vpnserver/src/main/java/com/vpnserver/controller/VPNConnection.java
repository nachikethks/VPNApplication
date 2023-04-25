package com.vpnserver.controller;

import com.vpnserver.model.VPNCipher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VPNConnection {
    private String protocol;
    private String clientAddress;
    private ServerSocket ss;
    private Socket s;
    private Socket ds;

    private DataInputStream din;
    private DataOutputStream douts;
    private DataInputStream dis;
    private DataOutputStream dout;
    private VPNCipher cipher;
    public VPNConnection(VPNCipher cipher) {
        this.cipher = cipher;
    }

    public void setup() throws Exception
    {
        System.out.println("Opening");

        this.ss = new ServerSocket(8000);
        this.s = this.ss.accept();

        this.ds = new Socket("10.0.2.27", 9000);

        this.din = new DataInputStream(this.ds.getInputStream());
        this.dis = new DataInputStream(this.s.getInputStream());
        this.douts = new DataOutputStream(this.s.getOutputStream());
        this.dout=new DataOutputStream(this.ds.getOutputStream());
    }
    public int send() throws Exception {
        new Thread(() -> {
            String str;
            try {

                while (true) {
                    str = this.din.readUTF();
                    this.douts.write(this.cipher.encrypt(str));

                    if (!str.equals(""))
                    {
                        System.out.print(str);
                        System.out.println(" : " + this.cipher.encrypt(str));
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
        return 0;
    }

    public int receive() throws Exception {
        new Thread(() -> {
            try {
                while (true) {

                    int length = this.dis.available();
                    byte[] data = new byte[length];
                    this.dis.readFully(data);



                    String str = this.cipher.decrypt(data);

                    if (!str.equals(""))
                    {
                        System.out.print(data);
                        System.out.println(" : " + str);
                    }
                    this.dout.writeUTF(str);
                    this.dout.flush();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
        return 0;
    }
}