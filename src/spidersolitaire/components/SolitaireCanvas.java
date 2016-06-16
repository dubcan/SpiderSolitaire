package spidersolitaire.components;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import spidersolitaire.Solitaire;
import spidersolitaire.effects.FireWorkManager;
import spidersolitaire.objects.GameField;

public class SolitaireCanvas extends Canvas implements MouseListener,
		MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private GameField gameField;

	public SolitaireCanvas(GameField gameField) {
		this.gameField = gameField;
		

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		setPreferredSize(new Dimension(Solitaire.GAME_WIDTH,
				Solitaire.GAME_HEIGHT));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		gameField.mouseDragged(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gameField.mouseMoved(e.getPoint());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gameField.press(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		gameField.mouseRelease(e.getPoint());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 87) { //w
			FireWorkManager.launch();
		} else if (e.getKeyCode() == 83) { //s
			ExitDialog.INSTANCE.show();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
