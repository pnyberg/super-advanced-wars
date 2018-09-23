package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;

public class Tank extends Unit {
	private static int price = 7000;
	private static String typeName = "Tank";

	public Tank(int x, int y, Color color) {
		super(x, y, color);

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
		g.fillRect(point.getX() * tileSize + headAlignX, point.getY() * tileSize + headAlignY, headSize, headSize);

		g.setColor(Color.black);
		g.drawRect(point.getX() * tileSize + headAlignX, point.getY() * tileSize + headAlignY, headSize, headSize);

		// cannon
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() * tileSize + cannonAlignX, point.getY() * tileSize + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() * tileSize + cannonAlignX, point.getY() * tileSize + cannonAlignY, cannonWidth, cannonHeight);

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() * tileSize + bodyAlignX, point.getY() * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() * tileSize + bodyAlignX, point.getY() * tileSize + bodyAlignY, bodyWidth, bodyHeight);
	}
}