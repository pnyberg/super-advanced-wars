/**
 * TODO:
 *  - enable allowed movement in team-play
 *  - create and return the movementMap (instead of taking it as a parameter)
 */
package routing;

import gameObjects.MapDim;
import hero.Hero;
import main.HeroHandler;
import map.area.Area;
import map.area.AreaChecker;
import map.area.TerrainType;
import units.MovementType;
import units.Unit;

public class RouteChecker {
	private MapDim mapDim;
	private HeroHandler heroHandler;
	private Area[][] map;
	private MovementMap movementMap;
	private boolean[][] moveabilityMatrix;
	private AreaChecker areaChecker;
	private MovementCostCalculator movementCostCalculator;
	
	public RouteChecker(MapDim mapDimension, HeroHandler heroHandler, Area[][] map, MovementMap movementMap, boolean[][] moveabilityMatrix, AreaChecker areaChecker, MovementCostCalculator movementCostCalculator) {
		this.mapDim = mapDimension;
		this.heroHandler = heroHandler;
		this.map = map;
		this.movementMap = movementMap;
		this.moveabilityMatrix = moveabilityMatrix;
		this.areaChecker = areaChecker;
		this.movementCostCalculator = movementCostCalculator;
	}

	public void findPossibleMovementLocations(Unit checkedUnit) {
		int x = checkedUnit.getPoint().getX() / mapDim.tileSize;
		int y = checkedUnit.getPoint().getY() / mapDim.tileSize;
		findPossibleMovementLocations(x, y, checkedUnit.getMovement(), checkedUnit);
	}

	public void findPossibleMovementLocations(int x, int y, int movementSteps, Unit checkedUnit) {
		movementMap.setAcceptedMove(x, y);		
		checkPath(x + 1, y, movementSteps, checkedUnit);
		checkPath(x, y + 1, movementSteps, checkedUnit);
		checkPath(x - 1, y, movementSteps, checkedUnit);
		checkPath(x, y - 1, movementSteps, checkedUnit);
	}

	private void checkPath(int x, int y, int movementSteps, Unit checkedUnit) {
		if (x < 0 || y < 0 || x >= mapDim.width || y >= mapDim.height) {
			return;
		}

		Hero hero = heroHandler.getHeroFromUnit(checkedUnit);
		// TODO: enable allowed movement in team-play
		if (allowedMovementPosition(x, y, checkedUnit.getMovementType(), hero)) {
			movementSteps -= movementCostCalculator.movementCost(x, y, checkedUnit.getMovementType());
			if (movementSteps >= 0) {
				findPossibleMovementLocations(x, y, movementSteps, checkedUnit);
			}
		}
	}

	public boolean allowedMovementPosition(int x, int y, MovementType movementType, Hero hero) {
		TerrainType terrainType = map[x][y].getTerrainType();

		if (areaChecker.areaOccupiedByNonFriendly(x, y, hero)) {
			return false;
		}

		return moveabilityMatrix[movementType.movementTypeIndex()][terrainType.terrainTypeIndex()];
	}

	public boolean allowedMovementPosition(int x, int y, MovementType movementType) {
		return allowedMovementPosition(x, y, movementType, heroHandler.getCurrentHero());
	}
}