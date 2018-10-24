package map;

import map.area.Area;

public class GameMap {
	private Area[][] map;
	
	public GameMap(int width, int height) {
		map = new Area[width][height];
	}
	
	public void resizeMap(int width, int height) {
		map = new Area[width][height];
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
	
	public Area[][] getMap() {
		return map;
	}
}