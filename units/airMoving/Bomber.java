package units.airMoving;

import java.awt.Color;

import graphics.images.units.BomberImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Bomber extends Unit {
	private static int price = 22000;
	private static String typeName = "Bomber";

	public Bomber(int x, int y, Color color, int tileSize) {
		super(UnitType.BOMBER, x, y, color, tileSize);

		movementSteps = 7;
		movementType = MovementType.AIR;
		unitClass = UnitCategory.PLANE;
		unitSupply = new UnitSupply(99, 9);
		
		unitImage = new BomberImage(tileSize);
	}

	public static void setPrice(int price) {
		Bomber.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}