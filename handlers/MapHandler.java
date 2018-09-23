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
	private MapDimension mapDimension;
	private Area[][] map;
	private boolean[][] moveabilityMatrix;
	private int[][] movementCostMatrix;
	private ArrayList<Building> buildings;
	private HeroPortrait heroPortrait;
	private MapGettersObject mapGettersObject;
	private AreaChecker areaChecker;
	private MapPainter mapPainter;
	private MapInitiator mapInitiator;
	private GameProperties gameProperties;

	public MapHandler(MapDimension mapDimension, RouteHandler routeHandler, HeroHandler heroHandler, GameProperties gameProperties) {
		this.mapDimension = mapDimension;
		map = new Area[mapDimension.width][mapDimension.height];
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
		movementCostMatrix = new MovementCostMatrixFactory().getMovementCostMatrix();
		buildings = new ArrayList<Building>();
		heroPortrait = new HeroPortrait(mapDimension, heroHandler);
		mapGettersObject = new MapGettersObject(new UnitGetter(heroPortrait), new BuildingGetter(heroPortrait, buildings));
		areaChecker = new AreaChecker(heroHandler, mapGettersObject.unitGetter);
		mapPainter = new MapPainter(heroPortrait, mapDimension, map, routeHandler, mapGettersObject.buildingGetter);
		int index = 0;
		mapInitiator = new MapInitiator(mapDimension, this, map, buildings, heroPortrait, index);
		this.gameProperties = gameProperties;
	}

	public void updateCash() {
		int numberOfCashgivers = 0;
		Hero hero = heroPortrait.getHeroHandler().getCurrentHero();

		for (Building building : buildings) {
			if (building.getOwner() == hero) {
				numberOfCashgivers++;
			}
		}

		int newCash = Building.getIncome() * numberOfCashgivers;
		hero.manageCash(newCash);
	}

	public void fuelMaintenance() {
		Hero hero = heroPortrait.getHeroHandler().getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			Unit unit = hero.getTroopHandler().getTroop(k);
			
			if (unit.getMovementType() == MovementType.SHIP ||
				unit.getMovementType() == MovementType.TRANSPORT ||
				unit.getMovementType() == MovementType.AIR) {
				unit.getUnitSupply().useFuel(gameProperties.fuelMaintenancePerTurn);
			}
		}
	}

	public void resetActiveVariable() {
		Hero hero = heroPortrait.getHeroHandler().getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			hero.getTroopHandler().getTroop(k).regulateActive(true);
		}
	}

	public boolean isLand(int x, int y) {
		TerrainType terrainType = map[x][y].getTerrainType();
		return moveabilityMatrix[UnitType.INFANTRY.unitIndex()][terrainType.terrainTypeIndex()];
	}
	
	public int movementCost(int x, int y, MovementType movementType) {
		TerrainType terrainType = map[x][y].getTerrainType();
		return movementCostMatrix[movementType.movementTypeIndex()][terrainType.terrainTypeIndex()];
	}

	public HeroPortrait getHeroPortrait() {
		return heroPortrait;
	}
	
	public Area[][] getMap() {
		return map;
	}

	public boolean[][] getMoveabilityMatrix() {
		return moveabilityMatrix;
	}
	
	public MapDimension getMapDimension() {
		return mapDimension;
	}
	
	public MapGettersObject getMapGettersObject() {
		return mapGettersObject;
	}
	
	public AreaChecker getAreaChecker() {
		return areaChecker;
	}
	
	public MapPainter getMapPainter() {
		return mapPainter;
	}
}