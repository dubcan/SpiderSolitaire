package spidersolitaire;

import spidersolitaire.components.SolitaireFrame;
import spidersolitaire.utils.UIStyler;

public class Main {

	public static void main(String[] args) {
		UIStyler.setWindowStyleUI();

		Solitaire solitaire = new Solitaire();

		SolitaireFrame solitaireFrame = new SolitaireFrame();
		solitaireFrame.add(solitaire.getCanvas());

		solitaire.start();
	}
}