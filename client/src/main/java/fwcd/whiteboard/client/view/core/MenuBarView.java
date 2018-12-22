package fwcd.whiteboard.client.view.core;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import fwcd.fructose.Observable;
import fwcd.fructose.ReadOnlyObservable;
import fwcd.fructose.swing.View;
import fwcd.sketch.model.SketchBoardModel;
import fwcd.whiteboard.client.view.WhiteboardView;

public class MenuBarView implements View {
	private final JMenuBar component;
	private final JFileChooser fileChooser = new JFileChooser();
	private final ReadOnlyObservable<Color> bgColor;
	private final ReadOnlyObservable<Color> fgColor;

	public MenuBarView(WhiteboardView app) {
		bgColor = app.getDrawBoard().getModel().getBackground();
		fgColor = bgColor.mapStrongly(it -> new Color(255 - it.getRed(), 255 - it.getGreen(), 255 - it.getBlue()));
		
		fileChooser.setFileFilter(new FileNameExtensionFilter("Whiteboard File (.wb)", "wb"));

		SketchBoardModel drawBoardModel = app.getDrawBoard().getModel();
		
		component = new JMenuBar();
		component.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		JMenu fileMenu = newMenu("File");
		fileMenu.add(newItem("New", () -> drawBoardModel.clear()));
		fileMenu.add(new JSeparator());
		fileMenu.add(newItem("Open...", () -> openDrawBoard(drawBoardModel)));
		fileMenu.add(newItem("Save...", () -> saveDrawBoard(drawBoardModel)));
		component.add(fileMenu);
		
		JMenu editMenu = newMenu("Edit");
		editMenu.add(newToggleItem("Toggle Snap To Grid", drawBoardModel.getSnapToGrid()));
		editMenu.add(newToggleItem("Toggle Grid", drawBoardModel.getShowGrid()));
		component.add(editMenu);
		
		bgColor.listenAndFire(component::setBackground);
	}
	
	private JMenuItem newItem(String title, Runnable onClick) {
		JMenuItem item = new JMenuItem(title);
		item.addActionListener((l) -> onClick.run());
		bgColor.listenAndFire(item::setBackground);
		fgColor.listenAndFire(item::setForeground);
		return item;
	}
	
	private JCheckBoxMenuItem newToggleItem(String title, Observable<Boolean> property) {
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(title);
		bgColor.listenAndFire(item::setBackground);
		fgColor.listenAndFire(item::setForeground);
		
		item.addActionListener(l -> property.set(item.isSelected()));
		property.listen(item::setSelected);
		
		return item;
	}
	
	private JMenu newMenu(String title) {
		JMenu menu = new JMenu(title);
		bgColor.listenAndFire(menu::setBackground);
		fgColor.listenAndFire(menu::setForeground);
		return menu;
	}
	
	@Override
	public JMenuBar getComponent() {
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
