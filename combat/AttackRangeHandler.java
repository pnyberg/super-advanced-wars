package combat;

import java.awt.Color;
import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.Direction;
import gameObjects.MapDim;
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
	private MapDim mapDim;
	private UnitGetter unitGetter;
	private boolean[][] rangeMap;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler; 
	private RouteChecker routeChecker;
	private MovementMap movementMap;

	public AttackRangeHandler(MapDim mapDim, UnitGetter unitGetter, DamageHandler damageHandler, StructureHandler structureHandler, RouteChecker routeChecker, MovementMap movementMap) {
		this.mapDim = mapDim;
		this.unitGetter = unitGetter;
		rangeMap = new boolean[mapDim.getTileWidth()][mapDim.getTileHeight()];
		this.damageHandler = damageHandler;
		this.structureHandler = structureHandler;
		this.routeChecker = routeChecker;
		this.movementMap = movementMap;
	}
	
	public void clearRangeMap() {
		rangeMap = new boolean[mapDim.getTileWidth()][mapDim.getTileHeight()];
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

		if (attackingUnit.getPoint().getX() != cursor.getX() || attackingUnit.getPoint().getY() != cursor.getY()) {
			return false;
		}

		int unitTileX = attackingUnit.getPoint().getX() / mapDim.tileSize;
		int unitTileY = attackingUnit.getPoint().getY() / mapDim.tileSize;
		int minRange = attackingUnit.getMinRange();
		int maxRange = attackingUnit.getMaxRange();

		for (int tileY = unitTileY - maxRange ; tileY <= (unitTileY + maxRange) ; tileY++) {
			if (tileY < 0) {
				continue;
			} else if (tileY >= mapDim.getTileHeight()) {
				break;
			}
			for (int tileX = unitTileX - maxRange ; tileX <= (unitTileX + maxRange) ; tileX++) {
				if (tileX < 0) {
					continue;
				} else if (tileX >= mapDim.getTileWidth()) {
					break;
				}

				int distanceFromUnit = Math.abs(unitTileX - tileX) + Math.abs(unitTileY - tileY);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(tileX * mapDim.tileSize, tileY * mapDim.tileSize);
					if (targetUnit != null && damageHandler.validTarget(attackingUnit, targetUnit)) {
						return true;
					}
					Structure targetStructure = structureHandler.getStructure(tileX * mapDim.tileSize, tileY * mapDim.tileSize);
					if (targetStructure != null && structureHandler.unitCanAttackStructure(attackingUnit, targetStructure)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean directUnitCanFire(Unit chosenUnit, Cursor cursor) {
		return canAttackInDirection(chosenUnit, cursor, Direction.NORTH) 
			|| canAttackInDirection(chosenUnit, cursor, Direction.EAST)
			|| canAttackInDirection(chosenUnit, cursor, Direction.SOUTH)
			|| canAttackInDirection(chosenUnit, cursor, Direction.WEST);
	}
	
	private boolean canAttackInDirection(Unit chosenUnit, Cursor cursor, Direction direction) {
		Unit targetUnit = null;
		Structure targetStructure = null;
		
		if (direction == Direction.NORTH) {
			targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY() - mapDim.tileSize);
			targetStructure = structureHandler.getStructure(cursor.getX(), cursor.getY() - mapDim.tileSize);
		} else if (direction == Direction.EAST) {
			targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY());
			targetStructure = structureHandler.getStructure(cursor.getX() + mapDim.tileSize, cursor.getY());
		} else if (direction == Direction.SOUTH) {
			targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY() + mapDim.tileSize);
			targetStructure = structureHandler.getStructure(cursor.getX(), cursor.getY() + mapDim.tileSize);
		} else if (direction == Direction.WEST) {
			targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY());
			targetStructure = structureHandler.getStructure(cursor.getX() - mapDim.tileSize, cursor.getY());
		}
		
		if ((targetUnit != null && damageHandler.validTarget(chosenUnit, targetUnit))) {
			return true;
		}

		if (targetStructure != null && structureHandler.unitCanAttackStructure(chosenUnit, targetStructure)) {
			return true;
		}

		return false;
	}

	public void findPossibleDirectAttackLocations(Unit chosenUnit) {
		routeChecker.findPossibleMovementLocations(chosenUnit);
		for (int n = 0 ; n < mapDim.getTileHeight() ; n++) {
			for (int i = 0 ; i < mapDim.getTileWidth() ; i++) {
				if (movementMap.isAcceptedMove(i, n)) {
					if (i > 0) {
						rangeMap[i - 1][n] = true;
					}
					if (i < (mapDim.getTileWidth() - 1)) {
						rangeMap[i + 1][n] = true;
					}
					if (n > 0) {
						rangeMap[i][n - 1] = true;
					}
					if (n < (mapDim.getTileHeight() - 1)) {
						rangeMap[i][n + 1] = true;
					}
				}
			}
		}
		movementMap.clearMovementMap();
	}

	public void createRangeAttackLocations(Unit chosenUnit) {
		IndirectUnit unit = (IndirectUnit)chosenUnit;

		int unitTileX = unit.getPoint().getX() / mapDim.tileSize;
		int unitTileY = unit.getPoint().getY() / mapDim.tileSize;
		int minRange = unit.getMinRange();
		int maxRange = unit.getMaxRange();

		for (int tileY = unitTileY - maxRange ; tileY <= (unitTileY + maxRange) ; tileY++) {
			if (tileY < 0) {
				continue;
			} else if (tileY >= mapDim.getTileHeight()) {
				break;
			}
			for (int tileX = unitTileX - maxRange ; tileX <= (unitTileX + maxRange) ; tileX++) {
				if (tileX < 0) {
					continue;
				} else if (tileX >= mapDim.getTileWidth()) {
					break;
				}

				int distanceFromUnit = Math.abs(unitTileX - tileX) + Math.abs(unitTileY - tileY);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					rangeMap[tileX][tileY] = true;
				}
			}
		}
	}

	public void calculatePossibleAttackLocations(IndirectUnit indirectUnit) {
		int unitTileX = indirectUnit.getPoint().getX() / mapDim.tileSize;
		int unitTileY = indirectUnit.getPoint().getY() / mapDim.tileSize;
		int minRange = indirectUnit.getMinRange();
		int maxRange = indirectUnit.getMaxRange();

		for (int tileY = unitTileY - maxRange ; tileY <= (unitTileY + maxRange) ; tileY++) {
			if (tileY < 0) {
				continue;
			} else if (tileY >= mapDim.getTileHeight()) {
				break;
			}
			for (int tileX = unitTileX - maxRange ; tileX <= (unitTileX + maxRange) ; tileX++) {
				if (tileX < 0) {
					continue;
				} else if (tileX >= mapDim.getTileWidth()) {
					break;
				}

				int distanceFromUnit = Math.abs(unitTileX - tileX) + Math.abs(unitTileY - tileY);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(tileX * mapDim.tileSize, tileY * mapDim.tileSize);
					Structure targetStructure = structureHandler.getStructure(tileX * mapDim.tileSize, tileY * mapDim.tileSize);
					if (targetUnit != null && damageHandler.validTarget(indirectUnit, targetUnit)) {
						Point p = new Point(tileX * mapDim.tileSize, tileY * mapDim.tileSize);
						indirectUnit.addFiringLocation(p);
						rangeMap[tileX][tileY] = true;
					} else if (targetStructure != null && structureHandler.unitCanAttackStructure(indirectUnit, targetStructure)) {
						Point p = new Point(tileX * mapDim.tileSize, tileY * mapDim.tileSize);
						indirectUnit.addFiringLocation(p);
						rangeMap[tileX][tileY] = true;
					}
				}
			}
		}
	}
	
	public void importStructureAttackLocations(FiringStructure firingStructure) {
		firingStructure.fillRangeMap(rangeMap);
	}
	
	public boolean[][] getRangeMap() {
		return rangeMap;
	}
	
	public void paintRange(Graphics g) {
		for (int n = 0 ; n < mapDim.getTileHeight() ; n++) {
			for (int i = 0 ; i < mapDim.getTileWidth() ; i++) {
				if (rangeMap[i][n]) {
					int paintX = i * mapDim.tileSize;
					int paintY = n * mapDim.tileSize;

					g.setColor(Color.red);
					g.fillRect(paintX, paintY, mapDim.tileSize, mapDim.tileSize);
					g.setColor(Color.black);
					g.drawRect(paintX, paintY, mapDim.tileSize, mapDim.tileSize);
				}
			}
		}
	}
}