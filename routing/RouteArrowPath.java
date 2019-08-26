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
		int newPosTileX = newPosition.getX() / mapDimension.tileSize;
		int newPosTileY = newPosition.getY() / mapDimension.tileSize;

		if (repeatedPositionIndex > -1) {
			for (int i = arrowPoints.size() - 1 ; i > repeatedPositionIndex ; i--) {
				removeArrowPoint(i);
			}
		} else if (movementMap.isAcceptedMove(newPosTileX, newPosTileY)) {
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
	private int getRepeatedPositionIndex(Point point) {
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
			if (currentMovementValue > maximumMovement) {
				return true;
			}
		}
		return false;
	}
	
	private void removeArrowPoint(int index) {
		arrowPoints.remove(index);
	}
	
	public void clear() {
		arrowPoints.clear();
	}
	
	// TODO: rewrite code as a recursive search
	// @TODO: what happens if a tank want to go round a wood (U-movement) = will give it endless loop
	// @TODO: also, if (+2,0) is wood, (+3,0) is wood, if (+2,+1) is wood, (+3,+1) is wood and the 
	//        rest is road, what happens if you try to move the cursor from (+4,+2)->(+4,+1)->(+3,+1)
	//        result: will get stuck
	private void recountPath(Point newPosition, Unit chosenUnit) {
		MovementType movementType = chosenUnit.getMovementType();
		int tileSize = mapDimension.tileSize;
		int diffTileX = (newPosition.getX() - chosenUnit.getPosition().getX()) / tileSize;
		int diffTileY = (newPosition.getY() - chosenUnit.getPosition().getY()) / tileSize;

		clear();
		addArrowPoint(chosenUnit.getPosition());

		while(Math.abs(diffTileX) > 0 || Math.abs(diffTileY) > 0) {
			int last = getNumberOfArrowPoints() - 1;
			if (arrowPoints.get(last).isSamePosition(newPosition)) {
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

			int prevTileX = arrowPoints.get(last).getX() / tileSize;
			int prevTileY = arrowPoints.get(last).getY() / tileSize;

			if (xAxle == 1) {
				int diff = diffTileX / Math.abs(diffTileX);
				addArrowPoint(new Point((prevTileX + diff) * tileSize, prevTileY * tileSize));
				int movementCost = movementCostCalculator.movementCost(prevTileX + diff, prevTileY, movementType);
				diffTileX -= diff * movementCost;
			} else { // yAxle
				int diff = diffTileY / Math.abs(diffTileY);
				addArrowPoint(new Point(prevTileX * tileSize, (prevTileY + diff) * tileSize));
				int movementCost = movementCostCalculator.movementCost(prevTileX, prevTileY + diff, movementType);
				diffTileY -= diff * movementCost;
			}
		}
	}
	
	public int calculateFuelUsed(MovementType movementType) {
		int fuelUsed = 0;
		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int arrowTileX = arrowPoints.get(i).getX() / mapDimension.tileSize;
			int arrowTileY = arrowPoints.get(i).getY() / mapDimension.tileSize;
			fuelUsed += movementCostCalculator.movementCost(arrowTileX, arrowTileY, movementType);
		}
		return fuelUsed;
	}

	public Point getArrowPoint(int index) {
		return arrowPoints.get(index);
	}
	
	private int getNumberOfArrowPoints() {
		return arrowPoints.size();
	}
	
	public void paintArrow(Graphics g) {
		routeArrowPathPainter.paint(g, arrowPoints);
	}
}