package com.fwcd.whiteboard.ui;

import java.awt.Color;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import com.fwcd.fructose.swing.Viewable;
import com.fwcd.fructose.swing.properties.BoolProperty;
import com.fwcd.whiteboard.core.WhiteboardApp;

public class WMenuBar implements Viewable {
	private static final Color BG_COLOR = Color.DARK_GRAY;
	private static final Color FG_COLOR = Color.WHITE;
	private final JMenuBar view;

	public WMenuBar(WhiteboardApp parent) {
		view = new JMenuBar();
		view.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		JMenu fileMenu = menu("File");
		fileMenu.add(item("New", () -> parent.getDrawBoard().clear()));
		fileMenu.add(new JSeparator());
		fileMenu.add(item("Open...", () -> parent.openDrawBoard()));
		fileMenu.add(item("Save...", () -> parent.saveDrawBoard()));
		view.add(fileMenu);
		
		JMenu editMenu = menu("Edit");
		editMenu.add(item("Clear", () -> parent.getDrawBoard().clear())); // Redundant, but whatever...
		editMenu.add(new JSeparator());
		editMenu.add(toggleItem("Toggle Snap To Grid", parent.getDrawBoard().getSnapToGrid()));
		editMenu.add(toggleItem("Toggle Grid", parent.getDrawBoard().getShowGrid()));
		view.add(editMenu);
		
		JMenu viewMenu = menu("View");
		viewMenu.add(toggleItem("Toggle Script Panel", parent.getScriptPanel().getVisibility()));
		view.add(viewMenu);
		
		view.setBackground(BG_COLOR);
	}
	
	private JMenuItem item(String title, Runnable onClick) {
		JMenuItem item = new JMenuItem(title);
		item.addActionListener((l) -> onClick.run());
		item.setBackground(BG_COLOR);
		item.setForeground(FG_COLOR);
		return item;
	}
	
	private JCheckBoxMenuItem toggleItem(String title, BoolProperty property) {
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(title);
		property.bind(item);
		item.setBackground(BG_COLOR);
		item.setForeground(FG_COLOR);
		return item;
	}
	
	private JMenu menu(String title) {
		JMenu menu = new JMenu(title);
		menu.setBackground(BG_COLOR);
		menu.setForeground(FG_COLOR);
		return menu;
	}
	
	@Override
	public JComponent getView() {
		return view;
	}
}
