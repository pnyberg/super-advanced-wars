import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class IndirectUnit extends Unit {
	protected int minimumRange, maximumRange, firingIndex;
	protected ArrayList<Point> possibleFiringLocationList;

	public IndirectUnit(int x, int y, Color color) {
		super(x, y, color);

		attackType = Unit.INDIRECT_ATTACK;
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
		System.out.println(possibleFiringLocationList.size() + " - " + firingIndex);
		if (possibleFiringLocationList.isEmpty()) {
			return null;
		}

		firingIndex = (firingIndex + 1) % possibleFiringLocationList.size();
		Point p = possibleFiringLocationList.get(firingIndex);

		return p;
	}

	public Point getPreviousFiringLocation() {
		System.out.println(possibleFiringLocationList.size() + " - " + firingIndex);
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