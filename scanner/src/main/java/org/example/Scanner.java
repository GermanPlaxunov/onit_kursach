package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Scanner {

	private final String ip;
	private final Integer timeout;

	public Scanner(String ip, Integer timeout){
		this.ip = ip;
		this.timeout = timeout;
	}

	public void scan(){
		var count = 0;
		for(var port = 1; port < 65535; port++){
			try{
				var socket = new Socket();
				socket.connect(new InetSocketAddress(ip, port), timeout);
				socket.close();
				System.out.println("Port: " + port + " is free");
				count++;
			} catch (IOException e) {
			}
		}
	}

}
