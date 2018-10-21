package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class NeotankImage extends UnitImage {
	private int tileSize;

	public NeotankImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int bodySize = 6 * tileSize / 10;
		int bodyAlignX = 5 * tileSize / 20 - 3;
		int bodyAlignY = tileSize / 10;

		int cannonWidth = bodySize / 8;
		int cannonHeight = 2 * bodySize / 3;
		int cannonAlignX = bodyAlignX + bodySize;
		int cannonAlignY = bodyAlignY + bodySize / 6;

		int legWidth = tileSize / 10 + 2;
		int legHeight = tileSize / 4 + 2;
		int legAlignX1 = 5 * tileSize / 20;
		int legAlignX2 = 11 * tileSize / 20;
		int legAlignY = tileSize / 2 + 1;

		// body
		g.setColor(unitColor);
		g.fillOval(x + bodyAlignX, y + bodyAlignY, bodySize, bodySize);

		g.setColor(Color.black);
		g.drawOval(x + bodyAlignX, y + bodyAlignY, bodySize, bodySize);

		// cannon
		g.setColor(unitColor);
		g.fillRect(x + cannonAlignX, y + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(x + cannonAlignX, y + cannonAlignY, cannonWidth, cannonHeight);

		// legs
		g.setColor(unitColor);
		g.fillOval(x + legAlignX1, y + legAlignY, legWidth, legHeight);
		g.fillOval(x + legAlignX2, y + legAlignY, legWidth, legHeight);

		g.setColor(Color.black);
		g.drawOval(x + legAlignX1, y + legAlignY, legWidth, legHeight);
		g.drawOval(x + legAlignX2, y + legAlignY, legWidth, legHeight);
	}
}