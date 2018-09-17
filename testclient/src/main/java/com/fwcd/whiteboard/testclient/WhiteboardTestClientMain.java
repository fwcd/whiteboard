package com.fwcd.whiteboard.testclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
			
			connectTo(host, port, scanner);
		}
	}
	
	private static void connectTo(String host, int port, Scanner scanner) throws IOException {
		try (
			Socket socket = new Socket(host, port);
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output);
		) {
			System.out.println("Connected to " + host + ":" + port);
			System.out.println("Enter JSON requests: ");
			
			pipeToStdoutAsync(socket.getInputStream());
			
			while (true) {
				String request = scanner.nextLine();
				writer.print(request);
				writer.flush();
			}
		}
	}
	
	private static void pipeToStdoutAsync(InputStream input) {
		new Thread(() -> {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
				int read;
				while ((read = reader.read()) >= 0) {
					System.out.print((char) read);
				}
			} catch (IOException e) {
				System.out.println("IOException while piping to stdout: " + e.getMessage());
			}
		}).start();
	}
}
