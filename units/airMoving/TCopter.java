/**
 * TODO:
 *  - implement ContainingUnit-interface
 */
package units.airMoving;

import java.awt.Color;

import graphics.images.units.TCopterImage;
import unitUtils.AttackType;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitContainer;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class TCopter extends Unit {
	private static int price = 5000;
	private static String typeName = "TCopter";

	public TCopter(int x, int y, Color color, int tileSize) {
		super(UnitType.TCOPTER, x, y, color, tileSize);

		movementSteps = 6;
		movementType = MovementType.AIR;
		attackType = AttackType.NONE;
		unitClass = UnitCategory.COPTER;
		unitSupply = new UnitSupply(99, 0);

		unitContainer = new UnitContainer(1);
		
		unitImage = new TCopterImage(tileSize);
	}

	public void moveTo(int x, int y) {
		super.moveTo(x, y);
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
}