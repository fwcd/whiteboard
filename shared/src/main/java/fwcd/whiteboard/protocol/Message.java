package fwcd.whiteboard.protocol;

import fwcd.whiteboard.protocol.dispatch.MessageDispatcher;

public abstract class Message {
	private final String category;
	private final String name;
	
	public Message(String category, String name) {
		this.category = category;
		this.name = name;
	}
	
	public String getCategory() { return category; }
	
	public String getName() { return name; }
	
	public abstract void dispatch(MessageDispatcher dispatcher);
}
