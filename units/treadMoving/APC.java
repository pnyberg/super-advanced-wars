package units.treadMoving;

import java.awt.Color;

import graphics.images.units.APCImage;
import unitUtils.AttackType;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitContainer;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class APC extends Unit {
	private static int price = 6000;
	private static String typeName = "APC";

	private boolean droppingOff;

	public APC(int x, int y, Color color, int tileSize) {
		super(UnitType.APC_unit, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.TREAD;
		attackType = AttackType.NONE;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(70, 0);
		droppingOff = false;
		unitContainer = new UnitContainer(1);

		unitImage = new APCImage(tileSize);
	}

	public void regulateDroppingOff(boolean droppingOff) {
		this.droppingOff = droppingOff;
	}

	public static void setPrice(int price) {
		APC.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	public boolean isFull() {
		return unitContainer.getContainerSize() == 1;
	}

	public boolean isDroppingOff() {
		return droppingOff;
	}
}