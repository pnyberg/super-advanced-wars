package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class LanderImage extends UnitImage {
	private int tileSize;

	public LanderImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int cx1 = x + tileSize / 8;
		int cy1 = y + 3 * tileSize / 5;
		int cx2 = x + 7 * tileSize / 8;
		int cy2 = y + 3 * tileSize / 5;
		int cx3 = x + 3 * tileSize / 4;
		int cy3 = y + 5 * tileSize / 6;
		int cx4 = x + tileSize / 4;
		int cy4 = y + 5 * tileSize / 6;

		// body
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		g.setColor(unitColor);
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);
	}
}