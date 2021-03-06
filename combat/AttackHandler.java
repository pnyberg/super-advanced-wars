package combat;

import cursors.Cursor;
import gameObjects.Direction;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import map.BoundsChecker;
import map.LocationGetter;
import map.UnitGetter;
import map.area.AreaChecker;
import map.structures.Structure;
import map.structures.StructureHandler;
import menus.unit.UnitMenuHandler;
import point.Point;
import routing.MovementMap;
import routing.RouteChecker;
import unitUtils.AttackType;
import unitUtils.ContUnitHandler;
import units.IndirectUnit;
import units.Unit;

public class AttackHandler {
	private GameProperties gameProperties;
	private GameState gameState;
	private MapDimension mapDimension;
	private UnitGetter unitGetter;
	private AttackRangeHandler attackRangeHandler;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler;
	private RouteChecker routeChecker;
	
	public AttackHandler(GameProperties gameProperties, GameState gameState) {
		this.gameProperties = gameProperties;
		this.gameState = gameState;
		mapDimension = gameProperties.getMapDimension();
		unitGetter = new UnitGetter(gameState.getHeroHandler());
		attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		damageHandler = new DamageHandler(gameState.getHeroHandler(), gameProperties.getGameMap());
		structureHandler = new StructureHandler(gameState, mapDimension);
		routeChecker = new RouteChecker(gameProperties, gameState);
	}

	public void setUpFiringTargets(Unit chosenUnit) {
		if (chosenUnit instanceof IndirectUnit) {
			IndirectUnit indirectUnit = (IndirectUnit)chosenUnit;
			calculatePossibleRangeTargetLocations(indirectUnit);
		} else {
			setUpDirectAttackFiringTargets(chosenUnit);
		}
	}
	
	public void calculatePossibleRangeTargetLocations(IndirectUnit indirectUnit) {
		int unitTileX = indirectUnit.getPosition().getX() / mapDimension.tileSize;
		int unitTileY = indirectUnit.getPosition().getY() / mapDimension.tileSize;
		int maxRange = indirectUnit.getMaxRange();
		int startTileX = getMinTilePos(unitTileX, maxRange);
		int startTileY = getMinTilePos(unitTileY, maxRange);

		for (int tileY = startTileY ; tileY <= getMaxTileY(unitTileY, maxRange) ; tileY++) {
			for (int tileX = startTileX ; tileX <= getMaxTileX(unitTileX, maxRange) ; tileX++) {
				if (isValidRangeDistance(indirectUnit, tileX, tileY)) {
					int x = tileX * mapDimension.tileSize;
					int y = tileY * mapDimension.tileSize;
					Point p = new Point(x, y);
					if (attackableTargetAtLocation(indirectUnit, x, y)) {
						indirectUnit.addFiringLocation(p);
						gameState.enableRangeMapLocation(tileX, tileY);
					}
				}
			}
		}
	}
	
	// TODO: change from direction-loop to four sub-method calls?
	private void setUpDirectAttackFiringTargets(Unit chosenUnit) {
		LocationGetter locationGetter = new LocationGetter(mapDimension.tileSize);
		int x = chosenUnit.getPosition().getX();
		int y = chosenUnit.getPosition().getY();
		Direction[] allDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
		for (Direction direction : allDirections) {
			Point neighbourLocation = locationGetter.getNeighbourLocationForDirection(x, y, direction);
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
		BoundsChecker boundsChecker = new BoundsChecker(mapDimension);
		int targetX = targetLocation.getX();
		int targetY = targetLocation.getY();

		if (boundsChecker.pointOutOfBounds(targetX, targetY)) {
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

	public void findPossibleDirectAttackLocations(Unit chosenUnit) {
		MovementMap movementMap = routeChecker.retrievePossibleMovementLocations(chosenUnit);
		for (int tileY = 0 ; tileY < mapDimension.getTileHeight() ; tileY++) {
			for (int tileX = 0 ; tileX < mapDimension.getTileWidth() ; tileX++) {
				int x = tileX * mapDimension.tileSize;
				int y = tileY * mapDimension.tileSize;
				if (movementMap.isAcceptedMove(tileX, tileY) && unitCanMoveToPosition(chosenUnit, x, y)) {
					// add possible attack-locations from "current position"
					addRangeMapLocationIfValid(tileX, tileY, Direction.NORTH);
					addRangeMapLocationIfValid(tileX, tileY, Direction.EAST);
					addRangeMapLocationIfValid(tileX, tileY, Direction.SOUTH);
					addRangeMapLocationIfValid(tileX, tileY, Direction.WEST);
				}
			}
		}
		movementMap.clearMovementMap();
	}
	
	private boolean unitCanMoveToPosition(Unit chosenUnit, int x, int y) {
		AreaChecker areaChecker = new AreaChecker(gameState.getHeroHandler(), gameProperties.getGameMap());
		ContUnitHandler containerUnitHandler = new ContUnitHandler(gameProperties, gameState);
		if (!areaChecker.areaOccupiedByFriendly(chosenUnit, x, y)) {
			return true;
		}
		if (containerUnitHandler.unitEntryingContainerUnit(chosenUnit, x, y)) {
			return true;
		}
		return unitGetter.hurtSameTypeUnitAtPosition(chosenUnit, x, y);
	}
	
	private void addRangeMapLocationIfValid(int tileX, int tileY, Direction direction) {
		BoundsChecker boundsChecker = new BoundsChecker(mapDimension);
		LocationGetter locationGetter = new LocationGetter(mapDimension.tileSize);
		Point tilePoint = locationGetter.getNeighbourTileLocationForDirection(tileX, tileY, direction);
		if (!boundsChecker.tilePointOutOfBounds(tilePoint.getX(), tilePoint.getY())) {
			gameState.enableRangeMapLocation(tilePoint.getX(), tilePoint.getY());
		}
	}
	
	public void fillRangeAttackMap(Unit chosenUnit) {
		IndirectUnit attackingUnit = (IndirectUnit)chosenUnit;
		int unitTileX = attackingUnit.getPosition().getX() / mapDimension.tileSize;
		int unitTileY = attackingUnit.getPosition().getY() / mapDimension.tileSize;
		int maxRange = attackingUnit.getMaxRange();
		int startTileX = getMinTilePos(unitTileX, maxRange);
		int startTileY = getMinTilePos(unitTileY, maxRange);

		for (int tileY = startTileY ; tileY <= getMaxTileY(unitTileY, maxRange) ; tileY++) {
			for (int tileX = startTileX ; tileX <= getMaxTileX(unitTileX, maxRange) ; tileX++) {
				if (isValidRangeDistance(attackingUnit, tileX, tileY)) {
					gameState.enableRangeMapLocation(tileX, tileY);
				}
			}
		}
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
	
	// TODO: move this to a proper class?
	public int getMinTilePos(int unitTilePos, int maxRange) {
		int minTilePos = unitTilePos - maxRange;
		if (minTilePos < 0) {
			return 0;
		}
		return minTilePos;
	}
	
	// TODO: move this to a proper class?
	public int getMaxTileX(int unitTileX, int maxRange) {
		int maxTileX = unitTileX + maxRange;
		if (maxTileX >= mapDimension.getTileWidth()) {
			return mapDimension.getTileWidth() - 1;
		}
		return maxTileX;
	}
	
	// TODO: move this to a proper class?
	public int getMaxTileY(int unitTileY, int maxRange) {
		int maxTileY = unitTileY + maxRange;
		if (maxTileY >= mapDimension.getTileHeight()) {
			return mapDimension.getTileHeight() - 1;
		}
		return maxTileY;
	}
	
	// TODO: move this to a proper class?
	public boolean isValidIndirectAttackableTarget(IndirectUnit attackingUnit, int tileX, int tileY) {
		boolean validRangeDistance =  isValidRangeDistance(attackingUnit, tileX, tileY);
		boolean attackableTarget = isIndirectAttackableTarget(attackingUnit, tileX, tileY);
		return validRangeDistance && attackableTarget;
	}
	
	public boolean isValidRangeDistance(IndirectUnit attackingUnit, int tileX, int tileY) {
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
		LocationGetter locationGetter = new LocationGetter(mapDimension.tileSize);
		Point neighbourLocation = locationGetter.getNeighbourLocationForDirection(cursor.getX(), cursor.getY(), direction);
		int x = neighbourLocation.getX();
		int y = neighbourLocation.getY();
		return attackableTargetAtLocation(attackingUnit, x, y);
	}
	
	public boolean attackableTargetAtLocation(Unit attackingUnit, int x, int y) {
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