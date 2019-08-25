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
	private AttackHandler attackHandler;
	private MapDimension mapDimension;
	private UnitGetter unitGetter;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler; 
	private RouteChecker routeChecker;

	public AttackRangeHandler(GameProperties gameProperties, GameState gameState) {
		this.gameState = gameState;
		attackHandler = new AttackHandler(gameProperties, gameState);
		mapDimension = gameProperties.getMapDimension();
		unitGetter = new UnitGetter(gameState.getHeroHandler());
		damageHandler = new DamageHandler(gameState.getHeroHandler(), gameProperties.getGameMap());
		structureHandler = new StructureHandler(gameState, mapDimension);
		routeChecker = new RouteChecker(gameProperties, gameState);
	}
	
	public void clearRangeMap() {
		gameState.resetRangeMap();
	}

	public void findPossibleDirectAttackLocations(Unit chosenUnit) {
		MovementMap movementMap = routeChecker.retrievePossibleMovementLocations(chosenUnit);
		for (int tileY = 0 ; tileY < mapDimension.getTileHeight() ; tileY++) {
			for (int tileX = 0 ; tileX < mapDimension.getTileWidth() ; tileX++) {
				if (movementMap.isAcceptedMove(tileX, tileY)) {
					// add possible attack-locations from "current position"
					addRangeMapLocationIfValid(tileX, tileY, Direction.NORTH);
					addRangeMapLocationIfValid(tileX, tileY, Direction.EAST);
					addRangeMapLocationIfValid(tileX, tileY, Direction.SOUTH);
					addRangeMapLocationIfValid(tileX, tileY, Direction.WEST);
				}
			}
		}
	}
	
	private void addRangeMapLocationIfValid(int tileX, int tileY, Direction direction) {
		Point tilePoint = attackHandler.getNeighbourTileLocationForDirection(tileX, tileY, direction);
		if (!tilePointOutOfBounds(tilePoint)) {
			gameState.enableRangeMapLocation(tilePoint.getX(), tilePoint.getY());
		}
	}
	
	// TODO: move this to a proper class?
	private boolean tilePointOutOfBounds(Point point) {
		if (0 < point.getX() && point.getX() < (mapDimension.getTileWidth() - 1)) {
			return true;
		}
		if (0 < point.getY() && point.getY() < (mapDimension.getTileHeight() - 1)) {
			return true;
		}
		return false;
	}
	
	public void fillRangeAttackMap(Unit chosenUnit) {
		IndirectUnit attackingUnit = (IndirectUnit)chosenUnit;
		int unitTileX = attackingUnit.getPosition().getX() / mapDimension.tileSize;
		int unitTileY = attackingUnit.getPosition().getY() / mapDimension.tileSize;
		int maxRange = attackingUnit.getMaxRange();
		int startTileX = attackHandler.getMinTilePos(unitTileX, maxRange);
		int startTileY = attackHandler.getMinTilePos(unitTileY, maxRange);

		for (int tileY = startTileY ; tileY <= attackHandler.getMaxTileY(unitTileY, maxRange) ; tileY++) {
			for (int tileX = startTileX ; tileX <= attackHandler.getMaxTileX(unitTileX, maxRange) ; tileX++) {
				if (attackHandler.isValidRangeDistance(attackingUnit, tileX, tileY)) {
					gameState.enableRangeMapLocation(tileX, tileY);
				}
			}
		}
	}

	public void calculatePossibleRangeTargetLocations(IndirectUnit indirectUnit) {
		int unitTileX = indirectUnit.getPosition().getX() / mapDimension.tileSize;
		int unitTileY = indirectUnit.getPosition().getY() / mapDimension.tileSize;
		int maxRange = indirectUnit.getMaxRange();
		int startTileX = attackHandler.getMinTilePos(unitTileX, maxRange);
		int startTileY = attackHandler.getMinTilePos(unitTileY, maxRange);

		for (int tileY = startTileY ; tileY <= attackHandler.getMaxTileY(unitTileY, maxRange) ; tileY++) {
			for (int tileX = startTileX ; tileX <= attackHandler.getMaxTileX(unitTileX, maxRange) ; tileX++) {
				if (attackHandler.isValidRangeDistance(indirectUnit, tileX, tileY)) {
					int x = tileX * mapDimension.tileSize;
					int y = tileY * mapDimension.tileSize;
					Point p = new Point(x, y);
					if (attackHandler.attackableTargetAtLocation(indirectUnit, x, y)) {
						indirectUnit.addFiringLocation(p);
						gameState.enableRangeMapLocation(tileX, tileY);
					}
				}
			}
		}
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