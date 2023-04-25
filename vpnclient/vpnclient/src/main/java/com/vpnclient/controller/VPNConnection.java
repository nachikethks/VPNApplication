package com.vpnclient.controller;

import java.io.*;
import java.net.*;
import com.vpnclient.view.View;

public class VPNConnection {
    private String protocol;
    private String clientAddress;
    private String serverAddress;
    private Socket socket; // % Change type
    private InetAddress ip;
    private DataInputStream dis;

    public VPNConnection() {
        //this.serverAddress = "localhost";
        this.serverAddress = "10.0.2.25";
    }

    public int setup() throws Exception {
        Socket ds = new Socket("10.0.2.25", 8000);
        this.socket = ds;
        return 0;
    }

    public int send(VPNProtocol p, String data) throws Exception {
        DataOutputStream dout=new DataOutputStream(this.socket.getOutputStream());
        dout.write(p.encrypt(data));
        dout.flush();
        return 0;
    }

    public int receive(VPNProtocol p, View v) throws Exception {
        new Thread(() -> {
            try {
                this.dis = new DataInputStream(this.socket.getInputStream());
            }catch (Exception e) {
                System.out.println(e);
            }
            int length;
            while(true) {
                try {
                    length = this.dis.available();
                    byte[] data = new byte[length];
                    this.dis.readFully(data);
                    String str = p.decrypt(data);
                    v.addTextMessage(str);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();
        return 0;
    }

    public int close() throws Exception {
        this.socket.close();
        return 0;
    }
}
