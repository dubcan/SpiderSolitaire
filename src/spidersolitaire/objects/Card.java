package spidersolitaire.objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import spidersolitaire.utils.ImageEffects;
import spidersolitaire.utils.Images;

public class Card {

	public enum Suit {
		ACE(1, "/spadesace.jpg"), TWO(2, "/spades2.jpg"), THREE(3,
				"/spades3.jpg"), FOUR(4, "/spades4.jpg"), FIVE(5,
				"/spades5.jpg"), SIX(6, "/spades6.jpg"), SEVEN(7,
				"/spades7.jpg"), EIGHT(8, "/spades8.jpg"), NINE(9,
				"/spades9.jpg"), TEN(10, "/spades10.jpg"), JACK(11,
				"/spadesjack.jpg"), QUEEN(12, "/spadesqueen.jpg"), KING(13,
				"/spadesking.jpg"), FIELD(14, "/cardfield.png"), DECK(0,
				"/cardback.jpg");

		private int rate;
		private String faceImageLocation;

		private Suit(int rate, String faceImageLocation) {
			this.rate = rate;
			this.faceImageLocation = faceImageLocation;
		}

		public int getRate() {
			return rate;
		}

		public String getFaceLocation() {
			return faceImageLocation;
		}
	}

	private boolean isOvered = false;
	private boolean isPressed = false;
	private boolean isClicked = false;
	private boolean isTurnedBack = false;
	private boolean isDragging = false;
	private boolean isUnVisible = false;
	private boolean isInAir = false;
	private boolean isPushedDown = false;
	private boolean isHintShowed = false;

	private Point currPos = new Point();
	private Point lastPos = new Point();
	private Point clickedPos = new Point();

	private Card child;
	private Card parent;
	private Card lastParent;

	private CardMagnetArea magnetArea = new CardMagnetArea();
	private Point locateOffset = new Point(0, 15);

//	private String backLocation = "/cardback.jpg";

	private BufferedImage bufferedImage;
	private BufferedImage faceImage;
	private BufferedImage backImage;
	private BufferedImage shadowImage;
	private BufferedImage overShadowImage;
	private BufferedImage pressedShadowImage;
	private BufferedImage hintShadowImage;

	private Suit suit;

	public Card(Suit suit) {
		this.suit = suit;
		faceImage = Images.load(suit.getFaceLocation());
		shadowImage = Images.load("/shadow.png");
		overShadowImage = Images.load("/overshadow.png");
		pressedShadowImage = Images.load("/pressedshadow.png");
		hintShadowImage = Images.load("/hintshadow.png");
		backImage = Images.load("/cardback.jpg");
		
		bufferedImage = faceImage;
	}
	
	public void setLocateOffset(Point lockateOffset) {
		this.locateOffset = lockateOffset;
	}

	public CardField getField() {
		Card elder = processParent();
		if (elder instanceof CardField)
			return (CardField) elder;
		return null;
	}

	public Card getElderCard() {
		if (parent == null)
			return this;
		if (parent.isTurnedBack())
			return this;
		if (parent.getRate() == Card.Suit.FIELD.getRate())
			return this;
		return parent.getElderCard();
	}

	public Card getElderClickedCard() {
		if (!isClicked)
			return null;

		if (parent != null && parent.isClicked)
			return parent.getElderClickedCard();

		return this;
	}

	public Card getChild() {
		return child;
	}

	public Rectangle getMagnetArea() {
		magnetArea.setPos(currPos);
		return magnetArea.getArea();
	}

	public Card getParent() {
		return parent;
	}

	public int getRate() {
		return suit.getRate();
	}

	public List<Card> getAttachedCards() {
		if (child != null) {
			ArrayList<Card> attachedCards = new ArrayList<Card>();
			child.processAttachedCard(attachedCards);
			return attachedCards;
		} else {
			return null;
		}
	}

	public Rectangle getArea() {
		return new Rectangle(currPos, new Dimension(bufferedImage.getWidth(),
				bufferedImage.getHeight()));
	}

	public Point getLocation() {
		return getArea().getLocation();
	}

	public Card getKing() {
		Card elder = getElderCard();
		if (elder != null && elder.getRate() == Suit.KING.getRate())
			return elder;
		return null;
	}

	public Card getLastChild() {
		if (child != null)
			return child.processChild();
		return null;
	}
	
	public Card getLastParent() {
		return lastParent;
	}

	public void setParent(Card parent) {
		this.parent = parent;
		if (parent != null)
			lastParent = parent;
	}

	public boolean isParent(Card parent) {
		if (this.parent != null)
			return this.parent.equals(parent);

		return false;
	}

	public boolean isChild(Card child) {
		if (this.child != null)
			return this.child.equals(child);

		return false;
	}

	public boolean isMarketToDestroy() {
		return isUnVisible;
	}

	public boolean isOvered() {
		return isOvered;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public boolean isDragging() {
		return isDragging;
	}

	public boolean isTurnedBack() {
		return isTurnedBack;
	}

	public boolean isChildsCorrect() {
		if (child == null)
			return true;

		return child.processChildCorrect(suit.getRate());
	}
	
	public void show() {
		isUnVisible = false;
	}

	@Override
	public String toString() {
		return suit.toString();
	}

	public void showHint() {
		isHintShowed = true;
	}

	public Card processParent() {
		if (parent != null)
			return parent.processParent();
		return this;
	}

	public Card processChild() {
		if (child != null)
			return child.processChild();
		return this;
	}

	public void attach(Card card) {
		this.child = card;
		child.setParent(this);
		card.locateBelow(getLocation());
	}

	public boolean hasAttachedCard() {
		if (child != null)
			return true;
		return false;
	}

	public void turnFace() {
		isTurnedBack = false;
		bufferedImage = faceImage;
	}

	public void turnBack() {
		isTurnedBack = true;
		bufferedImage = backImage;
	}

	public boolean processChildCorrect(int parentRate) {
		if (suit.getRate() != parentRate - 1)
			return false;

		if (child != null)
			return child.processChildCorrect(getRate());
		return true;
	}
	
	public void resetState() {
		isOvered = false;
		isPressed = false;
		isClicked = false;
		isTurnedBack = false;
		isDragging = false;
		isUnVisible = true;
		isInAir = false;
		isPushedDown = false;
		isHintShowed = false;
		
		currPos.x = 0;
		currPos.y = 0;
		
		lastPos.x = 0;
		lastPos.y = 0;
		
		clickedPos.x = 0;
		clickedPos.y = 0;
		
		turnFace();

		//TODO later надо что-то придумать
		disconnectChild();
//		child;
//		parent;
//		lastParent;
	}

	public void mouseOver() {
		if (isTurnedBack)
			return;

		isOvered = true;
	}

	public void mouseOut() {
		isOvered = false;
	}

	public void press(Point clickedPoint) {
		if (!isOvered) 
			return;
		
		clickedPos.x = clickedPoint.x - currPos.x;
		clickedPos.y = clickedPoint.y - currPos.y;
		isPressed = true;
	}

	public void release() {
		if (isOvered && !isDragging)
			click();

		isPressed = false;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void click() {
		processClick(!isClicked);
	}

	public void processClick(boolean value) {
		isClicked = value;

		if (child != null) {
			child.processClick(value);

			if (value)
				child.pushDown();
			else
				child.pushUp();
		}

	}

	public void pushDown() {
		if (isPushedDown)
			return;

		isPushedDown = true;
		moveToPos(currPos.x, currPos.y + 15);
	}

	public void pushUp() {
		if (!isPushedDown)
			return;

		isPushedDown = false;
		moveToPos(currPos.x, currPos.y - 15);
	}

	public void disconnectChild() {
		if (child != null) {
			child.setParent(null);
			child = null;
		}
	}

	public void processAttachedCard(ArrayList<Card> attachedCards) {
		attachedCards.add(this);
		if (child != null) {
			child.processAttachedCard(attachedCards);
		}
	}

	public void render(Graphics g) {

		if (isHintShowed)
			g.drawImage(hintShadowImage, currPos.x - 10, currPos.y - 10, null);

		if (isInAir) {
			g.drawImage(shadowImage, currPos.x - 7, currPos.y - 7, null);
		} else {
			if (isClicked)
				g.drawImage(pressedShadowImage, currPos.x - 10, currPos.y - 10,
						null);
		}

		if (!isTurnedBack && isOvered) {
			g.drawImage(overShadowImage, currPos.x - 10, currPos.y - 10, null);
			ImageEffects.drawBrighterImage((Graphics2D) g, this.bufferedImage,
					currPos.x, currPos.y, (float) 1.2);
		} else {
			g.drawImage(bufferedImage, currPos.x, currPos.y, null);
		}
	}

	public void updateCurrPos(Point cursorPos) {
		currPos.x = cursorPos.x - clickedPos.x;
		currPos.y = cursorPos.y - clickedPos.y;
		moveToPos(currPos.x, currPos.y);
	}

	public void moveToPos(int posX, int posY) {
		Point loc = new Point(posX, posY);
		currPos.setLocation(loc);
		if (child != null) {
			child.moveToPos(currPos.x, currPos.y + 15);
		}
	}

	public void locateBelow(Point pos) {
		currPos.setLocation(pos);
		currPos.x += locateOffset.x;
		currPos.y += locateOffset.y;
		lastPos.setLocation(currPos);
		if (child != null) {
			child.locateBelow(currPos);
		}
	}

	public void startDrag(Point dragPos) {
		updateCurrPos(dragPos);
		isDragging = true;
		setInAir(true);

		if (parent != null) {
			parent.disconnectChild();
		}
	}

	public void setInAir(boolean value) {
		isInAir = value;
		if (child != null)
			child.setInAir(value);
	}

	public void stopDrag() {
		isDragging = false;
		setInAir(false);
	}

	public void moveBack() {
		if (lastParent != null) {
			lastParent.attach(this);
//			this.locateBelow(lastParent.getLocation());
		}
		if (child != null) {
			child.moveBack();
		}
	}
	
	public boolean isVisible() {
		return !isUnVisible;
	}

	public void hide() {
		//TODO раскомментировать позже
//		disconnect();
//		if (parent != null)
//			parent.disconnect();

		isUnVisible = true;
	}
	
	public void disconnectParent() {
		if (parent != null)
			parent.disconnectChild();
	}
	
	public void totalDisconnect() {
		disconnectChild();
		disconnectParent();
	}

	public void hideHint() {
		isHintShowed = false;
	}
}