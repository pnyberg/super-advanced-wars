package units.airMoving;

import java.awt.Color;

import graphics.images.units.BCopterImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class BCopter extends Unit {
	private static int price = 9000;
	private static String typeName = "BCopter";

	public BCopter(int x, int y, Color color, int tileSize) {
		super(UnitType.BCOPTER, x, y, color, tileSize);

		movementSteps = 6;
		movementType = MovementType.AIR;
		unitClass = UnitCategory.COPTER;
		unitSupply = new UnitSupply(99, 6);
		
		unitImage = new BCopterImage(tileSize);
	}

	public static void setPrice(int price) {
		BCopter.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}