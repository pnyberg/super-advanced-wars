package units;

import java.awt.Color;
import java.awt.Graphics;

public class Infantry extends Unit {
	public Infantry(int x, int y, Color color) {
		super(x, y, color);

		movement = 3;
		movementType = Unit.INFANTRY;
		attackType = Unit.DIRECT_ATTACK;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int headSize = tileSize / 2 - 4;
		int headAlignX = tileSize / 4 + 2;
		int headAlignY = tileSize / 10;
		int bodyAlignX = tileSize / 2;
		int bodyAlignY = headSize + headAlignY;
		int bodyEndY = 3 * tileSize / 4;
		int feetLevel = tileSize - 2;
		int armAlignY = bodyAlignY + 2;
		int leftArmAlign = tileSize / 4;
		int rightArmEnd = 3 * tileSize / 4;
		int leftLegAlign = tileSize / 4 + 1;
		int rightLegEnd = 3 * tileSize / 4 - 1;

		g.setColor(color);

		// head
		g.fillOval(x * tileSize + headAlignX, y * tileSize + headAlignY, headSize, headSize);
		g.setColor(Color.black);
		g.drawOval(x * tileSize + headAlignX, y * tileSize + headAlignY, headSize, headSize);

		// body
		g.drawLine(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, x * tileSize + bodyAlignX, y * tileSize + bodyEndY);

		// arms
		g.drawLine(x * tileSize + leftArmAlign, y * tileSize + armAlignY, x * tileSize + rightArmEnd, y * tileSize + armAlignY);

		// legs
		g.drawLine(x * tileSize + leftLegAlign, y * tileSize + feetLevel, x * tileSize + bodyAlignX, y * tileSize + bodyEndY);
		g.drawLine(x * tileSize + bodyAlignX, y * tileSize + bodyEndY, x * tileSize + rightLegEnd, y * tileSize + feetLevel);
	}
}