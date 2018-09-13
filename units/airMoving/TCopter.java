package units.airMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.Unit;

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

		maxFuel = 99;
		maxAmmo = 0;
		replentish();

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
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillOval(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawOval(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);
		
		g.drawLine(x * tileSize + firstCrossLeftX, y * tileSize + crossUpperY, x * tileSize + firstCrossRightX, y * tileSize + crossLowerY);
		g.drawLine(x * tileSize + firstCrossLeftX, y * tileSize + crossLowerY, x * tileSize + firstCrossRightX, y * tileSize + crossUpperY);
		g.drawLine(x * tileSize + secondCrossLeftX, y * tileSize + crossUpperY, x * tileSize + secondCrossRightX, y * tileSize + crossLowerY);
		g.drawLine(x * tileSize + secondCrossLeftX, y * tileSize + crossLowerY, x * tileSize + secondCrossRightX, y * tileSize + crossUpperY);
	}
}