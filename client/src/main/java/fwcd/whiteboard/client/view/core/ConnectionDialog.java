package fwcd.whiteboard.client.view.core;

import java.awt.GridLayout;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fwcd.fructose.swing.HintTextField;

public class ConnectionDialog {
	private JPanel component;
	
	private JTextField hostField;
	private JTextField portField;
	private JTextField displayNameField;
	
	private String host = null;
	private String displayName = null;
	private int port = 0;
	
	public ConnectionDialog() {
		component = new JPanel();
		component.setLayout(new GridLayout(3, 2));
		
		component.add(new JLabel("Host:"));
		hostField = new HintTextField("localhost");
		component.add(hostField);
		
		component.add(new JLabel("Port:"));
		portField = new JTextField();
		component.add(portField);
		
		component.add(new JLabel("Displayed Name:"));
		displayNameField = new JTextField("Guest");
		component.add(displayNameField);
	}
	
	public boolean show() {
		int selectedOption = JOptionPane.showConfirmDialog(null, component, "Join Whiteboard", JOptionPane.OK_CANCEL_OPTION);
		
		if (selectedOption == JOptionPane.OK_OPTION) {
			host = hostField.getText();
			port = Integer.parseInt(portField.getText());
			displayName = displayNameField.getText();
			return true;
		} else {
			return false;
		}
	}

	public String getHost() { return Objects.requireNonNull(host, "No host present."); }

	public int getPort() { return Objects.requireNonNull(port, "No port present."); }
	
	public String getDisplayName() { return Objects.requireNonNull(displayName, "No displayName present."); }
}
