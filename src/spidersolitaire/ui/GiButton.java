package spidersolitaire.ui;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import spidersolitaire.utils.ImageEffects;
import spidersolitaire.utils.Images;

public class GiButton {
	private BufferedImage bufferedImage = Images.load("/button.png");
	private Point currPos;
	private String label;
	private Point clickedPos = new Point();
	private boolean isOvered = false;
	private boolean isPressed = false;
	
	GiButton(Point currPos, String label) {
		this.currPos = currPos;
		this.label = label;
		paintLabel();
	}
	
	public String getLabel() {
		return label;
	}
	
	public void render(Graphics g) {
		if (!isOvered) {
			g.drawImage(bufferedImage, currPos.x, currPos.y, null);			
		} else {
			ImageEffects.drawBrighterImage((Graphics2D) g, this.bufferedImage, currPos.x, currPos.y, (float) 1.2);
		}
	}
	
	public Rectangle getArea() {
		return new Rectangle(currPos, new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
	}
	
	public void calcCurrPos(Point cursorPos) {
		currPos.x = cursorPos.x - clickedPos.x;
		currPos.y = cursorPos.y - clickedPos.y;
	}
	
	public void calcClickedPos(Point cursorPos) {
		clickedPos.x = cursorPos.x - currPos.x;
		clickedPos.y = cursorPos.y - currPos.y;
	}
	
	public void mouseOver() {
		if (isOvered) {
			return;
		}
		isOvered = true;
	}
	
	public void mouseOut() {
		if (!isOvered) {
			return;
		}
		isOvered = false;
		mouseUp();
	}
	
	public void mouseDown() {
		isPressed = true;
		currPos.y += 1;
	}
	
	public void mouseUp() {
		if (!isPressed)
			return;
		isPressed = false;
		currPos.y -= 1;
	}

	private void paintLabel() {
		int w = bufferedImage.getWidth();
		int h = bufferedImage.getHeight();
		BufferedImage img = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(bufferedImage, 0, 0, null);
		String s = label;
		g2d.drawString(s, 20, 22);
		g2d.dispose();
		bufferedImage = img;
	}
}