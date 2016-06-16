package spidersolitaire.objects;

import java.awt.Point;

public class DeckOfCards extends Card {
	private IDealer dealer;
	private int startIndex;
	private int endIndex;

	public DeckOfCards(int startIndex, int endIndex, IDealer dealer) {
		super(Card.Suit.DECK);
		setLocateOffset(new Point(-20, 0));
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.dealer = dealer;
	}

	@Override
	public String toString() {
		return "DECK " + startIndex + " - " + endIndex;
	}

	@Override
	public void mouseOver() {
		if (getChild() != null) {
			return;
		} else {
			super.mouseOver();
		}
	}
	
	@Override
	public void startDrag(Point dragPos) {
	}
	
	@Override
	public void processClick(boolean value) {
	}

	@Override
	public void click() {
		if (isMarketToDestroy())
			return;
		
		//TODO uncomment later
		
//		dealer.dealCards(startIndex, endIndex);
		CardCommandManager.executeDeal(this, dealer, startIndex, endIndex);
	}
}