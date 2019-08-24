package combat;

import java.awt.Color;
import java.awt.Graphics;

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
	private UnitGetter unitGetter;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler; 
	private RouteChecker routeChecker;

	public AttackRangeHandler(GameProperties gameProperties, GameState gameState) {
		this.gameState = gameState;
		mapDimension = gameProperties.getMapDimension();
		unitGetter = new UnitGetter(gameState.getHeroHandler());
		damageHandler = new DamageHandler(gameState.getHeroHandler(), gameProperties.getGameMap());
		structureHandler = new StructureHandler(gameState, mapDimension);
		routeChecker = new RouteChecker(gameProperties, gameState);
	}
	
	public void clearRangeMap() {
		gameState.resetRangeMap();
	}

	// TODO: refactor from here
	public void findPossibleDirectAttackLocations(Unit chosenUnit) {
		MovementMap movementMap = gameState.getMovementMap();
		// TODO: change so that this call returns a map?
		routeChecker.findPossibleMovementLocations(chosenUnit);
		for (int tileY = 0 ; tileY < mapDimension.getTileHeight() ; tileY++) {
			for (int tileX = 0 ; tileX < mapDimension.getTileWidth() ; tileX++) {
				if (movementMap.isAcceptedMove(tileX, tileY)) {
					// add possible attack-locations from "current position"
					if (tileX > 0) {
						gameState.enableRangeMapLocation(tileX - 1, tileY);
					}
					if (tileX < (mapDimension.getTileWidth() - 1)) {
						gameState.enableRangeMapLocation(tileX + 1, tileY);
					}
					if (tileY > 0) {
						gameState.enableRangeMapLocation(tileX, tileY - 1);
					}
					if (tileY < (mapDimension.getTileHeight() - 1)) {
						gameState.enableRangeMapLocation(tileX, tileY + 1);
					}
				}
			}
		}
		movementMap.clearMovementMap();
	}
	
	public void fillRangeAttackMap(Unit chosenUnit) {
		IndirectUnit attackingUnit = (IndirectUnit)chosenUnit;
		int unitTileX = attackingUnit.getPosition().getX() / mapDimension.tileSize;
		int unitTileY = attackingUnit.getPosition().getY() / mapDimension.tileSize;
		int minRange = attackingUnit.getMinRange();
		int maxRange = attackingUnit.getMaxRange();
		for (int tileY = unitTileY - maxRange ; tileY <= (unitTileY + maxRange) ; tileY++) {
			if (tileY < 0) {
				continue;
			} else if (tileY >= mapDimension.getTileHeight()) {
				break;
			}
			for (int tileX = unitTileX - maxRange ; tileX <= (unitTileX + maxRange) ; tileX++) {
				if (tileX < 0) {
					continue;
				} else if (tileX >= mapDimension.getTileWidth()) {
					break;
				}
				int distanceFromUnit = Math.abs(unitTileX - tileX) + Math.abs(unitTileY - tileY);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					gameState.enableRangeMapLocation(tileX, tileY);
				}
			}
		}
	}

	public void calculatePossibleRangeTargetLocations(IndirectUnit indirectUnit) {
		int unitTileX = indirectUnit.getPosition().getX() / mapDimension.tileSize;
		int unitTileY = indirectUnit.getPosition().getY() / mapDimension.tileSize;
		int minRange = indirectUnit.getMinRange();
		int maxRange = indirectUnit.getMaxRange();

		for (int tileY = unitTileY - maxRange ; tileY <= (unitTileY + maxRange) ; tileY++) {
			if (tileY < 0) {
				continue;
			} else if (tileY >= mapDimension.getTileHeight()) {
				break;
			}
			for (int tileX = unitTileX - maxRange ; tileX <= (unitTileX + maxRange) ; tileX++) {
				if (tileX < 0) {
					continue;
				} else if (tileX >= mapDimension.getTileWidth()) {
					break;
				}
				int distanceFromUnit = Math.abs(unitTileX - tileX) + Math.abs(unitTileY - tileY);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(tileX * mapDimension.tileSize, tileY * mapDimension.tileSize);
					Structure targetStructure = structureHandler.getStructure(tileX * mapDimension.tileSize, tileY * mapDimension.tileSize);
					if (targetUnit != null && damageHandler.unitCanAttackTargetUnit(indirectUnit, targetUnit)) {
						Point p = new Point(tileX * mapDimension.tileSize, tileY * mapDimension.tileSize);
						indirectUnit.addFiringLocation(p);
						gameState.enableRangeMapLocation(tileX, tileY);
					} else if (targetStructure != null && structureHandler.unitCanAttackStructure(indirectUnit, targetStructure)) {
						Point p = new Point(tileX * mapDimension.tileSize, tileY * mapDimension.tileSize);
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
		for (int n = 0 ; n < mapDimension.getTileHeight() ; n++) {
			for (int i = 0 ; i < mapDimension.getTileWidth() ; i++) {
				if (gameState.getRangeMapLocation(i, n)) {
					int paintX = i * mapDimension.tileSize;
					int paintY = n * mapDimension.tileSize;

					g.setColor(Color.red);
					g.fillRect(paintX, paintY, mapDimension.tileSize, mapDimension.tileSize);
					g.setColor(Color.black);
					g.drawRect(paintX, paintY, mapDimension.tileSize, mapDimension.tileSize);
				}
			}
		}
	}
}