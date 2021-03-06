package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class BattleshipImage extends UnitImage {
	private int tileSize;

	public BattleshipImage(int tileSize) {
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

		int cannonWidth = headWidth / 4;
		int cannonHeight = headHeight / 2;
		int cannonAlignX = headAlignX + headWidth;
		int cannonAlignY = headAlignY + headHeight / 4;

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

		g.setColor(Color.black);
		g.drawRect(x + headAlignX, y + headAlignY, headWidth, headHeight);

		// cannon
		g.setColor(unitColor);
		g.fillRect(x + cannonAlignX, y + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(x + cannonAlignX, y + cannonAlignY, cannonWidth, cannonHeight);
	}
}