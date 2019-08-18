package units.treadMoving;

import java.awt.Color;

import graphics.images.units.AAirImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class AAir extends Unit {
	private static int price = 8000;
	private static String typeName = "A-Air";

	public AAir(int x, int y, Color color, int tileSize) {
		super(UnitType.A_AIR, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.TREAD;
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