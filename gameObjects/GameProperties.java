package gameObjects;

import map.GameMap;
import point.Point;

public class GameProperties {
	public final int fuelMaintenancePerTurn = 5;
	public final int initialMoneyPerBuilding = 1000;

	private MapDimension mapDimension;
	private GameMap gameMap;
	private Point infoBoxAnchorPoint;
	
	public GameProperties(MapDimension mapDimension, GameMap gameMap) {
		this.mapDimension = mapDimension;
		this.gameMap = gameMap;
		
		infoBoxAnchorPoint = new Point(0, gameMap.getTileHeight() * mapDimension.tileSize);
	}
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public Point getInfoBoxAnchorPoint() {
		return infoBoxAnchorPoint;
	}
	
	public int getTileSize() {
		return mapDimension.tileSize;
	}
	
	// TODO: remove when possible
	public MapDimension getMapDim() {
		return mapDimension;
	}
}