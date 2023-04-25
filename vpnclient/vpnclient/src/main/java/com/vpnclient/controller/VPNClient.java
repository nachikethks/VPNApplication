package com.vpnclient.controller;

import java.io.*;
import java.net.*;
import java.util.*;
import com.vpnclient.model.VPNConfig;
public class VPNClient {
    private String username;
    private String password;
    private VPNConfig config;
    private Socket s;

    public VPNClient() {
        this.config = new VPNConfig();
    }

    public int authenticate() throws Exception {
        // Prompt the user to enter their username and password
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        username = scanner.nextLine();
        System.out.print("Enter your password: ");
        password = scanner.nextLine();
        scanner.close();

        if (this.connect() == 1)
        {
            DataOutputStream dout = new DataOutputStream(this.s.getOutputStream());
            dout.writeUTF(username);
            dout.flush();
            dout.writeUTF(password);
            dout.flush();
            DataInputStream dis = new DataInputStream(this.s.getInputStream());
            String str = (String) dis.readUTF();
            dout.close();
            s.close();
            System.out.println(str);
            if (str.equals("Success")) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    public int connect() {
        try{
            this.s = new Socket(this.config.getServerAddress(), 8000);
            return 1;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}

