package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class TCopterImage extends UnitImage {
	private int tileSize;

	public TCopterImage(int tileSize) {
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		int bodyWidth = 2 * tileSize / 3;
		int bodyHeight = tileSize / 3;
		int bodyAlignX = tileSize / 6;
		int bodyAlignY = tileSize / 3;
		
		int firstCrossLeftX = bodyAlignX + 3;
		int firstCrossRightX = bodyAlignX + tileSize / 5 + 2;
		int secondCrossLeftX = bodyAlignX + 3 + tileSize / 3;
		int secondCrossRightX = bodyAlignX + 8 * tileSize / 15 + 2;

		int crossUpperY = 2 * tileSize / 9 + 4;
		int crossLowerY = 4 * tileSize / 9 + 1;

		// body
		g.setColor(unitColor);
		g.fillOval(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawOval(x + bodyAlignX, y + bodyAlignY, bodyWidth, bodyHeight);
		
		g.drawLine(x + firstCrossLeftX, y + crossUpperY, x + firstCrossRightX, y + crossLowerY);
		g.drawLine(x + firstCrossLeftX, y + crossLowerY, x + firstCrossRightX, y + crossUpperY);
		g.drawLine(x + secondCrossLeftX, y + crossUpperY, x + secondCrossRightX, y + crossLowerY);
		g.drawLine(x + secondCrossLeftX, y + crossLowerY, x + secondCrossRightX, y + crossUpperY);
	}
}