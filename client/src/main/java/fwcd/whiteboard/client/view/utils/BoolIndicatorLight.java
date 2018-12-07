package fwcd.whiteboard.client.view.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.fructose.swing.RenderPanel;
import fwcd.fructose.swing.View;

public class BoolIndicatorLight implements View {
	private JPanel component;

	private boolean enabled = false;
	
	public BoolIndicatorLight() {
		component = new RenderPanel(this::render);
		component.setPreferredSize(new Dimension(24, 24));
		component.setOpaque(false);
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	private void render(Graphics2D g2d, Dimension canvasSize) {
		g2d.setColor(enabled ? Color.GREEN : Color.RED);
		
		int width = 15;
		int height = 15;
		
		g2d.fillOval((canvasSize.width / 2) - (width / 2), (canvasSize.height / 2) - (height / 2), width, height);
	}
}
