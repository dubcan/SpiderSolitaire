package spidersolitaire;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import spidersolitaire.components.SolitaireCanvas;
import spidersolitaire.objects.GameField;
import spidersolitaire.objects.GameStarter;

public class Solitaire implements Runnable, GameStarter {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 560;

	private boolean running = true;
	private Thread thread;

	private BufferedImage offscreenImage = new BufferedImage(GAME_WIDTH,
			GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private Graphics graphics = offscreenImage.getGraphics();
	private SolitaireCanvas canvas;

	private GameField gameField = new GameField();

	public Solitaire() {
		canvas = new SolitaireCanvas(gameField);

		SolitaireManager.setGameStarter(this);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void run() {
		while (running) {
			gameField.updateCards();

			graphics.drawImage(gameField.getImage(), 0, 0, null);
			gameField.render(graphics);

			Graphics g = canvas.getGraphics();

			if (g != null) {
				g.drawImage(offscreenImage, 0, 0, null);
				g.dispose();
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void newGame() {
		Stats.reset();
		gameField.reset();
	}

	public void start() {
		running = true;

		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		running = false;
	}
}
