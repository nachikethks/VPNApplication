package com.vpnclient.vpnclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import com.vpnclient.controller.*;
import com.vpnclient.view.View;

@SpringBootApplication
public class VpnclientApplication {

	public VpnclientApplication() throws Exception {
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(VpnclientApplication.class, args);

		int authenticated = 0;
		VPNClient a = new VPNClient();
		a.authenticate();


		VPNProtocol p = new VPNProtocol();
		byte[] ba = p.encrypt("Encryption done");
		//System.out.println(ba);
		System.out.println(p.decrypt(ba));

		p.negotiateConnection();

		System.out.println("Negotiating Done");

		VPNConnection c = new VPNConnection();

		Thread.sleep(1000);
		c.setup();

		System.out.println("Connection");

		System.setProperty("java.awt.headless", "false");

		View v = new View(p, c);

		c.receive(p, v);

	}
}
