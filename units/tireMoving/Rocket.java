package units.tireMoving;

import java.awt.Color;
import java.awt.Graphics;

import graphics.images.units.RocketImage;
import units.IndirectUnit;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Rocket extends IndirectUnit {
	private static int price = 15000;
	private static String typeName = "Rocket";

	public Rocket(int x, int y, Color color, int tileSize) {
		super(UnitType.ROCKET, x, y, color, tileSize);

		movement = 5;
		movementType = MovementType.TIRE;
		unitClass = UnitCategory.VEHICLE;
		minimumRange = 3;
		maximumRange = 5;
		unitSupply = new UnitSupply(50, 6);
		
		unitImage = new RocketImage(tileSize);
	}

	public static void setPrice(int price) {
		Rocket.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}