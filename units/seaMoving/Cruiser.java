package units.seaMoving;

import java.awt.Color;

import graphics.images.units.CruiserImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitContainer;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Cruiser extends Unit {
	private static int price = 18000;
	private static String typeName = "Cruiser";

	private boolean droppingOff;

	public Cruiser(int x, int y, Color color, int tileSize) {
		super(UnitType.CRUISER, x, y, color, tileSize);

		movementSteps = 6;
		movementType = MovementType.SHIP;
		unitClass = UnitCategory.BOAT;
		unitSupply = new UnitSupply(99, 9);
		droppingOff = false;
		unitContainer = new UnitContainer(2);
		
		unitImage = new CruiserImage(tileSize);
	}

	public void regulateDroppingOff(boolean droppingOff) {
		this.droppingOff = droppingOff;
	}

	public static void setPrice(int price) {
		Cruiser.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	public boolean isFull() {
		return unitContainer.getContainerSize() == 2;
	}

	public boolean isDroppingOff() {
		return droppingOff;
	}
}