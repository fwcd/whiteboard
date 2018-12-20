package fwcd.whiteboard.protocol.struct;

import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public abstract class WhiteboardItem {
	private final String name;
	
	public WhiteboardItem(String name) {
		this.name = name;
	}
	
	public String getName() { return name; }
	
	public abstract <T> T accept(WhiteboardItemVisitor<T> visitor);
	
	@Override
	public String toString() {
		return "WhiteboardItem [name=" + name + "]";
	}
}
