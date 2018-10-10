package units.seaMoving;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;

public class Cruiser extends Unit {
	private static int price = 18000;
	private static String typeName = "Cruiser";

	private ArrayList<Unit> containedUnits;
	private boolean droppingOff;
	private int chosenIndex;

	public Cruiser(int x, int y, Color color, int tileSize) {
		super(x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.SHIP;
		unitClass = UnitCategory.BOAT;
		unitSupply = new UnitSupply(99, 9);
		containedUnits = new ArrayList<Unit>();
		droppingOff = false;
		chosenIndex = -1;
	}

	public void addUnit(Unit unit) {
		if (containedUnits.size() == 2) {
			return;
		}

		containedUnits.add(unit);

		unit.regulateHidden(true);
	}

	public void moveTo(int x, int y) {
		super.moveTo(x, y);

		for (Unit unit : containedUnits) {
			unit.moveTo(x, y);
		}
	}

	public void regulateDroppingOff(boolean droppingOff) {
		this.droppingOff = droppingOff;
	}

	public void chooseUnit(int index) {
		chosenIndex = index;
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

	public Unit getUnit(int index) {
		return containedUnits.get(index);
	}

	public Unit getChosenUnit() {
		return containedUnits.get(chosenIndex);
	}

	public Unit removeChosenUnit() {
		Unit unit = containedUnits.remove(chosenIndex);

		unit.regulateHidden(false);

		return unit;
	}

	public int getNumberOfContainedUnits() {
		return containedUnits.size();
	}

	public boolean isFull() {
		return containedUnits.size() == 2;
	}

	public boolean isDroppingOff() {
		return droppingOff;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int cx1 = point.getX() + tileSize / 8;
		int cy1 = point.getY() + 3 * tileSize / 5;
		int cx2 = point.getX() + 7 * tileSize / 8;
		int cy2 = point.getY() + 3 * tileSize / 5;
		int cx3 = point.getX() + 3 * tileSize / 4;
		int cy3 = point.getY() + 5 * tileSize / 6;
		int cx4 = point.getX() + tileSize / 4;
		int cy4 = point.getY() + 5 * tileSize / 6;

		int headWidth = tileSize / 4;
		int headHeight = tileSize / 4;
		int headAlignX = (tileSize - headWidth) / 2;
		int headAlignY = 3 * tileSize / 5 - headHeight;

		int miniHeadWidth = tileSize / 8;
		int miniHeadHeight = tileSize / 8;
		int miniHeadAlignX = headAlignX + headWidth;
		int miniHeadAlignY = headAlignY + tileSize / 8;

		// body
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);

		// head
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + headAlignX, point.getY() + headAlignY, headWidth, headHeight);
		g.fillRect(point.getX() + miniHeadAlignX, point.getY() + miniHeadAlignY, miniHeadWidth, miniHeadHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + headAlignX, point.getY() + headAlignY, headWidth, headHeight);
	}
}