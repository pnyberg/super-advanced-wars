import handlers.*;

import java.awt.Color;
import java.awt.Graphics;

public class Cursor {
	private int x, y;
	private final Color fillColor, edgeColor;

	public Cursor(int x, int y) {
		this.x = x;
		this.y = y;

		fillColor = Color.red;
		edgeColor = Color.yellow;
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

	public void paint(Graphics g) {
		int tileSize = MapHandler.tileSize;
		int smallPiece = tileSize / 20 + 1;
		int bigPiece = smallPiece * 3;

		int posX = x * tileSize;
		int posY = y * tileSize;

		g.setColor(fillColor);

		// upper left corner
		g.fillRect(posX, posY, smallPiece, bigPiece);
		g.fillRect(posX, posY, bigPiece, smallPiece);

		// upper right corner
		g.fillRect(posX + tileSize - smallPiece, posY, smallPiece, bigPiece);
		g.fillRect(posX + tileSize - bigPiece, posY, bigPiece, smallPiece);

		// lower left corner
		g.fillRect(posX, posY + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(posX, posY + tileSize - smallPiece, bigPiece, smallPiece);

		// lower right corner
		g.fillRect(posX + tileSize - smallPiece, posY + tileSize - bigPiece, smallPiece, bigPiece);
		g.fillRect(posX + tileSize - bigPiece, posY + tileSize - smallPiece, bigPiece, smallPiece);

		g.setColor(edgeColor);

		// upper left corner
		g.drawLine(posX, posY, posX + bigPiece, posY);
		g.drawLine(posX + bigPiece, posY, posX + bigPiece, posY + smallPiece);
		g.drawLine(posX + bigPiece, posY + smallPiece, posX + smallPiece, posY + smallPiece);
		g.drawLine(posX + smallPiece, posY + smallPiece, posX + smallPiece, posY + bigPiece);
		g.drawLine(posX + smallPiece, posY + bigPiece, posX, posY + bigPiece);
		g.drawLine(posX, posY + bigPiece, posX, posY);

		// upper right corner
		g.drawLine(posX + tileSize - bigPiece, posY, posX + tileSize, posY);
		g.drawLine(posX + tileSize, posY, posX + tileSize, posY + bigPiece);
		g.drawLine(posX + tileSize, posY + bigPiece, posX + tileSize - smallPiece, posY + bigPiece);
		g.drawLine(posX + tileSize - smallPiece, posY + bigPiece, posX + tileSize - smallPiece, posY + smallPiece);
		g.drawLine(posX + tileSize - smallPiece, posY + smallPiece, posX + tileSize - bigPiece, posY + smallPiece);
		g.drawLine(posX + tileSize - bigPiece, posY + smallPiece, posX + tileSize - bigPiece, posY);

		// lower left corner
		g.drawLine(posX, posY + tileSize - bigPiece, posX + smallPiece, posY + tileSize - bigPiece);
		g.drawLine(posX + smallPiece, posY + tileSize - bigPiece, posX + smallPiece, posY + tileSize - smallPiece);
		g.drawLine(posX + smallPiece, posY + tileSize - smallPiece, posX + bigPiece, posY + tileSize - smallPiece);
		g.drawLine(posX + bigPiece, posY + tileSize - smallPiece, posX + bigPiece, posY + tileSize);
		g.drawLine(posX + bigPiece, posY + tileSize, posX, posY + tileSize);
		g.drawLine(posX, posY + tileSize, posX, posY + tileSize - bigPiece);

		// lower right corner
		g.drawLine(posX + tileSize - smallPiece, posY + tileSize - bigPiece, posX + tileSize, posY + tileSize - bigPiece);
		g.drawLine(posX + tileSize, posY + tileSize - bigPiece, posX + tileSize, posY + tileSize);
		g.drawLine(posX + tileSize, posY + tileSize, posX + tileSize - bigPiece, posY + tileSize);
		g.drawLine(posX + tileSize - bigPiece, posY + tileSize, posX + tileSize - bigPiece, posY + tileSize - smallPiece);
		g.drawLine(posX + tileSize - bigPiece, posY + tileSize - smallPiece, posX + tileSize - smallPiece, posY + tileSize - smallPiece);
		g.drawLine(posX + tileSize - smallPiece, posY + tileSize - smallPiece, posX + tileSize - smallPiece, posY + tileSize - bigPiece);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}