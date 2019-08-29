/**
 * TODO:
 *  - enable allowed movement in team-play
 */
package routing;

import gameObjects.Direction;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import hero.Hero;
import hero.HeroHandler;
import map.BoundsChecker;
import map.GameMap;
import map.LocationGetter;
import map.area.AreaChecker;
import map.area.TerrainType;
import point.Point;
import unitUtils.MovementType;
import units.Unit;

public class RouteChecker {
	private MapDimension mapDimension;
	private HeroHandler heroHandler;
	private GameMap gameMap;
	private MovementMap movementMap;
	private boolean[][] moveabilityMatrix;
	private int[][] maxMovementSteps;
	
	public RouteChecker(GameProperties gameProperties, GameState gameState) {
		this.mapDimension = gameProperties.getMapDimension();
		this.heroHandler = gameState.getHeroHandler();
		this.gameMap = gameProperties.getGameMap();
		this.movementMap = gameState.getMovementMap();
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
	}

	public MovementMap retrievePossibleMovementLocations(Unit checkedUnit) {
		maxMovementSteps = new int[mapDimension.getTileWidth()][mapDimension.getTileHeight()];
		int tileX = checkedUnit.getPosition().getX() / mapDimension.tileSize;
		int tileY = checkedUnit.getPosition().getY() / mapDimension.tileSize;
		findPossibleMovementLocations(tileX, tileY, checkedUnit.getMovementSteps(), checkedUnit);
		return movementMap;
	}

	private void findPossibleMovementLocations(int tileX, int tileY, int movementSteps, Unit checkedUnit) {
		LocationGetter locationGetter = new LocationGetter(mapDimension.tileSize);
		maxMovementSteps[tileX][tileY] = movementSteps;
		movementMap.setAcceptedMove(tileX, tileY);		

		Direction[] allDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
		for (Direction direction : allDirections) {
			Point neighbourTileLocation = locationGetter.getNeighbourTileLocationForDirection(tileX, tileY, direction);
			int neighbourTileX = neighbourTileLocation.getX();
			int neighbourTileY = neighbourTileLocation.getY();
			if (shouldCheckThisTileForPath(neighbourTileX, neighbourTileY, movementSteps)) {
				checkPath(neighbourTileX, neighbourTileY, movementSteps, checkedUnit);
			}
		}
	}
	
	private boolean shouldCheckThisTileForPath(int tileX, int tileY, int movementSteps) {
		BoundsChecker boundsChecker = new BoundsChecker(mapDimension);
		if (boundsChecker.tilePointOutOfBounds(tileX, tileY)) {
			return false;
		}
		if (movementSteps <= maxMovementSteps[tileX][tileY]) {
			return false;
		}
		return true;
	}

	private void checkPath(int tileX, int tileY, int movementSteps, Unit checkedUnit) {
		Hero hero = heroHandler.getHeroFromUnit(checkedUnit);
		// TODO: enable allowed movement in team-play
		if (allowedMovementPosition(tileX, tileY, checkedUnit.getMovementType(), hero)) {
			MovementCostCalculator movementCostCalculator = new MovementCostCalculator(gameMap);
			movementSteps -= movementCostCalculator.movementCost(tileX, tileY, checkedUnit.getMovementType());
			if (movementSteps >= 0) {
				findPossibleMovementLocations(tileX, tileY, movementSteps, checkedUnit);
			}
		}
	}

	public boolean allowedMovementPosition(int tileX, int tileY, MovementType movementType, Hero hero) {
		TerrainType terrainType = gameMap.getTerrainType(tileX, tileY);
		AreaChecker areaChecker = new AreaChecker(heroHandler, gameMap);
		int tileSize = mapDimension.tileSize;

		if (areaChecker.areaOccupiedByNonFriendly(tileX * tileSize, tileY * tileSize, hero)) {
			return false;
		}

		return moveabilityMatrix[movementType.movementTypeIndex()][terrainType.terrainTypeIndex()];
	}
}