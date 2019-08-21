/**
 * TODO:
 *  - implement ContainingUnit-interface (and change to unitContainer?)
 */
package units.treadMoving;

import java.awt.Color;

import graphics.images.units.APCImage;
import unitUtils.AttackType;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitContainer;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class APC extends Unit {
	private static int price = 6000;
	private static String typeName = "APC";

	private Unit containedUnit;
	private boolean droppingOff;

	public APC(int x, int y, Color color, int tileSize) {
		super(UnitType.APC, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.TREAD;
		attackType = AttackType.NONE;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(70, 0);
		droppingOff = false;
		
		containedUnit = null; // TODO: remove
		unitContainer = new UnitContainer(1);

		unitImage = new APCImage(tileSize);
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

	public Unit getContainedUnit() {
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