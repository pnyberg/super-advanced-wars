import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class IndirectUnit extends Unit {
	protected int minimumRange, maximumRange;
	protected ArrayList<Point> possibleFiringLocationList;

	public IndirectUnit(int x, int y, Color color) {
		super(x, y, color);

		attackType = Unit.INDIRECT_ATTACK;

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

		Point p = possibleFiringLocationList.get(firingIndex);
		firingIndex = (firingIndex + 1) % possibleFiringLocationList.size();

		return p;
	}

	public int getMinRange() {
		return minimumRange;
	}

	public int getMaxRange() {
		return maximumRange;
	}


	public abstract void paint(Graphics g, int tileSize);
}