package graphics.images.buildings;

import java.awt.Color;
import java.awt.Graphics;

public class FactoryImage extends BuildingImage {
	private int tileSize;
	
	public FactoryImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color buildingColor) {
		g.setColor(buildingColor);
		g.fillRect(x, y, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(x, y, tileSize, tileSize);

		int firstX = x + tileSize / 10;
		int secondX = x + 2 * tileSize / 5;
		int thirdX = x + 3 * tileSize / 5;
		int fourthX = x + 9 * tileSize / 10;

		int overY = y + 2 * tileSize / 5;
		int underY = y + 7 * tileSize / 10;

		g.drawLine(firstX, overY + 2, secondX, overY - 2);
		g.drawLine(secondX, overY - 2, thirdX, overY + 2);
		g.drawLine(thirdX, overY + 2, fourthX, overY - 2);

		g.drawLine(firstX, underY + 2, secondX, underY - 2);
		g.drawLine(secondX, underY - 2, thirdX, underY + 2);
		g.drawLine(thirdX, underY + 2, fourthX, underY - 2);
	}
}