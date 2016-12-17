package handlers;

import units.*;
import menus.*;
import buildings.*;
import heroes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
/**
 * Handles the map
 * - contains movementcost and movementability (should perhaps be put in RouteHandler?)
 */
public class MapHandler {
	public static final int 	ROAD = 0,
								PLAIN = 1,
								WOOD = 2,
								MOUNTAIN = 3,
								SEA = 4,
								CITY = 5,
								PORT = 6,
								AIRPORT = 7,
								FACTORY = 8,
								REEF = 9,
								SHORE = 10;

	public static final int tileSize = 40;
	private static final int numberOfMovementTypes = 7;
	private static final int numberOfAreaTypes = 11;

	private static int[][] map, movementCostMatrix;
	private static boolean[][] moveabilityCostMatrix;
	private static HeroPortrait portrait;
	private static ArrayList<Building> buildings;

	public static void initMapHandler(int mapWidth, int mapHeight) {
		portrait = new HeroPortrait(mapWidth, mapHeight);
		initHeroes();

		initMovementCostMatrix();
		initMoveabilityMatrix();

		initMapAndTroops(mapWidth, mapHeight, map, portrait, 0);

		Building.init(1000);
	}

	private static void initMovementCostMatrix() {
		// number of types of units x number of types of terrain
		movementCostMatrix = new int[numberOfMovementTypes][numberOfAreaTypes];

		for (int unitIndex = 0 ; unitIndex < movementCostMatrix.length ; unitIndex++) {
			for (int terrainIndex = 0 ; terrainIndex < movementCostMatrix[0].length ; terrainIndex++) {
				movementCostMatrix[unitIndex][terrainIndex] = 1;
			}
		}

		movementCostMatrix[Unit.TIRE][PLAIN] = 2;
		movementCostMatrix[Unit.BAND][WOOD] = 2;
		movementCostMatrix[Unit.TIRE][WOOD] = 3;
		movementCostMatrix[Unit.INFANTRY][MOUNTAIN] = 2;
		movementCostMatrix[Unit.SHIP][REEF] = 2;
		movementCostMatrix[Unit.TRANSPORT][REEF] = 2;
	}

	private static void initMoveabilityMatrix() {
		// number of types of units x number of types of terrain
		moveabilityCostMatrix = new boolean[numberOfMovementTypes][numberOfAreaTypes];

		moveabilityCostMatrix[Unit.INFANTRY][ROAD] = true;
		moveabilityCostMatrix[Unit.MECH][ROAD] = true;
		moveabilityCostMatrix[Unit.BAND][ROAD] = true;
		moveabilityCostMatrix[Unit.TIRE][ROAD] = true;
		moveabilityCostMatrix[Unit.AIR][ROAD] = true;

		moveabilityCostMatrix[Unit.INFANTRY][PLAIN] = true;
		moveabilityCostMatrix[Unit.MECH][PLAIN] = true;
		moveabilityCostMatrix[Unit.BAND][PLAIN] = true;
		moveabilityCostMatrix[Unit.TIRE][PLAIN] = true;
		moveabilityCostMatrix[Unit.AIR][PLAIN] = true;

		moveabilityCostMatrix[Unit.INFANTRY][WOOD] = true;
		moveabilityCostMatrix[Unit.MECH][WOOD] = true;
		moveabilityCostMatrix[Unit.BAND][WOOD] = true;
		moveabilityCostMatrix[Unit.TIRE][WOOD] = true;
		moveabilityCostMatrix[Unit.AIR][WOOD] = true;

		moveabilityCostMatrix[Unit.INFANTRY][MOUNTAIN] = true;
		moveabilityCostMatrix[Unit.MECH][MOUNTAIN] = true;
		moveabilityCostMatrix[Unit.AIR][MOUNTAIN] = true;

		moveabilityCostMatrix[Unit.SHIP][SEA] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][SEA] = true;
		moveabilityCostMatrix[Unit.AIR][SEA] = true;

		moveabilityCostMatrix[Unit.SHIP][REEF] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][REEF] = true;
		moveabilityCostMatrix[Unit.AIR][REEF] = true;

		moveabilityCostMatrix[Unit.INFANTRY][SHORE] = true;
		moveabilityCostMatrix[Unit.MECH][SHORE] = true;
		moveabilityCostMatrix[Unit.BAND][SHORE] = true;
		moveabilityCostMatrix[Unit.TIRE][SHORE] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][SHORE] = true;
		moveabilityCostMatrix[Unit.AIR][SHORE] = true;

		moveabilityCostMatrix[Unit.INFANTRY][CITY] = true;
		moveabilityCostMatrix[Unit.MECH][CITY] = true;
		moveabilityCostMatrix[Unit.BAND][CITY] = true;
		moveabilityCostMatrix[Unit.TIRE][CITY] = true;
		moveabilityCostMatrix[Unit.AIR][CITY] = true;
		
		moveabilityCostMatrix[Unit.INFANTRY][FACTORY] = true;
		moveabilityCostMatrix[Unit.MECH][FACTORY] = true;
		moveabilityCostMatrix[Unit.BAND][FACTORY] = true;
		moveabilityCostMatrix[Unit.TIRE][FACTORY] = true;
		moveabilityCostMatrix[Unit.AIR][FACTORY] = true;

		moveabilityCostMatrix[Unit.INFANTRY][PORT] = true;
		moveabilityCostMatrix[Unit.MECH][PORT] = true;
		moveabilityCostMatrix[Unit.BAND][PORT] = true;
		moveabilityCostMatrix[Unit.TIRE][PORT] = true;
		moveabilityCostMatrix[Unit.SHIP][PORT] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][PORT] = true;
		moveabilityCostMatrix[Unit.AIR][PORT] = true;

		moveabilityCostMatrix[Unit.INFANTRY][AIRPORT] = true;
		moveabilityCostMatrix[Unit.MECH][AIRPORT] = true;
		moveabilityCostMatrix[Unit.BAND][AIRPORT] = true;
		moveabilityCostMatrix[Unit.TIRE][AIRPORT] = true;
		moveabilityCostMatrix[Unit.AIR][AIRPORT] = true;
	}

	private static void initMapAndTroops(int mapWidth, int mapHeight, int[][] map, HeroPortrait portrait, int index) {
		MapInitiator.initMap(mapWidth, mapHeight, map, buildings, portrait, index);
	}

	private static void setOwnerships(Hero[][] ownerMap) {
		for (Building building : buildings) {
			int x = building.getX();
			int y = building.getY();
			if (ownerMap[x][y] == null) {
				continue;
			}

			building.setOwnership(ownerMap[x][y]);
		}
	}

	private static void initHeroes() {
		portrait.addHero(new Hero(0));
		portrait.addHero(new Hero(1));
		portrait.selectStartHero();
	}

	public static void updatePortraitSideChoice(int cursorX, int cursorY) {
		portrait.updateSideChoice(cursorX, cursorY);
	}

	public static void changeHero() {
		portrait.nextHero();
	}

	public static void updateCash() {
		int numberOfCashgivers = 0;
		Hero hero = portrait.getCurrentHero();

		for (Building building : buildings) {
			if (building.getOwner() == hero) {
				numberOfCashgivers++;
			}
		}

		int newCash = Building.getIncome() * numberOfCashgivers;
		hero.manageCash(newCash);
	}

	public static void resetActiveVariable() {
		Hero hero = portrait.getCurrentHero();
		for (int k = 0 ; k < hero.getTroopSize() ; k++) {
			hero.getTroop(k).regulateActive(true);
		}
	}

	public static Unit getAnyUnit(int x, int y) {
		for (int h = 0 ; h < portrait.getNumberOfHeroes() ; h++) {
			for (int k = 0 ; k < portrait.getHero(h).getTroopSize() ; k++) {
				Unit unit = getUnitFromHero(h, k);
				if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}

		return null;
	}

	public static Unit getNonFriendlyUnit(int x, int y, Hero hero) {
		for (int h = 0 ; h < portrait.getNumberOfHeroes() ; h++) {
			if (portrait.getHero(h) == hero) {
				continue;
			}
			for (int k = 0 ; k < portrait.getHero(h).getTroopSize() ; k++) {
				Unit unit = getUnitFromHero(h, k);
				if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}

		return null;
	}

	public static Unit getNonFriendlyUnit(int x, int y) {
		return getNonFriendlyUnit(x, y, portrait.getCurrentHero());
	}

	public static Unit getFriendlyUnit(int x, int y) {
		for (int k = 0 ; k < getFriendlyTroopSize() ; k++) {
			Unit unit = getFriendlyUnitFromCurrentHero(k);
			if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}

	public static Unit getFriendlyUnitExceptSelf(Unit notUnit, int x, int y) {
		for (int k = 0 ; k < getFriendlyTroopSize() ; k++) {
			Unit unit = getFriendlyUnitFromCurrentHero(k);
			if (unit.getX() == x && unit.getY() == y && unit != notUnit && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}

	public static boolean areaOccupiedByAny(Unit chosenUnit, int x, int y) {
		Unit testAnyUnit = getAnyUnit(x, y);

		if (chosenUnit == testAnyUnit) {
			return false;
		}

		return testAnyUnit != null;
	}

	public static boolean areaOccupiedByFriendly(Unit chosenUnit, int x, int y) {
		Unit testFriendlyUnit = getFriendlyUnit(x, y);

		if (chosenUnit == testFriendlyUnit) {
			return false;
		}

		return testFriendlyUnit != null;
	}

	public static boolean areaOccupiedByNonFriendly(int x, int y, Hero hero) {
		Unit testAnyUnit = getNonFriendlyUnit(x, y, hero);

		return testAnyUnit != null;
	}

	public static boolean areaOccupiedByNonFriendly(int x, int y) {
		return areaOccupiedByNonFriendly(x, y, portrait.getCurrentHero());
	}

	/***
	 * Used to check if a positions can be moved to by a specific movement-type
	 ***/
	public static boolean allowedMovementPosition(int x, int y, int movementType, Hero hero) {
		int terrainType = map[x][y];

		if (areaOccupiedByNonFriendly(x, y, hero)) {
			return false;
		}

		return moveabilityCostMatrix[movementType][terrainType];
	}

	public static boolean allowedMovementPosition(int x, int y, int movementType) {
		return allowedMovementPosition(x, y, movementType, portrait.getCurrentHero());
	}

	/**
	 * Because infantry can move to every area that is considered "land" we will use 
	 *  the infantrys boolean-value to determine if it's land or not
	 * 
	 * @param unit
	 * @return
	 */
	public static boolean unitOnLand(int x, int y) {
		int terrainType = map[x][y];
		
		return moveabilityCostMatrix[Unit.INFANTRY][terrainType];
	}
	
	public static int movementCost(int x, int y, int movementType) {
		int terrainType = map[x][y];

		return movementCostMatrix[movementType][terrainType];
	}

	public static int getDefenceValue(int terrainType) {
		if (terrainType == ROAD ||
			terrainType == SEA ||
			terrainType == SHORE) {
			return 0;
		} else if (terrainType == PLAIN ||
					terrainType == REEF) {
			return 1;
		} else if (terrainType == WOOD) {
			return 2;
		} else if (terrainType == CITY ||
					terrainType == FACTORY ||
					terrainType == AIRPORT ||
					terrainType == PORT) {
			return 3;
		} else if (terrainType == MOUNTAIN) {
			return 4;
		} 
		return -1;
	}

	public static HeroPortrait getHeroPortrait() {
		return portrait;
	}

	public static int map(int x, int y) {
		return map[x][y];
	}

	public static Unit getUnitFromHero(int hero, int index) {
		return portrait.getHero(hero).getTroop(index);
	}

	public static Unit getFriendlyUnitFromCurrentHero(int index) {
		return portrait.getCurrentHero().getTroop(index);
	}

	public static int getTroopSize(int hero) {
		return portrait.getHero(hero).getTroopSize();
	}

	public static int getFriendlyTroopSize() {
		return portrait.getCurrentHero().getTroopSize();
	}

	public static Building getBuilding(int x, int y) {
		for (Building building : buildings) {
			if (building.getX() == x && building.getY() == y) {
				return building;
			}
		}

		return null;
	}

	public static Building getFriendlyBuilding(int x, int y) {
		Building building = getBuilding(x, y);

		if (building != null && building.getOwner() == portrait.getCurrentHero()) {
			return building;
		}

		return null;
	}

	public static void paintArea(Graphics g, int x, int y, boolean rangeAble) {
		int areaNumber = map[x][y];
		boolean movementAble = RouteHandler.movementMap(x, y);

		if (areaNumber == ROAD) {
			if (movementAble) {
				g.setColor(Color.lightGray);
			} else {
				g.setColor(Color.gray);
			}
		} else if (areaNumber == PLAIN) {
			if (movementAble) {
				g.setColor(new Color(255,250,205)); // lighter yellow
			} else {
				g.setColor(new Color(204,204,0)); // darker yellow
			}
		} else if (areaNumber == WOOD) {
			if (movementAble) {
				g.setColor(new Color(50,205,50)); // limegreen
			} else {
				g.setColor(new Color(0,128,0)); // green
			}
		} else if (areaNumber == MOUNTAIN) {
			if (movementAble) {
				g.setColor(new Color(205,133,63)); // lighter brown
			} else {
				g.setColor(new Color(142,101,64)); // brown
			}
		} else if (areaNumber == SEA) {
			if (movementAble) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else if (areaNumber == REEF) {
			if (movementAble) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else if (areaNumber == SHORE) {
			if (movementAble) {
				g.setColor(new Color(30,144,145)); // lighter blue
			} else {
				g.setColor(new Color(30,144,105)); // lighter blue
			}
		}

		if (getBuilding(x, y) != null && !rangeAble) {
			Building building = getBuilding(x, y);
			building.paint(g, tileSize);
			return;
		}

		int paintX = x * tileSize;
		int paintY = y * tileSize;

		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		if (areaNumber == REEF && !rangeAble) {
			g.fillRect(paintX + tileSize / 4, paintY + tileSize / 4, tileSize / 4, tileSize / 4);
			g.fillRect(paintX + 5 * tileSize / 8, paintY + 5 * tileSize / 8, tileSize / 4, tileSize / 4);
		}
	}

	public static void paintUnits(Graphics g, Unit chosenUnit) {
		for (int t = 0 ; t < 2 ; t++) {
			for (int k = 0 ; k < getTroopSize(t) ; k++) {
				Unit unit = getUnitFromHero(t, k);
				if (unit != chosenUnit) {
					if (!unit.isHidden()) {
						unit.paint(g, tileSize);
					}
				}
			}
		}
	}

	public static void paintPortrait(Graphics g) {
		portrait.paint(g);
	}
}