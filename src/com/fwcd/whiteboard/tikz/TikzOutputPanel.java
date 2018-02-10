package com.fwcd.whiteboard.tikz;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.fwcd.fructose.swing.Viewable;

public class TikzOutputPanel implements Viewable {
	private final JPanel view;
	private final JTextArea textArea;
	private final JButton copyButton;
	
	public TikzOutputPanel() {
		view = new JPanel();
		view.setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		view.add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		copyButton = new JButton("Copy Tikz..."); 
		copyButton.addActionListener((l) -> Toolkit
				.getDefaultToolkit()
				.getSystemClipboard()
				.setContents(new StringSelection(textArea.getText()), null));
		view.add(copyButton, BorderLayout.SOUTH);
	}
	
	public void setText(String text) {
		textArea.setText(text);
	}

	@Override
	public JComponent getView() {
		return view;
	}
}
