package fwcd.whiteboard.protocol.struct;

public class Vec2 {
	private final double x;
	private final double y;
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() { return x; }
	
	public double getY() { return y; }
	
	@Override
	public String toString() {
		return "Vec2 (" + x + ", " + y + ")";
	}
}
