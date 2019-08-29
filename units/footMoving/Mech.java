package units.footMoving;

import java.awt.Color;

import graphics.images.units.MechImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Mech extends Unit {
	private static int price = 3000;
	private static String typeName = "Mech";

	public Mech(int x, int y, Color color, int tileSize) {
		super(UnitType.MECH, x, y, color, tileSize);

		movementSteps = 2;
		movementType = MovementType.MECH;
		unitClass = UnitCategory.FOOTMAN;
		unitSupply = new UnitSupply(70, 3);
		
		unitImage = new MechImage(tileSize);
	}

	public static void setPrice(int price) {
		Mech.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}