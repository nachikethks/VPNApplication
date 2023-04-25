package com.vpnserver.vpnserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.*;
import com.vpnserver.controller.*;
import com.vpnserver.model.*;

@SpringBootApplication
public class VpnserverApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(VpnserverApplication.class, args);
		int authentaicated = 0;

		while(authentaicated == 0) {
			authentaicated = VPNAuthorize.authenticate();
		}

		ServerSocket ss=new ServerSocket(6000);
		Socket socket=ss.accept();
		// Create a buffer to read the data
		byte[] buffer = new byte[32];

		// Get the input stream of the socket
		InputStream inputStream = socket.getInputStream();

		// Read the data from the input stream
		int bytesRead = inputStream.read(buffer);
		socket.close();
		ss.close();
		System.out.println("Key is Received");
		VPNCipher cipher = new VPNCipher(buffer);
		VPNConnection c = new VPNConnection(cipher);
		c.setup();
		c.send();
		c.receive();

	}

}

