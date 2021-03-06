package units.treadMoving;

import java.awt.Color;

import graphics.images.units.MDTankImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class MDTank extends Unit {
	private static int price = 16000;
	private static String typeName = "MD Tank";

	public MDTank(int x, int y, Color color, int tileSize) {
		super(UnitType.MDTANK, x, y, color, tileSize);

		movementSteps = 5;
		movementType = MovementType.TREAD;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(50, 8);
		
		unitImage = new MDTankImage(tileSize);
	}

	public static void setPrice(int price) {
		MDTank.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}