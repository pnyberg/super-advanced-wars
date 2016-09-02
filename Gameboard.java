import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Gameboard extends JPanel {
	private final int tileSize = 20;
	private int[][] map;
	private int mapWidth, mapHeight;

	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		map = new int[mapWidth][mapHeight];
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int y = 0 ; y < mapHeight ; y++) {
			for (int x = 0 ; x < mapWidth ; x++) {
				paintArea(g, x * tileSize, y * tileSize, map[x][y]);
			}
		}
	}

	private void paintArea(Graphics g, int x, int y, int number) {
		if (number == 0) {
			g.setColor(Color.gray);
		} else if (number == 1) {
			g.setColor(Color.yellow);
		} else if (number == 2) {
			g.setColor(Color.green);
		} else if (number == 3) {
			g.setColor(new Color(142,101,64)); // brown
		} else if (number == 4) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.white);
		}

		g.fillRect(x, y, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(x, y, tileSize, tileSize);
	}
}