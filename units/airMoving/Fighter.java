package units.airMoving;

import java.awt.Color;
import java.awt.Graphics;

import graphics.images.units.FighterImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Fighter extends Unit {
	private static int price = 20000;
	private static String typeName = "Fighter";

	public Fighter(int x, int y, Color color, int tileSize) {
		super(UnitType.FIGHTER, x, y, color, tileSize);

		movement = 9;
		movementType = MovementType.AIR;
		unitClass = UnitCategory.PLANE;
		unitSupply = new UnitSupply(99, 9);
		
		unitImage = new FighterImage(tileSize);
	}

	public static void setPrice(int price) {
		Fighter.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}