package com.fwcd.whiteboard.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.fructose.swing.RenderPanel;
import com.fwcd.fructose.swing.Rendereable;
import com.fwcd.fructose.swing.Viewable;

public class BoolIndicatorLight implements Viewable, Rendereable {
	private JPanel view;

	private boolean enabled = false;
	
	public BoolIndicatorLight() {
		view = new RenderPanel(this);
		view.setPreferredSize(new Dimension(24, 24));
		view.setOpaque(false);
	}
	
	@Override
	public JComponent getView() {
		return view;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		g2d.setColor(enabled ? Color.GREEN : Color.RED);
		
		int width = 15;
		int height = 15;
		
		g2d.fillOval((canvasSize.width / 2) - (width / 2), (canvasSize.height / 2) - (height / 2), width, height);
	}
}
