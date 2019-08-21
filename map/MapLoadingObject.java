package map;

import java.util.ArrayList;

import gameObjects.MapDimension;
import map.area.Area;
import map.buildings.Building;
import map.structures.Structure;
import routing.MovementMap;

public class MapLoadingObject {
	private GameMap gameMap;
	private MapDimension mapDimension;
	private MovementMap movementMap;
	private ArrayList<Building> buildings;
	private ArrayList<Structure> structures;
	
	public MapLoadingObject() {
		gameMap = null;
		mapDimension = null;
		movementMap = null;
		buildings = new ArrayList<>();
		structures = new ArrayList<>();
	}
	
	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}
	
	public void setMapDimension(MapDimension mapDim) {
		this.mapDimension = mapDim;
	}
	
	public void setMovementMap(MovementMap movementMap) {
		this.movementMap = movementMap;
	}
	
	public void addBuilding(Building building) {
		buildings.add(building);
	}
	
	public void addStructure(Structure structure) {
		structures.add(structure);
	}
	
	public void setAreaAtPosition(Area area, int tileX, int tileY) {
		gameMap.getMap()[tileX][tileY] = area;
	}
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public MapDimension getMapDimension() {
		return mapDimension;
	}
	
	public MovementMap getMovementMap() {
		return movementMap;
	}
	
	public ArrayList<Building> getBuildingList() {
		return buildings;
	}
	
	public ArrayList<Structure> getStructureList() {
		return structures;
	}
}