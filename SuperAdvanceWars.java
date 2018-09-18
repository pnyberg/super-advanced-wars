import javax.swing.JFrame;

import handlers.MapDimension;

public class SuperAdvanceWars extends JFrame {
	private Gameboard board;
	private final int	width = 20, 
						height = 15,
						tileSize = 40;

	public SuperAdvanceWars() {
		board = new Gameboard(new MapDimension(width, height, tileSize));

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