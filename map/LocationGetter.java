package map;

import gameObjects.Direction;
import point.Point;

public class LocationGetter {
	private int tileSize;
	
	public LocationGetter(int tileSize) {
		this.tileSize = tileSize;
	}
	
	public Point getNeighbourTileLocationForDirection(int tileX, int tileY, Direction direction) {
		if (direction == Direction.NORTH) {
			tileY--;
		} else if (direction == Direction.EAST) {
			tileX++;
		} else if (direction == Direction.SOUTH) {
			tileY++;
		} else if (direction == Direction.WEST) {
			tileX--;
		}
		return new Point(tileX, tileY);
	}
	
	public Point getNeighbourLocationForDirection(int x, int y, Direction direction) {
		int tileX = x / tileSize;
		int tileY = y / tileSize;
		Point tilePoint = getNeighbourTileLocationForDirection(tileX, tileY, direction);
		int posX = tilePoint.getX() * tileSize;
		int posY = tilePoint.getY() * tileSize;
		return new Point(posX, posY);
	}
	
}