package fwcd.whiteboard.client.view.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import fwcd.fructose.swing.DrawGraphicsButton;
import fwcd.fructose.swing.HelpView;
import fwcd.fructose.swing.RangedSlider;
import fwcd.fructose.swing.Renderable;
import fwcd.fructose.swing.ResourceImage;
import fwcd.fructose.swing.SelectedButtonPanel;
import fwcd.fructose.swing.Viewable;
import fwcd.sketch.view.canvas.SketchBoardView;
import fwcd.sketch.view.tools.CommonSketchTool;
import fwcd.sketch.view.tools.SketchTool;
import fwcd.whiteboard.client.model.WhiteboardModel;
import fwcd.whiteboard.client.view.utils.ColorUtils;

public class SideBarView implements Viewable {
	private static final Icon HELP_ICON = new ResourceImage("/helpIcon.png").getAsIcon();
	private final Color highlightColor = Color.GRAY;
	
	private final JToolBar component;
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
	
	public SideBarView(SketchBoardView drawBoard, boolean horizontal, WhiteboardModel model) {
		component = new JToolBar(horizontal ? JToolBar.HORIZONTAL : JToolBar.VERTICAL);
		component.setOpaque(true);
		component.setFloatable(false);
		component.setLayout(new BorderLayout());
		component.setPreferredSize(horizontal ? new Dimension(200, 40) : new Dimension(40, 200));
		component.setMargin(new Insets(0, 0, 0, 0));
		component.setBorder(new EmptyBorder(0, 0, 0, 0));
		drawBoard.getModel().getBackground().listenAndFire(it -> component.setBackground(ColorUtils.softer(it)));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, horizontal ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
		
		// Add tool buttons
		
		SelectedButtonPanel toolsPanel = getButtonPanel(horizontal);
		
		for (CommonSketchTool enumTool : CommonSketchTool.values()) {
			SketchTool tool = enumTool.get();
			JButton button = new JButton();
			button.setIcon(tool.getIcon());
			toolsPanel.add(button, () -> drawBoard.getSelectedTool().set(tool));
			
			if (enumTool == CommonSketchTool.BRUSH) {
				toolsPanel.select(button);
			}
		}
		
		buttonPanel.add(toolsPanel.getComponent());
		
		// Add color buttons
		
		SelectedButtonPanel colorsPanel = getButtonPanel(horizontal);
		
		for (Color color : colors) {
			Renderable circle = (g2d, canvasSize) -> {
				g2d.setColor(color);
				
				int w = (int) canvasSize.getWidth();
				int h = (int) canvasSize.getHeight();
				int iconSize = (int) (Math.min(w, h) * 0.8D);
				
				g2d.fillOval((w / 2) - (iconSize / 2), (h / 2) - (iconSize / 2), iconSize, iconSize);
			};
			
			JButton button = new DrawGraphicsButton(new Dimension(24, 24), circle);
			colorsPanel.add(button, () -> drawBoard.getBrushProperties().setColor(color));
		}

		buttonPanel.add(colorsPanel.getComponent());
		
		// Add background buttons
		
		SelectedButtonPanel bgColorsPanel = getButtonPanel(horizontal);
		
		for (Color color : bgColors) {
			Renderable rect = (g2d, canvasSize) -> {
				g2d.setColor(color);
				g2d.fillRect(0, 0, (int) canvasSize.getWidth(), (int) canvasSize.getHeight());
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawRect(0, 0, (int) canvasSize.getWidth() - 1, (int) canvasSize.getHeight() - 1);
			};
			
			JButton button = new DrawGraphicsButton(new Dimension(24, 24), rect);
			bgColorsPanel.add(button, () -> {
				drawBoard.getModel().getBackground().set(color);
				drawBoard.repaint();
			});
		}

		buttonPanel.add(bgColorsPanel.getComponent());
		
		// Add brush thickness slider
		
		JToolBar brushThicknessOptions = getPanel(horizontal);
		
		RangedSlider slider = new RangedSlider();
		slider.setOpaque(false);
		slider.setAlignmentX(0);
		slider.setOrientation(horizontal ? SwingConstants.HORIZONTAL : SwingConstants.VERTICAL);
		slider.setPreferredSize(new Dimension(10, 80));
		drawBoard.getBrushProperties().getThicknessProperty().bind(slider);
		brushThicknessOptions.add(slider);
		
		buttonPanel.add(brushThicknessOptions);
		component.add(buttonPanel, horizontal ? BorderLayout.WEST : BorderLayout.NORTH);
		
		// Add other buttons
		
		JToolBar otherButtonsPane = new JToolBar();
		otherButtonsPane.setLayout(new BoxLayout(otherButtonsPane, horizontal ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
		otherButtonsPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		otherButtonsPane.setOpaque(false);
		
		networkControls = new NetworkControls(horizontal, model.getConnectionManager());
		otherButtonsPane.add(networkControls.getComponent());
		
		JButton helpButton = new JButton();
		helpButton.setIcon(HELP_ICON);
		helpButton.addActionListener((l) -> showHelp());
		otherButtonsPane.add(helpButton);
		
		component.add(otherButtonsPane, horizontal ? BorderLayout.EAST : BorderLayout.SOUTH);
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
	public JToolBar getComponent() {
		return component;
	}

	public void setBackground(Color color) {
		component.setBackground(color);
	}
}
