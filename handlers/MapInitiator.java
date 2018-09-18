/**
 * TODO:
 * - contains movementcost and movementability (should perhaps be put in RouteHandler?)
 * - should probably read from a file?
 * - add HQ
 * - add Silo
 *
 */
package handlers;

import java.util.ArrayList;

import area.Area;
import area.TerrainType;
import area.buildings.*;
import heroes.Hero;
import point.Point;
import units.airMoving.*;
import units.footMoving.*;
import units.seaMoving.*;
import units.tireMoving.*;
import units.treadMoving.*;
import heroes.*;

public class MapInitiator {
	private MapDimension mapDimension;
	protected Area[][] map;
	private ArrayList<Building> buildings;
	private HeroPortrait portrait;
	private MapHandler mapHandler;

	public MapInitiator(MapDimension mapDimension, MapHandler mapHandler, Area[][] map, ArrayList<Building> buildings, HeroPortrait portrait, int index) {
		this.mapDimension = mapDimension;
		this.map = map;
		this.buildings = buildings;
		this.portrait = portrait;
		this.mapHandler = mapHandler;

		if (index == 1) {
			
		} else {
			initTestMap();
			initTestTroops();
		}
	}
	
	private void initTestMap() {
		for (int y = 0 ; y < 2 ; y++) {
			for (int x = 0 ; x < mapDimension.width ; x++) {
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
		addAreaObject(4, 2, TerrainType.PLAIN);
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
			for (int x = 8 ; x < mapDimension.width ; x++) {
				addAreaObject(x, y, TerrainType.SEA);
			}
		}

		for (int y = 8 ; y < mapDimension.height ; y++) {
			for (int x = 0 ; x < mapDimension.width ; x++) {
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

		Building building = mapHandler.getBuilding(2, 4); // factory

		building.setOwnership(portrait.getCurrentHero());

		building = mapHandler.getBuilding(4, 8); // port
		building.setOwnership(portrait.getCurrentHero());
		building = mapHandler.getBuilding(7, 3); // airport
		building.setOwnership(portrait.getCurrentHero());
	}
	
	private void addAreaObject(int x, int y, TerrainType terrainType) {
		map[x][y] =  new Area(new Point(x * mapDimension.tileSize, y * mapDimension.tileSize), terrainType, mapDimension.tileSize);
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

	private void initTestTroops() {
		Hero hero1 = portrait.getHero(0);
		Hero hero2 = portrait.getHero(1);

		hero1.getTroopHandler().addTroop(new Infantry(3, 6, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Mech(3, 3, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Tank(4, 4, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Recon(5, 5, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Artillery(5, 2, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Rocket(2, 5, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Battleship(1, 3, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new APC(3, 2, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new AAir(3, 4, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Lander(8, 5, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Fighter(10, 2, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Bomber(10, 4, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new BCopter(8, 2, hero1.getColor()));
		hero1.getTroopHandler().addTroop(new Cruiser(8, 3, hero1.getColor()));

		hero2.getTroopHandler().addTroop(new Infantry(6, 6, hero2.getColor()));
		hero2.getTroopHandler().addTroop(new Infantry(5, 7, hero2.getColor()));
		hero2.getTroopHandler().addTroop(new Battleship(6, 8, hero2.getColor()));
		hero2.getTroopHandler().addTroop(new Lander(7, 9, hero2.getColor()));
		hero2.getTroopHandler().addTroop(new Fighter(10, 8, hero2.getColor()));

		for (int h = 0 ; h < 2 ; h++) {
			for (int k = 0 ; k < portrait.getHero(h).getTroopHandler().getTroopSize() ; k++) {
				mapHandler.getHeroPortrait().getUnitFromHero(h, k).regulateActive(true);
			}
		}
	}
}
