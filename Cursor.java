import java.awt.Color;
import java.awt.Graphics;

public class Cursor {
	private int x, y;
	private final Color color;

	public Cursor(int x, int y) {
		this.x = x;
		this.y = y;

		color = Color.red;
	}

	public void moveUp() {
		y--;
	}

	public void moveDown() {
		y++;
	}

	public void moveLeft() {
		x--;
	}

	public void moveRight() {
		x++;
	}

	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}

	public void paint(Graphics g, int tileSize) {
		int smallPiece = tileSize / 20 + 1;
		int bigPiece = smallPiece * 3;

		int posX = x * tileSize;
		int posY = y * tileSize;

		g.setColor(color);

		g.fillRect(posX, posY, smallPiece, bigPiece);
		g.fillRect(posX, posY, bigPiece, smallPiece);

		g.fillRect(posX + tileSize - smallPiece, posY, smallPiece, bigPiece);
		g.fillRect(posX + tileSize - bigPiece, posY, bigPiece, smallPiece);

		g.fillRect(posX, posY + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(posX, posY + tileSize - smallPiece, bigPiece, smallPiece);

		g.fillRect(posX + tileSize - smallPiece, posY + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(posX + tileSize - bigPiece, posY + tileSize - smallPiece, bigPiece, smallPiece);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}