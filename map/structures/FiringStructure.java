package map.structures;

import java.awt.Color;

public abstract class FiringStructure extends Structure {
	public FiringStructure(int x, int y, Color color, int tileSize) {
		super(x, y, color, tileSize);
	}

	public abstract int getDamage();
	
	public abstract void fillRangeMap(boolean[][] rangeMap);
}