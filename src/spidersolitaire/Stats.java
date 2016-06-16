package spidersolitaire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import spidersolitaire.objects.StatsObserver;
import spidersolitaire.utils.IOUtils;

public class Stats {
	private static final int INIT_SCORE = 500;
	private static final int DECREASE_SCORE = 1;
	private static final int BONUS_SCORE = 100;
	
	private static int score;
	private static int turns;
	private static int highScore;
	
	private static List<StatsObserver>observers = new ArrayList<StatsObserver>();
	
	static {
		reset();
		
		try {
			highScore = (int) IOUtils.readeObj("highscore.out");
		} catch (IOException e) {
			try {
				IOUtils.writeObj(0, "highscore.out");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private Stats() {
	}
	
	public static void writeHighScore() {
		if (highScore < score) {
			highScore = score;
			try {
				IOUtils.writeObj(highScore, "highscore.out");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static int getScore() {
		return score;
	}
	
	public static int getHighScore() {
		return highScore;
	}
	
	public static int getTurns() {
		return turns;
	}
	
	public static void attach(StatsObserver observer) {
		observers.add(observer);
		observer.updateStats(score, turns);
	}
	
	public static void reset() {
		score = INIT_SCORE;
		turns = 0;
		notifyObservers();
	}
	
	public static void decrease() {
		score -= DECREASE_SCORE;
		turns++;
		notifyObservers();
	}
	
	public static void bonus() {
		score += BONUS_SCORE;
		notifyObservers();
	}
	
	private static void notifyObservers() {
		for (StatsObserver observer : observers) {
			observer.updateStats(score, turns);
		}
	}
}
