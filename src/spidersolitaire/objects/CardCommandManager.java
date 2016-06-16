package spidersolitaire.objects;

import java.util.ArrayList;
import java.util.List;

//TODO очень сложный таск. Сделаю его позже. Сейчас много проблем с отменой.
//Нет никакого 
public class CardCommandManager {

	private static List<Command> commands = new ArrayList<Command>();

	public static void executeDeal(DeckOfCards deck, IDealer dealer, int startIndex, int endIndex) {
		commands.add(new DealCardsCommand(deck, dealer, startIndex, endIndex));
	}
	
	public static void executeDrop(Card parentCard, Card childCard) {
		commands.add(new DropCardCommand(parentCard, childCard));
	}

	public static void executeCompleteSet(List<Card> set) {
		commands.add(new CompleteSetCommand(set));
	}

	public static void executeTurnBack(Card card) {
		commands.add(new TurnBackCommand(card));
	}

	public static void executeTurnFace(Card card) {
		commands.add(new TurnFaceCommand(card));
	}

	public static void reset() {
		commands.clear();
	}

	public static void undo() {
		if (getLastIndex() < 0)
			return;

		if (getLastPlayerCommand() == null)
			return;

		getLastCommand().undo();

		boolean isPlayerCommand = getLastCommand() instanceof PlayerCommand;
		commands.remove(getLastIndex());

		if (!isPlayerCommand)
			undo();
	}

	public static Command getLastPlayerCommand() {
		for (Command command : commands) {
			if (command instanceof PlayerCommand)
				return command;
		}
		return null;
	}

	private static Command getLastCommand() {
		return commands.get(getLastIndex());
	}

	private static int getLastIndex() {
		return commands.size() - 1;
	}

	static class DealCardsCommand implements Command, PlayerCommand {
		private IDealer dealer;
		private int startIndex;
		private int endIndex;
		private DeckOfCards deck;

		public DealCardsCommand(DeckOfCards deck, IDealer dealer, int startIndex, int endIndex) {
			this.deck = deck;
			this.dealer = dealer;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			execute();
		}

		@Override
		public void execute() {
			deck.hide();
			deck.totalDisconnect();
			dealer.dealCards(startIndex, endIndex);
		}

		@Override
		public void undo() {
			deck.moveBack();
			deck.show();
			dealer.undealCards(deck, startIndex, endIndex);
		}
	}

	static class TurnBackCommand implements Command {
		private Card card;

		public TurnBackCommand(Card card) {
			this.card = card;
			execute();
		}

		@Override
		public void execute() {
			card.turnBack();
		}

		@Override
		public void undo() {
			card.turnFace();
		}

	}

	static class TurnFaceCommand implements Command {
		private Card card;

		public TurnFaceCommand(Card card) {
			this.card = card;
			execute();
		}

		@Override
		public void execute() {
			card.turnFace();
		}

		@Override
		public void undo() {
			card.turnBack();
		}

	}

	static class CompleteSetCommand implements Command {
		private List<Card> set;

		public CompleteSetCommand(List<Card> set) {
			this.set = set;
			execute();
		}

		@Override
		public void execute() {
			for (Card card : set) {
				card.hide();

				if (card.getRate() == Card.Suit.KING.getRate())
					card.disconnectParent();
			}
		}

		@Override
		public void undo() {
			for (Card card : set) {
				card.show();

				if (card.getRate() == Card.Suit.KING.getRate())
					card.moveBack();
			}
		}
	}

	static class DropCardCommand implements Command, PlayerCommand {
		private Card parentCard;
		private Card childCard;
		private Card childLastParentCard;

		public DropCardCommand(Card parentCard, Card childCard) {
			this.parentCard = parentCard;
			this.childCard = childCard;
			this.childLastParentCard = childCard.getLastParent();
			execute();
		}

		@Override
		public void execute() {
			parentCard.attach(childCard);
//			childCard.locateBelow(parentCard.getLocation());
		}

		@Override
		public void undo() {
			parentCard.disconnectChild();
			childLastParentCard.attach(childCard);
//			childCard.locateBelow(childLastParentCard.getLocation());
		}
	}
}