/**
 * Handles the route-calculation
 * 
 * TODO:
 *  - check which of the addNewArrowPoint-methods should stay (or if they do different things)
 */
package routing;

import point.*;
import units.*;

import gameObjects.MapDim;

public class RouteHandler {
	private RouteArrowPath routeArrowPath;
	private MovementMap movementMap;

	public RouteHandler(MapDim mapDimension, MovementMap movementMap, MovementCostCalculator movementCostCalculator) {
		routeArrowPath = new RouteArrowPath(mapDimension, movementCostCalculator);
		this.movementMap = movementMap;
	}

	// TODO: which one should stay - what is the difference?
	public void addNewArrowPoint(Point point) {
		routeArrowPath.addArrowPoint(point);
	}

	// TODO: which one should stay - what is the difference?
	public void updateArrowPath(Point point, Unit chosenUnit) {
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