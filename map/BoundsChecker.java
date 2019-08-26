package map;

import gameObjects.MapDimension;

public class BoundsChecker {
	private MapDimension mapDimension;

	public BoundsChecker(MapDimension mapDimension) {
		this.mapDimension = mapDimension;
	}
	
	public boolean tilePointOutOfBounds(int tileX, int tileY) {
		if (tileX < 0 || mapDimension.getTileWidth() <= tileX) {
			return true;
		}
		if (tileY < 0 || mapDimension.getTileHeight() <= tileY) {
			return true;
		}
		return false;
	}
	
	public boolean pointOutOfBounds(int targetX, int targetY) {
		if (targetX < 0 || targetX >= mapDimension.getTileWidth() * mapDimension.tileSize) {
			return true;
		}
		if (targetY < 0 || targetY >= mapDimension.getTileHeight() * mapDimension.tileSize) {
			return true;
		}
		return false;
	}
}