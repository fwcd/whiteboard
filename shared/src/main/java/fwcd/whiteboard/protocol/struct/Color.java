package fwcd.whiteboard.protocol.struct;

public class Color {
	// Each value is between 0 and 255 (inclusive)
	private final double r;
	private final double g;
	private final double b;
	private final double a;
	
	public Color(double r, double g, double b, double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public double getR() { return r; }
	
	public double getG() { return g; }
	
	public double getB() { return b; }
	
	public double getA() { return a; }
}
