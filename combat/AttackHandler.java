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
		int x = chosenUnit.getPosition().getX();
		int y = chosenUnit.getPosition().getY();

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
				int targetTileX = x / mapDimension.tileSize;
				int targetTileY = y / mapDimension.tileSize - 1;
				chosenUnit.addFiringLocation(new Point(targetTileX, targetTileY));
			}
			if (validTargetEast) {
				attackRangeHandler.getRangeMap()[x / mapDimension.tileSize + 1][y / mapDimension.tileSize] = true;
				int targetTileX = x / mapDimension.tileSize + 1;
				int targetTileY = y / mapDimension.tileSize;
				chosenUnit.addFiringLocation(new Point(targetTileX, targetTileY));
			}
			if (validTargetSouth) {
				attackRangeHandler.getRangeMap()[x / mapDimension.tileSize][y / mapDimension.tileSize + 1] = true;
				int targetTileX = x / mapDimension.tileSize;
				int targetTileY = y / mapDimension.tileSize + 1;
				chosenUnit.addFiringLocation(new Point(targetTileX, targetTileY));
			}
			if (validTargetWest) {
				attackRangeHandler.getRangeMap()[x / mapDimension.tileSize - 1][y / mapDimension.tileSize] = true;
				int targetTileX = x / mapDimension.tileSize - 1;
				int targetTileY = y / mapDimension.tileSize;
				chosenUnit.addFiringLocation(new Point(targetTileX, targetTileY));
			}
			
			/*
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
			*/
			Point p = chosenUnit.getNextFiringLocation();
			x = p.getX();
			y = p.getY();
		}

		cursor.setPosition(x, y);
	}
	
	// TODO: rewrite code to make it simpler (if possible)
	private boolean validTarget(Direction direction, int x, int y, Unit chosenUnit) {
		Unit targetUnit = null;
		Structure targetStructure = null;
		boolean directionCondition = false;
		int targetX = x;
		int targetY = y;
		
		if (direction == Direction.NORTH) {
			directionCondition = y > 0;
			targetY -= mapDimension.tileSize;
		} else if (direction == Direction.EAST) {
			directionCondition = x < (mapDimension.getTileWidth() - 1) * mapDimension.tileSize;
			targetX += mapDimension.tileSize;
		} else if (direction == Direction.SOUTH) {
			directionCondition = y < (mapDimension.getTileHeight() - 1) * mapDimension.tileSize;
			targetY += mapDimension.tileSize;
		} else if (direction == Direction.WEST) {
			directionCondition = x > 0;
			targetX -= mapDimension.tileSize;
		}
		targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(targetX, targetY);
		targetStructure = structureHandler.getStructure(targetX, targetY);
		
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