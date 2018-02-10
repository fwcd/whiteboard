package com.fwcd.whiteboard.ui;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fwcd.fructose.swing.ResourceImage;
import com.fwcd.fructose.swing.Viewable;
import com.fwcd.whiteboard.core.WhiteboardApp;

public class NetworkControls implements Viewable {
	private static final Icon ICON = new ResourceImage("/resources/networkIcon.png").getAsIcon();
	private JPanel view;

	private ConnectionDialog dialog = new ConnectionDialog();

	private BoolIndicatorLight indicator;
	private JLabel statusLabel;
	private JButton connectButton;

	public NetworkControls(boolean horizontal) {
		view = new JPanel();
		view.setLayout(new BoxLayout(view, horizontal ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
		view.setBorder(new EmptyBorder(0, 0, 0, 0));
		view.setOpaque(false);

		indicator = new BoolIndicatorLight();
		view.add(indicator.getView());

		statusLabel = new JLabel();
		view.add(statusLabel);

		connectButton = new JButton(ICON);
		connectButton.setEnabled(false); // TODO: Implement networking properly and enable this button
		connectButton.addActionListener((l) -> {
			try {
				if (dialog.show()) {
					new WhiteboardApp(dialog.getHost(), dialog.getPort());
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid IP address!");
			}
		});
		view.add(connectButton);
	}

	@Override
	public JComponent getView() {
		return view;
	}
}
