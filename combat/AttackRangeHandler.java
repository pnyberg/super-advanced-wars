package combat;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.Direction;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import map.UnitGetter;
import map.structures.FiringStructure;
import map.structures.Structure;
import map.structures.StructureHandler;
import point.Point;
import routing.MovementMap;
import routing.RouteChecker;
import units.IndirectUnit;
import units.Unit;

public class AttackRangeHandler {
	private GameState gameState;
	private MapDimension mapDimension;

	public AttackRangeHandler(GameProperties gameProperties, GameState gameState) {
		this.gameState = gameState;
		mapDimension = gameProperties.getMapDimension();
	}
	
	public void importStructureAttackLocations(FiringStructure firingStructure) {
		int mapTileWidth = mapDimension.getTileWidth();
		int mapTileHeight = mapDimension.getTileHeight();
		boolean [][] firingStructureRangeMap = firingStructure.getFiringRangeMap(mapTileWidth, mapTileHeight);
		gameState.setRangeMap(firingStructureRangeMap);
	}
	
	public boolean[][] getRangeMap() {
		return gameState.getRangeMap();
	}
	
	public void paintRange(Graphics g) {
		for (int y = 0 ; y < mapDimension.getTileHeight() ; y++) {
			for (int x = 0 ; x < mapDimension.getTileWidth() ; x++) {
				if (gameState.getRangeMapLocation(x, y)) {
					int paintX = x * mapDimension.tileSize;
					int paintY = y * mapDimension.tileSize;

					g.setColor(Color.red);
					g.fillRect(paintX, paintY, mapDimension.tileSize, mapDimension.tileSize);
					g.setColor(Color.black);
					g.drawRect(paintX, paintY, mapDimension.tileSize, mapDimension.tileSize);
				}
			}
		}
	}
}