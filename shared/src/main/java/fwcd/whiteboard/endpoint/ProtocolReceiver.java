package fwcd.whiteboard.endpoint;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import fwcd.whiteboard.protocol.utils.MessageListener;
import fwcd.whiteboard.utils.ExceptionHandler;

/**
 * Reads messages from a JSON input stream and dispatches them to a
 * client/server interface.
 */
public class ProtocolReceiver implements MessageDispatcher {
	private static final Logger LOG = LoggerFactory.getLogger(ProtocolReceiver.class);
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(Message.class, new MessageDeserializer())
			.registerTypeAdapter(WhiteboardItem.class, new WhiteboardItemDeserializer()).create();
	private final InputStream jsonInput;
	
	private final Either<WhiteboardClient, WhiteboardServer> receiver;
	private final List<ExceptionHandler<?>> exceptionHandlers = new ArrayList<>();
	private final List<MessageListener<?>> messageListeners = new ArrayList<>();
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
	 * Adds a handler for (unchecked) exceptions.
	 * 
	 * @param exceptionType - The runtime type of the exception to handle
	 * @param action - The function to invoke. Accepts the exception and returns true if this object should continue to run.
	 */
	public <T extends Throwable> void addExceptionHandler(Class<? extends T> exceptionType, Function<? super T, Boolean> action) {
		exceptionHandlers.add(new ExceptionHandler<>(exceptionType, action));
	}
	
	/**
	 * Adds a listener for (unchecked) exceptions. This method does not
	 * have any effect on whether the loop continues to run (by always
	 * returning true) as opposed to {@code addExceptionHandler}.
	 * 
	 * @param exceptionType - The runtime type of the exception to respond to
	 * @param action - The consumer to invoke. Accepts the exception.
	 */
	public <T extends Throwable> void addExceptionListener(Class<? extends T> exceptionType, Consumer<? super T> action) {
		addExceptionHandler(exceptionType, e -> {
			action.accept(e);
			return true;
		});
	}
	
	/**
	 * Removes all registered exception handlers for the given type.
	 */
	public void removeExceptionHandlers(Class<?> exceptionType) {
		exceptionHandlers.removeIf(it -> it.getExceptionType().equals(exceptionType));
	}
	
	public <T extends Message> void addMessageListener(Class<? extends T> messageType, Consumer<? super T> action) {
		messageListeners.add(new MessageListener<>(messageType, action));
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
			boolean continueLoop = true;
			while (continueLoop && condition.getAsBoolean()) {
				try {
					// Parse the next JSON object from the stream
					// TODO: Delay?
					Message message = gson.fromJson(reader, Message.class);
					if (message == null) {
						continueLoop = false;
					} else {
						message.dispatch(this);
						fireMessageListeners(message);
						LOG.debug(">> In:  {}", message);
					}
				} catch (UncheckedIOException e) {
					if (e.getCause() instanceof EOFException) {
						continueLoop = false;
						handleException(e);
					} else {
						continueLoop = handleException(e);
					}
				} catch (RuntimeException e) {
					continueLoop = handleException(e);
				}
			}
			onClose.ifPresent(Runnable::run);
		}
	}
	
	private boolean handleException(Throwable e) {
		Class<? extends Throwable> exceptionType = e.getClass();
		boolean continueLoop = true;
		
		for (ExceptionHandler<?> handler : exceptionHandlers) {
			if (handler.getExceptionType().equals(exceptionType)) {
				continueLoop &= handler.run(e);
			}
		}
		
		return continueLoop;
	}
	
	private void fireMessageListeners(Message message) {
		if (!messageListeners.isEmpty()) {
			Class<? extends Message> messageType = message.getClass();
			for (MessageListener<?> listener : messageListeners) {
				if (listener.getExceptionType().equals(messageType)) {
					listener.run(message);
				}
			}
		}
	}
	
	@Override
	public void receiveEvent(Event event) {
		receiver.match(
			client -> event.sendTo(client),
			server -> LOG.warn("Server should not receive Event messages!") // TODO: Proper logging that does not happen on System.out (which potentially also transports JSON objects)
		);
	}
	
	@Override
	public void receiveRequest(Request request) {
		receiver.match(
			client -> LOG.warn("Client should not receive Request messages!"), // TODO: Proper logging that does not happen on System.out (which potentially also transports JSON objects)
			server -> request.sendTo(server)
		);
	}
}
