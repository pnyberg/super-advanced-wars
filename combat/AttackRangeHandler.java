package combat;

import java.awt.Color;
import java.awt.Graphics;

import cursors.Cursor;
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
import units.airMoving.TCopter;
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class AttackRangeHandler {
	private MapDimension mapDimension;
	private UnitGetter unitGetter;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler; 
	private RouteChecker routeChecker;
	private GameState gameState;

	public AttackRangeHandler(GameProperties gameProperties, GameState gameState) {
		this.mapDimension = gameProperties.getMapDimension();
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
		this.damageHandler = new DamageHandler(gameState.getHeroHandler(), gameProperties.getGameMap());
		this.structureHandler = new StructureHandler(gameState, mapDimension);
		routeChecker = new RouteChecker(gameProperties, gameState);
		this.gameState = gameState;
	}
	
	public void clearRangeMap() {
		gameState.resetRangeMap();
	}

	public boolean unitCanFire(Unit chosenUnit, Cursor cursor) {
		if (chosenUnit instanceof IndirectUnit) {
			return indirectUnitCanFire(chosenUnit, cursor);
		} else if (chosenUnit instanceof APC
					|| chosenUnit instanceof Lander
					|| chosenUnit instanceof TCopter) {
			return false;
		}

		return directUnitCanFire(chosenUnit, cursor);
	}

	private boolean indirectUnitCanFire(Unit chosenUnit, Cursor cursor) {
		IndirectUnit attackingUnit = (IndirectUnit)chosenUnit;

		if (!attackingUnit.getPoint().isSamePosition(cursor.getX(), cursor.getY())) {
			return false;
		}

		int unitTileX = attackingUnit.getPoint().getX() / mapDimension.tileSize;
		int unitTileY = attackingUnit.getPoint().getY() / mapDimension.tileSize;
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
					boolean attackableTarget = isInderectAttackableTarget(attackingUnit, tileX, tileY);
					if (attackableTarget) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean isInderectAttackableTarget(IndirectUnit attackingUnit, int targetTileX, int targetTileY) {
		Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(targetTileX * mapDimension.tileSize, targetTileY * mapDimension.tileSize);
		if (targetUnit != null && damageHandler.validTarget(attackingUnit, targetUnit)) {
			return true;
		}
		Structure targetStructure = structureHandler.getStructure(targetTileX * mapDimension.tileSize, targetTileY * mapDimension.tileSize);
		if (targetStructure != null && structureHandler.unitCanAttackStructure(attackingUnit, targetStructure)) {
			return true;
		}
		return false;
	}

	private boolean directUnitCanFire(Unit chosenUnit, Cursor cursor) {
		return canDirectAttackInDirection(chosenUnit, cursor, Direction.NORTH) 
			|| canDirectAttackInDirection(chosenUnit, cursor, Direction.EAST)
			|| canDirectAttackInDirection(chosenUnit, cursor, Direction.SOUTH)
			|| canDirectAttackInDirection(chosenUnit, cursor, Direction.WEST);
	}
	
	private boolean canDirectAttackInDirection(Unit chosenUnit, Cursor cursor, Direction direction) {
		int x = cursor.getX();
		int y = cursor.getY();
		if (direction == Direction.NORTH) {
			y -= mapDimension.tileSize;
		} else if (direction == Direction.EAST) {
			x += mapDimension.tileSize;
		} else if (direction == Direction.SOUTH) {
			y += mapDimension.tileSize;
		} else if (direction == Direction.WEST) {
			x -= mapDimension.tileSize;
		}
		Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(x, y);
		if ((targetUnit != null && damageHandler.validTarget(chosenUnit, targetUnit))) {
			return true;
		}
		Structure targetStructure = structureHandler.getStructure(x, y);
		if (targetStructure != null && structureHandler.unitCanAttackStructure(chosenUnit, targetStructure)) {
			return true;
		}
		return false;
	}

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
		int unitTileX = attackingUnit.getPoint().getX() / mapDimension.tileSize;
		int unitTileY = attackingUnit.getPoint().getY() / mapDimension.tileSize;
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
		int unitTileX = indirectUnit.getPoint().getX() / mapDimension.tileSize;
		int unitTileY = indirectUnit.getPoint().getY() / mapDimension.tileSize;
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
					if (targetUnit != null && damageHandler.validTarget(indirectUnit, targetUnit)) {
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
		firingStructure.importRangeMap(gameState.getRangeMap());
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