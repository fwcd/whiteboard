package com.fwcd.whiteboard.endpoint;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import com.fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import com.fwcd.whiteboard.protocol.event.Event;
import com.fwcd.whiteboard.protocol.request.Request;
import com.google.gson.Gson;

/**
 * Proxy implementation of a remote WhiteboardServer/WhiteboardClient.
 * 
 * <p>Usually this class should only be aliased either through the server
 * or the client interface (but not both).</p>
 */
public class RemoteWhiteboard implements WhiteboardServer, WhiteboardClient {
	private final Gson gson = new Gson();
	private final Writer writer;
	
	public RemoteWhiteboard(OutputStream jsonOutput) {
		writer = new OutputStreamWriter(jsonOutput);
	}
	
	@Override
	public void otherRequest(Request request) {
		gson.toJson(request, writer);
	}
	
	@Override
	public void otherEvent(Event event) {
		gson.toJson(event, writer);
	}
}
