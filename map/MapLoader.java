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
	private GameMap gameMap;
	private HeroHandler heroHandler;
	private ArrayList<Building> buildings;
	private ArrayList<Structure> structures;
	
	public MapLoader(MapDim mapDim, GameMap gameMap, HeroHandler heroHandler, ArrayList<Building> buildings, ArrayList<Structure> structures) {
		this.mapDim = mapDim;
		this.gameMap = gameMap;
		this.heroHandler = heroHandler;
		this.buildings = buildings;
		this.structures = structures;
	}
	
	public void loadMap(String fileName) {
		buildings.clear();
		structures.clear();
		try {
			Scanner scanner = new Scanner(new File(fileName));
			ArrayList<String> mapLines = new ArrayList<>();
			
			while(scanner.hasNext()) {
				mapLines.add(scanner.nextLine());
			}
			scanner.close();
			
			if (mapLines.isEmpty()) {
				System.err.println("Map-file is empty!");
				System.exit(1);
			}
			
			int mapWidth = mapLines.get(0).split(" ").length;
			int mapHeight = mapLines.size();
			gameMap.resizeMap(mapWidth, mapHeight);
			mapDim.setDimension(mapWidth, mapHeight);
			
			for (int y = 0 ; y < mapHeight ; y++) {
				String nextLine = mapLines.get(y);
				String[] tokens = nextLine.split(" ");
				for (int x = 0 ; x < mapWidth ; x++) {
					String tileCode = tokens[x];
					insertMapTile(tileCode, x, y);
				}
			}
		} catch (IOException e) {
			System.err.println("Couldn't load file '" + fileName + "'.");
		}
	}
	
	private void insertMapTile(String tileCode, int x, int y) {
		TerrainType terrainType = null;
		final String cityAbbrev = "CT";
		final String factoryAbbrev = "FT";
		final String airportAbbrev = "AT";
		final String portAbbrev = "PT";
		
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
		case cityAbbrev + "00":
			terrainType = TerrainType.CITY;
			buildings.add(new City(x, y));
			break;
		case cityAbbrev + "01":
			terrainType = TerrainType.CITY;
			City city01 = new City(x, y); 
			buildings.add(city01);
			city01.setOwnership(heroHandler.getHero(0));
			break;
		case cityAbbrev + "02":
			terrainType = TerrainType.CITY;
			City city02 = new City(x, y); 
			buildings.add(city02);
			city02.setOwnership(heroHandler.getHero(1));
			break;
		case cityAbbrev + "03":
			terrainType = TerrainType.CITY;
			City city03 = new City(x, y); 
			buildings.add(city03);
			city03.setOwnership(heroHandler.getHero(2));
			break;
		case cityAbbrev + "04":
			terrainType = TerrainType.CITY;
			City city04 = new City(x, y); 
			buildings.add(city04);
			city04.setOwnership(heroHandler.getHero(3));
			break;
		case factoryAbbrev + "00":
			terrainType = TerrainType.FACTORY;
			buildings.add(new Factory(x, y));
			break;
		case factoryAbbrev + "01":
			terrainType = TerrainType.FACTORY;
			Factory factory01 = new Factory(x, y); 
			buildings.add(factory01);
			factory01.setOwnership(heroHandler.getHero(0));
			break;
		case factoryAbbrev + "02":
			terrainType = TerrainType.FACTORY;
			Factory factory02 = new Factory(x, y); 
			buildings.add(factory02);
			factory02.setOwnership(heroHandler.getHero(1));
			break;
		case factoryAbbrev + "03":
			terrainType = TerrainType.FACTORY;
			Factory factory03 = new Factory(x, y); 
			buildings.add(factory03);
			factory03.setOwnership(heroHandler.getHero(2));
			break;
		case factoryAbbrev + "04":
			terrainType = TerrainType.FACTORY;
			Factory factory04 = new Factory(x, y); 
			buildings.add(factory04);
			factory04.setOwnership(heroHandler.getHero(3));
			break;
		case airportAbbrev + "00":
			terrainType = TerrainType.AIRPORT;
			buildings.add(new Airport(x, y));
			break;
		case airportAbbrev + "01":
			terrainType = TerrainType.AIRPORT;
			Airport airport01 = new Airport(x, y); 
			buildings.add(airport01);
			airport01.setOwnership(heroHandler.getHero(0));
			break;
		case airportAbbrev + "02":
			terrainType = TerrainType.AIRPORT;
			Airport airport02 = new Airport(x, y); 
			buildings.add(airport02);
			airport02.setOwnership(heroHandler.getHero(1));
			break;
		case airportAbbrev + "03":
			terrainType = TerrainType.AIRPORT;
			Airport airport03 = new Airport(x, y); 
			buildings.add(airport03);
			airport03.setOwnership(heroHandler.getHero(2));
			break;
		case airportAbbrev + "04":
			terrainType = TerrainType.AIRPORT;
			Airport airport04 = new Airport(x, y); 
			buildings.add(airport04);
			airport04.setOwnership(heroHandler.getHero(3));
			break;
		case portAbbrev + "00":
			terrainType = TerrainType.PORT;
			buildings.add(new Port(x, y));
			break;
		case portAbbrev + "01":
			terrainType = TerrainType.PORT;
			Port port01 = new Port(x, y); 
			buildings.add(port01);
			port01.setOwnership(heroHandler.getHero(0));
			break;
		case portAbbrev + "02":
			terrainType = TerrainType.PORT;
			Port port02 = new Port(x, y); 
			buildings.add(port02);
			port02.setOwnership(heroHandler.getHero(1));
			break;
		case portAbbrev + "03":
			terrainType = TerrainType.PORT;
			Port port03 = new Port(x, y); 
			buildings.add(port03);
			port03.setOwnership(heroHandler.getHero(2));
			break;
		case portAbbrev + "04":
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
		
		gameMap.getMap()[x][y] = new Area(new Point(x * mapDim.tileSize, y * mapDim.tileSize), terrainType, mapDim.tileSize);
	}
}