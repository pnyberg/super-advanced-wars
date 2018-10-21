package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Tank extends Unit {
	private static int price = 7000;
	private static String typeName = "Tank";

	public Tank(int x, int y, Color color, int tileSize) {
		super(UnitType.TANK, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.BAND;
		unitClass = UnitCategory.VEHICLE;

		unitSupply = new UnitSupply(70, 9);
	}

	public static void setPrice(int price) {
		Tank.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int headSize = tileSize / 4 + 2;
		int headAlignX = 3 * tileSize / 5 - 2;
		int headAlignY = tileSize / 10 + 1;

		int cannonWidth = headSize / 4;
		int cannonHeight = headSize / 2;
		int cannonAlignX = headAlignX + headSize;
		int cannonAlignY = headAlignY + headSize / 4;

		int bodyWidth = 3 * tileSize / 5 + 1;
		int bodyHeight = tileSize / 4 + 3;
		int bodyAlignX = tileSize / 4 - 1;
		int bodyAlignY = headSize + headAlignY;

		// head
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + headAlignX, point.getY() + headAlignY, headSize, headSize);

		g.setColor(Color.black);
		g.drawRect(point.getX() + headAlignX, point.getY() + headAlignY, headSize, headSize);

		// cannon
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + cannonAlignX, point.getY() + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + cannonAlignX, point.getY() + cannonAlignY, cannonWidth, cannonHeight);

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodyWidth, bodyHeight);
	}
}