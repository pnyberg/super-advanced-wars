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
	private boolean[][] movementMap;
	private MapDimension mapDimension;
	private RouteArrowPath routeArrowPath;

	public RouteHandler(MapDimension mapDimension) {
		this.mapDimension = mapDimension;
		clearMovementMap();
		routeArrowPath = new RouteArrowPath(mapDimension);
	}

	// TODO: which one should stay
	public void addNewArrowPoint(Point point) {
		routeArrowPath.addArrowPoint(point);
	}

	// TODO: which one should stay
	public void updateArrowPath(Point point, Unit chosenUnit, MapHandler mapHandler) {
		routeArrowPath.updateArrowPath(point, chosenUnit, mapHandler, movementMap);
	}
	
	public void findPossibleMovementLocations(MapHandler mapHandler, Unit checkedUnit) {
		RouteChecker routeChecker = new RouteChecker(mapHandler, movementMap, checkedUnit);
		routeChecker.findPossibleMovementLocations();
	}

	public void clearMovementMap() {
		movementMap = new boolean[mapDimension.width][mapDimension.height];
	}

	public void clearArrowPoints() {
		routeArrowPath.clear();
	}

	public int getFuelFromArrows(Unit unit, MapHandler mapHandler) {
		return routeArrowPath.calculateFuelUsed(mapHandler, unit.getMovementType());
	}

	public boolean movementMap(int x, int y) {
		return movementMap[x][y];
	}
	
	public RouteArrowPath getRouteArrowPath() {
		return routeArrowPath;
	}
}