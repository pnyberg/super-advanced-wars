package routing;

import gameObjects.MapDim;

public class MovementMap {
	private boolean[][] movementMap;
	private MapDim mapDimension;
	
	public MovementMap(MapDim mapDimension) {
		this.mapDimension = mapDimension;
		clearMovementMap();
	}

	public void clearMovementMap() {
		movementMap = new boolean[mapDimension.width][mapDimension.height];
	}

	public void setAcceptedMove(int x, int y) {
		movementMap[x][y] = true;
	}
	
	public boolean isAcceptedMove(int x, int y) {
		return movementMap[x][y];
	}
}
