package combat;

import cursors.Cursor;
import gameObjects.Direction;
import gameObjects.MapDim;
import map.UnitGetter;
import map.structures.Structure;
import map.structures.StructureHandler;
import point.Point;
import units.IndirectUnit;
import units.Unit;

public class AttackHandler {
	private MapDim mapDim;
	private UnitGetter unitGetter;
	private AttackRangeHandler attackRangeHandler;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler;
	
	public AttackHandler(MapDim mapDim, UnitGetter unitGetter, AttackRangeHandler attackRangeHandler, DamageHandler damageHandler, StructureHandler structureHandler) {
		this.mapDim = mapDim;
		this.unitGetter = unitGetter;
		this.attackRangeHandler = attackRangeHandler;
		this.damageHandler = damageHandler;
		this.structureHandler = structureHandler;
	}

	public void setUpFiringTargets(Unit chosenUnit, Cursor cursor) {
		chosenUnit.regulateAttack(true);

		int x = chosenUnit.getPoint().getX();
		int y = chosenUnit.getPoint().getY();

		if (chosenUnit instanceof IndirectUnit) {
			IndirectUnit indirectUnit = (IndirectUnit)chosenUnit;
			attackRangeHandler.calculatePossibleAttackLocations(indirectUnit);
			Point p = indirectUnit.getNextFiringLocation();
			x = p.getX();
			y = p.getY();
		} else {
			boolean validTargetNorth = validTarget(Direction.NORTH, x, y, chosenUnit);
			boolean validTargetEast = validTarget(Direction.EAST, x, y, chosenUnit);
			boolean validTargetSouth = validTarget(Direction.SOUTH, x, y, chosenUnit);
			boolean validTargetWest = validTarget(Direction.WEST, x, y, chosenUnit);
			
			if (validTargetNorth) {
				attackRangeHandler.getRangeMap()[x / mapDim.tileSize][y / mapDim.tileSize - 1] = true;
			}
			if (validTargetEast) {
				attackRangeHandler.getRangeMap()[x / mapDim.tileSize + 1][y / mapDim.tileSize] = true;
			}
			if (validTargetSouth) {
				attackRangeHandler.getRangeMap()[x / mapDim.tileSize][y / mapDim.tileSize + 1] = true;
			}
			if (validTargetWest) {
				attackRangeHandler.getRangeMap()[x / mapDim.tileSize - 1][y / mapDim.tileSize] = true;
			}
			
			if (validTargetNorth) {
				y -= mapDim.tileSize;
			} else if (validTargetEast) {
				x += mapDim.tileSize;
			} else if (validTargetSouth) {
				y += mapDim.tileSize;
			} else if (validTargetWest) {
				x -= mapDim.tileSize;
			} else {
				return; // 
			}
		}

		cursor.setPosition(x, y);
	}
	
	private boolean validTarget(Direction direction, int x, int y, Unit chosenUnit) {
		Unit targetUnit = null;
		Structure targetStructure = null;
		boolean directionCondition = false;
		
		if (direction == Direction.NORTH) {
			targetUnit = unitGetter.getNonFriendlyUnit(x, y - mapDim.tileSize);
			targetStructure = structureHandler.getStructure(x, y - mapDim.tileSize);
			directionCondition = y > 0;
		} else if (direction == Direction.EAST) {
			targetUnit = unitGetter.getNonFriendlyUnit(x + mapDim.tileSize, y);
			targetStructure = structureHandler.getStructure(x + mapDim.tileSize, y);
			directionCondition = x < (mapDim.getTileWidth() - 1) * mapDim.tileSize;
		} else if (direction == Direction.SOUTH) {
			targetUnit = unitGetter.getNonFriendlyUnit(x, y + mapDim.tileSize);
			targetStructure = structureHandler.getStructure(x, y + mapDim.tileSize);
			directionCondition = y < (mapDim.getTileHeight() - 1) * mapDim.tileSize;
		} else if (direction == Direction.WEST) {
			targetUnit = unitGetter.getNonFriendlyUnit(x - mapDim.tileSize, y);
			targetStructure = structureHandler.getStructure(x - mapDim.tileSize, y);
			directionCondition = x > 0;
		}
		
		if (!directionCondition) {
			return false;
		}
		if (targetUnit != null && damageHandler.validTarget(chosenUnit, targetUnit)) {
			return true;
		}
		if (targetStructure != null && structureHandler.unitCanAttackStructure(chosenUnit, targetStructure)) {
			return true;
		}
		
		return false;
	}

	public boolean unitWantsToFire(Unit chosenUnit) {
		return chosenUnit != null && chosenUnit.isAttacking();
	}

	public AttackRangeHandler getAttackRangeHandler() {
		return attackRangeHandler;
	}
}