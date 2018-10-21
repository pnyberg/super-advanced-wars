package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class CruiserImage extends UnitImage {
	private int tileSize;

	public CruiserImage(int tileSize) {
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

		int headWidth = tileSize / 4;
		int headHeight = tileSize / 4;
		int headAlignX = (tileSize - headWidth) / 2;
		int headAlignY = 3 * tileSize / 5 - headHeight;

		int miniHeadWidth = tileSize / 8;
		int miniHeadHeight = tileSize / 8;
		int miniHeadAlignX = headAlignX + headWidth;
		int miniHeadAlignY = headAlignY + tileSize / 8;

		// body
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		g.setColor(unitColor);
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);

		// head
		g.setColor(unitColor);
		g.fillRect(x + headAlignX, y + headAlignY, headWidth, headHeight);
		g.fillRect(x + miniHeadAlignX, y + miniHeadAlignY, miniHeadWidth, miniHeadHeight);

		g.setColor(Color.black);
		g.drawRect(x + headAlignX, y + headAlignY, headWidth, headHeight);
		g.drawRect(x + miniHeadAlignX, y + miniHeadAlignY, miniHeadWidth, miniHeadHeight);
	}
}