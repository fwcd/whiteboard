package fwcd.whiteboard.protocol.struct;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;

public class PathItem extends WhiteboardItem {
	private final List<Vec2> vertices;
	private final Color color;
	private final double thickness;
	
	public PathItem(List<Vec2> vertices, Color color, double thickness) {
		super(WhiteboardItemName.PATH);
		this.vertices = vertices;
		this.color = color;
		this.thickness = thickness;
	}
	
	@Override
	public void accept(WhiteboardItemVisitor visitor) {
		visitor.visitPath(this);
	}
	
	public List<Vec2> getVertices() { return vertices; }
	
	public double getThickness() { return thickness; }
	
	public Color getColor() { return color; }
}
