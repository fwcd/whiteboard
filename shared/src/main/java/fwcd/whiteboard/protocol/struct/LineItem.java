package fwcd.whiteboard.protocol.struct;

import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public class LineItem extends WhiteboardItem {
	private Vec2 start;
	private Vec2 end;
	private Color color;
	private double thickness;
	
	// Gson constructor
	protected LineItem() {}
	
	public LineItem(Vec2 start, Vec2 end, Color color, double thickness) {
		super(WhiteboardItemName.LINE);
		this.start = start;
		this.end = end;
		this.color = color;
		this.thickness = thickness;
	}
	
	@Override
	public <T> T accept(WhiteboardItemVisitor<T> visitor) {
		return visitor.visitLine(this);
	}
	
	public Vec2 getStart() { return start; }
	
	public Vec2 getEnd() { return end; }
	
	public Color getColor() { return color; }
	
	public double getThickness() { return thickness; }
	
	@Override
	public String toString() {
		return "LineItem [start=" + start + ",end=" + end + ",color=" + color + ",thickness=" + thickness + "]";
	}
}
