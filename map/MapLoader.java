package map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import gameObjects.Direction;
import gameObjects.MapDim;
import main.HeroHandler;
import map.area.Area;
import map.area.TerrainType;
import map.buildings.Airport;
import map.buildings.Building;
import map.buildings.City;
import map.buildings.Factory;
import map.buildings.Port;
import map.structures.MiniCannon;
import map.structures.Structure;
import point.Point;

public class MapLoader {
	private MapDim mapDim;
	private Area[][] map;
	private HeroHandler heroHandler;
	private ArrayList<Building> buildings;
	private ArrayList<Structure> structures;
	
	public MapLoader(MapDim mapDim, Area[][] map, HeroHandler heroHandler, ArrayList<Building> buildings, ArrayList<Structure> structures) {
		this.mapDim = mapDim;
		this.map = map;
		this.heroHandler = heroHandler;
		this.buildings = buildings;
		this.structures = structures;
	}
	
	public void loadMap(String fileName) {
		buildings.clear();
		structures.clear();
		try {
			Scanner scanner = new Scanner(new File(fileName));
			
			int mapWidth = -1;
			int y = 0;
			for ( ; scanner.hasNext() ; y++) {
				String nextLine = scanner.nextLine();
				Scanner tileCodeScanner = new Scanner(nextLine);
				int x = 0;
				for ( ; tileCodeScanner.hasNext() ; x++) {
					String tileCode = tileCodeScanner.next();
					insertMapTile(tileCode, x, y);
				}
				tileCodeScanner.close();
				if (mapWidth == -1 || mapWidth == x) {
					mapWidth = x;
				} else {
					System.err.println("The width of the map differs from row to row, invalid map-structure!");
					scanner.close();
					System.exit(1);
				}
			}
			scanner.close();
			mapDim.setDimension(mapWidth, y);
		} catch (IOException e) {
			System.err.println("Couldn't load file '" + fileName + "'.");
		}
	}
	
	private void insertMapTile(String tileCode, int x, int y) {
		TerrainType terrainType = null;
		
		switch (tileCode) {
		case "PLAN":
			terrainType = TerrainType.PLAIN;
			break;
		case "WOOD":
			terrainType = TerrainType.WOOD;
			break;
		case "MOUN":
			terrainType = TerrainType.MOUNTAIN;
			break;
		case "SEAS":
			terrainType = TerrainType.SEA;
			break;
		case "SHOR":
			terrainType = TerrainType.SHORE;
			break;
		case "REEF":
			terrainType = TerrainType.REEF;
			break;
/*		case "RIVR":
			terrainType = TerrainType.RIVER;
			break;*/
		case "ROAD":
			terrainType = TerrainType.ROAD;
			break;
		case "CT00":
			terrainType = TerrainType.CITY;
			buildings.add(new City(x, y));
			break;
		case "CT01":
			terrainType = TerrainType.CITY;
			City city01 = new City(x, y); 
			buildings.add(city01);
			city01.setOwnership(heroHandler.getHero(0));
			break;
		case "CT02":
			terrainType = TerrainType.CITY;
			City city02 = new City(x, y); 
			buildings.add(city02);
			city02.setOwnership(heroHandler.getHero(1));
			break;
		case "CT03":
			terrainType = TerrainType.CITY;
			City city03 = new City(x, y); 
			buildings.add(city03);
			city03.setOwnership(heroHandler.getHero(2));
			break;
		case "CT04":
			terrainType = TerrainType.CITY;
			City city04 = new City(x, y); 
			buildings.add(city04);
			city04.setOwnership(heroHandler.getHero(3));
			break;
		case "FT00":
			terrainType = TerrainType.FACTORY;
			buildings.add(new Factory(x, y));
			break;
		case "FT01":
			terrainType = TerrainType.FACTORY;
			Factory factory01 = new Factory(x, y); 
			buildings.add(factory01);
			factory01.setOwnership(heroHandler.getHero(0));
			break;
		case "FT02":
			terrainType = TerrainType.FACTORY;
			Factory factory02 = new Factory(x, y); 
			buildings.add(factory02);
			factory02.setOwnership(heroHandler.getHero(1));
			break;
		case "FT03":
			terrainType = TerrainType.FACTORY;
			Factory factory03 = new Factory(x, y); 
			buildings.add(factory03);
			factory03.setOwnership(heroHandler.getHero(2));
			break;
		case "FT04":
			terrainType = TerrainType.FACTORY;
			Factory factory04 = new Factory(x, y); 
			buildings.add(factory04);
			factory04.setOwnership(heroHandler.getHero(3));
			break;
		case "AP00":
			terrainType = TerrainType.AIRPORT;
			buildings.add(new Airport(x, y));
			break;
		case "AP01":
			terrainType = TerrainType.AIRPORT;
			Airport airport01 = new Airport(x, y); 
			buildings.add(airport01);
			airport01.setOwnership(heroHandler.getHero(0));
			break;
		case "AP02":
			terrainType = TerrainType.AIRPORT;
			Airport airport02 = new Airport(x, y); 
			buildings.add(airport02);
			airport02.setOwnership(heroHandler.getHero(1));
			break;
		case "AP03":
			terrainType = TerrainType.AIRPORT;
			Airport airport03 = new Airport(x, y); 
			buildings.add(airport03);
			airport03.setOwnership(heroHandler.getHero(2));
			break;
		case "AP04":
			terrainType = TerrainType.AIRPORT;
			Airport airport04 = new Airport(x, y); 
			buildings.add(airport04);
			airport04.setOwnership(heroHandler.getHero(3));
			break;
		case "PT00":
			terrainType = TerrainType.PORT;
			buildings.add(new Port(x, y));
			break;
		case "PT01":
			terrainType = TerrainType.PORT;
			Port port01 = new Port(x, y); 
			buildings.add(port01);
			port01.setOwnership(heroHandler.getHero(0));
			break;
		case "PT02":
			terrainType = TerrainType.PORT;
			Port port02 = new Port(x, y); 
			buildings.add(port02);
			port02.setOwnership(heroHandler.getHero(1));
			break;
		case "PT03":
			terrainType = TerrainType.PORT;
			Port port03 = new Port(x, y); 
			buildings.add(port03);
			port03.setOwnership(heroHandler.getHero(2));
			break;
		case "PT04":
			terrainType = TerrainType.PORT;
			Port port04 = new Port(x, y); 
			buildings.add(port04);
			port04.setOwnership(heroHandler.getHero(3));
			break;
		case "MCN0":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.NORTH, null, mapDim.tileSize));
			break;
		case "MCN1":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.NORTH, heroHandler.getHero(0), mapDim.tileSize));
			break;
		case "MCN2":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.NORTH, heroHandler.getHero(1), mapDim.tileSize));
			break;
		case "MCN3":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.NORTH, heroHandler.getHero(2), mapDim.tileSize));
			break;
		case "MCN4":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.NORTH, heroHandler.getHero(3), mapDim.tileSize));
			break;
		case "MCE0":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.EAST, null, mapDim.tileSize));
			break;
		case "MCE1":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.EAST, heroHandler.getHero(0), mapDim.tileSize));
			break;
		case "MCE2":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.EAST, heroHandler.getHero(1), mapDim.tileSize));
			break;
		case "MCE3":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.EAST, heroHandler.getHero(2), mapDim.tileSize));
			break;
		case "MCE4":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.EAST, heroHandler.getHero(3), mapDim.tileSize));
			break;
		case "MCS0":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.SOUTH, null, mapDim.tileSize));
			break;
		case "MCS1":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.SOUTH, heroHandler.getHero(0), mapDim.tileSize));
			break;
		case "MCS2":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.SOUTH, heroHandler.getHero(1), mapDim.tileSize));
			break;
		case "MCS3":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.SOUTH, heroHandler.getHero(2), mapDim.tileSize));
			break;
		case "MCS4":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.SOUTH, heroHandler.getHero(3), mapDim.tileSize));
			break;
		case "MCW0":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.WEST, null, mapDim.tileSize));
			break;
		case "MCW1":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.WEST, heroHandler.getHero(0), mapDim.tileSize));
			break;
		case "MCW2":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.WEST, heroHandler.getHero(1), mapDim.tileSize));
			break;
		case "MCW3":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.WEST, heroHandler.getHero(2), mapDim.tileSize));
			break;
		case "MCW4":
			terrainType = TerrainType.MINI_CANNON;
			structures.add(new MiniCannon(x, y, Direction.WEST, heroHandler.getHero(3), mapDim.tileSize));
			break;
		default:
			break;
		}
		
		map[x][y] =  new Area(new Point(x * mapDim.tileSize, y * mapDim.tileSize), terrainType, mapDim.tileSize);
	}
}