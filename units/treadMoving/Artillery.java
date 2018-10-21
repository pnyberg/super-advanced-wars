package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import graphics.images.units.ArtilleryImage;
import units.IndirectUnit;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Artillery extends IndirectUnit {
	private static int price = 6000;
	private static String typeName = "Artillery";

	public Artillery(int x, int y, Color color, int tileSize) {
		super(UnitType.ARTILLERY, x, y, color, tileSize);

		movement = 5;
		movementType = MovementType.BAND;
		unitClass = UnitCategory.VEHICLE;
		minimumRange = 2;
		maximumRange = 3;
		unitSupply = new UnitSupply(50, 9);
		
		unitImage = new ArtilleryImage(tileSize);
	}

	public static void setPrice(int price) {
		Artillery.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}