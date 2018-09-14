package com.fwcd.whiteboard.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class WhiteboardFrame {
	public WhiteboardFrame(String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(new WhiteboardView().getComponent());
		frame.setVisible(true);
	}
}
