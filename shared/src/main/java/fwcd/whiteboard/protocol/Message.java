package fwcd.whiteboard.protocol;

import fwcd.whiteboard.protocol.dispatch.MessageDispatcher;

public abstract class Message {
	private String category;
	private String name;
	
	// Gson constructor
	protected Message() {}
	
	public Message(String category, String name) {
		this.category = category;
		this.name = name;
	}
	
	public String getCategory() { return category; }
	
	public String getName() { return name; }
	
	public abstract void dispatch(MessageDispatcher dispatcher);
	
	@Override
	public String toString() {
		return "Message [name=" + name + ", category=" + category + "]";
	}
}
