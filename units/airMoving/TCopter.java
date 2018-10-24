/**
 * TODO:
 *  - implement ContainingUnit-interface
 */
package units.airMoving;

import java.awt.Color;

import graphics.images.units.TCopterImage;
import units.AttackType;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class TCopter extends Unit {
	private static int price = 5000;
	private static String typeName = "TCopter";

	private Unit containedUnit;
	private boolean droppingOff;

	public TCopter(int x, int y, Color color, int tileSize) {
		super(UnitType.TCOPTER, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.AIR;
		attackType = AttackType.NONE;
		unitClass = UnitCategory.COPTER;
		unitSupply = new UnitSupply(99, 0);

		containedUnit = null;
		droppingOff = false;
		
		unitImage = new TCopterImage(tileSize);
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
}