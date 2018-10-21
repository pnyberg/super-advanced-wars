package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class MDTankImage extends UnitImage {
	private int tileSize;

	public MDTankImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int headSize = tileSize / 3 + 2;
		int headAlignX = 5 * tileSize / 10 - 2;
		int headAlignY = tileSize / 12 + 1;

		int cannonWidth = headSize / 4;
		int cannonHeight = headSize / 2;
		int cannonAlignX = headAlignX + headSize;
		int cannonAlignY = headAlignY + headSize / 4;

		int bodyWidth = 7 * tileSize / 10 + 5;
		int bodyHeight = 2 * tileSize / 5;
		int bodyAlignX = tileSize / 10;
		int bodyAlignY = headSize + headAlignY;

		// head
		g.setColor(unitColor);
		g.fillRect(x + headAlignX, y + headAlignY, headSize, headSize);

		g.setColor(Color.black);
		g.drawRect(x + headAlignX, y + headAlignY, headSize, headSize);

		// cannon
		g.setColor(unitColor);
		g.fillRect(x + cannonAlignX, y + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(x + cannonAlignX, y + cannonAlignY, cannonWidth, cannonHeight);

		// body
		g.setColor(unitColor);
		g.fillRect(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);
	}
}