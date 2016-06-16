package spidersolitaire.objects;

import java.awt.Point;

public class CardField extends Card {
	private int column;
	public CardField(Point initPos, int column) {
		super(Card.Suit.FIELD);
		this.column = column;
		moveToPos(initPos.x, initPos.y);
	}
	
	@Override
	public void resetState() {
		disconnectChild();
	}
	
	@Override
	public void processClick(boolean value) {
	}
	
	@Override
	public void release() {
	}
	
	@Override
	public void click() {
	}
	
	public int getColumn() {
		return column;
	}
	
	@Override
	public void startDrag(Point cursorPos) {
	}
	
	@Override
	public void mouseOver() {
	}
}