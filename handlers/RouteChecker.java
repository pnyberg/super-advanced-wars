/**
 * TODO:
 *  - enable allowed movement in team-play
 *  - create and return the movementMap (instead of taking it as a parameter)
 */
package handlers;

import area.Area;
import area.TerrainType;
import heroes.Hero;
import units.MovementType;
import units.Unit;

public class RouteChecker {
	private MapHandler mapHandler;
	private MovementMap movementMap;
	
	public RouteChecker(MapHandler mapHandler, MovementMap movementMap) {
		this.mapHandler = mapHandler;
		this.movementMap = movementMap;
	}

	public void findPossibleMovementLocations(Unit checkedUnit) {
		findPossibleMovementLocations(checkedUnit.getX(), checkedUnit.getY(), checkedUnit.getMovement(), checkedUnit);
	}

	public void findPossibleMovementLocations(int x, int y, int movementSteps, Unit checkedUnit) {
		movementMap.setAcceptedMove(x, y);		
		checkPath(x + 1, y, movementSteps, checkedUnit);
		checkPath(x, y + 1, movementSteps, checkedUnit);
		checkPath(x - 1, y, movementSteps, checkedUnit);
		checkPath(x, y - 1, movementSteps, checkedUnit);
	}

	private void checkPath(int x, int y, int movementSteps, Unit checkedUnit) {
		MapDimension mapDimension = mapHandler.getMapDimension();
		if (x < 0 || y < 0 || x >= mapDimension.width || y >= mapDimension.height) {
			return;
		}

		Hero hero = mapHandler.getHeroPortrait().getHeroHandler().getHeroFromUnit(checkedUnit);
		// TODO: enable allowed movement in team-play
		if (allowedMovementPosition(x, y, checkedUnit.getMovementType(), hero)) {
			movementSteps -= mapHandler.movementCost(x, y, checkedUnit.getMovementType());
			if (movementSteps >= 0) {
				findPossibleMovementLocations(x, y, movementSteps, checkedUnit);
			}
		}
	}

	public boolean allowedMovementPosition(int x, int y, MovementType movementType, Hero hero) {
		Area[][] map = mapHandler.getMap();
		TerrainType terrainType = map[x][y].getTerrainType();

		if (mapHandler.getAreaChecker().areaOccupiedByNonFriendly(x, y, hero)) {
			return false;
		}

		boolean[][] moveabilityMatrix = mapHandler.getMoveabilityMatrix();
		return moveabilityMatrix[movementType.movementTypeIndex()][terrainType.terrainTypeIndex()];
	}

	public boolean allowedMovementPosition(int x, int y, MovementType movementType) {
		return allowedMovementPosition(x, y, movementType, mapHandler.getHeroPortrait().getHeroHandler().getCurrentHero());
	}
}