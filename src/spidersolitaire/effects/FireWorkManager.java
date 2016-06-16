package spidersolitaire.effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class FireWorkManager {

	private static FireWork[] fireWorks = new FireWork[5];
	private static boolean isLaunched = false;
	
	static {
		for (int i = 0; i < fireWorks.length; i++) {
			fireWorks[i] = new FireWork();
		}
	}
	
	private FireWorkManager() {
	}
	
	public static boolean isLaunched() {
		return isLaunched;
	}
	
	public static void launch() {
		isLaunched = true;
	}
	
	public static void stop() {
		isLaunched = false;
	}

	public static void render(Graphics g) {
		for (FireWork fireWork : fireWorks) {
			if (!fireWork.isLaunched()) {
				Random randomGenerator = new Random();
				int randX = randomGenerator.nextInt(700) + 30;
				int targetY = randomGenerator.nextInt(400) + 100;

				int minColor = 20;
				int colorOffset = 255 - minColor;
				int red = randomGenerator.nextInt(minColor) + colorOffset;
				int green = randomGenerator.nextInt(minColor) + colorOffset;
				int blue = randomGenerator.nextInt(minColor) + colorOffset;

				Color randomColour = new Color(red, green, blue);

				fireWork.launch(new Point(randX, 600),
						new Point(randX, targetY), randomColour);
			} else {
				fireWork.render(g);
			}
		}
	}
}
