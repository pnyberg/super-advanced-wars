package units.footMoving;

import java.awt.Color;

import graphics.images.units.InfantryImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Infantry extends Unit {
	private static int price = 1000;
	private static String typeName = "Infantry";
	//private BufferedImage redImg;

	public Infantry(int x, int y, Color color, int tileSize) {
		super(UnitType.INFANTRY, x, y, color, tileSize);

		movementSteps = 3;
		movementType = MovementType.INFANTRY;
		unitClass = UnitCategory.FOOTMAN;
		unitSupply = new UnitSupply(99, 0);
		
		unitImage = new InfantryImage(tileSize);
	}

	public static void setPrice(int price) {
		Infantry.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}