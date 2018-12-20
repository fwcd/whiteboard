package fwcd.whiteboard.endpoint;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.event.Event;
import fwcd.whiteboard.protocol.request.Request;

/**
 * Proxy implementation of a remote WhiteboardServer/WhiteboardClient.
 * 
 * <p>
 * Usually this class should only be aliased either through the server or the
 * client interface (but not both).
 * </p>
 */
public class RemoteWhiteboard implements WhiteboardServer, WhiteboardClient {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteWhiteboard.class);
	private final Gson gson = new Gson();
	private final Writer writer;
	
	public RemoteWhiteboard(OutputStream jsonOutput) {
		writer = new OutputStreamWriter(jsonOutput);
	}
	
	@Override
	public void otherRequest(Request request) {
		gson.toJson(request, writer);
		flush();
		LOG.debug("<< Out: {}", request);
	}
	
	@Override
	public void otherEvent(Event event) {
		gson.toJson(event, writer);
		flush();
		LOG.debug("<< Out: {}", event);
	}
	
	private void flush() {
		try {
			writer.flush();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
