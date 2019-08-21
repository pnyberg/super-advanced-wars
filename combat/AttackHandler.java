package combat;

import cursors.Cursor;
import gameObjects.Direction;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import map.UnitGetter;
import map.structures.Structure;
import map.structures.StructureHandler;
import point.Point;
import units.IndirectUnit;
import units.Unit;

public class AttackHandler {
	private MapDimension mapDimension;
	private UnitGetter unitGetter;
	private AttackRangeHandler attackRangeHandler;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler;
	
	// TODO: rewrite to have fewer parameters
	public AttackHandler(GameProperties gameProperties, GameState gameState) {
		this.mapDimension = gameProperties.getMapDimension();
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
		attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		this.damageHandler = new DamageHandler(gameState.getHeroHandler(), gameProperties.getGameMap());
		this.structureHandler = new StructureHandler(gameState, mapDimension);
	}

	// TODO: rewrite code to make it simpler (if possible)
	public void setUpFiringTargets(Unit chosenUnit, Cursor cursor) {
		chosenUnit.regulateAttack(true);
		int x = chosenUnit.getPoint().getX();
		int y = chosenUnit.getPoint().getY();

		if (chosenUnit instanceof IndirectUnit) {
			IndirectUnit indirectUnit = (IndirectUnit)chosenUnit;
			attackRangeHandler.calculatePossibleRangeTargetLocations(indirectUnit);
			Point p = indirectUnit.getNextFiringLocation();
			x = p.getX();
			y = p.getY();
		} else {
			boolean validTargetNorth = validTarget(Direction.NORTH, x, y, chosenUnit);
			boolean validTargetEast = validTarget(Direction.EAST, x, y, chosenUnit);
			boolean validTargetSouth = validTarget(Direction.SOUTH, x, y, chosenUnit);
			boolean validTargetWest = validTarget(Direction.WEST, x, y, chosenUnit);
			
			if (validTargetNorth) {
				attackRangeHandler.getRangeMap()[x / mapDimension.tileSize][y / mapDimension.tileSize - 1] = true;
			}
			if (validTargetEast) {
				attackRangeHandler.getRangeMap()[x / mapDimension.tileSize + 1][y / mapDimension.tileSize] = true;
			}
			if (validTargetSouth) {
				attackRangeHandler.getRangeMap()[x / mapDimension.tileSize][y / mapDimension.tileSize + 1] = true;
			}
			if (validTargetWest) {
				attackRangeHandler.getRangeMap()[x / mapDimension.tileSize - 1][y / mapDimension.tileSize] = true;
			}
			
			if (validTargetNorth) {
				y -= mapDimension.tileSize;
			} else if (validTargetEast) {
				x += mapDimension.tileSize;
			} else if (validTargetSouth) {
				y += mapDimension.tileSize;
			} else if (validTargetWest) {
				x -= mapDimension.tileSize;
			} else {
				return; // 
			}
		}

		cursor.setPosition(x, y);
	}
	
	// TODO: rewrite code to make it simpler (if possible)
	private boolean validTarget(Direction direction, int x, int y, Unit chosenUnit) {
		Unit targetUnit = null;
		Structure targetStructure = null;
		boolean directionCondition = false;
		
		if (direction == Direction.NORTH) {
			y -= mapDimension.tileSize;
			directionCondition = y > 0;
		} else if (direction == Direction.EAST) {
			x += mapDimension.tileSize;
			directionCondition = x < (mapDimension.getTileWidth() - 1) * mapDimension.tileSize;
		} else if (direction == Direction.SOUTH) {
			y += mapDimension.tileSize;
			directionCondition = y < (mapDimension.getTileHeight() - 1) * mapDimension.tileSize;
		} else if (direction == Direction.WEST) {
			x -= mapDimension.tileSize;
			directionCondition = x > 0;
		}
		targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(x, y);
		targetStructure = structureHandler.getStructure(x, y);
		
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