package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class SubImage extends UnitImage {
	private int tileSize;

	public SubImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int bodyWidth = 2 * tileSize / 3;
		int bodyHeight = tileSize / 3;
		int bodyAlignX = tileSize / 6;
		int bodyAlignY = tileSize / 2;
		
		int headWidth = tileSize / 4;
		int headHeight = tileSize / 4;
		int headAlignX = (tileSize - headWidth) / 2;
		int headAlignY = 3 * tileSize / 5 - headHeight;

		// body
		g.setColor(unitColor);
		g.fillOval(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawOval(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		// head
		g.setColor(unitColor);
		g.fillRect(x + headAlignX, y + headAlignY, headWidth, headHeight);

		g.setColor(Color.black);
		g.drawRect(x + headAlignX, y + headAlignY, headWidth, headHeight);
	}
}