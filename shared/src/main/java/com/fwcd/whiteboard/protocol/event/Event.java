package com.fwcd.whiteboard.protocol.event;

import com.fwcd.whiteboard.protocol.Message;
import com.fwcd.whiteboard.protocol.MessageCategory;
import com.fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import com.fwcd.whiteboard.protocol.dispatch.WhiteboardClient;

public abstract class Event extends Message {
	public Event(String type) {
		super(MessageCategory.EVENT, type);
	}
	
	@Override
	public void dispatch(MessageDispatcher dispatcher) {
		dispatcher.receiveEvent(this);
	}
	
	public abstract void sendTo(WhiteboardClient client);
}
