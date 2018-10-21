package units.seaMoving;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import units.AttackType;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Lander extends Unit {
	private static int price = 12000;
	private static String typeName = "Lander";

	private ArrayList<Unit> containedUnits;
	private boolean droppingOff;
	private int chosenIndex;

	public Lander(int x, int y, Color color, int tileSize) {
		super(UnitType.LANDER, x, y, color, tileSize);

		movement = 6;
		movementType = MovementType.TRANSPORT;
		attackType = AttackType.NONE;
		unitClass = UnitCategory.BOAT;
		unitSupply = new UnitSupply(99, 0);
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
		Lander.price = price;
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
	}
}