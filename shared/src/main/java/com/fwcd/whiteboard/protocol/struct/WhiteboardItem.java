package com.fwcd.whiteboard.protocol.struct;

import com.fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public abstract class WhiteboardItem {
	private final String name;
	
	public WhiteboardItem(String name) {
		this.name = name;
	}
	
	public String getName() { return name; }
	
	public abstract void accept(WhiteboardItemVisitor visitor);
}
