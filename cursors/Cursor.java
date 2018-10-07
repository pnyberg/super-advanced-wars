package cursors;
import java.awt.Color;
import java.awt.Graphics;

/***
 * Class to handle the cursor used for the map
 * 
 * @author pernyberg
 */
public class Cursor {
	private int x;
	private int y;

	private final Color fillColor = Color.red;
	private final Color edgeColor = Color.yellow;

	private int tileSize;
	private int smallPiece;
	private int bigPiece;

	public Cursor(int x, int y, int tileSize) {
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;
		smallPiece = tileSize / 20 + 1;
		bigPiece = smallPiece * 3;
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void paint(Graphics g) {
		// Get position for the cursor
		int posX = x * tileSize;
		int posY = y * tileSize;

		g.setColor(fillColor);

		// upper left corner (filling)
		g.fillRect(posX, posY, smallPiece, bigPiece);
		g.fillRect(posX, posY, bigPiece, smallPiece);

		// upper right corner (filling)
		g.fillRect(posX + tileSize - smallPiece, posY, smallPiece, bigPiece);
		g.fillRect(posX + tileSize - bigPiece, posY, bigPiece, smallPiece);

		// lower left corner (filling)
		g.fillRect(posX, posY + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(posX, posY + tileSize - smallPiece, bigPiece, smallPiece);

		// lower right corner (filling)
		g.fillRect(posX + tileSize - smallPiece, posY + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(posX + tileSize - bigPiece, posY + tileSize - smallPiece, bigPiece, smallPiece);

		g.setColor(edgeColor);

		// upper left corner (border)
		g.drawLine(posX, posY, posX + bigPiece, posY);
		g.drawLine(posX + bigPiece, posY, posX + bigPiece, posY + smallPiece);
		g.drawLine(posX + bigPiece, posY + smallPiece, posX + smallPiece, posY + smallPiece);
		g.drawLine(posX + smallPiece, posY + smallPiece, posX + smallPiece, posY + bigPiece);
		g.drawLine(posX + smallPiece, posY + bigPiece, posX, posY + bigPiece);
		g.drawLine(posX, posY + bigPiece, posX, posY);

		// upper right corner (border)
		g.drawLine(posX + tileSize - bigPiece, posY, posX + tileSize, posY);
		g.drawLine(posX + tileSize, posY, posX + tileSize, posY + bigPiece);
		g.drawLine(posX + tileSize, posY + bigPiece, posX + tileSize - smallPiece, posY + bigPiece);
		g.drawLine(posX + tileSize - smallPiece, posY + bigPiece, posX + tileSize - smallPiece, posY + smallPiece);
		g.drawLine(posX + tileSize - smallPiece, posY + smallPiece, posX + tileSize - bigPiece, posY + smallPiece);
		g.drawLine(posX + tileSize - bigPiece, posY + smallPiece, posX + tileSize - bigPiece, posY);

		// lower left corner (border)
		g.drawLine(posX, posY + tileSize - bigPiece, posX + smallPiece, posY + tileSize - bigPiece);
		g.drawLine(posX + smallPiece, posY + tileSize - bigPiece, posX + smallPiece, posY + tileSize - smallPiece);
		g.drawLine(posX + smallPiece, posY + tileSize - smallPiece, posX + bigPiece, posY + tileSize - smallPiece);
		g.drawLine(posX + bigPiece, posY + tileSize - smallPiece, posX + bigPiece, posY + tileSize);
		g.drawLine(posX + bigPiece, posY + tileSize, posX, posY + tileSize);
		g.drawLine(posX, posY + tileSize, posX, posY + tileSize - bigPiece);

		// lower right corner (border)
		g.drawLine(posX + tileSize - smallPiece, posY + tileSize - bigPiece, posX + tileSize, posY + tileSize - bigPiece);
		g.drawLine(posX + tileSize, posY + tileSize - bigPiece, posX + tileSize, posY + tileSize);
		g.drawLine(posX + tileSize, posY + tileSize, posX + tileSize - bigPiece, posY + tileSize);
		g.drawLine(posX + tileSize - bigPiece, posY + tileSize, posX + tileSize - bigPiece, posY + tileSize - smallPiece);
		g.drawLine(posX + tileSize - bigPiece, posY + tileSize - smallPiece, posX + tileSize - smallPiece, posY + tileSize - smallPiece);
		g.drawLine(posX + tileSize - smallPiece, posY + tileSize - smallPiece, posX + tileSize - smallPiece, posY + tileSize - bigPiece);
	}
}