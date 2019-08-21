/**
 * TODO:
 *  - enable allowed movement in team-play
 *  - create and return the movementMap (instead of taking it as a parameter)
 *  - check if both allowedMovementPosition-methods are useful
 */
package routing;

import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import hero.Hero;
import main.HeroHandler;
import map.GameMap;
import map.area.AreaChecker;
import map.area.TerrainType;
import unitUtils.MovementType;
import units.Unit;

public class RouteChecker {
	private MapDimension mapDimension;
	private HeroHandler heroHandler;
	private GameMap gameMap;
	private MovementMap movementMap;
	private boolean[][] moveabilityMatrix;
	private AreaChecker areaChecker;
	private MovementCostCalculator movementCostCalculator;
	
	public RouteChecker(GameProperties gameProperties, GameState gameState) {
		this.mapDimension = gameProperties.getMapDimension();
		this.heroHandler = gameState.getHeroHandler();
		this.gameMap = gameProperties.getGameMap();
		this.movementMap = gameState.getMovementMap();
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
		areaChecker = new AreaChecker(gameState.getHeroHandler(), gameProperties.getGameMap());
		this.movementCostCalculator = new MovementCostCalculator(gameProperties.getGameMap());
	}

	public void findPossibleMovementLocations(Unit checkedUnit) {
		int tileX = checkedUnit.getPoint().getX() / mapDimension.tileSize;
		int tileY = checkedUnit.getPoint().getY() / mapDimension.tileSize;
		findPossibleMovementLocations(tileX, tileY, checkedUnit.getMovement(), checkedUnit);
	}

	public void findPossibleMovementLocations(int tileX, int tileY, int movementSteps, Unit checkedUnit) {
		movementMap.setAcceptedMove(tileX, tileY);		
		checkPath(tileX + 1, tileY, movementSteps, checkedUnit);
		checkPath(tileX, tileY + 1, movementSteps, checkedUnit);
		checkPath(tileX - 1, tileY, movementSteps, checkedUnit);
		checkPath(tileX, tileY - 1, movementSteps, checkedUnit);
	}

	private void checkPath(int tileX, int tileY, int movementSteps, Unit checkedUnit) {
		if (tileX < 0 || tileY < 0 || tileX >= mapDimension.getTileWidth() || tileY >= mapDimension.getTileHeight()) {
			return;
		}

		Hero hero = heroHandler.getHeroFromUnit(checkedUnit);
		// TODO: enable allowed movement in team-play
		if (allowedMovementPosition(tileX, tileY, checkedUnit.getMovementType(), hero)) {
			movementSteps -= movementCostCalculator.movementCost(tileX, tileY, checkedUnit.getMovementType());
			if (movementSteps >= 0) {
				findPossibleMovementLocations(tileX, tileY, movementSteps, checkedUnit);
			}
		}
	}

	// TODO: what's the difference? - are both useful?
	public boolean allowedMovementPosition(int tileX, int tileY, MovementType movementType, Hero hero) {
		TerrainType terrainType = gameMap.getMap()[tileX][tileY].getTerrainType();

		if (areaChecker.areaOccupiedByNonFriendly(tileX * mapDimension.tileSize, tileY * mapDimension.tileSize, hero)) {
			return false;
		}

		return moveabilityMatrix[movementType.movementTypeIndex()][terrainType.terrainTypeIndex()];
	}

	// TODO: what's the difference? - are both useful?
	public boolean allowedMovementPosition(int tileX, int tileY, MovementType movementType) {
		return allowedMovementPosition(tileX, tileY, movementType, heroHandler.getCurrentHero());
	}
}