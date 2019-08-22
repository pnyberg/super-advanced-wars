/***
 * Class to handle the cursor used for the map
 * 
 * @author pernyberg
 */
package cursors;

import java.awt.Color;
import java.awt.Graphics;
import point.Point;;

public class Cursor {
	private final Color fillColor = Color.red;
	private final Color edgeColor = Color.yellow;

	private int x;
	private int y;
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
	
	public void setPosition(Point point) {
		setPosition(point.getX(), point.getY());
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// TODO: replace with image
	public void paint(Graphics g) {
		// Get position for the cursor
		g.setColor(fillColor);

		// upper left corner (filling)
		g.fillRect(x, y, smallPiece, bigPiece);
		g.fillRect(x, y, bigPiece, smallPiece);

		// upper right corner (filling)
		g.fillRect(x + tileSize - smallPiece, y, smallPiece, bigPiece);
		g.fillRect(x + tileSize - bigPiece, y, bigPiece, smallPiece);

		// lower left corner (filling)
		g.fillRect(x, y + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(x, y + tileSize - smallPiece, bigPiece, smallPiece);

		// lower right corner (filling)
		g.fillRect(x + tileSize - smallPiece, y + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(x + tileSize - bigPiece, y + tileSize - smallPiece, bigPiece, smallPiece);

		g.setColor(edgeColor);

		// upper left corner (border)
		g.drawLine(x, y, x + bigPiece, y);
		g.drawLine(x + bigPiece, y, x + bigPiece, y + smallPiece);
		g.drawLine(x + bigPiece, y + smallPiece, x + smallPiece, y + smallPiece);
		g.drawLine(x + smallPiece, y + smallPiece, x + smallPiece, y + bigPiece);
		g.drawLine(x + smallPiece, y + bigPiece, x, y + bigPiece);
		g.drawLine(x, y + bigPiece, x, y);

		// upper right corner (border)
		g.drawLine(x + tileSize - bigPiece, y, x + tileSize, y);
		g.drawLine(x + tileSize, y, x + tileSize, y + bigPiece);
		g.drawLine(x + tileSize, y + bigPiece, x + tileSize - smallPiece, y + bigPiece);
		g.drawLine(x + tileSize - smallPiece, y + bigPiece, x + tileSize - smallPiece, y + smallPiece);
		g.drawLine(x + tileSize - smallPiece, y + smallPiece, x + tileSize - bigPiece, y + smallPiece);
		g.drawLine(x + tileSize - bigPiece, y + smallPiece, x + tileSize - bigPiece, y);

		// lower left corner (border)
		g.drawLine(x, y + tileSize - bigPiece, x + smallPiece, y + tileSize - bigPiece);
		g.drawLine(x + smallPiece, y + tileSize - bigPiece, x + smallPiece, y + tileSize - smallPiece);
		g.drawLine(x + smallPiece, y + tileSize - smallPiece, x + bigPiece, y + tileSize - smallPiece);
		g.drawLine(x + bigPiece, y + tileSize - smallPiece, x + bigPiece, y + tileSize);
		g.drawLine(x + bigPiece, y + tileSize, x, y + tileSize);
		g.drawLine(x, y + tileSize, x, y + tileSize - bigPiece);

		// lower right corner (border)
		g.drawLine(x + tileSize - smallPiece, y + tileSize - bigPiece, x + tileSize, y + tileSize - bigPiece);
		g.drawLine(x + tileSize, y + tileSize - bigPiece, x + tileSize, y + tileSize);
		g.drawLine(x + tileSize, y + tileSize, x + tileSize - bigPiece, y + tileSize);
		g.drawLine(x + tileSize - bigPiece, y + tileSize, x + tileSize - bigPiece, y + tileSize - smallPiece);
		g.drawLine(x + tileSize - bigPiece, y + tileSize - smallPiece, x + tileSize - smallPiece, y + tileSize - smallPiece);
		g.drawLine(x + tileSize - smallPiece, y + tileSize - smallPiece, x + tileSize - smallPiece, y + tileSize - bigPiece);
	}
}