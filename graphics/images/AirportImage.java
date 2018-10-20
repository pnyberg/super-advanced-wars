package graphics.images;

import java.awt.Color;
import java.awt.Graphics;

public class AirportImage extends BuildingImage {
	private int tileSize;
	
	public AirportImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color buildingColor) {
		g.setColor(buildingColor);
		g.fillRect(x, y, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(x, y, tileSize, tileSize);

		int baseX = x + tileSize / 4;
		int baseY = y + tileSize / 4;
		int baseDiameter = tileSize / 2;

		int towerX = x + tileSize / 8;
		int towerY = y + tileSize / 8;
		int towerHeight = tileSize / 2;
		int towerWidth = tileSize / 8;

		g.drawOval(baseX, baseY, baseDiameter, baseDiameter);
		g.drawRect(towerX, towerY, towerWidth, towerHeight);
	}
}