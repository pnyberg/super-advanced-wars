package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class BCopterImage extends UnitImage {
	private int tileSize;

	public BCopterImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int bodyWidth = tileSize / 2;
		int bodyHeight = tileSize / 2;
		int bodyAlignX = 7 * tileSize / 20;
		int bodyAlignY = tileSize / 5 + 3;

		int legWidth = tileSize / 10;
		int legHeight = tileSize / 5;
		int leftLegAlignX = 9 * tileSize / 20;
		int rightLegAlignX = 13 * tileSize / 20;
		int legAlignY = 7 * tileSize /	 10 - 2;

		int feetWidth = tileSize / 2 + 4;
		int feetHeight = tileSize / 8;
		int feetAlignX = tileSize / 5 + 4;
		int feetAlignY = 3 * tileSize / 4 + 2;

		int x1 = x + tileSize / 7;
		int x2 = x + tileSize / 5 - 1;
		int x3 = x2;
		int x4 = x + 7 * tileSize / 20;
		int x5 = x4;
		int x6 = x1;
		int y1 = y + tileSize / 4 + 2;
		int y2 = y1;
		int y3 = y + 2 * tileSize / 5 + 1;
		int y4 = y3;
		int y5 = y + tileSize / 2;
		int y6 = y5;

		int number = 6;
		int[] cx = {x1, x2, x3, x4, x5, x6};
		int[] cy = {y1, y2, y3, y4, y5, y6};

		// body
		g.setColor(unitColor);
		g.fillOval(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawOval(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		// fen
		g.setColor(unitColor);
		g.fillPolygon(cx, cy, number);

		g.setColor(Color.black);
		g.drawPolygon(cx, cy, number);

		// feet
		g.setColor(unitColor);
		g.fillRect(x + feetAlignX, y + feetAlignY, feetWidth, feetHeight);

		g.setColor(Color.black);
		g.drawRect(x + feetAlignX, y + feetAlignY, feetWidth, feetHeight);

		// legs
		g.setColor(unitColor);
		g.fillRect(x + leftLegAlignX, y + legAlignY, legWidth, legHeight);
		g.fillRect(x + rightLegAlignX, y + legAlignY, legWidth, legHeight);

		g.setColor(Color.black);
		g.drawRect(x + leftLegAlignX, y + legAlignY, legWidth, legHeight);
		g.drawRect(x + rightLegAlignX, y + legAlignY, legWidth, legHeight);
	}
}