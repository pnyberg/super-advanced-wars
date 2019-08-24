package map.structures;

import hero.Hero;

public abstract class FiringStructure extends Structure {
	public FiringStructure(int x, int y, Hero owner, int tileSize) {
		super(x, y, owner, tileSize);
	}

	public abstract int getDamage();
	
	public abstract boolean[][] getFiringRangeMap(int mapTileWidth, int mapTileHeight);
}