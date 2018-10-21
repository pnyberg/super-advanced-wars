package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class MDTank extends Unit {
	private static int price = 16000;
	private static String typeName = "MD Tank";

	public MDTank(int x, int y, Color color, int tileSize) {
		super(UnitType.MDTANK, x, y, color, tileSize);

		movement = 5;
		movementType = MovementType.BAND;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(50, 8);
	}

	public static void setPrice(int price) {
		MDTank.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int headSize = tileSize / 3 + 2;
		int headAlignX = 5 * tileSize / 10 - 2;
		int headAlignY = tileSize / 12 + 1;

		int cannonWidth = headSize / 4;
		int cannonHeight = headSize / 2;
		int cannonAlignX = headAlignX + headSize;
		int cannonAlignY = headAlignY + headSize / 4;

		int bodyWidth = 7 * tileSize / 10 + 5;
		int bodyHeight = 2 * tileSize / 5;
		int bodyAlignX = tileSize / 10;
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