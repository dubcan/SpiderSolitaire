package spidersolitaire.components;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import spidersolitaire.Solitaire;
import spidersolitaire.SolitaireManager;

public class SolitaireFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	public SolitaireFrame() {
		super("Solitaire");
		setSize(Solitaire.GAME_WIDTH, Solitaire.GAME_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setJMenuBar(Menu.getInstance());
		
		BottomPanel bottomPanel = new BottomPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		addWindowListener(this);
		
		Menu.setFrame(this);
		SolitaireManager.setFrame(this);
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("windowClosing");
		ExitDialog.INSTANCE.show();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
