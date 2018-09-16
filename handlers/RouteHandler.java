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
		movementMap = new boolean[mapWidth][mapHeight];
		routeArrowPath = new RouteArrowPath();
	}

	public void clearMovementMap() {
		movementMap = new boolean[mapWidth][mapHeight];
	}

	public void findPossibleMovementLocations(Unit chosenUnit, MapHandler mapHandler) {
		int x = chosenUnit.getX();
		int y = chosenUnit.getY();
		int movementSteps = chosenUnit.getMovement();
		int movementType = chosenUnit.getMovementType();

		Hero hero = mapHandler.getHeroPortrait().getHeroFromUnit(chosenUnit);

		movementMap[x][y] = true;

		checkPath(x + 1, y, movementSteps, movementType, hero, mapHandler);
		checkPath(x, y + 1, movementSteps, movementType, hero, mapHandler);
		checkPath(x - 1, y, movementSteps, movementType, hero, mapHandler);
		checkPath(x, y - 1, movementSteps, movementType, hero, mapHandler);
	}

	private void checkPath(int x, int y, int movementSteps, int movementType, Hero hero, MapHandler mapHandler) {
		if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
			return;
		}

		// @TODO: enable allowed movement in team-play
		if (!mapHandler.allowedMovementPosition(x, y, movementType, hero)) {
			return;
		}

		movementSteps -= mapHandler.movementCost(x, y, movementType);

		if (movementSteps < 0) {
			return;
		}

		movementMap[x][y] = true;

		checkPath(x + 1, y, movementSteps, movementType, hero, mapHandler);
		checkPath(x, y + 1, movementSteps, movementType, hero, mapHandler);
		checkPath(x - 1, y, movementSteps, movementType, hero, mapHandler);
		checkPath(x, y - 1, movementSteps, movementType, hero, mapHandler);
	}

	public void addNewArrowPoint(int newX, int newY) {
		routeArrowPath.addArrowPoint(new Point(newX, newY));
	}

	public void addArrowPoint(int newX, int newY, Unit chosenUnit, MapHandler mapHandler) {
		int newLast = -1;

		for (int i = 0 ; i < routeArrowPath.getNumberOfArrowPoints() ; i++) {
			int arrowX = routeArrowPath.getArrowPoint(i).getX();
			int arrowY = routeArrowPath.getArrowPoint(i).getY();

			if (arrowX == newX && arrowY == newY) {
				newLast = i;
				break;
			}
		}

		if (newLast > -1) {
			for (int i = routeArrowPath.getNumberOfArrowPoints() - 1 ; i > newLast ; i--) {
				routeArrowPath.removeArrowPoint(i);
			}
		} else if (movementMap(newX, newY)) {
			routeArrowPath.addArrowPoint(new Point(newX, newY));

			if (newPointNotConnectedToPreviousPoint()) {
				recountPath(newX, newY, chosenUnit, mapHandler);
				// @TODO: add what happens when you make a "jump" between accepted locations
			}

			// @TODO: if movement is changed due to for example mountains, what happens?
			while (invalidCurrentPath(chosenUnit, mapHandler)) {
				recountPath(newX, newY, chosenUnit, mapHandler);
			}
		}
	}

	public void clearArrowPoints() {
		routeArrowPath.clear();
	}

	private boolean invalidCurrentPath(Unit chosenUnit, MapHandler mapHandler) {
		int maximumMovement = chosenUnit.getMovement();
		int movementType = chosenUnit.getMovementType();

		int currentMovementValue = 0;

		for (int i = 1 ; i < routeArrowPath.getNumberOfArrowPoints() ; i++) {
			int x = routeArrowPath.getArrowPoint(i).getX();
			int y = routeArrowPath.getArrowPoint(i).getY();
			currentMovementValue += mapHandler.movementCost(x, y, movementType);
		}

		return currentMovementValue > maximumMovement;
	}

	// @TODO: what happens if a tank want to go round a wood (U-movement) = will givet endless loop
	// @TODO: also, if (+2,0) is wood, (+3,0) is wood, if (+2,+1) is wood, (+3,+1) is wood and the 
	//        rest is road, what happens if you try to move the cursor from (+4,+2)->(+4,+1)->(+3,+1)
	//        result: will get stuck
	private void recountPath(int newX, int newY, Unit chosenUnit, MapHandler mapHandler) {
		int mainX = chosenUnit.getX();
		int mainY = chosenUnit.getY();
		int movementType = chosenUnit.getMovementType();

		int diffX = newX - mainX;
		int diffY = newY - mainY;

		routeArrowPath.clear();
		routeArrowPath.addArrowPoint(new Point(mainX, mainY));


		while(Math.abs(diffX) > 0 || Math.abs(diffY) > 0) {
			int last = routeArrowPath.getNumberOfArrowPoints() - 1;
			int prevX = routeArrowPath.getArrowPoint(last).getX();
			int prevY = routeArrowPath.getArrowPoint(last).getY();

			if (prevX == newX && prevY == newY) {
				break;
			}

			int xAxle;
			if (Math.abs(diffX) > 0 && Math.abs(diffY) > 0) {
				xAxle = (int)(Math.random() * 10) % 2;
			} else if (Math.abs(diffX) > 0) {
				xAxle = 1;
			} else {
				xAxle = 0;
			}

			if (xAxle == 1) {
				int diff = diffX / Math.abs(diffX);
				routeArrowPath.addArrowPoint(new Point(prevX + diff, prevY));
				int movementCost = mapHandler.movementCost(prevX + diff, prevY, movementType);
				diffX -= diff * movementCost;
			} else { // yAxel
				int diff = diffY / Math.abs(diffY);
				routeArrowPath.addArrowPoint(new Point(prevX, prevY + diff));
				int movementCost = mapHandler.movementCost(prevX, prevY + diff, movementType);
				diffY -= diff * movementCost;
			}
		}
	}

	private boolean newPointNotConnectedToPreviousPoint() {
		int size = routeArrowPath.getNumberOfArrowPoints();

		int x1 = routeArrowPath.getArrowPoint(size - 2).getX();
		int y1 = routeArrowPath.getArrowPoint(size - 2).getY();
		int x2 = routeArrowPath.getArrowPoint(size - 1).getX();
		int y2 = routeArrowPath.getArrowPoint(size - 1).getY();

		return Math.abs(x1 - x2) + Math.abs(y1 - y2) > 1;
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