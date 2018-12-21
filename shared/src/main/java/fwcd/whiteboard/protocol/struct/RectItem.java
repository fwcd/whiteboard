package fwcd.whiteboard.protocol.struct;

import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public class RectItem extends WhiteboardItem {
	private Vec2 topLeft;
	private double width;
	private double height;
	private Color color;
	private double thickness;
	
	// Gson constructor
	protected RectItem() {}
	
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
