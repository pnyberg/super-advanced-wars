import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JPanel;

public class Gameboard extends JPanel implements KeyListener {
	private final int tileSize = 20;
	private int[][] map;
	private LinkedList<Infantry> troops;

	private int mapWidth, mapHeight;

	private Cursor cursor;

	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		map = new int[mapWidth][mapHeight];
		troops = new LinkedList<Infantry>();

		cursor = new Cursor(0, 0, tileSize);

		addKeyListener(this);

		initMap();
		initTroops();

		repaint();
	}

	public void initMap() {
		for (int n = 0 ; n < 2 ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				map[i][n] = 4;
			}
		}

		for (int i = 0 ; i < 2 ; i++) {
			for (int n = 2 ; n < 8 ; n++) {
				map[i][n] = 4;
			}
		}

		map[2][2] = 5;
		map[4][2] = 1;
		map[5][2] = 1;
		map[6][2] = 3;
		map[7][2] = 3;

		map[2][3] = 1;
		map[7][3] = 3;

		map[2][4] = 1;
		map[4][4] = 2;
		map[5][4] = 2;
		map[7][4] = 1;

		map[2][5] = 1;
		map[4][5] = 2;
		map[5][5] = 2;
		map[7][5] = 1;

		map[2][6] = 3;
		map[7][6] = 1;

		map[2][7] = 3;
		map[3][7] = 3;
		map[4][7] = 1;
		map[5][7] = 1;
		map[7][7] = 5;

		for (int i = 8 ; i < mapWidth ; i++) {
			for (int n = 2 ; n < 8 ; n++) {
				map[i][n] = 4;
			}
		}

		for (int n = 8 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				map[i][n] = 4;
			}
		}
	}

	public void initTroops() {
		troops.add(new Infantry(2, 2, Color.red));
		troops.add(new Infantry(7, 7, Color.orange));
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (cursor.getY() > 0) {
				cursor.moveUp();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (cursor.getY() < (mapHeight - 1) * tileSize) {
				cursor.moveDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursor.getX() > 0) {
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursor.getX() < (mapHeight - 1) * tileSize) {
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			System.out.println("Pressing");
		}

		repaint();
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int y = 0 ; y < mapHeight ; y++) {
			for (int x = 0 ; x < mapWidth ; x++) {
				paintArea(g, x * tileSize, y * tileSize, map[x][y]);
			}
		}

		for (Infantry inf : troops) {
			inf.paint(g);
		}

		cursor.paint(g);
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