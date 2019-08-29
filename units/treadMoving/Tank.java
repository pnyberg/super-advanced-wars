package units.treadMoving;

import java.awt.Color;

import graphics.images.units.TankImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Tank extends Unit {
	private static int price = 7000;
	private static String typeName = "Tank";

	public Tank(int x, int y, Color color, int tileSize) {
		super(UnitType.TANK, x, y, color, tileSize);

		movementSteps = 6;
		movementType = MovementType.TREAD;
		unitClass = UnitCategory.VEHICLE;

		unitSupply = new UnitSupply(70, 9);
		
		unitImage = new TankImage(tileSize);
	}

	public static void setPrice(int price) {
		Tank.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}