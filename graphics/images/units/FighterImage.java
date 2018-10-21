package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class FighterImage extends UnitImage {
	private int tileSize;

	public FighterImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int bodyWidth = 2 * tileSize / 5 + 5;
		int bodyHeight = tileSize / 5;
		int bodyAlignX = tileSize / 4 - 1;
		int bodyAlignY = 2 * tileSize / 5 + 3;

		int headSize = tileSize / 4;
		int headAlignX = 3 * tileSize / 5 + 2;
		int headAlignY = bodyAlignY - 2;

		int x1 = x + tileSize / 3 - 2;
		int x2 = x + 2 * tileSize / 5 - 2;
		int x3 = x + 3 * tileSize / 5 - 2;
		int x4 = x + 2 * tileSize / 3 - 2;
		int y1 = y + 3 * tileSize / 5;
		int y2 = y + 9 * tileSize / 10;
		int y3 = y2;
		int y4 = y1;

		int number = 4;
		int[] cx = {x1, x2, x3, x4};
		int[] cy = {y1, y2, y3, y4};

		// body
		g.setColor(unitColor);
		g.fillRect(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		// head
		g.setColor(unitColor);
		g.fillOval(x + headAlignX, y + headAlignY, headSize, headSize);

		g.setColor(Color.black);
		g.drawOval(x + headAlignX, y + headAlignY, headSize, headSize);

		// wings
		g.setColor(unitColor);
		g.fillPolygon(cx, cy, number);

		g.setColor(Color.black);
		g.drawPolygon(cx, cy, number);
	}
}