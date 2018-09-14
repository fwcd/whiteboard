package com.fwcd.whiteboard;

import javax.swing.UIManager;

import com.fwcd.whiteboard.core.WhiteboardApp;

public class WhiteboardMain {
	private static final float VERSION = 14.0F;
	
	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Whiteboard");

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		new WhiteboardApp();
	}

	public static float getVersion() {
		return VERSION;
	}
}
