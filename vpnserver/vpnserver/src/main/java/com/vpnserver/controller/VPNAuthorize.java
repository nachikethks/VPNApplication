package com.vpnserver.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VPNAuthorize {

    public static int authenticate() {
        try {
            ServerSocket ss = new ServerSocket(8000);
            Socket s = ss.accept();// establishes connection
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str1 = (String) dis.readUTF();
            System.out.println("message= " + str1);
            String str2 = (String) dis.readUTF();
            System.out.println("message= " + str2);

            if (str1.equals("nachiketh") && str2.equals("nachi"))
            {
                DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                dout.writeUTF("Authentication Success");
                dout.flush();
                ss.close();
                return 1;
            }
            else {
                DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                dout.writeUTF("Authentication Failed");
                dout.flush();
                ss.close();
                return 0;
            }


        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}