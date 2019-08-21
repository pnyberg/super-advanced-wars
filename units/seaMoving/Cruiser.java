/**
 * TODO:
 *  - implement ContainingUnit-interface (and change to unitContainer?)
 */
package units.seaMoving;

import java.awt.Color;
import java.util.ArrayList;

import graphics.images.units.CruiserImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Cruiser extends Unit {
	private static int price = 18000;
	private static String typeName = "Cruiser";

	private ArrayList<Unit> containedUnits;
	private boolean droppingOff;
	private int chosenIndex;

	public Cruiser(int x, int y, Color color, int tileSize) {
		super(UnitType.CRUISER, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.SHIP;
		unitClass = UnitCategory.BOAT;
		unitSupply = new UnitSupply(99, 9);
		containedUnits = new ArrayList<Unit>();
		droppingOff = false;
		chosenIndex = -1;
		
		unitImage = new CruiserImage(tileSize);
	}

	public void addUnit(Unit unit) {
		if (containedUnits.size() == 2) {
			return;
		}

		containedUnits.add(unit);

		unit.regulateHidden(true);
	}

	public void moveTo(int x, int y) {
		super.moveTo(x, y);

		for (Unit unit : containedUnits) {
			unit.moveTo(x, y);
		}
	}

	public void regulateDroppingOff(boolean droppingOff) {
		this.droppingOff = droppingOff;
	}

	public void chooseUnit(int index) {
		chosenIndex = index;
	}

	public static void setPrice(int price) {
		Cruiser.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	public Unit getUnit(int index) {
		return containedUnits.get(index);
	}

	public Unit getChosenUnit() {
		return containedUnits.get(chosenIndex);
	}

	public Unit removeChosenUnit() {
		Unit unit = containedUnits.remove(chosenIndex);

		unit.regulateHidden(false);

		return unit;
	}

	public int getNumberOfContainedUnits() {
		return containedUnits.size();
	}

	public boolean isFull() {
		return containedUnits.size() == 2;
	}

	public boolean isDroppingOff() {
		return droppingOff;
	}
}