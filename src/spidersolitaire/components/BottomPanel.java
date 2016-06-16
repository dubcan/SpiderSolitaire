package spidersolitaire.components;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import spidersolitaire.Solitaire;
import spidersolitaire.Stats;
import spidersolitaire.objects.StatsObserver;

public class BottomPanel extends JPanel implements StatsObserver {
	private static final long serialVersionUID = 1L;
	private JLabel scoreLabel;
	private JLabel turnsLabel;

	public BottomPanel() {
		setLayout(null);
		setPreferredSize(new Dimension(Solitaire.GAME_WIDTH, 20));

		add(ComponentsFactory.createVertSeparator(500, 0, 10, 20));

		scoreLabel = new JLabel();
		scoreLabel.setBounds(510, 0, 100, 20);
		add(scoreLabel);
		
		add(ComponentsFactory.createVertSeparator(650, 0, 10, 20));

		turnsLabel = new JLabel();
		turnsLabel.setBounds(660, 0, 100, 20);
		add(turnsLabel);
		
		Stats.attach(this);
	}

	@Override
	public void updateStats(int score, int turns) {
		scoreLabel.setText("Score: " + score);
		turnsLabel.setText("Turns: " + turns);
	}
}
