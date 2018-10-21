package units.airMoving;

import java.awt.Color;
import java.awt.Graphics;

import graphics.images.units.BCopterImage;
import graphics.images.units.BomberImage;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Bomber extends Unit {
	private static int price = 22000;
	private static String typeName = "Bomber";

	public Bomber(int x, int y, Color color, int tileSize) {
		super(UnitType.BOMBER, x, y, color, tileSize);

		movement = 7;
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