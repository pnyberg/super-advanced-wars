/**
 * TODO:
 * - contains movementcost and movementability (should perhaps be put in RouteHandler?)
 * - should probably read from a file?
 * - add HQ
 * - add Silo
 *
 */
package map;

import java.awt.Color;
import java.util.ArrayList;

import gameObjects.Direction;
import gameObjects.MapDim;
import hero.*;
import main.HeroHandler;
import map.area.Area;
import map.area.TerrainType;
import map.buildings.*;
import map.structures.MiniCannon;
import map.structures.Structure;
import map.structures.StructureHandler;
import point.Point;
import units.airMoving.*;
import units.footMoving.*;
import units.seaMoving.*;
import units.tireMoving.*;
import units.treadMoving.*;

public class MapInitiator {
	private MapDim mapDim;
	protected Area[][] map;
	private ArrayList<Building> buildings;
	private ArrayList<Structure> structures;
	private HeroPortrait portrait;
	private BuildingHandler buildingGetter;
	private StructureHandler structureHandler;
	private HeroHandler heroHandler;

	public MapInitiator(MapDim mapDimension, BuildingHandler buildingGetter, StructureHandler structureHandler, HeroHandler heroHandler, Area[][] map, ArrayList<Building> buildings, ArrayList<Structure> structures, HeroPortrait portrait) {
		this.mapDim = mapDimension;
		this.map = map;
		this.buildings = buildings;
		this.structures = structures;
		this.portrait = portrait;
		this.buildingGetter = buildingGetter;
		this.structureHandler = structureHandler;
		this.heroHandler = heroHandler;
	}
	
	public void loadMap(int index) {
		if (index == 1) {
			new MapLoader(mapDim, map, heroHandler, buildings, structures).loadMap("map-files/test_map.txt");
		} else {
			initTestMap();
			initTestTroops();
		}
	}
	
	private void initTestMap() {
		for (int y = 0 ; y < 2 ; y++) {
			for (int x = 0 ; x < mapDim.getWidth() ; x++) {
				addAreaObject(x, y, TerrainType.SEA);
			}
		}

		for (int y = 2 ; y < 8 ; y++) {
			for (int x = 0 ; x < 2 ; x++) {
				addAreaObject(x, y, TerrainType.SEA);
			}
		}

		addAreaObject(2, 2, TerrainType.CITY);
		addAreaObject(3, 2, TerrainType.ROAD);
		addAreaObject(4, 2, TerrainType.MINI_CANNON);
		addAreaObject(5, 2, TerrainType.PLAIN);
		addAreaObject(6, 2, TerrainType.MOUNTAIN);
		addAreaObject(7, 2, TerrainType.MOUNTAIN);

		addAreaObject(2, 3, TerrainType.PLAIN);
		addAreaObject(3, 3, TerrainType.ROAD);
		addAreaObject(4, 3, TerrainType.ROAD);
		addAreaObject(5, 3, TerrainType.ROAD);
		addAreaObject(6, 3, TerrainType.ROAD);
		addAreaObject(7, 3, TerrainType.AIRPORT);

		addAreaObject(2, 4, TerrainType.FACTORY);
		addAreaObject(3, 4, TerrainType.ROAD);
		addAreaObject(4, 4, TerrainType.WOOD);
		addAreaObject(5, 4, TerrainType.WOOD);
		addAreaObject(6, 4, TerrainType.ROAD);
		addAreaObject(7, 4, TerrainType.PLAIN);

		addAreaObject(2, 5, TerrainType.PLAIN);
		addAreaObject(3, 5, TerrainType.ROAD);
		addAreaObject(4, 5, TerrainType.WOOD);
		addAreaObject(5, 5, TerrainType.WOOD);
		addAreaObject(6, 5, TerrainType.ROAD);
		addAreaObject(7, 5, TerrainType.PLAIN);

		addAreaObject(2, 6, TerrainType.MOUNTAIN);
		addAreaObject(3, 6, TerrainType.ROAD);
		addAreaObject(4, 6, TerrainType.ROAD);
		addAreaObject(5, 6, TerrainType.ROAD);
		addAreaObject(6, 6, TerrainType.ROAD);
		addAreaObject(7, 6, TerrainType.PLAIN);

		addAreaObject(2, 7, TerrainType.MOUNTAIN);
		addAreaObject(3, 7, TerrainType.MOUNTAIN);
		addAreaObject(4, 7, TerrainType.PLAIN);
		addAreaObject(5, 7, TerrainType.PLAIN);
		addAreaObject(6, 7, TerrainType.ROAD);
		addAreaObject(7, 7, TerrainType.CITY);

		for (int y = 2 ; y < 8 ; y++) {
			for (int x = 8 ; x < mapDim.getWidth() ; x++) {
				addAreaObject(x, y, TerrainType.SEA);
			}
		}

		for (int y = 8 ; y < mapDim.getHeight() ; y++) {
			for (int x = 0 ; x < mapDim.getWidth() ; x++) {
				addAreaObject(x, y, TerrainType.SEA);
			}
		}

		addAreaObject(9, 0, TerrainType.REEF);
		addAreaObject(1, 1, TerrainType.REEF);

		addAreaObject(8, 4, TerrainType.SHORE);
		addAreaObject(8, 5, TerrainType.SHORE);

		addAreaObject(4, 8, TerrainType.PORT);

		addAreaObject(8, 8, TerrainType.REEF);
		addAreaObject(0, 9, TerrainType.REEF);

		// buildings-part
		initBuildings();

		Building building = buildingGetter.getBuilding(2, 4); // factory

		building.setOwnership(heroHandler.getCurrentHero());

		building = buildingGetter.getBuilding(4, 8); // port
		building.setOwnership(heroHandler.getCurrentHero());
		building = buildingGetter.getBuilding(7, 3); // airport
		building.setOwnership(heroHandler.getCurrentHero());
		
		// structures-part
		initStructures();
	}
	
	private void addAreaObject(int tileX, int tileY, TerrainType terrainType) {
		map[tileX][tileY] =  new Area(tileX, tileY, terrainType, mapDim.tileSize);
	}

	private void initBuildings() {
		for (int x = 0 ; x < map.length ; x++) {
			for (int y = 0 ; y < map[0].length ; y++) {
				if (map[x][y].getTerrainType() == TerrainType.CITY) {
					buildings.add(new City(x, y));
				} else if (map[x][y].getTerrainType() == TerrainType.PORT) {
					buildings.add(new Port(x, y));
				} else if (map[x][y].getTerrainType() == TerrainType.AIRPORT) {
					buildings.add(new Airport(x, y));
				} else if (map[x][y].getTerrainType() == TerrainType.FACTORY) {
					buildings.add(new Factory(x, y));
//				} else if (map[x][y].getTerrainType() == TerrainType.HQ) {
//					buildings.add(new HQ(x, y));
//				} else if (map[x][y].getTerrainType() == TerrainType.SILO) {
//					buildings.add(new Silo(x, y));
				}
			}
		}
	}
	
	private void initStructures() {
		int tileSize = mapDim.tileSize;
		for (int x = 0 ; x < map.length ; x++) {
			for (int y = 0 ; y < map[0].length ; y++) {
				if (map[x][y].getTerrainType() == TerrainType.MINI_CANNON) {
					structures.add(new MiniCannon(x * tileSize, y * tileSize, Direction.SOUTH, heroHandler.getHero(0), tileSize));
				}
			}
		}
	}

	private void initTestTroops() {
		int tileSize = mapDim.tileSize;
		Hero hero1 = heroHandler.getHero(0);
		Hero hero2 = heroHandler.getHero(1);

		hero1.getTroopHandler().addTroop(new Infantry(3 * tileSize, 6 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Mech(3 * tileSize, 3 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Tank(4 * tileSize, 4 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Recon(5 * tileSize, 5 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Artillery(5 * tileSize, 2 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Rocket(2 * tileSize, 5 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Battleship(1 * tileSize, 3 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new APC(3 * tileSize, 2 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new AAir(3 * tileSize, 4 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Lander(8 * tileSize, 5 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Fighter(10 * tileSize, 2 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Bomber(10 * tileSize, 4 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new BCopter(8 * tileSize, 2 * tileSize, hero1.getColor(), tileSize));
		hero1.getTroopHandler().addTroop(new Cruiser(8 * tileSize, 3 * tileSize, hero1.getColor(), tileSize));

		hero2.getTroopHandler().addTroop(new Infantry(6 * tileSize, 6 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Infantry(5 * tileSize, 7 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Battleship(6 * tileSize, 8 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Lander(7 * tileSize, 9 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Fighter(10 * tileSize, 8 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Cruiser(10 * tileSize, 9 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Cruiser(11 * tileSize, 9 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Cruiser(12 * tileSize, 9 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Cruiser(10 * tileSize, 10 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Cruiser(11 * tileSize, 10 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Cruiser(12 * tileSize, 10 * tileSize, hero2.getColor(), tileSize));
		hero2.getTroopHandler().addTroop(new Cruiser(10 * tileSize, 11 * tileSize, hero2.getColor(), tileSize));

		for (int h = 0 ; h < 2 ; h++) {
			for (int k = 0 ; k < portrait.getHeroHandler().getHero(h).getTroopHandler().getTroopSize() ; k++) {
				heroHandler.getUnitFromHero(h, k).regulateActive(true);
			}
		}
	}
}
