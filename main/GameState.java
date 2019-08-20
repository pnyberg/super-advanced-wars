package main;

import java.util.ArrayList;

import map.GameMap;
import map.area.Area;
import map.buildings.Building;
import map.structures.Structure;

public class GameState {
	private HeroHandler heroHandler;
	private GameMap gameMap;
	private ArrayList<Building> buildings;
	private ArrayList<Structure> structures;
	
	public GameState() {
		heroHandler = new HeroHandler();
		gameMap = null;
		buildings = new ArrayList<>();
		structures = new ArrayList<>();
	}
	
	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
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
	
	public HeroHandler getHeroHandler() {
		return heroHandler;
	}
}
