package com.fwcd.whiteboard.view.core;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fwcd.fructose.Observable;
import com.fwcd.fructose.swing.View;
import com.fwcd.sketch.model.SketchBoardModel;
import com.fwcd.whiteboard.view.WhiteboardView;

public class MenuBarView implements View {
	private static final Color BG_COLOR = Color.DARK_GRAY;
	private static final Color FG_COLOR = Color.WHITE;
	private final JMenuBar component;
	private final JFileChooser fileChooser = new JFileChooser();

	public MenuBarView(WhiteboardView app) {
		fileChooser.setFileFilter(new FileNameExtensionFilter("Whiteboard File (.wb)", "wb"));

		SketchBoardModel drawBoardModel = app.getDrawBoard().getModel();
		
		component = new JMenuBar();
		component.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		JMenu fileMenu = newMenu("File");
		fileMenu.add(newItem("New", () -> drawBoardModel.getItems().clear()));
		fileMenu.add(new JSeparator());
		fileMenu.add(newItem("Open...", () -> openDrawBoard(drawBoardModel)));
		fileMenu.add(newItem("Save...", () -> saveDrawBoard(drawBoardModel)));
		component.add(fileMenu);
		
		JMenu editMenu = newMenu("Edit");
		editMenu.add(newItem("Clear", () -> drawBoardModel.getItems().clear())); // Redundant, but whatever...
		editMenu.add(new JSeparator());
		editMenu.add(newToggleItem("Toggle Snap To Grid", drawBoardModel.getSnapToGrid()));
		editMenu.add(newToggleItem("Toggle Grid", drawBoardModel.getShowGrid()));
		component.add(editMenu);
		
		component.setBackground(BG_COLOR);
	}
	
	private JMenuItem newItem(String title, Runnable onClick) {
		JMenuItem item = new JMenuItem(title);
		item.addActionListener((l) -> onClick.run());
		item.setBackground(BG_COLOR);
		item.setForeground(FG_COLOR);
		return item;
	}
	
	private JCheckBoxMenuItem newToggleItem(String title, Observable<Boolean> property) {
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(title);
		item.setBackground(BG_COLOR);
		item.setForeground(FG_COLOR);
		
		item.addActionListener(l -> property.set(item.isSelected()));
		property.listen(item::setSelected);
		
		return item;
	}
	
	private JMenu newMenu(String title) {
		JMenu menu = new JMenu(title);
		menu.setBackground(BG_COLOR);
		menu.setForeground(FG_COLOR);
		return menu;
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
	
	public void openDrawBoard(SketchBoardModel drawBoard) {
		if (fileChooser.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			
			if (file != null) {
				try (Reader reader = Files.newBufferedReader(file.toPath())) {
					drawBoard.readItemsFromJSON(reader);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
	}
	
	public void saveDrawBoard(SketchBoardModel drawBoard) {
		if (fileChooser.showSaveDialog(component) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			
			if (selectedFile != null) {
				String fileName = selectedFile.getAbsolutePath();
				
				if (!fileName.endsWith(".wb")) {
					fileName += ".wb";
				}
				
				File file = new File(fileName);
				try (Writer writer = Files.newBufferedWriter(file.toPath())) {
					drawBoard.writeItemsAsJSON(writer);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
	}
}
