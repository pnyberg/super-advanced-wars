package map;

import map.area.Area;
import map.area.TerrainType;
import units.Unit;

public class GameMap {
	private Area[][] map;
	private int tileSize;
	
	public GameMap(Area[][] map, int tileSize) {
		this.map = map;
		this.tileSize = tileSize;
	}
	
	public void resizeMap(int width, int height) {
		map = new Area[width][height];
	}
	
	public Area getArea(int tileX, int tileY) {
		return map[tileX][tileY];
	}
	
	public TerrainType getTerrainType(int tileX, int tileY) {
		return map[tileX][tileY].getTerrainType();
	}
	
	public TerrainType getTerrainTypeAtUnitsPosition(Unit unit) {
		int tileX = unit.getPosition().getX() / tileSize;
		int tileY = unit.getPosition().getY() / tileSize;
		return getTerrainType(tileX, tileY);
	}
	
	public int getTileWidth() {
		return map.length;
	}
	
	public int getTileHeight() {
		if (map.length == 0) {
			return 0;
		}
		return map[0].length;
	}
}