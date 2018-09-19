package handlers;

public class MovementMap {
	private boolean[][] movementMap;
	private MapDimension mapDimension;
	
	public MovementMap(MapDimension mapDimension) {
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
