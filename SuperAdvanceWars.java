import javax.swing.JFrame;

public class SuperAdvanceWars extends JFrame {
	private Gameboard board;

	public SuperAdvanceWars() {
		board = new Gameboard(10, 10);

		add(board);

		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		board.requestFocus();
	}

	public static void main(String[] args) {
		new SuperAdvanceWars();
	}
}