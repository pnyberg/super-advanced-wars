/**
 * Handles the route-calculation
 */
package handlers;

import point.*;
import units.*;
import heroes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class RouteHandler {
	private RouteArrowPath routeArrowPath;
	private MovementMap movementMap;

	public RouteHandler(MapDimension mapDimension, MovementMap movementMap) {
		routeArrowPath = new RouteArrowPath(mapDimension);
		this.movementMap = movementMap;
	}

	// TODO: which one should stay
	public void addNewArrowPoint(Point point) {
		routeArrowPath.addArrowPoint(point);
	}

	// TODO: which one should stay
	public void updateArrowPath(Point point, Unit chosenUnit, MapHandler mapHandler) {
		routeArrowPath.updateArrowPath(point, chosenUnit, mapHandler, movementMap);
	}
	
	public void clearArrowPoints() {
		routeArrowPath.clear();
	}

	public int getFuelFromArrows(Unit unit, MapHandler mapHandler) {
		return routeArrowPath.calculateFuelUsed(mapHandler, unit.getMovementType());
	}

	public MovementMap getMovementMap() {
		return movementMap;
	}
	
	public RouteArrowPath getRouteArrowPath() {
		return routeArrowPath;
	}	
}