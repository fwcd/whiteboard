package fwcd.whiteboard.client.view.core;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fwcd.fructose.swing.ResourceImage;
import fwcd.fructose.swing.View;
import fwcd.whiteboard.client.view.utils.BoolIndicatorLight;

public class NetworkControls implements View {
	private static final Icon ICON = new ResourceImage("/networkIcon.png").getAsIcon();
	private JPanel component;

	private ConnectionDialog dialog = new ConnectionDialog();

	private BoolIndicatorLight indicator;
	private JLabel statusLabel;
	private JButton connectButton;

	public NetworkControls(boolean horizontal) {
		component = new JPanel();
		component.setLayout(new BoxLayout(component, horizontal ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
		component.setBorder(new EmptyBorder(0, 0, 0, 0));
		component.setOpaque(false);

		indicator = new BoolIndicatorLight();
		component.add(indicator.getComponent());

		statusLabel = new JLabel();
		component.add(statusLabel);

		connectButton = new JButton(ICON);
		connectButton.setEnabled(false); // TODO: Implement networking properly and enable this button
		connectButton.addActionListener((l) -> {
			try {
				if (dialog.show()) {
					// TODO
					System.out.println(dialog.getHost() + ":" + dialog.getPort());
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid IP address!");
			}
		});
		component.add(connectButton);
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
}
