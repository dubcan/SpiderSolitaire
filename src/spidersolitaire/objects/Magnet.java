package spidersolitaire.objects;

import java.awt.Point;
import java.awt.Rectangle;

public class Magnet {
	private Rectangle area;
	private int offsetX;
	private int offsetY;

	Magnet(Rectangle area) {
		this.area = area;
		offsetX = area.x;
		offsetY = area.y;
	}
	
	public void setPos(Point pos) {
		area.setLocation(pos.x + offsetX, pos.y + offsetY);
	}
	
	public Rectangle getArea() {
		return area;
	}
}