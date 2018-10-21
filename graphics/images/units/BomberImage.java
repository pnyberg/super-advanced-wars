package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class BomberImage extends UnitImage {
	private int tileSize;

	public BomberImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int bodyWidth = 3 * tileSize / 4;
		int bodyHeight = tileSize / 2;
		int bodyAlignX = 3 * tileSize / 20;
		int bodyAlignY = tileSize / 5 + 3;

		int x1 = x + tileSize / 3 - 3;
		int x2 = x + 2 * tileSize / 5 - 3;
		int x3 = x + 3 * tileSize / 5 + 4;
		int x4 = x + 3 * tileSize / 4 + 1;
		int y1 = y + 5 * tileSize / 10;
		int y2 = y + 9 * tileSize / 10;
		int y3 = y2;
		int y4 = y1;

		int number = 4;
		int[] cx = {x1, x2, x3, x4};
		int[] cy = {y1, y2, y3, y4};

		// body
		g.setColor(unitColor);
		g.fillOval(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawOval(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		// wings
		g.setColor(unitColor);
		g.fillPolygon(cx, cy, number);

		g.setColor(Color.black);
		g.drawPolygon(cx, cy, number);
	}
}