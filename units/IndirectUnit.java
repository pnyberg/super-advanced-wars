package units;

import point.Point;
import unitUtils.AttackType;
import unitUtils.UnitType;

import java.awt.Color;
import java.util.ArrayList;

public abstract class IndirectUnit extends Unit {
	protected int minimumRange, maximumRange, firingIndex;
	protected ArrayList<Point> possibleFiringLocationList;

	public IndirectUnit(UnitType unitType, int x, int y, Color color, int tileSize) {
		super(unitType, x, y, color, tileSize);

		attackType = AttackType.INDIRECT_ATTACK;
		firingIndex = -1;
		possibleFiringLocationList = new ArrayList<Point>();
	}

	public void addFiringLocation(Point p) {
		possibleFiringLocationList.add(p);
	}

	public void clearFiringLocations() {
		possibleFiringLocationList.clear();
	}

	public Point getNextFiringLocation() {
		if (possibleFiringLocationList.isEmpty()) {
			return null;
		}

		firingIndex = (firingIndex + 1) % possibleFiringLocationList.size();
		return possibleFiringLocationList.get(firingIndex);
	}

	public Point getPreviousFiringLocation() {
		if (possibleFiringLocationList.isEmpty()) {
			return null;
		}

		firingIndex = (firingIndex + possibleFiringLocationList.size() - 1) % possibleFiringLocationList.size();
		Point p = possibleFiringLocationList.get(firingIndex);

		return p;
	}

	public int getMinRange() {
		return minimumRange;
	}

	public int getMaxRange() {
		return maximumRange;
	}
}