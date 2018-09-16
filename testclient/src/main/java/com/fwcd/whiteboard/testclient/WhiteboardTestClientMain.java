package com.fwcd.whiteboard.testclient;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WhiteboardTestClientMain {
	public static void main(String[] args) throws IOException {
		System.out.println("========================");
		System.out.println(" Whiteboard Test Client ");
		System.out.println("========================");
		
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter server host: ");
			String host = scanner.nextLine();
			
			System.out.println("Enter server port: ");
			int port = scanner.nextInt();
			
			connectTo(host, port);
		}
	}
	
	private static void connectTo(String host, int port) throws IOException {
		try (Socket socket = new Socket(host, port)) {
			System.out.println("Connected to " + host + ":" + port);
		}
	}
}
