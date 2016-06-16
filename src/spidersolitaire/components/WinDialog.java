package spidersolitaire.components;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import spidersolitaire.SolitaireManager;
import spidersolitaire.Stats;

public enum WinDialog {
	INSTANCE;

	private JDialog dialog = new JDialog();

	private JLabel scoreLabel;
	private JLabel dateLabel;
	private JLabel recordLabel;

	public void show() {
		Stats.writeHighScore();
		updateLabels();
		dialog.setVisible(true);
	}

	private WinDialog() {
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (ae.getActionCommand().equals("New Game")) {
					SolitaireManager.startGame();
				} else if (ae.getActionCommand().equals("Exit")) {
					ExitDialog.INSTANCE.show();
				}
				dialog.setVisible(false);
			}
		};

		dialog.add(ComponentsFactory.createJLabel(100, 12, 200, 20,
				"Congratulations! You won the game!"));

		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.PAGE_AXIS));
		borderPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Game Score:"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		scoreLabel = new JLabel("Score");
		borderPanel.add(scoreLabel);
		borderPanel.setBounds(100, 40, 200, 70);

		dialog.add(Box.createRigidArea(new Dimension(10, 0)));
		dialog.add(borderPanel);

		dateLabel = ComponentsFactory.createJLabel(100, 120, 200, 20, "Date:");
		dialog.add(dateLabel);
		recordLabel = ComponentsFactory.createJLabel(100, 140, 200, 20,
				"Record: ");
		dialog.add(recordLabel);

		JButton newGameBtn = new JButton("New Game");
		newGameBtn.setBounds(100, 200, 100, 30);
		newGameBtn.addActionListener(actionListener);
		dialog.add(newGameBtn);

		JButton exitBtn = new JButton("Exit");
		exitBtn.setBounds(210, 200, 100, 30);
		exitBtn.addActionListener(actionListener);
		dialog.add(exitBtn);

		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setTitle("The Game is WON!");
		dialog.setLayout(null);
		dialog.setSize(new Dimension(400, 300));
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setAlwaysOnTop(true);
	}

	private void updateLabels() {
		scoreLabel.setText("Score: " + Stats.getScore() + "  Steps: "
				+ Stats.getTurns());
		dateLabel.setText("Date: "
				+ new Date(System.currentTimeMillis()).toString());
		recordLabel.setText("Record: " + Stats.getHighScore());
	}
}
