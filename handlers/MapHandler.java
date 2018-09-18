/**
 * TODO:
 * - contains movementcost and movementability (should perhaps be put in RouteHandler?)
 * - refactor code to make it shorter
 *
 */
package handlers;

import units.*;
import heroes.*;

import java.awt.Graphics;
import java.util.ArrayList;

import area.Area;
import area.TerrainType;
import area.buildings.*;

public class MapHandler {
	// logic-variables
	public static final int numberOfMovementTypes = 7;
	public static final int numberOfAreaTypes = 11;

	// ruling-variable
	private static final int fuelMaintenancePerTurn = 5;

	private MapDimension mapDimension;
	private Area[][] map;
	private boolean[][] moveabilityMatrix;
	private int[][] movementCostMatrix;
	private ArrayList<Building> buildings;
	private HeroPortrait portrait;
	private MapInitiator mapInitiator;
	private RouteHandler routeHandler;
	private UnitGetter unitGetter;
	private BuildingGetter buildingGetter;

	public MapHandler(MapDimension mapDimension, RouteHandler routeHandler) {
		this.mapDimension = mapDimension;
		buildings = new ArrayList<Building>();
		portrait = new HeroPortrait(mapDimension);
		this.routeHandler = routeHandler;
		unitGetter = new UnitGetter(portrait);
		buildingGetter = new BuildingGetter(portrait, buildings);
		movementCostMatrix = new MovementCostMatrixFactory().getMovementCostMatrix();
		map = new Area[mapDimension.width][mapDimension.height];
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();

		initHeroes();
		initMapAndTroops(mapDimension, 0);

		Building.init(1000);		
	}

	private void initMapAndTroops(MapDimension mapDimension, int index) {
		mapInitiator = new MapInitiator(mapDimension, this, map, buildings, portrait, index);
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


	public boolean areaOccupiedByAny(Unit chosenUnit, int x, int y) {
		Unit testAnyUnit = unitGetter.getAnyUnit(x, y);

		if (chosenUnit == testAnyUnit) {
			return false;
		}

		return testAnyUnit != null;
	}

	public boolean areaOccupiedByFriendly(Unit chosenUnit, int x, int y) {
		Unit testFriendlyUnit = unitGetter.getFriendlyUnit(x, y);

		if (chosenUnit == testFriendlyUnit) {
			return false;
		}

		return testFriendlyUnit != null;
	}

	public boolean areaOccupiedByNonFriendly(int x, int y, Hero hero) {
		Unit testAnyUnit = unitGetter.getNonFriendlyUnit(x, y, hero);

		return testAnyUnit != null;
	}

	public boolean areaOccupiedByNonFriendly(int x, int y) {
		return areaOccupiedByNonFriendly(x, y, portrait.getCurrentHero());
	}

	/***
	 * Used to check if a positions can be moved to by a specific movement-type
	 ***/
	public boolean allowedMovementPosition(int x, int y, int movementType, Hero hero) {
		TerrainType terrainType = map[x][y].getTerrainType();

		if (areaOccupiedByNonFriendly(x, y, hero)) {
			return false;
		}

		return moveabilityMatrix[movementType][terrainType.terrainTypeIndex()];
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
		TerrainType terrainType = map[x][y].getTerrainType();
		
		return moveabilityMatrix[Unit.INFANTRY][terrainType.terrainTypeIndex()];
	}
	
	public int movementCost(int x, int y, int movementType) {
		TerrainType terrainType = map[x][y].getTerrainType();

		return movementCostMatrix[movementType][terrainType.terrainTypeIndex()];
	}

	public HeroPortrait getHeroPortrait() {
		return portrait;
	}

	public Area map(int x, int y) {
		return map[x][y];
	}
	
	public MapDimension getMapDimension() {
		return mapDimension;
	}
	
	public UnitGetter getUnitGetter() {
		return unitGetter;
	}
	
	public BuildingGetter getBuildingGetter() {
		return buildingGetter;
	}

	public void paintArea(Graphics g, int x, int y, boolean rangeAble) {
		Area area = map[x][y];
		boolean movementAble = routeHandler.movementMap(x, y);

		if (buildingGetter.getBuilding(x, y) != null && !rangeAble) {
			Building building = buildingGetter.getBuilding(x, y);
			building.paint(g, mapDimension.tileSize);
			return;
		}

		area.paint(g, movementAble, rangeAble);
	}

	public void paintUnits(Graphics g, Unit chosenUnit) {
		for (int t = 0 ; t < 2 ; t++) {
			for (int k = 0 ; k < portrait.getTroopSize(t) ; k++) {
				Unit unit = portrait.getUnitFromHero(t, k);
				if (unit != chosenUnit) {
					if (!unit.isHidden()) {
						unit.paint(g, mapDimension.tileSize);
					}
				}
			}
		}
	}

	public void paintPortrait(Graphics g) {
		portrait.paint(g);
	}
}