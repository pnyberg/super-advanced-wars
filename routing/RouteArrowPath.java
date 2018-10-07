/**
 * @TODO:
 *  - what happens if a tank want to go round a wood (U-movement) = will give it endless loop
 *  - also, if (+2,0) is wood, (+3,0) is wood, if (+2,+1) is wood, (+3,+1) is wood and the 
 *    rest is road, what happens if you try to move the cursor from (+4,+2)->(+4,+1)->(+3,+1)
 * 	  result: will get stuck
 *  - fix it so that recountPath() doesn't need the invalidCurrentPath()-while-loop (in method updateArrowPath())
 *     that is, rewrite recountPath()
 */
package routing;

import java.awt.Graphics;
import java.util.ArrayList;

import gameObjects.MapDim;
import graphics.RouteArrowPathPainter;
import point.Point;
import units.MovementType;
import units.Unit;

public class RouteArrowPath {
	private ArrayList<Point> arrowPoints;
	private RouteArrowPathPainter routeArrowPathPainter;
	private MovementCostCalculator movementCostCalculator;

	public RouteArrowPath(MapDim mapDimension, MovementCostCalculator movementCostCalculator) {
		arrowPoints = new ArrayList<Point>();
		routeArrowPathPainter = new RouteArrowPathPainter(mapDimension);
		this.movementCostCalculator = movementCostCalculator;
	}
	
	public void addArrowPoint(Point point) {
		arrowPoints.add(point);
	}
	
	public void updateArrowPath(Point newPosition, Unit chosenUnit, MovementMap movementMap) {
		int repeatedPositionIndex = getRepeatedPositionIndex(newPosition);

		if (repeatedPositionIndex > -1) {
			for (int i = arrowPoints.size() - 1 ; i > repeatedPositionIndex ; i--) {
				removeArrowPoint(i);
			}
		} else if (movementMap.isAcceptedMove(newPosition.getX(), newPosition.getY())) {
			addArrowPoint(newPosition);

			if (newPointNotConnectedToPreviousPoint()) {
				recountPath(newPosition, chosenUnit);
				// @TODO: add what happens when you make a "jump" between accepted locations
			}

			// @TODO: if movement is changed due to for example mountains, what happens?
			while (invalidCurrentPath(chosenUnit)) {
				recountPath(newPosition, chosenUnit);
			}
		}
	}
	
	/**
	 * Check if the new position is already in the arrow points-list
	 * @param p the new position that might already be present
	 * @return index for point with that position (-1 if not present)
	 */
	public int getRepeatedPositionIndex(Point p) {
		for (int i = 0 ; i < arrowPoints.size() ; i++) {
			if (arrowPoints.get(i).getX() == p.getX() && arrowPoints.get(i).getY() == p.getY()) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean newPointNotConnectedToPreviousPoint() {
		int size = arrowPoints.size();
		int x1 = arrowPoints.get(size - 2).getX();
		int y1 = arrowPoints.get(size - 2).getY();
		int x2 = arrowPoints.get(size - 1).getX();
		int y2 = arrowPoints.get(size - 1).getY();

		return Math.abs(x1 - x2) + Math.abs(y1 - y2) > 1;
	}

	private boolean invalidCurrentPath(Unit chosenUnit) {
		int maximumMovement = chosenUnit.getMovement();
		int currentMovementValue = 0;

		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int x = arrowPoints.get(i).getX();
			int y = arrowPoints.get(i).getY();
			currentMovementValue += movementCostCalculator.movementCost(x, y, chosenUnit.getMovementType());
		}

		return currentMovementValue > maximumMovement;
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
	public void recountPath(Point newPosition, Unit chosenUnit) {
		MovementType movementType = chosenUnit.getMovementType();
		int diffX = newPosition.getX() - chosenUnit.getPoint().getX();
		int diffY = newPosition.getY() - chosenUnit.getPoint().getY();

		clear();
		addArrowPoint(chosenUnit.getPoint());

		while(Math.abs(diffX) > 0 || Math.abs(diffY) > 0) {
			int last = getNumberOfArrowPoints() - 1;
			int prevX = arrowPoints.get(last).getX();
			int prevY = arrowPoints.get(last).getY();

			if (prevX == newPosition.getX() && prevY == newPosition.getY()) {
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
				int movementCost = movementCostCalculator.movementCost(prevX + diff, prevY, movementType);
				diffX -= diff * movementCost;
			} else { // yAxel
				int diff = diffY / Math.abs(diffY);
				addArrowPoint(new Point(prevX, prevY + diff));
				int movementCost = movementCostCalculator.movementCost(prevX, prevY + diff, movementType);
				diffY -= diff * movementCost;
			}
		}
	}
	
	public int calculateFuelUsed(MovementType movementType) {
		int fuelUsed = 0;
		for (Point arrowPoint : arrowPoints) {
			fuelUsed += movementCostCalculator.movementCost(arrowPoint.getX(), arrowPoint.getY(), movementType);
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