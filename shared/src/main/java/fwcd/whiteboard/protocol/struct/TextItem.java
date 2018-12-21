package fwcd.whiteboard.protocol.struct;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public class TextItem extends WhiteboardItem {
	private List<String> text;
	private Vec2 pos;
	private Color color;
	private double size;
	
	// Gson constructor
	protected TextItem() {}
	
	public TextItem(List<String> text, Vec2 pos, Color color, double size) {
		super(WhiteboardItemName.TEXT);
		this.text = text;
		this.pos = pos;
		this.color = color;
		this.size = size;
	}
	
	@Override
	public <T> T accept(WhiteboardItemVisitor<T> visitor) {
		return visitor.visitText(this);
	}
	
	public List<String> getText() { return text; }
	
	public Vec2 getPos() { return pos; }
	
	public Color getColor() { return color; }
	
	public double getSize() { return size; }
	
	@Override
	public String toString() {
		return "TextItem [text=" + text + ",pos=" + pos + ",color=" + color + ",size=" + size + "]";
	}
}
