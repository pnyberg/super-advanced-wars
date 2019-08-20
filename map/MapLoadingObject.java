package map;

import java.util.ArrayList;

import gameObjects.DimensionObject;
import map.area.Area;
import map.buildings.Building;
import map.structures.Structure;

public class MapLoadingObject {
	private GameMap gameMap;
	private DimensionObject mapDim;
	private ArrayList<Building> buildings;
	private ArrayList<Structure> structures;
	
	public MapLoadingObject() {
		gameMap = null;
		mapDim = null;
		buildings = new ArrayList<>();
		structures = new ArrayList<>();
	}
	
	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}
	
	public void setMapDim(DimensionObject mapDim) {
		this.mapDim = mapDim;
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
	
	public DimensionObject getMapDimension() {
		return mapDim;
	}
	
	public ArrayList<Building> getBuildingList() {
		return buildings;
	}
	
	public ArrayList<Structure> getStructureList() {
		return structures;
	}
}