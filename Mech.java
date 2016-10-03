import java.awt.Color;
import java.awt.Graphics;

public class Mech extends Unit {
	public Mech(int x, int y, Color color) {
		super(x, y, color);

		movement = 2;
		movementType = Unit.MECH;
		attackType = Unit.DIRECT_ATTACK;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int rocketAlignX = tileSize / 20 * 3;
		int rocketAlignY = tileSize / 20 * 4;
		int rocketWidth = tileSize / 4 * 3;
		int rocketHeight = tileSize / 10 * 3;
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

		// rocket
		g.setColor(Color.gray);
		g.fillRect(x * tileSize + rocketAlignX, y * tileSize + rocketAlignY, rocketWidth, rocketHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + rocketAlignX, y * tileSize + rocketAlignY, rocketWidth, rocketHeight);

		// head
		g.setColor(color);
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