package main;
import javax.swing.JFrame;

import gameObjects.ChosenObject;
import gameObjects.GameProperties;
import gameObjects.MapDim;
import hero.HeroFactory;

public class SuperAdvanceWars extends JFrame {
	private Gameboard board;
	private final int widthExtension = 25;
	private final int heightExtension = 50;
	private final int tileSize = 40;
	private final int fuelMaintenancePerTurn = 5;

	public SuperAdvanceWars() {
		HeroHandler heroHandler = new HeroHandler();
		HeroFactory heroFactory = new HeroFactory();
		heroHandler.addHero(heroFactory.createHero(0));
		heroHandler.addHero(heroFactory.createHero(1));
		heroHandler.selectStartHero();
		
		board = new Gameboard(tileSize, fuelMaintenancePerTurn, heroHandler);

		add(board);

		setSize(board.getBoardWidth() + widthExtension, board.getBoardHeight() + heightExtension);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		board.requestFocus();
	}

	public static void main(String[] args) {
		new SuperAdvanceWars();
	}
}