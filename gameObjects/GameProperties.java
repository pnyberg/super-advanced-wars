package gameObjects;

import main.TerrainInfoBox;
import main.UnitContainedInfoBox;
import main.UnitInfoBox;
import map.GameMap;
import point.Point;

public class GameProperties {
	public final int fuelMaintenancePerTurn = 5;
	public final int initialMoneyPerBuilding = 1000;

	private MapDimension mapDimension;
	private GameMap gameMap;
	private GraphicMetrics infoBoxGraphicMetrics;
	private GraphicMetrics terrainInfoBoxGraphicMetrics;
	private GraphicMetrics unitInfoBoxGraphicMetrics;
	private GraphicMetrics unitContainedInfoBoxGraphicMetrics;
	
	public GameProperties(MapDimension mapDimension, GameMap gameMap) {
		this.mapDimension = mapDimension;
		this.gameMap = gameMap;
		Point infoBoxAnchorPoint = new Point(0, gameMap.getTileHeight() * mapDimension.tileSize);
		infoBoxGraphicMetrics = new GraphicMetrics(infoBoxAnchorPoint, mapDimension.getTileWidth() * mapDimension.tileSize, 3 * mapDimension.tileSize, mapDimension.tileSize);
		terrainInfoBoxGraphicMetrics = createTerrainInfoBoxMetrics(infoBoxGraphicMetrics);
		unitInfoBoxGraphicMetrics = createUnitInfoBox(infoBoxGraphicMetrics);
		unitContainedInfoBoxGraphicMetrics = createUnitContainedInfoBox(infoBoxGraphicMetrics);
	}
	
	private GraphicMetrics createTerrainInfoBoxMetrics(GraphicMetrics infoBoxGraphicMetrics) {
		int tileSize = mapDimension.tileSize;
		Point infoBoxAnchorPoint = infoBoxGraphicMetrics.anchorPoint;
		int terrainInfoBoxPosX = infoBoxAnchorPoint.getX() + tileSize / 4;
		int terrainInfoBoxPosY = infoBoxAnchorPoint.getY() + tileSize / 8;
		Point terrainInfoBoxPoint = new Point(terrainInfoBoxPosX, terrainInfoBoxPosY);
		int terrainInfoBoxWidth = tileSize * 2;
		int terrainInfoBoxHeight = infoBoxGraphicMetrics.height - tileSize / 4;
		return new GraphicMetrics(terrainInfoBoxPoint, terrainInfoBoxWidth, terrainInfoBoxHeight, tileSize);
	}
	
	private GraphicMetrics createUnitInfoBox(GraphicMetrics infoBoxGraphicMetrics) {
		int tileSize = mapDimension.tileSize;
		Point infoBoxAnchorPoint = infoBoxGraphicMetrics.anchorPoint;
		int unitInfoBoxPosX = infoBoxAnchorPoint.getX() + tileSize * 2 + tileSize / 4 + 5;
		int unitInfoBoxPosY = infoBoxAnchorPoint.getY() + tileSize / 8;
		Point unitInfoBoxPoint = new Point(unitInfoBoxPosX, unitInfoBoxPosY);
		int unitInfoBoxWidth = tileSize * 2;
		int unitInfoBoxHeight = infoBoxGraphicMetrics.height - tileSize / 4;
		return new GraphicMetrics(unitInfoBoxPoint, unitInfoBoxWidth, unitInfoBoxHeight, tileSize);
	}
	
	private GraphicMetrics createUnitContainedInfoBox(GraphicMetrics infoBoxGraphicMetrics) {
		int tileSize = mapDimension.tileSize;
		Point infoBoxAnchorPoint = infoBoxGraphicMetrics.anchorPoint;
		int unitContainedInfoBoxPosX = infoBoxAnchorPoint.getX() + tileSize * 4 + tileSize / 4 + 10;
		int unitContainedInfoBoxPosY = infoBoxAnchorPoint.getY() + tileSize / 8;
		Point unitContainedInfoBoxPoint = new Point(unitContainedInfoBoxPosX, unitContainedInfoBoxPosY);
		int unitContainedInfoBoxWidht = tileSize * 2;
		int unitContainedInfoBoxHeight = infoBoxGraphicMetrics.height - tileSize / 4;
		return new GraphicMetrics(unitContainedInfoBoxPoint, unitContainedInfoBoxWidht, unitContainedInfoBoxHeight, tileSize);
	}

	public GameMap getGameMap() {
		return gameMap;
	}
	
	public GraphicMetrics getInfoBoxGraphicMetrics() {
		return infoBoxGraphicMetrics;
	}
	
	public GraphicMetrics getTerrainInfoBoxGraphicMetrics() {
		return terrainInfoBoxGraphicMetrics;
	}
	
	public GraphicMetrics getUnitInfoBoxGraphicMetrics() {
		return unitInfoBoxGraphicMetrics;
	}
	
	public GraphicMetrics getUnitContainedInfoBoxGraphicMetrics() {
		return unitContainedInfoBoxGraphicMetrics;
	}
	
	public int getTileSize() {
		return mapDimension.tileSize;
	}
	
	// TODO: remove when possible
	public MapDimension getMapDimension() {
		return mapDimension;
	}
}