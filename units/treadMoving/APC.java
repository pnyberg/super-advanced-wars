package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.Unit;

public class APC extends Unit {
	private static int price = 6000;
	private static String typeName = "APC";

	private Unit containedUnit;
	private boolean droppingOff;

	public APC(int x, int y, Color color) {
		super(x, y, color);

		movement = 6;
		movementType = Unit.BAND;
		attackType = Unit.NONE;
		unitClass = Unit.VEHICLE;

		maxFuel = 70;
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
		APC.price = price;
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
		int bodyWidth = 3 * tileSize / 5 + 1;
		int bodyHeight = 2 * tileSize / 4 - 3;
		int bodyAlignX = tileSize / 4 - 1;
		int bodyAlignY = tileSize / 5 + 4;

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);
	}
}