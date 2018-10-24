package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class AAirImage extends UnitImage {
	private int tileSize;

	public AAirImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int headAlignX = x + tileSize / 3 - 1;
		int headAlignY = y + tileSize / 5;
		int headWidth = 3 * tileSize / 20 + 2;
		int headHeight = tileSize / 5 + 2;

		int bodyAlignX = headAlignX;
		int bodyAlignY = headAlignY + headHeight;
		int bodyWidth = 2 * tileSize / 5 + 1;
		int bodyHeight = tileSize / 4 + 3;

		// cannon-points
		int cx1 = x + 3 * tileSize / 4;
		int cy1 = y + tileSize / 8;
		int cx2 = x + 7 * tileSize / 8 - 3;
		int cy2 = y + tileSize / 4 - 3;
		int cx3 = x + 3 * tileSize / 4 - 9;
		int cy3 = bodyAlignY;
		int cx4 = headAlignX + headWidth;
		int cy4 = bodyAlignY;
		int cx5 = headAlignX + headWidth;
		int cy5 = bodyAlignY - 4;

		// cannon
		int[] cannonX = {cx1, cx2, cx3, cx4, cx5};
		int[] cannonY = {cy1, cy2, cy3, cy4, cy5};
		int npoints = 5;

		g.setColor(unitColor);
		g.fillRect(headAlignX, headAlignY, headWidth, headHeight); // head
		g.fillRect(bodyAlignX, bodyAlignY, bodyWidth, bodyHeight); // body
		g.fillPolygon(cannonX, cannonY, npoints); // cannon

		g.setColor(Color.black);
		g.drawRect(headAlignX, headAlignY, headWidth, headHeight); // head
		g.drawRect(bodyAlignX, bodyAlignY, bodyWidth, bodyHeight); // body
		g.drawPolygon(cannonX, cannonY, npoints); // cannon
	}
}