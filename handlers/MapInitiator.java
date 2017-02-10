package handlers;

import java.util.ArrayList;

import buildings.Airport;
import buildings.Building;
import buildings.City;
import buildings.Factory;
import buildings.Port;
import heroes.Hero;
import units.AAir;
import units.APC;
import units.Artillery;
import units.BCopter;
import units.Battleship;
import units.Bomber;
import units.Cruiser;
import units.Fighter;
import units.Infantry;
import units.Lander;
import units.Mech;
import units.Recon;
import units.Rocket;
import units.Tank;

import heroes.*;

/**
 * Handles the map
 * - contains movementcost and movementability (should perhaps be put in RouteHandler?)
 *
 */
public class MapInitiator {
	private static int mapWidth, mapHeight;
	protected static int[][] map;
	private static ArrayList<Building> buildings;
	private static HeroPortrait portrait;

	protected static void initMap(int mapWidth, int mapHeight, int[][] map, ArrayList<Building> buildings, HeroPortrait portrait, int index) {
		MapInitiator.mapWidth = mapWidth;
		MapInitiator.mapHeight = mapHeight;
		MapInitiator.map = map;
		MapInitiator.buildings = buildings;
		MapInitiator.portrait = portrait;

		if (index == 1) {
			
		} else {
			initTestMap();
			initTestTroops();
		}
	}
	
	private static void initTestMap() {
		for (int n = 0 ; n < 2 ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				map[i][n] = MapHandler.SEA;
			}
		}

		for (int i = 0 ; i < 2 ; i++) {
			for (int n = 2 ; n < 8 ; n++) {
				map[i][n] = MapHandler.SEA;
			}
		}

		map[2][2] = MapHandler.CITY;
		map[4][2] = MapHandler.PLAIN;
		map[5][2] = MapHandler.PLAIN;
		map[6][2] = MapHandler.MOUNTAIN;
		map[7][2] = MapHandler.MOUNTAIN;

		map[2][3] = MapHandler.PLAIN;
		map[7][3] = MapHandler.AIRPORT;

		map[2][4] = MapHandler.FACTORY;
		map[4][4] = MapHandler.WOOD;
		map[5][4] = MapHandler.WOOD;
		map[7][4] = MapHandler.PLAIN;

		map[2][5] = MapHandler.PLAIN;
		map[4][5] = MapHandler.WOOD;
		map[5][5] = MapHandler.WOOD;
		map[7][5] = MapHandler.PLAIN;

		map[2][6] = MapHandler.MOUNTAIN;
		map[7][6] = MapHandler.PLAIN;

		map[2][7] = MapHandler.MOUNTAIN;
		map[3][7] = MapHandler.MOUNTAIN;
		map[4][7] = MapHandler.PLAIN;
		map[5][7] = MapHandler.PLAIN;
		map[7][7] = MapHandler.CITY;

		for (int i = 8 ; i < mapWidth ; i++) {
			for (int n = 2 ; n < 8 ; n++) {
				map[i][n] = MapHandler.SEA;
			}
		}

		for (int n = 8 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				map[i][n] = MapHandler.SEA;
			}
		}

		map[9][0] = MapHandler.REEF;
		map[1][1] = MapHandler.REEF;

		map[8][4] = MapHandler.SHORE;
		map[8][5] = MapHandler.SHORE;

		map[4][8] = MapHandler.PORT;

		map[8][8] = MapHandler.REEF;
		map[0][9] = MapHandler.REEF;

		// buildings-part
		initBuildings();

		Building building = MapHandler.getBuilding(2, 4); // factory

		building.setOwnership(portrait.getCurrentHero());

		building = MapHandler.getBuilding(4, 8); // port
		building.setOwnership(portrait.getCurrentHero());
		building = MapHandler.getBuilding(7, 3); // airport
		building.setOwnership(portrait.getCurrentHero());
	}

	private static void initBuildings() {
		for (int x = 0 ; x < map.length ; x++) {
			for (int y = 0 ; y < map[0].length ; y++) {
				if (map[x][y] == MapHandler.CITY) {
					buildings.add(new City(x, y));
				} else if (map[x][y] == MapHandler.PORT) {
					buildings.add(new Port(x, y));
				} else if (map[x][y] == MapHandler.AIRPORT) {
					buildings.add(new Airport(x, y));
				} else if (map[x][y] == MapHandler.FACTORY) {
					buildings.add(new Factory(x, y));
//				} else if (map[x][y] == HQ) {
//					buildings.add(new HQ(x, y));
//				} else if (map[x][y] == SILO) {
//					buildings.add(new Silo(x, y));
				}
			}
		}
	}

	private static void initTestTroops() {
		Hero hero1 = portrait.getHero(0);
		Hero hero2 = portrait.getHero(1);

		hero1.addTroop(new Infantry(3, 6, hero1.getColor()));
		hero1.addTroop(new Mech(3, 3, hero1.getColor()));
		hero1.addTroop(new Tank(4, 4, hero1.getColor()));
		hero1.addTroop(new Recon(5, 5, hero1.getColor()));
		hero1.addTroop(new Artillery(5, 2, hero1.getColor()));
		hero1.addTroop(new Rocket(2, 5, hero1.getColor()));
		hero1.addTroop(new Battleship(1, 3, hero1.getColor()));
		hero1.addTroop(new APC(3, 2, hero1.getColor()));
		hero1.addTroop(new AAir(3, 4, hero1.getColor()));
		hero1.addTroop(new Lander(8, 5, hero1.getColor()));
		hero1.addTroop(new Fighter(10, 2, hero1.getColor()));
		hero1.addTroop(new Bomber(10, 4, hero1.getColor()));
		hero1.addTroop(new BCopter(8, 2, hero1.getColor()));
		hero1.addTroop(new Cruiser(8, 3, hero1.getColor()));

		hero2.addTroop(new Infantry(6, 6, hero2.getColor()));
		hero2.addTroop(new Infantry(5, 7, hero2.getColor()));
		hero2.addTroop(new Battleship(6, 8, hero2.getColor()));
		hero2.addTroop(new Lander(7, 9, hero2.getColor()));
		hero2.addTroop(new Fighter(10, 8, hero2.getColor()));

		for (int h = 0 ; h < 2 ; h++) {
			for (int k = 0 ; k < portrait.getHero(h).getTroopSize() ; k++) {
				MapHandler.getUnitFromHero(h, k).regulateActive(true);
			}
		}
	}
}
