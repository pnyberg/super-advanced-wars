package map.area.buildings;

import java.awt.Color;
import java.awt.Graphics;

public class City extends Building {
	public City(int x, int y) {
		super(x, y);		
	}

	public void paint(Graphics g, int tileSize) {
		int paintX = x * tileSize;
		int paintY = y * tileSize;

		if (owner == null) {
			g.setColor(Color.white);
		} else {
			g.setColor(owner.getColor());
		}
		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		g.drawLine(paintX, paintY, paintX + tileSize, paintY + tileSize);
		g.drawLine(paintX, paintY + tileSize, paintX + tileSize, paintY);
	}
}