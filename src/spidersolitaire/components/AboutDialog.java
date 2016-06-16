package spidersolitaire.components;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;

import spidersolitaire.utils.WebUtils;

public enum AboutDialog {
	INSTANCE;
	
	private JDialog dialog = new JDialog();

	private AboutDialog() {

		JLabel jLabel = new JLabel(
				"<html><body>Created by Ivan Dubkov<br>Inspired by Windows 7 Spider Solitaire<br>Sources availible on sourceforge.net</body></html>");
		jLabel.setLayout(new FlowLayout(FlowLayout.CENTER));
		jLabel.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 16));
		dialog.add(jLabel);

		JLabel link = new JLabel();
		link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		WebUtils.goWebsite(link, "http://sourceforge.net/", "sourceforge.net");
		dialog.add(link);

		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setTitle("About");
		dialog.setLayout(new FlowLayout(FlowLayout.CENTER));
		dialog.setSize(300, 200);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setAlwaysOnTop(true);
	}
	
	public void show() {
		dialog.setVisible(true);
	}
}
