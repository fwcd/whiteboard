package com.fwcd.whiteboard.protocol.struct;

import com.fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public class LineItem extends WhiteboardItem {
	private final Vec2 start;
	private final Vec2 end;
	private final Color color;
	private final double thickness;
	
	public LineItem(Vec2 start, Vec2 end, Color color, double thickness) {
		super(WhiteboardItemName.LINE);
		this.start = start;
		this.end = end;
		this.color = color;
		this.thickness = thickness;
	}
	
	@Override
	public void accept(WhiteboardItemVisitor visitor) {
		visitor.visitLine(this);
	}
	
	public Vec2 getStart() { return start; }
	
	public Vec2 getEnd() { return end; }
	
	public Color getColor() { return color; }
	
	public double getThickness() { return thickness; }
}
