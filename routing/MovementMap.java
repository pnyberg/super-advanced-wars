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
		movementMap = new boolean[mapDim.getWidth()][mapDim.getHeight()];
	}

	public void setAcceptedMove(int x, int y) {
		movementMap[x][y] = true;
	}
	
	public boolean isAcceptedMove(int x, int y) {
		return movementMap[x][y];
	}
}
