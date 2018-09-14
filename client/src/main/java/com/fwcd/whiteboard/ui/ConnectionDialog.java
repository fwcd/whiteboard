package com.fwcd.whiteboard.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectionDialog {
	private JPanel view;
	
	private JTextField hostField;
	private JTextField portField;
	
	private String host = null;
	private int port = 0;
	
	public ConnectionDialog() {
		view = new JPanel();
		view.setLayout(new GridLayout(2, 2));
		
		view.add(new JLabel("Host:"));
		hostField = new JTextField();
		view.add(hostField);
		
		view.add(new JLabel("Port:"));
		portField = new JTextField();
		view.add(portField);
	}
	
	public boolean show() {
		int selectedOption = JOptionPane.showConfirmDialog(null, view, "Join Whiteboard", JOptionPane.OK_CANCEL_OPTION);
		
		if (selectedOption == JOptionPane.OK_OPTION) {
			host = hostField.getText();
			port = Integer.parseInt(portField.getText());
			return true;
		} else {
			return false;
		}
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
}
