package spidersolitaire.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import spidersolitaire.SolitaireManager;
import spidersolitaire.objects.CardCommandManager;

public class Menu extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static Menu instance;
	
	public static void setFrame(JFrame frame) {
	}

	public static Menu getInstance() {
		if (instance == null)
			instance = new Menu();

		return instance;
	}

	private Menu() {
		JMenu menu;
		JMenuItem menuItem;

		menu = new JMenu("Game");
		add(menu);

		menuItem = new JMenuItem("New Game", KeyEvent.VK_F2);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("F2"));
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menu.addSeparator();

		menuItem = new JMenuItem("Cancel", KeyEvent.VK_Z
				+ ActionEvent.CTRL_MASK);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem("Hint", KeyEvent.VK_H);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("H"));
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menu.addSeparator();

		menuItem = new JMenuItem("Stats", KeyEvent.VK_F4);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("F4"));
		menuItem.setEnabled(false);
		menu.add(menuItem);

		menuItem = new JMenuItem("Params", KeyEvent.VK_F5);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		menuItem.setEnabled(false);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Exit");
		menu.add(menuItem);

		menuItem.addActionListener(this);

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"This menu does nothing");
		add(menu);

		menuItem = new JMenuItem("About");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String actionCommand = ae.getActionCommand();
		
		if (actionCommand.equals("Exit")) {
			ExitDialog.INSTANCE.show();
		} else if (actionCommand.equals("About")) {
			AboutDialog.INSTANCE.show();
		} else if (actionCommand.equals("Hint")) {
			SolitaireManager.showHint();
		} else if (actionCommand.equals("New Game")) {
			SolitaireManager.startGame();
		} else if (actionCommand.equals("Cancel")) {
			CardCommandManager.undo();
		}
	}
}
