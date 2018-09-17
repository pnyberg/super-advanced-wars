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
	private int mapWidth, mapHeight;
	private RouteArrowPath routeArrowPath;

	public RouteHandler(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		clearMovementMap();
		routeArrowPath = new RouteArrowPath();
	}

	// TODO: which one should stay
	public void addNewArrowPoint(int newX, int newY) {
		routeArrowPath.addArrowPoint(new Point(newX, newY));
	}

	// TODO: which one should stay
	public void addArrowPoint(int newX, int newY, Unit chosenUnit, MapHandler mapHandler) {
		routeArrowPath.addArrowPoint(newX, newY, chosenUnit, mapHandler, movementMap);
	}
	
	public void findPossibleMovementLocations(MapHandler mapHandler, Unit checkedUnit) {
		RouteChecker routeChecker = new RouteChecker(mapHandler, movementMap, checkedUnit);
		routeChecker.findPossibleMovementLocations();
	}

	public void clearMovementMap() {
		movementMap = new boolean[mapWidth][mapHeight];
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