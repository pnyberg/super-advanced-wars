package units.footMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;

public class Mech extends Unit {
	private static int price = 3000;
	private static String typeName = "Mech";

	public Mech(int x, int y, Color color, int tileSize) {
		super(x, y, color, tileSize);

		movement = 2;
		movementType = MovementType.MECH;
		unitClass = UnitCategory.FOOTMAN;
		unitSupply = new UnitSupply(70, 3);
	}

	public static void setPrice(int price) {
		Mech.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
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
		g.fillRect(point.getX() + rocketAlignX, point.getY() + rocketAlignY, rocketWidth, rocketHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + rocketAlignX, point.getY() + rocketAlignY, rocketWidth, rocketHeight);

		// head
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillOval(point.getX() + headAlignX, point.getY() + headAlignY, headSize, headSize);

		g.setColor(Color.black);
		g.drawOval(point.getX() + headAlignX, point.getY() + headAlignY, headSize, headSize);

		// body
		g.drawLine(point.getX() + bodyAlignX, point.getY() + bodyAlignY, point.getX() + bodyAlignX, point.getY() + bodyEndY);

		// arms
		g.drawLine(point.getX() + leftArmAlign, point.getY() + armAlignY, point.getX() + rightArmEnd, point.getY() + armAlignY);

		// legs
		g.drawLine(point.getX() + leftLegAlign, point.getY() + feetLevel, point.getX() + bodyAlignX, point.getY() + bodyEndY);
		g.drawLine(point.getX() + bodyAlignX, point.getY() + bodyEndY, point.getX() + rightLegEnd, point.getY() + feetLevel);
	}
}