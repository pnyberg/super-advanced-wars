package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class MissilesImage extends UnitImage {
	private int tileSize;

	public MissilesImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int cx1 = x + 2 * tileSize / 3 + 4;
		int cy1 = y + tileSize / 10 + 8;
		int cx2 = x + 4 * tileSize / 5;
		int cy2 = y + tileSize / 4 + 8;
		int cx3 = x + tileSize / 4 - 2;
		int cy3 = y + tileSize / 2 + 9;
		int cx4 = x + tileSize / 5 - 2;
		int cy4 = y + 2 * tileSize / 5 + 7;

		int bodyWidth = 2 * tileSize / 5 + 5;
		int bodyHeight = tileSize / 4 - 1;
		int bodyAlignX = tileSize / 3 - 3;
		int bodyAlignY = 7 * tileSize / 20 + 7;

		// body
		g.setColor(unitColor);
		g.fillRect(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		// cannon
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		g.setColor(unitColor);
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);
	}
}