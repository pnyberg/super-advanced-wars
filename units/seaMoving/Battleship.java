package units.seaMoving;

import java.awt.Color;
import java.awt.Graphics;

import graphics.images.units.BattleshipImage;
import units.IndirectUnit;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Battleship extends IndirectUnit {
	private static int price = 28000;
	private static String typeName = "Battleship";

	public Battleship(int x, int y, Color color, int tileSize) {
		super(UnitType.BATTLESHIP, x, y, color, tileSize);

		movement = 5;
		movementType = MovementType.SHIP;
		unitClass = UnitCategory.BOAT;
		minimumRange = 2;
		maximumRange = 6;
		unitSupply = new UnitSupply(99, 9);
		
		unitImage = new BattleshipImage(tileSize);
	}

	public static void setPrice(int price) {
		Battleship.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}