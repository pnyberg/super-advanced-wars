/**
 * TODO:
 *  - enable allowed movement in team-play
 *  - create and return the movementMap (instead of taking it as a parameter)
 */
package handlers;

import heroes.Hero;
import units.Unit;

public class RouteChecker {
	private MapHandler mapHandler;
	private boolean[][] movementMap;
	private Unit checkedUnit;
	
	public RouteChecker(MapHandler mapHandler, boolean[][] movementMap, Unit checkedUnit) {
		this.mapHandler = mapHandler;
		this.movementMap = movementMap;
		this.checkedUnit = checkedUnit;
	}

	public void findPossibleMovementLocations() {
		findPossibleMovementLocations(checkedUnit.getX(), checkedUnit.getY(), checkedUnit.getMovement());
	}
	
	private void findPossibleMovementLocations(int x, int y, int movementSteps) {
		movementMap[x][y] = true;
		checkPathAllDirections(x, y, movementSteps);
	} 
	
	private void checkPathAllDirections(int x, int y, int movementSteps) {
		checkPath(x + 1, y, movementSteps);
		checkPath(x, y + 1, movementSteps);
		checkPath(x - 1, y, movementSteps);
		checkPath(x, y - 1, movementSteps);
	}

	private void checkPath(int x, int y, int movementSteps) {
		MapDimension mapDimension = mapHandler.getMapDimension();
		if (x < 0 || y < 0 || x >= mapDimension.width || y >= mapDimension.height) {
			return;
		}

		Hero hero = mapHandler.getHeroPortrait().getHeroFromUnit(checkedUnit);
		// TODO: enable allowed movement in team-play
		if (mapHandler.allowedMovementPosition(x, y, checkedUnit.getMovementType(), hero)) {
			movementSteps -= mapHandler.movementCost(x, y, checkedUnit.getMovementType());
			if (movementSteps >= 0) {
				findPossibleMovementLocations(x, y, movementSteps);
			}
		}
	}
}