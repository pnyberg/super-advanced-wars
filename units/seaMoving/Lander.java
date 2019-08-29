/**
 * TODO:
 *  - implement ContainingUnit-interface (and change to unitContainer?)
 */
package units.seaMoving;

import java.awt.Color;
import java.util.ArrayList;

import graphics.images.units.LanderImage;
import unitUtils.AttackType;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitContainer;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Lander extends Unit {
	private static int price = 12000;
	private static String typeName = "Lander";

	private ArrayList<Unit> containedUnits;
	private boolean droppingOff;
	private int chosenIndex;

	public Lander(int x, int y, Color color, int tileSize) {
		super(UnitType.LANDER, x, y, color, tileSize);

		movementSteps = 6;
		movementType = MovementType.TRANSPORT;
		attackType = AttackType.NONE;
		unitClass = UnitCategory.BOAT;
		unitSupply = new UnitSupply(99, 0);
		droppingOff = false;
		chosenIndex = -1;

		containedUnits = new ArrayList<Unit>(); // TODO: remove
		unitContainer = new UnitContainer(2);

		unitImage = new LanderImage(tileSize);
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
		Lander.price = price;
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