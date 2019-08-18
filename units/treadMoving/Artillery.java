package units.treadMoving;

import java.awt.Color;

import graphics.images.units.ArtilleryImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.IndirectUnit;

public class Artillery extends IndirectUnit {
	private static int price = 6000;
	private static String typeName = "Artillery";

	public Artillery(int x, int y, Color color, int tileSize) {
		super(UnitType.ARTILLERY, x, y, color, tileSize);

		movement = 5;
		movementType = MovementType.TREAD;
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