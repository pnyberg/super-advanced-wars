package routing;

import gameObjects.DimensionObject;

public class MovementMap {
	private boolean[][] movementMap;
	private DimensionObject mapDim;
	
	public MovementMap(DimensionObject mapDim) {
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