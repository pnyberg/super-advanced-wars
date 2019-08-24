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
import unitUtils.AttackType;
import units.IndirectUnit;
import units.Unit;

public class AttackHandler {
	private MapDimension mapDimension;
	private UnitGetter unitGetter;
	private AttackRangeHandler attackRangeHandler;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler;
	
	public AttackHandler(GameProperties gameProperties, GameState gameState) {
		mapDimension = gameProperties.getMapDimension();
		unitGetter = new UnitGetter(gameState.getHeroHandler());
		attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		damageHandler = new DamageHandler(gameState.getHeroHandler(), gameProperties.getGameMap());
		structureHandler = new StructureHandler(gameState, mapDimension);
	}

	public void setUpFiringTargets(Unit chosenUnit) {
		if (chosenUnit instanceof IndirectUnit) {
			IndirectUnit indirectUnit = (IndirectUnit)chosenUnit;
			attackRangeHandler.calculatePossibleRangeTargetLocations(indirectUnit);
		} else {
			setUpDirectAttackFiringTargets(chosenUnit);
		}
	}
	
	// TODO: change from direction-loop to four sub-method calls?
	private void setUpDirectAttackFiringTargets(Unit chosenUnit) {
		int x = chosenUnit.getPosition().getX();
		int y = chosenUnit.getPosition().getY();
		Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
		for (Direction direction : directions) {
			Point neighbourLocation = getNeighbourLocationForDirection(x, y, direction);
			boolean validTargetInDirection = validTargetAtLocation(neighbourLocation, chosenUnit);
			if (validTargetInDirection) {
				int targetTileX = neighbourLocation.getX() / mapDimension.tileSize;
				int targetTileY = neighbourLocation.getY() / mapDimension.tileSize;
				attackRangeHandler.getRangeMap()[targetTileX][targetTileY] = true;
				chosenUnit.addFiringLocation(neighbourLocation);
			}
		}
	}
	
	private boolean validTargetAtLocation(Point targetLocation, Unit chosenUnit) {
		int targetX = targetLocation.getX();
		int targetY = targetLocation.getY();
		// TODO: use a general out-of-bounds-checking-method (in a proper class)
		if (targetX < 0 || targetX >= mapDimension.getTileWidth() * mapDimension.tileSize
			|| targetY < 0 || targetY >= mapDimension.getTileHeight() * mapDimension.tileSize) {
			return false;
		}
		Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(targetX, targetY);
		Structure targetStructure = structureHandler.getStructure(targetX, targetY);
		
		if (targetUnit != null && damageHandler.unitCanAttackTargetUnit(chosenUnit, targetUnit)) {
			return true;
		}
		if (targetStructure != null && structureHandler.unitCanAttackStructure(chosenUnit, targetStructure)) {
			return true;
		}
		return false;
	}

	// TODO: move this to a proper class?
	private Point getNeighbourLocationForDirection(int x, int y, Direction direction) {
		if (direction == Direction.NORTH) {
			y -= mapDimension.tileSize;
		} else if (direction == Direction.EAST) {
			x += mapDimension.tileSize;
		} else if (direction == Direction.SOUTH) {
			y += mapDimension.tileSize;
		} else if (direction == Direction.WEST) {
			x -= mapDimension.tileSize;
		}
		return new Point(x, y);
	}
	
	public boolean unitCanFire(Unit chosenUnit, Cursor cursor) {
		if (chosenUnit instanceof IndirectUnit) {
			return indirectUnitCanFire(chosenUnit, cursor);
		} else if (chosenUnit.getAttackType() == AttackType.NONE) {
			return false;
		}
		return directUnitCanFire(chosenUnit, cursor);
	}

	private boolean indirectUnitCanFire(Unit chosenUnit, Cursor cursor) {
		IndirectUnit attackingUnit = (IndirectUnit)chosenUnit;

		if (!attackingUnit.getPosition().isSamePosition(cursor.getX(), cursor.getY())) {
			return false;
		}

		int unitTileX = attackingUnit.getPosition().getX() / mapDimension.tileSize;
		int unitTileY = attackingUnit.getPosition().getY() / mapDimension.tileSize;
		int maxRange = attackingUnit.getMaxRange();
		int startTileX = getMinTilePos(unitTileX, maxRange);
		int startTileY = getMinTilePos(unitTileY, maxRange);

		for (int tileY = startTileY ; tileY <= getMaxTileY(unitTileY, maxRange) ; tileY++) {
			for (int tileX = startTileX ; tileX <= getMaxTileX(unitTileX, maxRange) ; tileX++) {
				if (isValidIndirectAttackableTarget(attackingUnit, tileX, tileY)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private int getMinTilePos(int unitTilePos, int maxRange) {
		int minTilePos = unitTilePos - maxRange;
		if (minTilePos < 0) {
			return 0;
		}
		return minTilePos;
	}
	
	private int getMaxTileX(int unitTileX, int maxRange) {
		int maxTileX = unitTileX + maxRange;
		if (maxTileX >= mapDimension.getTileWidth()) {
			return mapDimension.getTileWidth() - 1;
		}
		return maxTileX;
	}
	
	private int getMaxTileY(int unitTileY, int maxRange) {
		int maxTileY = unitTileY + maxRange;
		if (maxTileY >= mapDimension.getTileHeight()) {
			return mapDimension.getTileHeight() - 1;
		}
		return maxTileY;
	}
	
	private boolean isValidIndirectAttackableTarget(IndirectUnit attackingUnit, int tileX, int tileY) {
		boolean validRangeDistance =  isValidRangeDistance(attackingUnit, tileX, tileY);
		boolean attackableTarget = isIndirectAttackableTarget(attackingUnit, tileX, tileY);
		return validRangeDistance && attackableTarget;
	}
	
	private boolean isValidRangeDistance(IndirectUnit attackingUnit, int tileX, int tileY) {
		int unitTileX = attackingUnit.getPosition().getX() / mapDimension.tileSize;
		int unitTileY = attackingUnit.getPosition().getY() / mapDimension.tileSize;
		int minRange = attackingUnit.getMinRange();
		int maxRange = attackingUnit.getMaxRange();
		int distanceFromUnit = Math.abs(unitTileX - tileX) + Math.abs(unitTileY - tileY);
		return minRange <= distanceFromUnit && distanceFromUnit <= maxRange;
	}
	
	private boolean isIndirectAttackableTarget(IndirectUnit attackingUnit, int targetTileX, int targetTileY) {
		int x = targetTileX * mapDimension.tileSize;
		int y = targetTileY * mapDimension.tileSize;
		return attackableTargetAtLocation(attackingUnit, x, y);
	}

	private boolean directUnitCanFire(Unit attackingUnit, Cursor cursor) {
		return canDirectAttackInDirection(attackingUnit, cursor, Direction.NORTH) 
			|| canDirectAttackInDirection(attackingUnit, cursor, Direction.EAST)
			|| canDirectAttackInDirection(attackingUnit, cursor, Direction.SOUTH)
			|| canDirectAttackInDirection(attackingUnit, cursor, Direction.WEST);
	}
	
	private boolean canDirectAttackInDirection(Unit attackingUnit, Cursor cursor, Direction direction) {
		Point neighbourLocation = getNeighbourLocationForDirection(cursor.getX(), cursor.getY(), direction);
		int x = neighbourLocation.getX();
		int y = neighbourLocation.getY();
		return attackableTargetAtLocation(attackingUnit, x, y);
	}
	
	private boolean attackableTargetAtLocation(Unit attackingUnit, int x, int y) {
		Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(x, y);
		if ((targetUnit != null && damageHandler.unitCanAttackTargetUnit(attackingUnit, targetUnit))) {
			return true;
		}
		Structure targetStructure = structureHandler.getStructure(x, y);
		if (targetStructure != null && structureHandler.unitCanAttackStructure(attackingUnit, targetStructure)) {
			return true;
		}
		return false;
	}

	public boolean unitWantsToFire(Unit chosenUnit) {
		return chosenUnit != null && chosenUnit.isAttacking();
	}
}