package map.buildings;

import java.awt.Color;
import java.awt.Graphics;

public class City extends Building {
	public City(int x, int y) {
		super(x, y);		
	}

	public void paint(Graphics g, int tileSize) {
		int paintX = x * tileSize;
		int paintY = y * tileSize;

		Color cityColor = Color.white;
		if (owner != null) {
			cityColor = owner.getColor();
		}
		g.setColor(cityColor);
		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		g.drawRect(paintX + tileSize / 8, paintY + tileSize / 8, 2 * tileSize / 5, 2 * tileSize / 3);
		g.setColor(cityColor);
		g.fillRect(paintX + tileSize / 3, paintY + tileSize / 4, 2 * tileSize / 5, 2 * tileSize / 3);
		g.setColor(Color.black);
		g.drawRect(paintX + tileSize / 3, paintY + tileSize / 4, 2 * tileSize / 5, 2 * tileSize / 3);
	}
}