/**
 * @TODO:
 *  - what happens if a tank want to go round a wood (U-movement) = will give it endless loop
 *  - also, if (+2,0) is wood, (+3,0) is wood, if (+2,+1) is wood, (+3,+1) is wood and the 
 *    rest is road, what happens if you try to move the cursor from (+4,+2)->(+4,+1)->(+3,+1)
 * 	  result: will get stuck
 */
package handlers;

import java.awt.Graphics;
import java.util.ArrayList;

import graphics.RouteArrowPathPainter;
import point.Point;
import units.Unit;

public class RouteArrowPath {
	private ArrayList<Point> arrowPoints;
	private RouteArrowPathPainter routeArrowPathPainter;

	public RouteArrowPath() {
		arrowPoints = new ArrayList<Point>();
		routeArrowPathPainter = new RouteArrowPathPainter();
	}
	
	public void addArrowPoint(Point point) {
		arrowPoints.add(point);
	}
	
	public void addArrowPoint(int newX, int newY, Unit chosenUnit, MapHandler mapHandler, boolean[][] movementMap) {
		int newLast = -1;

		for (int i = 0 ; i < getNumberOfArrowPoints() ; i++) {
			int arrowX = getArrowPoint(i).getX();
			int arrowY = getArrowPoint(i).getY();

			if (arrowX == newX && arrowY == newY) {
				newLast = i;
				break;
			}
		}

		if (newLast > -1) {
			for (int i = getNumberOfArrowPoints() - 1 ; i > newLast ; i--) {
				removeArrowPoint(i);
			}
		} else if (movementMap[newX][newY]) {
			addArrowPoint(new Point(newX, newY));

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
	
	private boolean invalidCurrentPath(Unit chosenUnit, MapHandler mapHandler) {
		int maximumMovement = chosenUnit.getMovement();
		int movementType = chosenUnit.getMovementType();

		int currentMovementValue = 0;

		for (int i = 1 ; i < getNumberOfArrowPoints() ; i++) {
			int x = getArrowPoint(i).getX();
			int y = getArrowPoint(i).getY();
			currentMovementValue += mapHandler.movementCost(x, y, movementType);
		}

		return currentMovementValue > maximumMovement;
	}
	
	private boolean newPointNotConnectedToPreviousPoint() {
		int size = getNumberOfArrowPoints();
		int x1 = getArrowPoint(size - 2).getX();
		int y1 = getArrowPoint(size - 2).getY();
		int x2 = getArrowPoint(size - 1).getX();
		int y2 = getArrowPoint(size - 1).getY();

		return Math.abs(x1 - x2) + Math.abs(y1 - y2) > 1;
	}

	public void removeArrowPoint(int index) {
		arrowPoints.remove(index);
	}
	
	public void clear() {
		arrowPoints.clear();
	}
	
	// @TODO: what happens if a tank want to go round a wood (U-movement) = will give it endless loop
	// @TODO: also, if (+2,0) is wood, (+3,0) is wood, if (+2,+1) is wood, (+3,+1) is wood and the 
	//        rest is road, what happens if you try to move the cursor from (+4,+2)->(+4,+1)->(+3,+1)
	//        result: will get stuck
	public void recountPath(int newX, int newY, Unit chosenUnit, MapHandler mapHandler) {
		int mainX = chosenUnit.getX();
		int mainY = chosenUnit.getY();
		int movementType = chosenUnit.getMovementType();

		int diffX = newX - mainX;
		int diffY = newY - mainY;

		clear();
		addArrowPoint(new Point(mainX, mainY));


		while(Math.abs(diffX) > 0 || Math.abs(diffY) > 0) {
			int last = getNumberOfArrowPoints() - 1;
			int prevX = getArrowPoint(last).getX();
			int prevY = getArrowPoint(last).getY();

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
				addArrowPoint(new Point(prevX + diff, prevY));
				int movementCost = mapHandler.movementCost(prevX + diff, prevY, movementType);
				diffX -= diff * movementCost;
			} else { // yAxel
				int diff = diffY / Math.abs(diffY);
				addArrowPoint(new Point(prevX, prevY + diff));
				int movementCost = mapHandler.movementCost(prevX, prevY + diff, movementType);
				diffY -= diff * movementCost;
			}
		}
	}
	
	public int calculateFuelUsed(MapHandler mapHandler, int movementType) {
		int fuelUsed = 0;
		for (Point arrowPoint : arrowPoints) {
			fuelUsed += mapHandler.movementCost(arrowPoint.getX(), arrowPoint.getY(), movementType);
		}
		return fuelUsed;
	}

	public Point getArrowPoint(int index) {
		return arrowPoints.get(index);
	}
	
	public int getNumberOfArrowPoints() {
		return arrowPoints.size();
	}
	
	public void paintArrow(Graphics g) {
		routeArrowPathPainter.paint(g, arrowPoints);
	}
}