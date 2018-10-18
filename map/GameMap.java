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
	
	public Area[][] getMap() {
		return map;
	}
}
