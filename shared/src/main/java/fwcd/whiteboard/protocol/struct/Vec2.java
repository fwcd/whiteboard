package fwcd.whiteboard.protocol.struct;

public class Vec2 {
	private double x;
	private double y;
	
	// Gson constructor
	protected Vec2() {}
	
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
