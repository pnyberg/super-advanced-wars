package units.seaMoving;

import java.awt.Color;

import graphics.images.units.SubImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Sub extends Unit {
	private static int price = 20000;
	private static String typeName = "Sub";

	public Sub(int x, int y, Color color, int tileSize) {
		super(UnitType.SUB, x, y, color, tileSize);

		movementSteps = 5;
		movementType = MovementType.SHIP;
		unitClass = UnitCategory.SUB;
		unitSupply = new UnitSupply(60, 6);
		
		unitImage = new SubImage(tileSize);
	}

	public static void setPrice(int price) {
		Sub.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}