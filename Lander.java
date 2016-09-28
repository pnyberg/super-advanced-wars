import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Lander extends Unit {
	private ArrayList<Unit> containedUnits;
	private boolean droppingOff;
	private int chosenIndex;

	public Lander(int x, int y, Color color) {
		super(x, y, color);

		movement = 6;
		movementType = Unit.TRANSPORT;
		attackType = Unit.NONE;

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

	public void paint(Graphics g, int tileSize) {
		int cx1 = x * tileSize + tileSize / 8;
		int cy1 = y * tileSize + 3 * tileSize / 5;
		int cx2 = x * tileSize + 7 * tileSize / 8;
		int cy2 = y * tileSize + 3 * tileSize / 5;
		int cx3 = x * tileSize + 3 * tileSize / 4;
		int cy3 = y * tileSize + 5 * tileSize / 6;
		int cx4 = x * tileSize + tileSize / 4;
		int cy4 = y * tileSize + 5 * tileSize / 6;

		// body
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		g.setColor(color);
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);
	}
}