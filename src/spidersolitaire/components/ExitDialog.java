package spidersolitaire.components;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import spidersolitaire.SolitaireManager;

public enum ExitDialog {
	INSTANCE;

	private JDialog dialog = new JDialog();

	private ExitDialog() {
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (ae.getActionCommand().equals("Save")) {
					SolitaireManager.saveGame();
					SolitaireManager.closeGame();
				} else if (ae.getActionCommand().equals("Don't Save")) {
					SolitaireManager.clearGameSave();
					SolitaireManager.closeGame();
				} else if (ae.getActionCommand().equals("Cancel")) {
					dialog.setVisible(false);
				}
			}
		};
		
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setTitle("Exit from game");
		dialog.setLayout(null);
		dialog.setSize(300, 110);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setAlwaysOnTop(true);

		dialog.add(ComponentsFactory.createJLabel(10, 10, 300, 20,
				"The current game is not finished. What should you do?"));
		
		JButton saveBtn = new JButton("Save");
		saveBtn.setBounds(10, 40, 70, 30);
		saveBtn.addActionListener(actionListener);
		dialog.add(saveBtn);
		
		JButton dontsaveBtn = new JButton("Don't Save");
		dontsaveBtn.setBounds(90, 40, 110, 30);
		dontsaveBtn.addActionListener(actionListener);
		dialog.add(dontsaveBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(210, 40, 70, 30);
		cancelBtn.addActionListener(actionListener);
		dialog.add(cancelBtn);
	}

	public void show() {
		dialog.setVisible(true);
	}
}
