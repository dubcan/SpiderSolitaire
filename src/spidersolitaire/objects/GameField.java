package spidersolitaire.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import spidersolitaire.Settings;
import spidersolitaire.Stats;
import spidersolitaire.components.WinDialog;
import spidersolitaire.effects.FireWorkManager;
import spidersolitaire.objects.Card.Suit;
import spidersolitaire.utils.Images;
import spidersolitaire.utils.RaisingComparator;

public class GameField implements HintShower, IDealer {
	
	private final int NUM_OF_FIELDS = 10;
	private final BufferedImage image = Images.load("/background.jpg");

	private RaisingComparator<Card> raisingComparator = new RaisingComparator<Card>();
	private Point dragCursorPos;
	private Card pressedCard;
	private Card topCard;

	private List<Card> allCards = new LinkedList<Card>();
	private List<CardField> cardsFields = new LinkedList<CardField>();
	private List<Card> decksOfCards = new LinkedList<Card>();
	private List<Card> playingCards = new LinkedList<Card>();

	private DeckOfCards pack10Fifth;
	private DeckOfCards pack10Fourth;
	private DeckOfCards pack10Third;
	private DeckOfCards pack10Second;
	private DeckOfCards pack10First;
	private DeckOfCards pack54;

	public GameField() {
		for (int i = 0; i < NUM_OF_FIELDS; i++) {
			cardsFields.add(new CardField(new Point(10 + i * 78, 0), i));
		}
		allCards.addAll(cardsFields);

		if (Settings.IS_NEW_GAME) {
			for (int i = 0; i < 8; i++) {
				playingCards.add(new Card(Card.Suit.ACE));
				playingCards.add(new Card(Card.Suit.TWO));
				playingCards.add(new Card(Card.Suit.THREE));
				playingCards.add(new Card(Card.Suit.FOUR));
				playingCards.add(new Card(Card.Suit.FIVE));
				playingCards.add(new Card(Card.Suit.SIX));
				playingCards.add(new Card(Card.Suit.SEVEN));
				playingCards.add(new Card(Card.Suit.EIGHT));
				playingCards.add(new Card(Card.Suit.NINE));
				playingCards.add(new Card(Card.Suit.TEN));
				playingCards.add(new Card(Card.Suit.JACK));
				playingCards.add(new Card(Card.Suit.QUEEN));
				playingCards.add(new Card(Card.Suit.KING));
			}
			
			allCards.addAll(playingCards);
			
			pack10Fifth = createPack(94, 104, 400, 300);
			pack10Fourth = createPack(84, 94, 380, 300);
			pack10Third = createPack(74, 84, 360, 300);
			pack10Second = createPack(64, 74, 340, 300);
			pack10First = createPack(54, 64, 320, 300);
			pack54 = createPack(0, 54, 300, 300);
			allCards.addAll(decksOfCards);
			
			reset();
		} else {
			generateCardsFromData();
		}
	}

	private void generateCardsFromData() {
		String[] cardsData = new String[3];
		cardsData[0] = "ACE_back";
		cardsData[1] = "ACE_back";
		cardsData[2] = "ACE_face";
		
		for (String data : cardsData) {
			Card card = null;
			
			if (data.indexOf("ACE") > -1) 
				card = new Card(Suit.ACE);
			
			if (card == null)
				return;
			
			card.resetState();
			playingCards.add(card);
			allCards.addAll(playingCards);
			
			
			if (data.indexOf("back") > -1) 
				CardCommandManager.executeTurnBack(card);
			else
				CardCommandManager.executeTurnFace(card);
			
			card.show();
			
			CardField cardField = cardsFields.get(0);
			if (cardField.getLastChild() != null)
				cardField.getLastChild().attach(card);
			else
				cardField.attach(card);
		}
	}

	public Card getElderDeck() {
		return pack10Fifth;
	}

	@Override
	public void dealCards(int cardsStartIndex, int cardsEndIndex) {
		for (int i = cardsStartIndex; i < cardsEndIndex; i++) {
			Card card = playingCards.get(i);

			CardField cardField = cardsFields.get(i % NUM_OF_FIELDS);
			Card lastChild = cardField.getLastChild();
			if (lastChild != null) {
				lastChild.attach(card);
//				card.locateBelow(lastChild.getLocation());
			} else {
				cardField.attach(card);
//				card.locateBelow(cardField.getLocation());
			}

			int turnedCardIndex = i + 10;
			int deltaIndex = cardsEndIndex - cardsStartIndex;
			if (turnedCardIndex < deltaIndex) {
				CardCommandManager.executeTurnBack(card);
			}

			card.show();
		}
	}

	@Override
	public void undealCards(DeckOfCards topDeck, int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			Card card = playingCards.get(i);
			card.hide();
			card.totalDisconnect();
		}
	}

	private DeckOfCards createPack(int startIndex, int endIndex, int posX,
			int posY) {
		DeckOfCards deckOfCards = new DeckOfCards(startIndex, endIndex, this);
		deckOfCards.moveToPos(posX, posY);
		decksOfCards.add(deckOfCards);
		return deckOfCards;
	}

	// TODO эту фичу надо основательно подрефакторить
	@Override
	public void showHint() {
		for (Card card : allCards) {
			card.hideHint();
		}

		for (Card card : allCards) {
			if (card instanceof CardField)
				continue;

			if (card instanceof DeckOfCards)
				continue;

			if (card.isTurnedBack())
				continue;

			if (!card.isChildsCorrect())
				continue;

			for (Card anotherCard : allCards) {
				if (anotherCard.equals(card))
					continue;

				if (anotherCard instanceof CardField)
					continue;

				if (anotherCard instanceof DeckOfCards)
					continue;

				if (anotherCard.isTurnedBack())
					continue;

				if (!anotherCard.isChildsCorrect())
					continue;

				if (anotherCard.getRate() == (card.getRate() + 1)
						|| anotherCard.getRate() == (card.getRate() - 1)) {

					if (anotherCard.getField() == card.getField())
						continue;

					anotherCard.showHint();
					card.showHint();
					return;
				}
			}
		}
	}

	public void mouseDragged(Point dragPos) {
		this.dragCursorPos = dragPos;
		
		if (topCard != null && topCard.isChildsCorrect())
			topCard.startDrag(dragPos);
	}

	public List<CardField> getCardFields() {
		return cardsFields;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void addCards(List<Card> cardsToDrop) {
		allCards.addAll(cardsToDrop);
	}

	public void addCard(Card card) {
		allCards.add(card);
	}

	public void render(Graphics g) {
		for (Card card : allCards) {
			if (!card.isMarketToDestroy())
				card.render(g);
		}
		
		if (FireWorkManager.isLaunched())
			FireWorkManager.render(g);
	}

	public void mouseMoved(Point movedPoint) {
		Card topCard = null;

		for (Card card : allCards) {
			card.mouseOut();
			if (!card.isMarketToDestroy()
					&& card.getArea().contains(movedPoint))
				topCard = card;
		}

		if (topCard != null) {
			topCard.mouseOver();
		}
	}
	
	public void mouseRelease(Point cursorPos) {
		
		this.dragCursorPos = cursorPos;

		clickLogic();

		// TODO concurrent exception
		for (Card card : allCards) {
			if (!card.isDragging())
				continue;
			card.stopDrag();

			for (Card anotherCard : allCards) {
				if (anotherCard.isMarketToDestroy())
					continue;

				if (anotherCard.equals(card))
					continue;

				if (anotherCard.hasAttachedCard())
					continue;

				boolean itsAttachedCard = false;
				List<Card> attachedCards = card.getAttachedCards();
				if (attachedCards != null) {
					for (Card attachedCard : card.getAttachedCards()) {
						if (anotherCard.equals(attachedCard))
							itsAttachedCard = true;
					}
					if (itsAttachedCard)
						continue;
				}

				if (anotherCard.getMagnetArea()
						.intersects(card.getMagnetArea())) {
					if (!(anotherCard instanceof CardField)) {
						if (anotherCard.getRate() != (card.getRate() + 1)) {
							card.moveBack();
							break;
						}
					}

					Stats.decrease();
					CardCommandManager.executeDrop(anotherCard, card);

					updateCardsFaces();
				}
			}

			if (card.getParent() == null) {
				card.moveBack();
			}

			Card king = card.getKing();
			if (king != null && king.hasAttachedCard()) {
				List<Card> set = new ArrayList<Card>();
				set.add(king);
				set.addAll(king.getAttachedCards());
				if (isSetCorrect(set)) {
					
					CardCommandManager.executeCompleteSet(set);
					
					updateCardsFaces();
					Stats.bonus();
					
					if (isWin()) 
						win();	
				}
			}
		}
	}
	
	private void win() {
		FireWorkManager.launch();
	}
	
	private boolean isWin() {
		for (Card deckOfCard : decksOfCards) {
			if (deckOfCard.isVisible())
				return false;
		}
		
		for (Card playingCard : playingCards) {
			if (playingCard.isVisible())
				return false;
		}
		
		return true;
	}

	public void setTopCard(Card topCard) {
		this.topCard = topCard;
	}

	public void press(Point clickedPoint) {
		if (FireWorkManager.isLaunched()) {
			FireWorkManager.stop();
			WinDialog.INSTANCE.show();
			return;
		}
		
		pressedCard = null;
		dragCursorPos = clickedPoint;

		topCard = null;

		for (Card card : allCards) {
			card.hideHint();

			if (!card.isMarketToDestroy()
					&& card.getArea().contains(clickedPoint))
				pressedCard = card;
		}

		if (pressedCard == null)
			return;

		pressedCard.press(clickedPoint);
		if (pressedCard.isTurnedBack()) {
			System.out.println("card is turned back so fuck off");
			return;
		}

		System.out.println("card turned face so its a top card " + topCard);
		topCard = pressedCard;
	}

	public void updateCards() {
		for (Card cardField : cardsFields) {
			List<Card> attachedCards = cardField.getAttachedCards();
			if (attachedCards != null && attachedCards.size() > 0) {
				for (Card attachedCard : attachedCards) {
					raisingComparator.raiseToTheTop(attachedCard, allCards);
				}
			}
		}
		
		if (topCard != null) {
			raisingComparator.raiseToTheTop(topCard, allCards);

			List<Card> attachedCards = topCard.getAttachedCards();
			if (attachedCards != null && attachedCards.size() > 0) {
				for (Card attachedCard : attachedCards) {
					raisingComparator.raiseToTheTop(attachedCard, allCards);
				}
			}
		}
		

		for (Card card : allCards) {
			if (card.isDragging())
				card.updateCurrPos(dragCursorPos);
		}
	}

	public void updateCardsFaces() {
		for (int i = 0; i < NUM_OF_FIELDS; i++) {
			CardField field = cardsFields.get(i);
			Card lastChild = field.getLastChild();
			if (lastChild != null) {
				if (lastChild.isTurnedBack()) 
					CardCommandManager.executeTurnFace(lastChild);
			}
		}
	}

	private boolean isSetCorrect(List<Card> set) {
		if (set == null)
			return false;
		if (set.size() != 13)
			return false;

		int parentRate = Card.Suit.KING.getRate() + 1;
		for (Card card : set) {
			if (card.getRate() != parentRate - 1)
				return false;
			parentRate--;
		}
		return true;
	}

	private void clickLogic() {
		Card clickedCard = null;
		for (Card card : allCards) {
			if (!card.isOvered())
				continue;

			if (!card.isChildsCorrect())
				continue;

			if (card.getElderClickedCard() != null) {
				card.getElderClickedCard().click();
				continue;
			}

			clickedCard = card;
			break;
		}

		for (Card card : allCards) {
			if (card.isTurnedBack())
				continue;

			if (card.equals(clickedCard))
				continue;

			card.processClick(false);
		}

		if (clickedCard != null)
			clickedCard.release();
	}

	public void reset() {
		FireWorkManager.stop();
		
		if (Settings.IS_CARDS_SHUFFLED)
			Collections.shuffle(playingCards);

		pack10Fifth.attach(pack10Fourth);
		pack10Fourth.attach(pack10Third);
		pack10Third.attach(pack10Second);
		pack10Second.attach(pack10First);
		pack10First.attach(pack54);

		setTopCard(pack10Fifth);

		for (Card deckOfCards : decksOfCards) {
			deckOfCards.show();
		}

		for (Card playingCard : playingCards) {
			playingCard.resetState();
		}

		for (CardField cardField : cardsFields) {
			cardField.resetState();
		}

		updateCards();
	}
}