package routing;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import graphics.RouteArrowPathPainter;
import hero.HeroHandler;
import map.area.AreaChecker;
import point.Point;
import unitUtils.MovementType;
import units.Unit;

public class RouteArrowPath {
	private MapDimension mapDimension;
	private ArrayList<Point> arrowPoints;
	private RouteArrowPathPainter routeArrowPathPainter;
	private MovementCostCalculator movementCostCalculator;
	private AreaChecker areaChecker;
	private HeroHandler heroHandler;

	public RouteArrowPath(GameProperties gameProperties, GameState gameState) {
		this.mapDimension = gameProperties.getMapDimension();
		arrowPoints = gameState.getArrowPoints();
		routeArrowPathPainter = new RouteArrowPathPainter(mapDimension);
		movementCostCalculator = new MovementCostCalculator(gameProperties.getGameMap());
		areaChecker = new AreaChecker(gameState.getHeroHandler(), gameProperties.getGameMap());
		heroHandler = gameState.getHeroHandler();
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

			if (newPointNotConnectedToPreviousPoint() || pathCostsToMuchMovement(chosenUnit)) {
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
	
	private boolean pathCostsToMuchMovement(Unit chosenUnit) {
		int movementSteps = chosenUnit.getMovementSteps();
		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int tileX = arrowPoints.get(i).getX() / mapDimension.tileSize;
			int tileY = arrowPoints.get(i).getY() / mapDimension.tileSize;
			movementSteps -= movementCostCalculator.movementCost(tileX, tileY, chosenUnit.getMovementType());
			if (movementSteps < 0) {
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
		/*
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
		*/
		int[][] maximumStepsLeftWhenPositionReached = new int[mapDimension.getTileWidth()][mapDimension.getTileHeight()];
		Point[][] previousTilePosition = new Point[mapDimension.getTileWidth()][mapDimension.getTileHeight()];
		Point lastTilePosition = null;
		int tileX = chosenUnit.getPosition().getX() / mapDimension.tileSize;
		int tileY = chosenUnit.getPosition().getY() / mapDimension.tileSize;
		int numberOfMovementStepsLeft = chosenUnit.getMovementSteps();
		checkPathViaTile(maximumStepsLeftWhenPositionReached, previousTilePosition, lastTilePosition, chosenUnit, tileX, tileY, numberOfMovementStepsLeft);
		
		Stack<Point> reversedTilePath = new Stack<>();
		Point tilePosition = new Point(newPosition.getX() / mapDimension.tileSize, newPosition.getY() / mapDimension.tileSize);
		while(true) {
			reversedTilePath.add(tilePosition);
			tilePosition = previousTilePosition[tilePosition.getX()][tilePosition.getY()];
			if (tilePosition.getX() == chosenUnit.getPosition().getX() / mapDimension.tileSize
					&& tilePosition.getY() == chosenUnit.getPosition().getY() / mapDimension.tileSize) {
				break;
			}
		}
		while (!reversedTilePath.isEmpty()) {
			Point nextTilePosition = reversedTilePath.pop();
			int x = nextTilePosition.getX() * mapDimension.tileSize;
			int y = nextTilePosition.getY() * mapDimension.tileSize;
			addArrowPoint(new Point(x, y));
		}
	}
	
	private void checkPathViaTile(int[][] maximumStepsLeftWhenPositionReached, Point[][] previousTilePosition, Point lastTilePosition, Unit chosenUnit, int tileX, int tileY, int numberOfMovementStepsLeft) {
		if (numberOfMovementStepsLeft < 0) {
			return;
		}
		if (tileX < 0 || tileX >= mapDimension.getTileWidth() || tileY < 0 || tileY >= mapDimension.getTileHeight()) {
			return;
		}
		if (maximumStepsLeftWhenPositionReached[tileX][tileY] > numberOfMovementStepsLeft) {
			return;
		}
		if (maximumStepsLeftWhenPositionReached[tileX][tileY] == numberOfMovementStepsLeft
				&& previousTilePosition[tileX][tileY] != null) {
			return;
		}
		if (areaChecker.areaOccupiedByNonFriendly(tileX * mapDimension.tileSize, tileY * mapDimension.tileSize, heroHandler.getHeroFromUnit(chosenUnit))) {
			return;
		}

		maximumStepsLeftWhenPositionReached[tileX][tileY] = numberOfMovementStepsLeft;
		previousTilePosition[tileX][tileY] = lastTilePosition;
		Point currentTilePosition = new Point(tileX, tileY);
		int movementCost = movementCostCalculator.movementCost(tileX, tileY, chosenUnit.getMovementType());

		checkPathViaTile(maximumStepsLeftWhenPositionReached, previousTilePosition, currentTilePosition, chosenUnit, tileX+1, tileY, numberOfMovementStepsLeft - movementCost);
		checkPathViaTile(maximumStepsLeftWhenPositionReached, previousTilePosition, currentTilePosition, chosenUnit, tileX, tileY+1, numberOfMovementStepsLeft - movementCost);
		checkPathViaTile(maximumStepsLeftWhenPositionReached, previousTilePosition, currentTilePosition, chosenUnit, tileX-1, tileY, numberOfMovementStepsLeft - movementCost);
		checkPathViaTile(maximumStepsLeftWhenPositionReached, previousTilePosition, currentTilePosition, chosenUnit, tileX, tileY-1, numberOfMovementStepsLeft - movementCost);
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