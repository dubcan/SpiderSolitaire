package spidersolitaire.objects;


public interface IDealer {
	void dealCards(int startIndex, int endIndex);
	void undealCards(DeckOfCards topDeck, int startIndex, int endIndex);
}
