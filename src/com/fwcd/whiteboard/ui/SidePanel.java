package com.fwcd.whiteboard.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.fwcd.fructose.swing.DrawGraphicsButton;
import com.fwcd.fructose.swing.HelpView;
import com.fwcd.fructose.swing.RangedSlider;
import com.fwcd.fructose.swing.Rendereable;
import com.fwcd.fructose.swing.ResourceImage;
import com.fwcd.fructose.swing.SelectedButtonPanel;
import com.fwcd.fructose.swing.Viewable;
import com.fwcd.sketch.tools.EnumSketchTool;
import com.fwcd.sketch.tools.SketchTool;
import com.fwcd.whiteboard.core.WhiteboardApp;

public class SidePanel implements Viewable {
	private static final Icon HELP_ICON = new ResourceImage("/resources/helpIcon.png").getAsIcon();
	private final Color highlightColor = Color.GRAY;
	
	private final JToolBar view;
	private final Color[] colors = {
			Color.BLACK,
			Color.RED,
			Color.ORANGE,
			Color.YELLOW,
			Color.GREEN,
			Color.BLUE,
			Color.MAGENTA
	};
	private final Color[] bgColors = {
			Color.WHITE,
			Color.BLACK
	};
	
	private final NetworkControls networkControls;
	private final HelpView help = new HelpView(
			"Draw using your mouse!"
	);
	
	public SidePanel(WhiteboardApp parent, boolean horizontal) {
		view = new JToolBar(horizontal ? JToolBar.HORIZONTAL : JToolBar.VERTICAL);
		view.setOpaque(true);
		view.setFloatable(false);
		view.setLayout(new BorderLayout());
		view.setPreferredSize(horizontal ? new Dimension(200, 40) : new Dimension(40, 200));
		view.setBackground(Color.DARK_GRAY);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, horizontal ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
		
		// Add tool buttons
		
		SelectedButtonPanel toolsPanel = getButtonPanel(horizontal);
		
		for (EnumSketchTool enumTool : EnumSketchTool.values()) {
			SketchTool tool = enumTool.get();
			JButton button = new JButton();
			button.setIcon(tool.getIcon());
			toolsPanel.add(button, () -> parent.setSelectedTool(tool));
			
			if (enumTool == EnumSketchTool.BRUSH) {
				toolsPanel.select(button);
			}
		}
		
		buttonPanel.add(toolsPanel.getView());
		
		// Add color buttons
		
		SelectedButtonPanel colorsPanel = getButtonPanel(horizontal);
		
		for (Color color : colors) {
			Rendereable circle = (g2d, canvasSize) -> {
				g2d.setColor(color);
				
				int w = (int) canvasSize.getWidth();
				int h = (int) canvasSize.getHeight();
				int iconSize = (int) (Math.min(w, h) * 0.8D);
				
				g2d.fillOval((w / 2) - (iconSize / 2), (h / 2) - (iconSize / 2), iconSize, iconSize);
			};
			
			JButton button = new DrawGraphicsButton(new Dimension(24, 24), circle);
			colorsPanel.add(button, () -> parent.getBrushProperties().setColor(color));
		}

		buttonPanel.add(colorsPanel.getView());
		
		// Add background buttons
		
		SelectedButtonPanel bgColorsPanel = getButtonPanel(horizontal);
		
		for (Color color : bgColors) {
			Rendereable circle = (g2d, canvasSize) -> {
				g2d.setColor(color);
				g2d.fillRect(0, 0, (int) canvasSize.getWidth(), (int) canvasSize.getHeight());
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawRect(0, 0, (int) canvasSize.getWidth() - 1, (int) canvasSize.getHeight() - 1);
			};
			
			JButton button = new DrawGraphicsButton(new Dimension(24, 24), circle);
			bgColorsPanel.add(button, () -> parent.setBackground(color));
		}

		buttonPanel.add(bgColorsPanel.getView());
		
		// Add brush thickness slider
		
		JToolBar brushThicknessOptions = getPanel(horizontal);
		
		RangedSlider slider = new RangedSlider();
		slider.setOpaque(false);
		slider.setAlignmentX(0);
		slider.setOrientation(horizontal ? SwingConstants.HORIZONTAL : SwingConstants.VERTICAL);
		slider.setPreferredSize(new Dimension(10, 80));
		parent.getBrushProperties().getThicknessProperty().bind(slider);
		brushThicknessOptions.add(slider);
		
		buttonPanel.add(brushThicknessOptions);
		view.add(buttonPanel, horizontal ? BorderLayout.WEST : BorderLayout.NORTH);
		
		// Add other buttons
		
		JToolBar otherButtonsPane = new JToolBar();
		otherButtonsPane.setLayout(new BoxLayout(otherButtonsPane, horizontal ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
		otherButtonsPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		otherButtonsPane.setOpaque(false);
		
		networkControls = new NetworkControls(horizontal);
		otherButtonsPane.add(networkControls.getView());
		
		JButton helpButton = new JButton();
		helpButton.setIcon(HELP_ICON);
		helpButton.addActionListener((l) -> showHelp());
		otherButtonsPane.add(helpButton);
		
		view.add(otherButtonsPane, horizontal ? BorderLayout.EAST : BorderLayout.SOUTH);
	}

	private SelectedButtonPanel getButtonPanel(boolean horizontal) {
		SelectedButtonPanel panel = new SelectedButtonPanel(horizontal, highlightColor, true);
		panel.setFolding(true);
		panel.setFloatable(false);
		return panel;
	}

	private JToolBar getPanel(boolean horizontal) {
		JToolBar panel = new JToolBar(horizontal ? JToolBar.HORIZONTAL : JToolBar.VERTICAL);
		panel.setOpaque(false);
		panel.setFloatable(false);
		return panel;
	}
	
	private void showHelp() {
		help.showAsDialog();
	}
	
	@Override
	public JToolBar getView() {
		return view;
	}

	public void setBackground(Color color) {
		view.setBackground(color);
	}
}
