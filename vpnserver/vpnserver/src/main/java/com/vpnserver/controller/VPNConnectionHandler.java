package com.vpnserver.controller;

import java.io.*;
import java.net.*;
import com.vpnserver.model.VPNCipher;
import com.vpnserver.model.VPNConfig;


class VPNConnectionHandler {
    private Socket clientSocket;
    private VPNConfig config;
    private String authentication; // ?
    private VPNConnection connection;
    private int authorized;

    VPNConnectionHandler() {
        this.authorized = 0;
    }

    public int run() {
        return 0;
    }

    public int authenticate() {
        this.authorized = VPNAuthorize.authenticate();
        return this.authorized;
    }

    public int connectToVPN() {
        return 0;
    }

    public int startCommunication() {

        return 0;
    }
}


