package com.fwcd.whiteboard.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fwcd.sketch.model.utils.PolymorphicSerializer;
import com.fwcd.whiteboard.networking.packets.Packet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerConnection<T> {
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(Packet.class, new PolymorphicSerializer<Packet<?>>())
			.create();
	private final T linkedModel;
	
	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;
	
	public ServerConnection(T linkedModel, String host, int port) {
		this.linkedModel = linkedModel;
		
		try {
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Writes a packet to the output stream.
	 * 
	 * @param packet - The packet
	 */
	public void write(Packet<T> packet) {
		gson.toJson(packet, out);
	}
	
	/**
	 * Blocks until a packet is received.
	 * 
	 * @return The packet
	 */
	@SuppressWarnings("unchecked")
	public Packet<T> read() {
		return (Packet<T>) gson.fromJson(in, Packet.class);
	}
	
	/**
	 * Blocks until a packet is received and then
	 * updates the associated model.
	 */
	public void updateLinkedModel() {
		try {
			read().apply(linkedModel);
		} catch (Exception e) {}
	}
	
	@Override
	public String toString() {
		return socket.getInetAddress().toString();
	}
}
