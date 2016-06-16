package spidersolitaire.effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FireWork {

	private boolean isExploded;
	private boolean isLaunched;

	private Point vel = new Point(0, -3);
	private Point currPos;
	private Point targetPos;
	private Color color;
	private List<Particle> particles = new ArrayList<Particle>();

	public boolean isLaunched() {
		return isLaunched;
	}

	public void launch(Point currPos, Point targetPos, Color color) {
		isLaunched = true;
		isExploded = false;
		this.currPos = currPos;
		this.targetPos = targetPos;
		this.color = color;
	}

	public void render(Graphics g) {
		if (!isExploded) {
			if (currPos.y > targetPos.y) {
				currPos.y += vel.y;
				createParticles(currPos, 3, 1);
			} else {
				createParticles(currPos, 200, 5);
				isExploded = true;
			}
		}

		g.setColor(color);

		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext()) {
			Particle particle = iterator.next();

			int randSize = (int) (Math.random() * 3) + 1;

			g.fillRect((int) particle.x, (int) particle.y, randSize, randSize);

			particle.velY += 0.1;
			particle.x += particle.velX;
			particle.y += particle.velY;

			if (particle.y >= 600) {
				iterator.remove();
			}
		}

		if (particles.size() == 0) {
			isLaunched = false;
		}
	}

	private void createParticles(Point pos, int numOfParticles, int speed) {
		for (int i = 0; i < numOfParticles; i++) {
			createParticle(pos, speed);
		}
	}

	private void createParticle(Point pos, int speed) {
		double length = Math.random() * speed;
		double angle = Math.random() * 360;
		double velX = length * Math.cos(angle);
		double velY = length * Math.sin(angle);

		particles.add(new Particle(pos.x, pos.y, velX, velY));
	}
}
