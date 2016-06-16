package spidersolitaire.objects;

import java.awt.Rectangle;

public class CardMagnetArea extends Magnet {
	final static int OFFSET_X = 15;
	final static int OFFSET_Y = 5;
	final static int WIDTH = 40;
	final static int HEIGHT = 40;

	CardMagnetArea() {
		super(new Rectangle(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
	}
}