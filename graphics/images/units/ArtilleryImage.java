package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class ArtilleryImage extends UnitImage {
	private int tileSize;

	public ArtilleryImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int cx1 = x + 3 * tileSize / 4;
		int cy1 = y + tileSize / 8;

		int cx2 = x + 7 * tileSize / 8;
		int cy2 = y + tileSize / 4;

		int cx3 = x + 3 * tileSize / 4 - 3;
		int cy3 = y + 7 * tileSize / 20 + 3;
		int cx4 = x + tileSize / 4 + 5;
		int cy4 = y + 7 * tileSize / 20 + 3;

		int bodyWidth = 2 * tileSize / 5 + 1;
		int bodyHeight = tileSize / 4 + 3;
		int bodyAlignX = tileSize / 3 - 1;
		int bodyAlignY = 7 * tileSize / 20 + 3;

		// cannon
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		g.setColor(unitColor);
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);

		// body
		g.setColor(unitColor);
		g.fillRect(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);
	}
}