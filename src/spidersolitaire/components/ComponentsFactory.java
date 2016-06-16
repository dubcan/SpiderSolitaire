package spidersolitaire.components;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ComponentsFactory {

	public static JLabel createJLabel(int x, int y, int width, int height,
			String label) {
		JLabel jLabel = new JLabel(label);
		jLabel.setBounds(x, y, width, height);
		return jLabel;
	}

	public static JSeparator createVertSeparator(int x, int y, int width,
			int height) {
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setBounds(x, y, width, height);
		return separator;
	}
}
