package fwcd.whiteboard.protocol.utils;

import java.util.function.Consumer;

import fwcd.whiteboard.protocol.Message;

/**
 * Listens to messages of a specific type.
 * 
 * @param <T> - The message type to handle
 */
public class MessageListener<T extends Message> {
	private final Class<? extends T> messageType;
	private final Consumer<? super T> action;
	
	public MessageListener(Class<? extends T> messageType, Consumer<? super T> action) {
		this.messageType = messageType;
		this.action = action;
	}
	
	public Class<? extends T> getExceptionType() {
		return messageType;
	}
	
	@SuppressWarnings("unchecked")
	public void run(Message message) {
		action.accept((T) message);
	}
}
