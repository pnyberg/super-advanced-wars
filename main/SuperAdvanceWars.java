package main;
import javax.swing.JFrame;

import gameObjects.ChosenObject;
import gameObjects.GameProp;
import gameObjects.MapDim;
import heroes.HeroFactory;

public class SuperAdvanceWars extends JFrame {
	private Gameboard board;
	private final int width = 20; 
	private final int height = 15;
	private final int widthExtension = 20;
	private final int heightExtension = 45;
	private final int tileSize = 40;
	private final int fuelMaintenancePerTurn = 5;

	public SuperAdvanceWars() {
		HeroHandler heroHandler = new HeroHandler();
		HeroFactory heroFactory = new HeroFactory();
		heroHandler.addHero(heroFactory.createHero(0));
		heroHandler.addHero(heroFactory.createHero(1));
		heroHandler.selectStartHero();
		
		GameProp gameProperties = new GameProp(fuelMaintenancePerTurn, new MapDim(width, height, tileSize), new ChosenObject());
		board = new Gameboard(gameProperties, heroHandler);

		add(board);

		setSize(width * tileSize + widthExtension, height * tileSize + heightExtension);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		board.requestFocus();
	}

	public static void main(String[] args) {
		new SuperAdvanceWars();
	}
}