package com.fwcd.whiteboard.client.view.utils;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class Spacer extends JPanel {
	private static final long serialVersionUID = 3610198332538919155L;
	
	public Spacer() {
		setPreferredSize(new Dimension(10, 10));
		setLayout(new GridBagLayout());
	}
}
