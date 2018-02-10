package com.fwcd.whiteboard.tikz;

import java.io.ByteArrayOutputStream;

import org.jtikz.TikzGraphics2D;

public class TikzCanvas {
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private final TikzGraphics2D g2d = new TikzGraphics2D(out);
	
	public TikzGraphics2D getGraphics() {
		return g2d;
	}
	
	public String getOutput() {
		g2d.flush();
		return out.toString();
	}
}
