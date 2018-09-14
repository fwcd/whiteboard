package com.fwcd.whiteboard.view.tikz;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.fwcd.fructose.swing.View;

public class TikzOutputPanel implements View {
	private final JPanel component;
	private final JTextArea textArea;
	private final JButton copyButton;
	
	public TikzOutputPanel() {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		component.add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		copyButton = new JButton("Copy Tikz..."); 
		copyButton.addActionListener((l) -> Toolkit
				.getDefaultToolkit()
				.getSystemClipboard()
				.setContents(new StringSelection(textArea.getText()), null));
		component.add(copyButton, BorderLayout.SOUTH);
	}
	
	public void setText(String text) {
		textArea.setText(text);
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
}
