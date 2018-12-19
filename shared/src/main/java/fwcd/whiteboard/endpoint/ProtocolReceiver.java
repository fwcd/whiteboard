package fwcd.whiteboard.endpoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.BooleanSupplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import fwcd.fructose.Either;
import fwcd.fructose.Option;
import fwcd.whiteboard.endpoint.json.MessageDeserializer;
import fwcd.whiteboard.endpoint.json.WhiteboardItemDeserializer;
import fwcd.whiteboard.protocol.Message;
import fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.event.Event;
import fwcd.whiteboard.protocol.request.Request;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

/**
 * Reads messages from a JSON input stream and dispatches them to a
 * client/server interface.
 */
public class ProtocolReceiver implements MessageDispatcher {
	private final Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageDeserializer())
			.registerTypeAdapter(WhiteboardItem.class, new WhiteboardItemDeserializer()).create();
	private final InputStream jsonInput;
	private final Either<WhiteboardClient, WhiteboardServer> receiver;
	private Option<Runnable> onClose = Option.empty();
	
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
	 * Adds a close handler that is invoked once the
	 * receiver has finished running.
	 */
	public void setOnClose(Runnable onClose) { this.onClose = Option.of(onClose); }
	
	/**
	 * Runs while the condition evaluates to true,
	 * dispatching JSON messages from the input stream.
	 */
	public void runWhile(BooleanSupplier condition) throws IOException {
		try (JsonReader reader = gson.newJsonReader(new InputStreamReader(jsonInput))) {
			while (condition.getAsBoolean()) {
				// Parse the next JSON object from the stream
				Message message = gson.fromJson(reader, Message.class);
				message.dispatch(this);
			}
			onClose.ifPresent(Runnable::run);
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
