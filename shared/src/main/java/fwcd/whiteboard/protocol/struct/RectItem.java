package fwcd.whiteboard.protocol.struct;

import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public class RectItem extends WhiteboardItem {
	private final Vec2 topLeft;
	private final double width;
	private final double height;
	private final Color color;
	private final double thickness;
	
	public RectItem(Vec2 topLeft, double width, double height, Color color, double thickness) {
		super(WhiteboardItemName.RECT);
		this.topLeft = topLeft;
		this.width = width;
		this.height = height;
		this.color = color;
		this.thickness = thickness;
	}
	
	@Override
	public <T> T accept(WhiteboardItemVisitor<T> visitor) {
		return visitor.visitRect(this);
	}
	
	public Vec2 getTopLeft() { return topLeft; }
	
	public double getWidth() { return width; }
	
	public double getHeight() { return height; }
	
	public Color getColor() { return color; }
	
	public double getThickness() { return thickness; }
	
	@Override
	public String toString() {
		return "RectItem [topLeft=" + topLeft + ",width=" + width + ",height=" + height + ",color=" + color + ",thickness=" + thickness + "]";
	}
}
