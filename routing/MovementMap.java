package routing;

import gameObjects.MapDimension;

public class MovementMap {
	private boolean[][] movementMap;
	private MapDimension mapDimension;
	
	public MovementMap(MapDimension mapDimension) {
		this.mapDimension = mapDimension;
		clearMovementMap();
	}

	public void clearMovementMap() {
		movementMap = new boolean[mapDimension.getTileWidth()][mapDimension.getTileHeight()];
	}

	public void setAcceptedMove(int tileX, int tileY) {
		movementMap[tileX][tileY] = true;
	}
	
	public boolean isAcceptedMove(int tileX, int tileY) {
		return movementMap[tileX][tileY];
	}
}