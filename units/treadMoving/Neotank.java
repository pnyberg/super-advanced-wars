package units.treadMoving;

import java.awt.Color;

import graphics.images.units.NeotankImage;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Neotank extends Unit {
	private static int price = 22000;
	private static String typeName = "Neotank";

	public Neotank(int x, int y, Color color, int tileSize) {
		super(UnitType.NEOTANK, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.TREAD;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(99, 9);
		
		unitImage = new NeotankImage(tileSize);
	}

	public static void setPrice(int price) {
		Neotank.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}