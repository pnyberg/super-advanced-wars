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
	private ArrayList<Point> arrowPoints;

	public RouteHandler(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		movementMap = new boolean[mapWidth][mapHeight];
		arrowPoints = new ArrayList<Point>();
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

	public void initArrowPoint(int newX, int newY) {
		arrowPoints.add(new Point(newX, newY));
	}

	public void addArrowPoint(int newX, int newY, Unit chosenUnit, MapHandler mapHandler) {
		int newLast = -1;

		for (int i = 0 ; i < arrowPoints.size() ; i++) {
			int arrowX = arrowPoints.get(i).getX();
			int arrowY = arrowPoints.get(i).getY();

			if (arrowX == newX && arrowY == newY) {
				newLast = i;
				break;
			}
		}

		if (newLast > -1) {
			for (int i = arrowPoints.size() - 1 ; i > newLast ; i--) {
				arrowPoints.remove(i);
			}
		} else if (movementMap(newX, newY)) {
			arrowPoints.add(new Point(newX, newY));

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
		arrowPoints.clear();
	}

	private boolean invalidCurrentPath(Unit chosenUnit, MapHandler mapHandler) {
		int maximumMovement = chosenUnit.getMovement();
		int movementType = chosenUnit.getMovementType();

		int currentMovementValue = 0;

		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int x = arrowPoints.get(i).getX();
			int y = arrowPoints.get(i).getY();
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

		arrowPoints.clear();
		arrowPoints.add(new Point(mainX, mainY));


		while(Math.abs(diffX) > 0 || Math.abs(diffY) > 0) {
			int last = arrowPoints.size() - 1;
			int prevX = arrowPoints.get(last).getX();
			int prevY = arrowPoints.get(last).getY();

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
				arrowPoints.add(new Point(prevX + diff, prevY));
				int movementCost = mapHandler.movementCost(prevX + diff, prevY, movementType);
				diffX -= diff * movementCost;
			} else { // yAxel
				int diff = diffY / Math.abs(diffY);
				arrowPoints.add(new Point(prevX, prevY + diff));
				int movementCost = mapHandler.movementCost(prevX, prevY + diff, movementType);
				diffY -= diff * movementCost;
			}
		}
	}

	private boolean newPointNotConnectedToPreviousPoint() {
		int size = arrowPoints.size();

		int x1 = arrowPoints.get(size - 2).getX();
		int y1 = arrowPoints.get(size - 2).getY();
		int x2 = arrowPoints.get(size - 1).getX();
		int y2 = arrowPoints.get(size - 1).getY();

		return Math.abs(x1 - x2) + Math.abs(y1 - y2) > 1;
	}

	public int getFuelFromArrows(Unit unit, MapHandler mapHandler) {
		int fuelUsed = 0;

		int movementType = unit.getMovementType();

		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int x = arrowPoints.get(i).getX();
			int y = arrowPoints.get(i).getY();
			fuelUsed += mapHandler.movementCost(x, y, movementType);
		}

		return fuelUsed;
	}

	public boolean movementMap(int x, int y) {
		return movementMap[x][y];
	}

	public Point getArrowPoint(int index) {
		return arrowPoints.get(index);
	}

	public void paintArrow(Graphics g) {
		int tileSize = MapHandler.tileSize;

		if (arrowPoints.size() < 2) {
			return;
		}

		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int x1 = arrowPoints.get(i - 1).getX() * tileSize + tileSize / 2;
			int y1 = arrowPoints.get(i - 1).getY() * tileSize + tileSize / 2;
			int x2 = arrowPoints.get(i).getX() * tileSize + tileSize / 2;
			int y2 = arrowPoints.get(i).getY() * tileSize + tileSize / 2;

			g.setColor(Color.red);
			g.drawLine(x1, y1, x2, y2);
		}

		int size = arrowPoints.size();

		int xNext = arrowPoints.get(size - 2).getX() * tileSize;
		int yNext = arrowPoints.get(size - 2).getY() * tileSize;
		int xLast = arrowPoints.get(size - 1).getX() * tileSize;
		int yLast = arrowPoints.get(size - 1).getY() * tileSize;

		if (xNext == xLast) {
			if (yNext < yLast) {
				g.drawLine(xLast - 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			} else {
				g.drawLine(xLast - 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			}
		} else {
			if (xNext < xLast) {
				g.drawLine(xLast - 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast - 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			} else {
				g.drawLine(xLast + 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			}
		}
	}
}