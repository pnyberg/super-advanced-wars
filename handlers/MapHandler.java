/**
 * TODO:
 * - contains movementcost and movementability (should perhaps be put in RouteHandler?)
 * - refactor code to make it shorter
 *
 */
package handlers;

import units.*;
import heroes.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import area.TerrainType;
import area.buildings.*;

public class MapHandler {
	// appearance-variable
	public static final int tileSize = 40;
	
	// logic-variables
	public static final int numberOfMovementTypes = 7;
	public static final int numberOfAreaTypes = 11;

	// ruling-variable
	private static final int fuelMaintenancePerTurn = 5;

	private MapDimension mapDimension;
	private TerrainType[][] map;
	private boolean[][] moveabilityCostMatrix;
	private HeroPortrait portrait;
	private MapInitiator mapInitiator;
	private RouteHandler routeHandler;
	private MovementCostMatrixFactory movementCostMatrixFactory;
	private ArrayList<Building> buildings;

	public MapHandler(MapDimension mapDimension, RouteHandler routeHandler) {
		this.mapDimension = mapDimension;
		portrait = new HeroPortrait(mapDimension.width);
		this.routeHandler = routeHandler;
		movementCostMatrixFactory = new MovementCostMatrixFactory();
		initHeroes();

		initMoveabilityMatrix();

		map = new TerrainType[mapDimension.width][mapDimension.height];
		buildings = new ArrayList<Building>();

		initMapAndTroops(mapDimension.width, mapDimension.height, 0);

		Building.init(1000);		
	}

	private void initMoveabilityMatrix() {
		// number of types of units x number of types of terrain
		moveabilityCostMatrix = new boolean[numberOfMovementTypes][numberOfAreaTypes];

		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.BAND][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TIRE][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.ROAD.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.BAND][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TIRE][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.PLAIN.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.BAND][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TIRE][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.WOOD.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.SHIP][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.SEA.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.SHIP][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.REEF.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.BAND][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TIRE][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.SHORE.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.BAND][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TIRE][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.CITY.terrainTypeIndex()] = true;
		
		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.BAND][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TIRE][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.FACTORY.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.BAND][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TIRE][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.SHIP][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.PORT.terrainTypeIndex()] = true;

		moveabilityCostMatrix[Unit.INFANTRY][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.MECH][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.BAND][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.TIRE][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityCostMatrix[Unit.AIR][TerrainType.AIRPORT.terrainTypeIndex()] = true;
	}

	private void initMapAndTroops(int mapWidth, int mapHeight, int index) {
		mapInitiator = new MapInitiator(mapWidth, mapHeight, this, map, buildings, portrait, index);
	}

	private void setOwnerships(Hero[][] ownerMap) {
		for (Building building : buildings) {
			int x = building.getX();
			int y = building.getY();
			if (ownerMap[x][y] == null) {
				continue;
			}

			building.setOwnership(ownerMap[x][y]);
		}
	}

	private void initHeroes() {
		HeroFactory heroFactory = new HeroFactory();
		portrait.addHero(heroFactory.createHero(0));
		portrait.addHero(heroFactory.createHero(1));
		portrait.selectStartHero();
	}

	public void updatePortraitSideChoice(int cursorX, int cursorY) {
		portrait.updateSideChoice(cursorX, cursorY);
	}

	public void changeHero() {
		portrait.nextHero();
	}

	public void updateCash() {
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

	public void fuelMaintenance() {
		Hero hero = portrait.getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			Unit unit = hero.getTroopHandler().getTroop(k);
			
			if (unit.getMovementType() == Unit.SHIP ||
				unit.getMovementType() == Unit.TRANSPORT ||
				unit.getMovementType() == Unit.AIR) {
				unit.useFuel(fuelMaintenancePerTurn);
			}
		}
	}

	public void resetActiveVariable() {
		Hero hero = portrait.getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			hero.getTroopHandler().getTroop(k).regulateActive(true);
		}
	}

	public Unit getAnyUnit(int x, int y) {
		for (int h = 0 ; h < portrait.getNumberOfHeroes() ; h++) {
			for (int k = 0 ; k < portrait.getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = getUnitFromHero(h, k);
				if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}

		return null;
	}

	public Unit getNonFriendlyUnit(int x, int y, Hero hero) {
		for (int h = 0 ; h < portrait.getNumberOfHeroes() ; h++) {
			if (portrait.getHero(h) == hero) {
				continue;
			}
			for (int k = 0 ; k < portrait.getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = getUnitFromHero(h, k);
				if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}

		return null;
	}

	public Unit getNonFriendlyUnit(int x, int y) {
		return getNonFriendlyUnit(x, y, portrait.getCurrentHero());
	}

	public Unit getFriendlyUnit(int x, int y) {
		for (int k = 0 ; k < getFriendlyTroopSize() ; k++) {
			Unit unit = getFriendlyUnitFromCurrentHero(k);
			if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}

	public Unit getFriendlyUnitExceptSelf(Unit notUnit, int x, int y) {
		for (int k = 0 ; k < getFriendlyTroopSize() ; k++) {
			Unit unit = getFriendlyUnitFromCurrentHero(k);
			if (unit.getX() == x && unit.getY() == y && unit != notUnit && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}

	public boolean areaOccupiedByAny(Unit chosenUnit, int x, int y) {
		Unit testAnyUnit = getAnyUnit(x, y);

		if (chosenUnit == testAnyUnit) {
			return false;
		}

		return testAnyUnit != null;
	}

	public boolean areaOccupiedByFriendly(Unit chosenUnit, int x, int y) {
		Unit testFriendlyUnit = getFriendlyUnit(x, y);

		if (chosenUnit == testFriendlyUnit) {
			return false;
		}

		return testFriendlyUnit != null;
	}

	public boolean areaOccupiedByNonFriendly(int x, int y, Hero hero) {
		Unit testAnyUnit = getNonFriendlyUnit(x, y, hero);

		return testAnyUnit != null;
	}

	public boolean areaOccupiedByNonFriendly(int x, int y) {
		return areaOccupiedByNonFriendly(x, y, portrait.getCurrentHero());
	}

	/***
	 * Used to check if a positions can be moved to by a specific movement-type
	 ***/
	public boolean allowedMovementPosition(int x, int y, int movementType, Hero hero) {
		TerrainType terrainType = map[x][y];

		if (areaOccupiedByNonFriendly(x, y, hero)) {
			return false;
		}

		return moveabilityCostMatrix[movementType][terrainType.terrainTypeIndex()];
	}

	public boolean allowedMovementPosition(int x, int y, int movementType) {
		return allowedMovementPosition(x, y, movementType, portrait.getCurrentHero());
	}

	/**
	 * Because infantry can move to every area that is considered "land" we will use 
	 *  the infantrys boolean-value to determine if it's land or not
	 * 
	 * @param unit
	 * @return
	 */
	public boolean unitOnLand(int x, int y) {
		TerrainType terrainType = map[x][y];
		
		return moveabilityCostMatrix[Unit.INFANTRY][terrainType.terrainTypeIndex()];
	}
	
	public int movementCost(int x, int y, int movementType) {
		TerrainType terrainType = map[x][y];

		return movementCostMatrixFactory.getMovementCost(movementType, terrainType);
	}

	public int getDefenceValue(TerrainType terrainType) {
		if (terrainType == TerrainType.ROAD ||
			terrainType == TerrainType.SEA ||
			terrainType == TerrainType.SHORE) {
			return 0;
		} else if (terrainType == TerrainType.PLAIN ||
					terrainType == TerrainType.REEF) {
			return 1;
		} else if (terrainType == TerrainType.WOOD) {
			return 2;
		} else if (terrainType == TerrainType.CITY ||
					terrainType == TerrainType.FACTORY ||
					terrainType == TerrainType.AIRPORT ||
					terrainType == TerrainType.PORT) {
			return 3;
		} else if (terrainType == TerrainType.MOUNTAIN) {
			return 4;
		} 
		return -1;
	}

	public HeroPortrait getHeroPortrait() {
		return portrait;
	}

	public TerrainType map(int x, int y) {
		return map[x][y];
	}

	public Unit getUnitFromHero(int hero, int index) {
		return portrait.getHero(hero).getTroopHandler().getTroop(index);
	}

	public Unit getFriendlyUnitFromCurrentHero(int index) {
		return portrait.getCurrentHero().getTroopHandler().getTroop(index);
	}

	public int getTroopSize(int hero) {
		return portrait.getHero(hero).getTroopHandler().getTroopSize();
	}

	public int getFriendlyTroopSize() {
		return portrait.getCurrentHero().getTroopHandler().getTroopSize();
	}

	public Building getBuilding(int x, int y) {
		for (Building building : buildings) {
			if (building.getX() == x && building.getY() == y) {
				return building;
			}
		}

		return null;
	}

	public Building getFriendlyBuilding(int x, int y) {
		Building building = getBuilding(x, y);

		if (building != null && building.getOwner() == portrait.getCurrentHero()) {
			return building;
		}

		return null;
	}
	
	public MapDimension getMapDimension() {
		return mapDimension;
	}

	public void paintArea(Graphics g, int x, int y, boolean rangeAble) {
		TerrainType areaType = mapInitiator.map[x][y];
		boolean movementAble = routeHandler.movementMap(x, y);

		if (areaType == TerrainType.ROAD) {
			if (movementAble) {
				g.setColor(Color.lightGray);
			} else {
				g.setColor(Color.gray);
			}
		} else if (areaType == TerrainType.PLAIN) {
			if (movementAble) {
				g.setColor(new Color(255,250,205)); // lighter yellow
			} else {
				g.setColor(new Color(204,204,0)); // darker yellow
			}
		} else if (areaType == TerrainType.WOOD) {
			if (movementAble) {
				g.setColor(new Color(50,205,50)); // limegreen
			} else {
				g.setColor(new Color(0,128,0)); // green
			}
		} else if (areaType == TerrainType.MOUNTAIN) {
			if (movementAble) {
				g.setColor(new Color(205,133,63)); // lighter brown
			} else {
				g.setColor(new Color(142,101,64)); // brown
			}
		} else if (areaType == TerrainType.SEA) {
			if (movementAble) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else if (areaType == TerrainType.REEF) {
			if (movementAble) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else if (areaType == TerrainType.SHORE) {
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

		if (areaType == TerrainType.REEF && !rangeAble) {
			g.fillRect(paintX + tileSize / 4, paintY + tileSize / 4, tileSize / 4, tileSize / 4);
			g.fillRect(paintX + 5 * tileSize / 8, paintY + 5 * tileSize / 8, tileSize / 4, tileSize / 4);
		}
	}

	public void paintUnits(Graphics g, Unit chosenUnit) {
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

	public void paintPortrait(Graphics g) {
		portrait.paint(g);
	}
}