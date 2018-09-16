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

import area.TerrainType;
import area.buildings.Airport;
import area.buildings.Building;
import area.buildings.City;
import area.buildings.Factory;
import area.buildings.Port;
import heroes.Hero;
import units.airMoving.BCopter;
import units.airMoving.Bomber;
import units.airMoving.Fighter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Battleship;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.APC;
import units.treadMoving.Artillery;
import units.treadMoving.Tank;
import heroes.*;

public class MapInitiator {
	private int mapWidth, mapHeight;
	protected TerrainType[][] map;
	private ArrayList<Building> buildings;
	private HeroPortrait portrait;
	private MapHandler mapHandler;

	public MapInitiator(int mapWidth, int mapHeight, MapHandler mapHandler, TerrainType[][] map, ArrayList<Building> buildings, HeroPortrait portrait, int index) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
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
		for (int n = 0 ; n < 2 ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				map[i][n] = TerrainType.SEA;
			}
		}

		for (int i = 0 ; i < 2 ; i++) {
			for (int n = 2 ; n < 8 ; n++) {
				map[i][n] = TerrainType.SEA;
			}
		}

		map[2][2] = TerrainType.CITY;
		map[3][2] = TerrainType.ROAD;
		map[4][2] = TerrainType.PLAIN;
		map[5][2] = TerrainType.PLAIN;
		map[6][2] = TerrainType.MOUNTAIN;
		map[7][2] = TerrainType.MOUNTAIN;

		map[2][3] = TerrainType.PLAIN;
		map[3][3] = TerrainType.ROAD;
		map[4][3] = TerrainType.ROAD;
		map[5][3] = TerrainType.ROAD;
		map[6][3] = TerrainType.ROAD;
		map[7][3] = TerrainType.AIRPORT;

		map[2][4] = TerrainType.FACTORY;
		map[3][4] = TerrainType.ROAD;
		map[4][4] = TerrainType.WOOD;
		map[5][4] = TerrainType.WOOD;
		map[6][4] = TerrainType.ROAD;
		map[7][4] = TerrainType.PLAIN;

		map[2][5] = TerrainType.PLAIN;
		map[3][5] = TerrainType.ROAD;
		map[4][5] = TerrainType.WOOD;
		map[5][5] = TerrainType.WOOD;
		map[6][5] = TerrainType.ROAD;
		map[7][5] = TerrainType.PLAIN;

		map[2][6] = TerrainType.MOUNTAIN;
		map[3][6] = TerrainType.ROAD;
		map[4][6] = TerrainType.ROAD;
		map[5][6] = TerrainType.ROAD;
		map[6][6] = TerrainType.ROAD;
		map[7][6] = TerrainType.PLAIN;

		map[2][7] = TerrainType.MOUNTAIN;
		map[3][7] = TerrainType.MOUNTAIN;
		map[4][7] = TerrainType.PLAIN;
		map[5][7] = TerrainType.PLAIN;
		map[6][7] = TerrainType.ROAD;
		map[7][7] = TerrainType.CITY;

		for (int i = 8 ; i < mapWidth ; i++) {
			for (int n = 2 ; n < 8 ; n++) {
				map[i][n] = TerrainType.SEA;
			}
		}

		for (int n = 8 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				map[i][n] = TerrainType.SEA;
			}
		}

		map[9][0] = TerrainType.REEF;
		map[1][1] = TerrainType.REEF;

		map[8][4] = TerrainType.SHORE;
		map[8][5] = TerrainType.SHORE;

		map[4][8] = TerrainType.PORT;

		map[8][8] = TerrainType.REEF;
		map[0][9] = TerrainType.REEF;

		// buildings-part
		initBuildings();

		Building building = mapHandler.getBuilding(2, 4); // factory

		building.setOwnership(portrait.getCurrentHero());

		building = mapHandler.getBuilding(4, 8); // port
		building.setOwnership(portrait.getCurrentHero());
		building = mapHandler.getBuilding(7, 3); // airport
		building.setOwnership(portrait.getCurrentHero());
	}

	private void initBuildings() {
		for (int x = 0 ; x < map.length ; x++) {
			for (int y = 0 ; y < map[0].length ; y++) {
				if (map[x][y] == TerrainType.CITY) {
					buildings.add(new City(x, y));
				} else if (map[x][y] == TerrainType.PORT) {
					buildings.add(new Port(x, y));
				} else if (map[x][y] == TerrainType.AIRPORT) {
					buildings.add(new Airport(x, y));
				} else if (map[x][y] == TerrainType.FACTORY) {
					buildings.add(new Factory(x, y));
//				} else if (map[x][y] == HQ) {
//					buildings.add(new HQ(x, y));
//				} else if (map[x][y] == SILO) {
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
				mapHandler.getUnitFromHero(h, k).regulateActive(true);
			}
		}
	}
}
