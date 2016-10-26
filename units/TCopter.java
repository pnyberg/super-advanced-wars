package units;

import java.awt.Color;
import java.awt.Graphics;

public class TCopter extends Unit {
	private static int price = 5000;
	private static String typeName = "TCopter";

	private Unit containedUnit;
	private boolean droppingOff;

	public TCopter(int x, int y, Color color) {
		super(x, y, color);

		movement = 6;
		movementType = Unit.AIR;
		attackType = Unit.NONE;
		unitClass = Unit.COPTER;

		containedUnit = null;
		droppingOff = false;
	}

	public void addUnit(Unit unit) {
		containedUnit = unit;

		containedUnit.regulateHidden(true);
	}

	public void moveTo(int x, int y) {
		super.moveTo(x, y);

		if (containedUnit != null) {
			containedUnit.moveTo(x, y);
		}
	}

	public void regulateDroppingOff(boolean droppingOff) {
		this.droppingOff = droppingOff;
	}

	public static void setPrice(int price) {
		TCopter.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	public Unit getUnit() {
		return containedUnit;
	}

	public Unit removeUnit() {
		Unit unit = containedUnit;

		containedUnit.regulateHidden(false);
		containedUnit = null;

		return unit;
	}

	public boolean isFull() {
		return containedUnit != null;
	}

	public boolean isDroppingOff() {
		return droppingOff;
	}

	protected void paintUnit(Graphics g, int tileSize) {
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

		int x1 = x * tileSize + tileSize / 7;
		int x2 = x * tileSize + tileSize / 5 - 1;
		int x3 = x2;
		int x4 = x * tileSize + 7 * tileSize / 20;
		int x5 = x4;
		int x6 = x1;
		int y1 = y * tileSize + tileSize / 4 + 2;
		int y2 = y1;
		int y3 = y * tileSize + 2 * tileSize / 5 + 1;
		int y4 = y3;
		int y5 = y * tileSize + tileSize / 2;
		int y6 = y5;

		int number = 6;
		int[] cx = {x1, x2, x3, x4, x5, x6};
		int[] cy = {y1, y2, y3, y4, y5, y6};

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillOval(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawOval(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		// fen
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillPolygon(cx, cy, number);

		g.setColor(Color.black);
		g.drawPolygon(cx, cy, number);

		// feet
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(x * tileSize + feetAlignX, y * tileSize + feetAlignY, feetWidth, feetHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + feetAlignX, y * tileSize + feetAlignY, feetWidth, feetHeight);

		// legs
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(x * tileSize + leftLegAlignX, y * tileSize + legAlignY, legWidth, legHeight);
		g.fillRect(x * tileSize + rightLegAlignX, y * tileSize + legAlignY, legWidth, legHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + leftLegAlignX, y * tileSize + legAlignY, legWidth, legHeight);
		g.drawRect(x * tileSize + rightLegAlignX, y * tileSize + legAlignY, legWidth, legHeight);
	}
}