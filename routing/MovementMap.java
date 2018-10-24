package routing;

import gameObjects.MapDim;

public class MovementMap {
	private boolean[][] movementMap;
	private MapDim mapDim;
	
	public MovementMap(MapDim mapDim) {
		this.mapDim = mapDim;
		clearMovementMap();
	}

	public void clearMovementMap() {
		movementMap = new boolean[mapDim.getTileWidth()][mapDim.getTileHeight()];
	}

	public void setAcceptedMove(int tileX, int tileY) {
		movementMap[tileX][tileY] = true;
	}
	
	public boolean isAcceptedMove(int tileX, int tileY) {
		return movementMap[tileX][tileY];
	}
}