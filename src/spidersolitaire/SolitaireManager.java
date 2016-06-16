package spidersolitaire;

import javax.swing.JFrame;

import spidersolitaire.objects.GameStarter;
import spidersolitaire.objects.HintShower;

public class SolitaireManager {

	private static GameStarter gameStarter;
	private static JFrame frame;
	private static HintShower hintShower;
//	private static GameField gameField;

	private SolitaireManager() {
	}
	
	public static void saveGame() {
		System.out.println("saveGame");
//		if (gameField != null) {
//			try {
//				System.out.println("game data saved succesfully!");
//				IOUtils.writeObj(gameField, "gamefield.out");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public static void clearGameSave() {
		System.out.println("clearGameSave");
//		try {
//			System.out.println("clear game save!");
//			IOUtils.writeObj(null, "gamefield.out");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void loadGame() {
		System.out.println("clearGameSave");
//		try {
//			gameField = (GameField) IOUtils.readeObj("gamefield.out");
//		} catch (IOException e) {
//			System.out.println("cannot load gamefield.out because it's not exist");
//			e.printStackTrace();
//		}
	}
	
//	public static void setGameField(GameField gameField) {
//		SolitaireManager.gameField = gameField;		
//	}
	
	public static void setGameStarter(GameStarter gameStarter) {
		SolitaireManager.gameStarter = gameStarter;
	}
	
	public static void setHintShower(HintShower hintShower) {
		SolitaireManager.hintShower = hintShower;
	}
	
	public static void setFrame(JFrame frame) {
		SolitaireManager.frame = frame;
	}

	public static void showHint() {
		if (hintShower != null)
			hintShower.showHint();
	}
	
	public static void startGame() {
		if (gameStarter != null)
			gameStarter.newGame();
	}
	
	public static void closeGame() {
		if (frame != null)
			System.exit(0);
	}
}
