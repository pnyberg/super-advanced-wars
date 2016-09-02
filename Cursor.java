import java.awt.Color;
import java.awt.Graphics;

public class Cursor {
	private int x, y, tileSize;
	private final Color color;

	public Cursor(int x, int y, int tileSize) {
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;

		color = Color.red;
	}

	public void moveUp() {
		y -= tileSize;
	}

	public void moveDown() {
		y += tileSize;
	}

	public void moveLeft() {
		x -= tileSize;
	}

	public void moveRight() {
		x += tileSize;
	}

	public void paint(Graphics g) {
		int smallPiece = 2;
		int bigPiece = 6;

		g.setColor(color);

		g.fillRect(x, y, smallPiece, bigPiece);
		g.fillRect(x, y, bigPiece, smallPiece);

		g.fillRect(x + tileSize - smallPiece, y, smallPiece, bigPiece);
		g.fillRect(x + tileSize - bigPiece, y, bigPiece, smallPiece);

		g.fillRect(x, y + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(x, y + tileSize - smallPiece, bigPiece, smallPiece);

		g.fillRect(x + tileSize - smallPiece, y + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(x + tileSize - bigPiece, y + tileSize - smallPiece, bigPiece, smallPiece);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}