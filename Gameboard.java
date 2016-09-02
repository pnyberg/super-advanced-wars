import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Gameboard extends JPanel implements KeyListener {
	private final int tileSize = 20;
	private int[][] map;
	private int mapWidth, mapHeight;

	private Cursor cursor;

	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		map = new int[mapWidth][mapHeight];

		cursor = new Cursor(0, 0, tileSize);

		addKeyListener(this);

		repaint();
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

		System.out.println("Getting here " + e.getKeyCode());

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