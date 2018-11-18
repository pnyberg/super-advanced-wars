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
import units.UnitContainer;
import units.UnitSupply;
import units.UnitType;

public class TCopter extends Unit {
	private static int price = 5000;
	private static String typeName = "TCopter";

	public TCopter(int x, int y, Color color, int tileSize) {
		super(UnitType.TCOPTER, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.AIR;
		attackType = AttackType.NONE;
		unitClass = UnitCategory.COPTER;
		unitSupply = new UnitSupply(99, 0);

		unitContainer = new UnitContainer(1);
		
		unitImage = new TCopterImage(tileSize);
	}

	public void moveTo(int x, int y) {
		super.moveTo(x, y);

		if (unitContainer != null) {
			unitContainer.moveContainedUnits(x, y);
		}
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

	public UnitContainer getUnitContainer() {
		return unitContainer;
	}
}