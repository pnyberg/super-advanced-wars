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

import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import graphics.RouteArrowPathPainter;
import point.Point;
import unitUtils.MovementType;
import units.Unit;

public class RouteArrowPath {
	private MapDimension mapDimension;
	private ArrayList<Point> arrowPoints;
	private RouteArrowPathPainter routeArrowPathPainter;
	private MovementCostCalculator movementCostCalculator;

	public RouteArrowPath(GameProperties gameProperties, GameState gameState) {
		this.mapDimension = gameProperties.getMapDimension();
		arrowPoints = gameState.getArrowPoints();
		routeArrowPathPainter = new RouteArrowPathPainter(mapDimension);
		this.movementCostCalculator = new MovementCostCalculator(gameProperties.getGameMap());
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
		} else if (movementMap.isAcceptedMove(newPosition.getX() / mapDimension.tileSize, newPosition.getY() / mapDimension.tileSize)) {
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
	 * @param point the new position that might already be present
	 * @return index for point with that position (-1 if not present)
	 */
	public int getRepeatedPositionIndex(Point point) {
		for (int i = 0 ; i < arrowPoints.size() ; i++) {
			if (arrowPoints.get(i).isSamePosition(point)) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean newPointNotConnectedToPreviousPoint() {
		int size = arrowPoints.size();
		int tilex1 = arrowPoints.get(size - 2).getX() / mapDimension.tileSize;
		int tiley1 = arrowPoints.get(size - 2).getY() / mapDimension.tileSize;
		int tilex2 = arrowPoints.get(size - 1).getX() / mapDimension.tileSize;
		int tiley2 = arrowPoints.get(size - 1).getY() / mapDimension.tileSize;
		return Math.abs(tilex1 - tilex2) + Math.abs(tiley1 - tiley2) > 1;
	}

	private boolean invalidCurrentPath(Unit chosenUnit) {
		int maximumMovement = chosenUnit.getMovement();
		int currentMovementValue = 0;

		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int tileX = arrowPoints.get(i).getX() / mapDimension.tileSize;
			int tileY = arrowPoints.get(i).getY() / mapDimension.tileSize;
			currentMovementValue += movementCostCalculator.movementCost(tileX, tileY, chosenUnit.getMovementType());
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
		int diffTileX = (newPosition.getX() - chosenUnit.getPosition().getX()) / mapDimension.tileSize;
		int diffTileY = (newPosition.getY() - chosenUnit.getPosition().getY()) / mapDimension.tileSize;

		clear();
		addArrowPoint(chosenUnit.getPosition());

		while(Math.abs(diffTileX) > 0 || Math.abs(diffTileY) > 0) {
			int last = getNumberOfArrowPoints() - 1;
			int prevX = arrowPoints.get(last).getX() / mapDimension.tileSize;
			int prevY = arrowPoints.get(last).getY() / mapDimension.tileSize;

			if (prevX / mapDimension.tileSize == newPosition.getX() / mapDimension.tileSize && prevY / mapDimension.tileSize == newPosition.getY() / mapDimension.tileSize) {
				break;
			}

			int xAxle;
			if (Math.abs(diffTileX) > 0 && Math.abs(diffTileY) > 0) {
				xAxle = (int)(Math.random() * 10) % 2;
			} else if (Math.abs(diffTileX) > 0) {
				xAxle = 1;
			} else {
				xAxle = 0;
			}

			if (xAxle == 1) {
				int diff = diffTileX / Math.abs(diffTileX);
				addArrowPoint(new Point((prevX + diff) * mapDimension.tileSize, prevY * mapDimension.tileSize));
				int movementCost = movementCostCalculator.movementCost(prevX + diff, prevY, movementType);
				diffTileX -= diff * movementCost;
			} else { // yAxle
				int diff = diffTileY / Math.abs(diffTileY);
				addArrowPoint(new Point(prevX * mapDimension.tileSize, (prevY + diff) * mapDimension.tileSize));
				int movementCost = movementCostCalculator.movementCost(prevX, prevY + diff, movementType);
				diffTileY -= diff * movementCost;
			}
		}
	}
	
	public int calculateFuelUsed(MovementType movementType) {
		int fuelUsed = 0;
		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			Point arrowPoint = arrowPoints.get(i);
			fuelUsed += movementCostCalculator.movementCost(arrowPoint.getX()/mapDimension.tileSize, arrowPoint.getY()/mapDimension.tileSize, movementType);
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