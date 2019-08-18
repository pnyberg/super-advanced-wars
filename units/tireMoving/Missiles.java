package units.tireMoving;

import java.awt.Color;

import graphics.images.units.MissilesImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.IndirectUnit;

public class Missiles extends IndirectUnit {
	private static int price = 12000;
	private static String typeName = "Missiles";

	public Missiles(int x, int y, Color color, int tileSize) {
		super(UnitType.MISSILES, x, y, color, tileSize);

		movement = 4;
		movementType = MovementType.TIRE;
		unitClass = UnitCategory.VEHICLE;
		minimumRange = 3;
		maximumRange = 5;
		unitSupply = new UnitSupply(50, 6);
		
		unitImage = new MissilesImage(tileSize);
	}

	public static void setPrice(int price) {
		Missiles.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}