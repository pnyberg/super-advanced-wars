package loading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import gameObjects.Direction;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import hero.Hero;
import hero.HeroHandler;
import map.GameMap;
import map.MapTileObject;
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

public class GameLoader {
	private HeroHandler heroHandler;
	private int tileSize;

	public GameLoader(HeroHandler heroHandler, int tileSize) {
		this.heroHandler = heroHandler;
		this.tileSize = tileSize;
	}

	public GameLoadingObject loadMap(String fileName) {
		try {
			// load map-file
			Scanner scanner = new Scanner(new File(fileName));
			ArrayList<String> mapLines = new ArrayList<>();
			while(scanner.hasNext()) {
				mapLines.add(scanner.nextLine());
			}
			scanner.close();
			if(mapLines.isEmpty()) {
				System.err.println("Map-file is empty!");
				System.exit(1);
			}

			// setup map
			int mapWidth = mapLines.get(0).split(" ").length;
			int mapHeight = mapLines.size();
			Area[][] map = new Area[mapWidth][mapHeight];
			MapDimension mapDimension = new MapDimension(mapWidth, mapHeight, tileSize);
			ArrayList<Building> buildings = new ArrayList<>();
			ArrayList<Structure> structures = new ArrayList<>();
			for(int tileY = 0 ; tileY < mapHeight ; tileY++) {
				String nextLine = mapLines.get(tileY);
				String[] tokens = nextLine.split(" ");
				for(int tileX = 0 ; tileX < mapWidth ; tileX++) {
					String tileCode = tokens[tileX];
					MapTileObject mapTileObject = createMapTile(tileCode, new Point(tileX, tileY));
					map[tileX][tileY] = mapTileObject.area;

					if(mapTileObject.building != null) {
						buildings.add(mapTileObject.building);
					} else if(mapTileObject.structure != null) {
						structures.add(mapTileObject.structure);
					}
				}
			}
	
			GameMap gameMap = new GameMap(map, tileSize);
			GameProperties gameProperties = new GameProperties(mapDimension, gameMap);
			GameState gameState = new GameState(mapDimension, heroHandler, buildings, structures);
			GameLoadingObject gameLoadingObject = new GameLoadingObject(gameProperties, gameState);

			return gameLoadingObject;
		} catch (IOException e) {
			System.err.println("Couldn't load file '" + fileName + "'.");
		}
		return null;
	}
	
	private MapTileObject createMapTile(String tileCode, Point tilePoint) {
		final String cityAbbrev = "CT";
		final String factoryAbbrev = "FT";
		final String airportAbbrev = "AT";
		final String portAbbrev = "PT";
		final String miniCannonAbbrev = "MC";
		
		int tileX = tilePoint.getX();
		int tileY = tilePoint.getY();
		TerrainType terrainType = null;
		Building building = null;
		Structure structure = null;

		if (tileCode.equals("PLAN")) {
			terrainType = TerrainType.PLAIN;
		} else if (tileCode.equals("WOOD")) {
			terrainType = TerrainType.WOOD;
		} else if (tileCode.equals("MOUN")) {
			terrainType = TerrainType.MOUNTAIN;
		} else if (tileCode.equals("SEAS")) {
			terrainType = TerrainType.SEA;
		} else if (tileCode.equals("SHOR")) {
			terrainType = TerrainType.SHOAL;
		} else if (tileCode.equals("REEF")) {
			terrainType = TerrainType.REEF;
		} else if (tileCode.equals("RIVR")) {
			//terrainType = TerrainType.RIVER;
		} else if (tileCode.equals("ROAD")) {
			terrainType = TerrainType.ROAD;
		} else if (tileCode.substring(0, 3).equals(cityAbbrev + "0") && tileCode.substring(3, 4).matches("[0-4]")) {
			terrainType = TerrainType.CITY;
			City city = new City(tileX * tileSize, tileY * tileSize, tileSize); 
			String numString = tileCode.substring(2, 4);
			if (!numString.equals("00")) {
				city.setOwnership(getHeroFromNumber(numString));
			}
			building = city;
		} else if (tileCode.substring(0, 3).equals(factoryAbbrev + "0") && tileCode.substring(3, 4).matches("[0-4]")) {
			terrainType = TerrainType.FACTORY;
			Factory factory = new Factory(tileX * tileSize, tileY * tileSize, tileSize); 
			String numString = tileCode.substring(2, 4);
			if (!numString.equals("00")) {
				factory.setOwnership(getHeroFromNumber(numString));
			}
			building = factory;
		} else if (tileCode.substring(0, 3).equals(airportAbbrev + "0") && tileCode.substring(3, 4).matches("[0-4]")) {
			terrainType = TerrainType.AIRPORT;
			Airport airport = new Airport(tileX * tileSize, tileY * tileSize, tileSize); 
			String numString = tileCode.substring(2, 4);
			if (!numString.equals("00")) {
				airport.setOwnership(getHeroFromNumber(numString));
			}
			building = airport;
		} else if (tileCode.substring(0, 3).equals(portAbbrev + "0") && tileCode.substring(3, 4).matches("[0-4]")) {
			terrainType = TerrainType.PORT;
			Port port = new Port(tileX * tileSize, tileY * tileSize, tileSize); 
			String numString = tileCode.substring(2, 4);
			if (!numString.equals("00")) {
				port.setOwnership(getHeroFromNumber(numString));
			}
			building = port;
		} else if (tileCode.substring(0, 2).equals(miniCannonAbbrev) && tileCode.substring(2, 3).matches("[NESW]") && tileCode.substring(3, 4).matches("[0-4]")) {
			Direction direction = getDirectionFromLetter(tileCode.substring(2, 3));
			String numString = tileCode.substring(3, 4);
			Hero hero = numString.equals("0") ? null : getHeroFromNumber(numString);
			terrainType = TerrainType.MINI_CANNON;
			structure = new MiniCannon(tileX * tileSize, tileY * tileSize, direction, hero, tileSize);
		}
		Area area = new Area(terrainType, new Point(tileX * tileSize, tileY * tileSize), tileSize);
		return new MapTileObject(area, building, structure);
	}
	
	private Hero getHeroFromNumber(String numberString) {
		int number = Integer.parseInt(numberString) - 1;
		return heroHandler.getHero(number);
	}
	
	private Direction getDirectionFromLetter(String directionLetter) {
		switch(directionLetter) {
			case "N":
				return Direction.NORTH;
			case "E":
				return Direction.EAST;
			case "S":
				return Direction.SOUTH;
			case "W":
				return Direction.WEST;
			default:
				return null;
		}
	}	
}