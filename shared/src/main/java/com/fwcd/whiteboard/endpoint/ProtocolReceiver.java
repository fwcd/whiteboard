package com.fwcd.whiteboard.endpoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.function.BooleanSupplier;

import com.fwcd.fructose.Either;
import com.fwcd.whiteboard.endpoint.json.MessageDeserializer;
import com.fwcd.whiteboard.endpoint.json.WhiteboardItemDeserializer;
import com.fwcd.whiteboard.protocol.Message;
import com.fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import com.fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import com.fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import com.fwcd.whiteboard.protocol.event.Event;
import com.fwcd.whiteboard.protocol.request.Request;
import com.fwcd.whiteboard.protocol.struct.WhiteboardItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Reads messages from a JSON input stream and
 * dispatches them to a client/server interface.
 */
public class ProtocolReceiver implements MessageDispatcher {
	private final Gson gson = new GsonBuilder()
		.registerTypeAdapter(Message.class, new MessageDeserializer())
		.registerTypeAdapter(WhiteboardItem.class, new WhiteboardItemDeserializer())
		.create();
	private final InputStream jsonInput;
	private final Either<WhiteboardClient, WhiteboardServer> receiver;
	
	private ProtocolReceiver(InputStream jsonInput, Either<WhiteboardClient, WhiteboardServer> receiver) {
		this.jsonInput = jsonInput;
		this.receiver = receiver;
	}
	
	public static ProtocolReceiver ofClient(InputStream jsonInput, WhiteboardClient client) {
		return new ProtocolReceiver(jsonInput, Either.ofLeft(client));
	}
	
	public static ProtocolReceiver ofServer(InputStream jsonInput, WhiteboardServer server) {
		return new ProtocolReceiver(jsonInput, Either.ofRight(server));
	}
	
	/**
	 * Runs while the condition evaluates to true,
	 * dispatching JSON messages from the input stream.
	 */
	public void runWhile(BooleanSupplier condition) throws IOException {
		try (Reader reader = new InputStreamReader(jsonInput)) {
			while (condition.getAsBoolean()) {
				Message message = gson.fromJson(reader, Message.class);
				message.dispatch(this);
			}
		}
	}
	
	@Override
	public void receiveEvent(Event event) {
		receiver.match(
			client -> event.sendTo(client),
			server -> System.out.println("Server should not receive Event messages!") // TODO: Proper logging that does not happen on System.out (which potentially also transports JSON objects)
		);
	}
	
	@Override
	public void receiveRequest(Request request) {
		receiver.match(
			client -> System.out.println("Client should not receive Request messages!"), // TODO: Proper logging that does not happen on System.out (which potentially also transports JSON objects)
			server -> request.sendTo(server)
		);
	}
}
