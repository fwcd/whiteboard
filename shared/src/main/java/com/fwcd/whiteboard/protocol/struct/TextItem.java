package com.fwcd.whiteboard.protocol.struct;

import java.util.List;

import com.fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public class TextItem extends WhiteboardItem {
	private final List<String> text;
	private final Vec2 pos;
	private final Color color;
	private final double size;
	
	public TextItem(List<String> text, Vec2 pos, Color color, double size) {
		super(WhiteboardItemName.TEXT);
		this.text = text;
		this.pos = pos;
		this.color = color;
		this.size = size;
	}
	
	@Override
	public void accept(WhiteboardItemVisitor visitor) {
		visitor.visitText(this);
	}
	
	public List<String> getText() { return text; }
	
	public Vec2 getPos() { return pos; }
	
	public Color getColor() { return color; }
	
	public double getSize() { return size; }
}
