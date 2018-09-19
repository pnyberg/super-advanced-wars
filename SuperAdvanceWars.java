import javax.swing.JFrame;

import handlers.GameProperties;
import handlers.HeroHandler;
import handlers.MapDimension;
import heroes.HeroFactory;

public class SuperAdvanceWars extends JFrame {
	private Gameboard board;
	private final int	width = 20, 
						height = 15,
						tileSize = 40;

	public SuperAdvanceWars() {
		HeroHandler heroHandler = new HeroHandler();
		HeroFactory heroFactory = new HeroFactory();
		heroHandler.addHero(heroFactory.createHero(0));
		heroHandler.addHero(heroFactory.createHero(1));
		heroHandler.selectStartHero();
		
		GameProperties gameProperties = new GameProperties(5);
		
		board = new Gameboard(new MapDimension(width, height, tileSize), heroHandler, gameProperties);

		add(board);

		setSize(width * 40 + 20, height * 40 + 30);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		board.requestFocus();
	}

	public static void main(String[] args) {
		new SuperAdvanceWars();
	}
}