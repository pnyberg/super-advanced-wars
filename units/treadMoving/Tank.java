package units.treadMoving;

import java.awt.Color;

import graphics.images.units.TankImage;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Tank extends Unit {
	private static int price = 7000;
	private static String typeName = "Tank";

	public Tank(int x, int y, Color color, int tileSize) {
		super(UnitType.TANK, x, y, color, tileSize);

		movement = 6;
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