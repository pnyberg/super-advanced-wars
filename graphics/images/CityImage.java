package graphics.images;

import java.awt.Color;
import java.awt.Graphics;

public class CityImage extends BuildingImage {
	private int tileSize;
	
	public CityImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color buildingColor) {
		g.setColor(buildingColor);
		g.fillRect(x, y, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(x, y, tileSize, tileSize);

		g.drawRect(x + tileSize / 8, y + tileSize / 8, 2 * tileSize / 5, 2 * tileSize / 3);
		g.setColor(buildingColor);
		g.fillRect(x + tileSize / 3, y + tileSize / 4, 2 * tileSize / 5, 2 * tileSize / 3);
		g.setColor(Color.black);
		g.drawRect(x + tileSize / 3, y + tileSize / 4, 2 * tileSize / 5, 2 * tileSize / 3);
	}
}