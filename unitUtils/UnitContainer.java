package unitUtils;

import java.util.ArrayList;

import point.Point;
import units.Unit;

public class UnitContainer {
	private int size;
	private int chosenIndex;
	private boolean droppingOff;
	private ArrayList<Unit> containedUnits;
	private int dropOffIndex;
	private ArrayList<Point> possibleDropOffLocationList;
	
	public UnitContainer(int size) {
		this.size = size;
		chosenIndex = 0;
		droppingOff = false;
		containedUnits = new ArrayList<>();
		dropOffIndex = -1;
		possibleDropOffLocationList = new ArrayList<Point>();
	}

	public void addUnit(Unit unit) {
		containedUnits.add(unit);
		unit.regulateHidden(true);
	}
	
	public void chooseUnit(int index) {
		chosenIndex = index;
	}

	public void moveContainedUnits(int x, int y) {
		for (Unit unit : containedUnits) {
			unit.moveTo(x, y);
		}
	}

	public void addDropOffLocation(Point p) {
		possibleDropOffLocationList.add(p);
	}

	public void clearDropOffLocations() {
		possibleDropOffLocationList.clear();
	}

	public Point getNextDropOffLocation() {
		if (possibleDropOffLocationList.isEmpty()) {
			return null;
		}
		dropOffIndex = (dropOffIndex + 1) % possibleDropOffLocationList.size();
		return possibleDropOffLocationList.get(dropOffIndex);
	}

	public Point getPreviousDropOffLocation() {
		if (possibleDropOffLocationList.isEmpty()) {
			return null;
		}
		int size = possibleDropOffLocationList.size();
		dropOffIndex = (dropOffIndex + size - 1) % size;
		return possibleDropOffLocationList.get(dropOffIndex);
	}

	public void regulateDroppingOff(boolean droppingOff) {
		this.droppingOff = droppingOff;
	}

	public Unit getUnit(int index) {
		return containedUnits.get(index);
	}
	
	public Unit getChosenUnit() {
		return getUnit(chosenIndex);
	}

	public Unit removeUnit(int index) {
		// TODO: move this statement out of this method?
		containedUnits.get(index).regulateHidden(false);
		return containedUnits.remove(index);
	}

	public Unit removeChosenUnit() {
		return removeUnit(chosenIndex);
	}
	
	public int getNumberOfContainedUnits() {
		return containedUnits.size();
	}

	public int getContainerSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return containedUnits.size() == 0;
	}

	public boolean isFull() {
		return containedUnits.size() == size;
	}

	public boolean isDroppingOff() {
		return droppingOff;
	}
}
