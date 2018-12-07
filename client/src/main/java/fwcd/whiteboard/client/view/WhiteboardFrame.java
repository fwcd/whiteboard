package fwcd.whiteboard.client.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import fwcd.whiteboard.client.view.core.MenuBarView;

public class WhiteboardFrame {
	public WhiteboardFrame(String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		WhiteboardView app = new WhiteboardView();
		frame.add(app.getComponent(), BorderLayout.CENTER);
		
		MenuBarView menuBar = new MenuBarView(app);
		frame.add(menuBar.getComponent(), BorderLayout.NORTH);
		
		frame.setVisible(true);
	}
}
