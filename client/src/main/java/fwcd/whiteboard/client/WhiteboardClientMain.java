package fwcd.whiteboard.client;

import fwcd.whiteboard.client.view.WhiteboardFrame;

public class WhiteboardClientMain {
	private static final float VERSION = 14.0F;
	
	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Whiteboard");
		
		int width = 900;
		int height = 600;
		new WhiteboardFrame("Whiteboard " + VERSION, width, height);
	}
}
