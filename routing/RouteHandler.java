/**
 * Handles the route-calculation
 */
package routing;

import point.*;
import units.*;
import gameObjects.GameProperties;
import gameObjects.GameState;

public class RouteHandler {
	private RouteArrowPath routeArrowPath;
	private MovementMap movementMap;

	public RouteHandler(GameProperties gameProperties, GameState gameState) {
		routeArrowPath = new RouteArrowPath(gameProperties, gameState);
		this.movementMap = gameState.getMovementMap();
	}

	public void addNewArrowPoint(Point point) {
		routeArrowPath.addArrowPoint(point);
	}

	public void updateCurrentArrowPath(Point point, Unit chosenUnit) {
		routeArrowPath.updateArrowPath(point, chosenUnit, movementMap);
	}
	
	public void clearArrowPoints() {
		routeArrowPath.clear();
	}

	public int getFuelFromArrows(Unit unit) {
		return routeArrowPath.calculateFuelUsed(unit.getMovementType());
	}

	public MovementMap getMovementMap() {
		return movementMap;
	}
	
	public RouteArrowPath getRouteArrowPath() {
		return routeArrowPath;
	}	
}