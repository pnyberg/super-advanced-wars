package main;
import javax.swing.JFrame;

import gameObjects.ChosenObject;
import gameObjects.GameProperties;
import gameObjects.DimensionObject;
import hero.HeroFactory;

public class SuperAdvanceWars extends JFrame {
	private Gameboard board;
	private final int widthExtension = 25;
	private final int heightExtension = 50;

	public SuperAdvanceWars() {
		board = new Gameboard();
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