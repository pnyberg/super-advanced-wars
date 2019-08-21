package gameObjects;

import map.GameMap;
import point.Point;

public class GameProperties {
	public final int fuelMaintenancePerTurn = 5;
	public final int initialMoneyPerBuilding = 1000;

	private MapDimension mapDimension;
	private GameMap gameMap;
	private GraphicMetrics infoBoxGraphicMetrics;
	
	public GameProperties(MapDimension mapDimension, GameMap gameMap) {
		this.mapDimension = mapDimension;
		this.gameMap = gameMap;
		Point infoBoxAnchorPoint = new Point(0, gameMap.getTileHeight() * mapDimension.tileSize);
		infoBoxGraphicMetrics = new GraphicMetrics(infoBoxAnchorPoint, mapDimension.getTileWidth() * mapDimension.tileSize, 3 * mapDimension.tileSize, mapDimension.tileSize);
	}
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public GraphicMetrics getInfoBoxGraphicMetrics() {
		return infoBoxGraphicMetrics;
	}
	
	public int getTileSize() {
		return mapDimension.tileSize;
	}
	
	// TODO: remove when possible
	public MapDimension getMapDimension() {
		return mapDimension;
	}
}