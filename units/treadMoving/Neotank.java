package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;

public class Neotank extends Unit {
	private static int price = 22000;
	private static String typeName = "Neotank";

	public Neotank(int x, int y, Color color, int tileSize) {
		super(x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.BAND;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(99, 9);
	}

	public static void setPrice(int price) {
		Neotank.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
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
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillOval(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodySize, bodySize);

		g.setColor(Color.black);
		g.drawOval(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodySize, bodySize);

		// cannon
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + cannonAlignX, point.getY() + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + cannonAlignX, point.getY() + cannonAlignY, cannonWidth, cannonHeight);

		// legs
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillOval(point.getX() + legAlignX1, point.getY() + legAlignY, legWidth, legHeight);
		g.fillOval(point.getX() + legAlignX2, point.getY() + legAlignY, legWidth, legHeight);

		g.setColor(Color.black);
		g.drawOval(point.getX() + legAlignX1, point.getY() + legAlignY, legWidth, legHeight);
		g.drawOval(point.getX() + legAlignX2, point.getY() + legAlignY, legWidth, legHeight);
	}
}