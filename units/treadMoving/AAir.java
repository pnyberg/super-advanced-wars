package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import graphics.images.units.AAirImage;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class AAir extends Unit {
	private static int price = 8000;
	private static String typeName = "A-Air";

	public AAir(int x, int y, Color color, int tileSize) {
		super(UnitType.A_AIR, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.BAND;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(60, 9);
		
		unitImage = new AAirImage(tileSize);
	}

	public static void setPrice(int price) {
		AAir.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}