/**
 * TODO:
 *  - implement ContainingUnit-interface (and change to unitContainer?)
 */
package units.seaMoving;

import java.awt.Color;

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

		unitContainer = new UnitContainer(2);

		unitImage = new LanderImage(tileSize);
	}

	public void addUnit(Unit unit) {
		if(unitContainer.isFull()) {
			return;
		}

		unitContainer.addUnit(unit);

		unit.regulateHidden(true);
	}

	public void moveTo(int x, int y) {
		super.moveTo(x, y);

		unitContainer.moveContainedUnits(x, y);
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
		return unitContainer.getUnit(index);
	}

	public Unit getChosenUnit() {
		return unitContainer.getUnit(chosenIndex);
	}

	public Unit removeChosenUnit() {
		Unit unit = unitContainer.getUnit(chosenIndex);

		unit.regulateHidden(false);

		return unit;
	}

	public int getNumberOfContainedUnits() {
		return unitContainer.getNumContainedUnits();
	}

	public boolean isFull() {
		return unitContainer.isFull();
	}

	public boolean isDroppingOff() {
		return droppingOff;
	}
}