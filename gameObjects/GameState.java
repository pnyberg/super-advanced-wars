package gameObjects;

import java.util.ArrayList;

import cursors.Cursor;
import main.HeroHandler;
import map.buildings.Building;
import map.structures.Structure;
import menus.BuildingMenuState;
import menus.MenuState;
import menus.UnitMenuState;
import routing.MovementMap;

public class GameState {
	private HeroHandler heroHandler;
	private ChosenObject chosenObject;
	private Cursor cursor;
	private MovementMap movementMap;
	private ArrayList<Building> buildings;
	private ArrayList<Structure> structures;
	private MenuState buildingMenuState;
	private MenuState mapMenuState;
	private UnitMenuState unitMenuState;
	
	public GameState(int tileSize) {
		heroHandler = new HeroHandler();
		chosenObject = new ChosenObject();
		cursor = new Cursor(0, 0, tileSize);
		buildings = new ArrayList<>();
		structures = new ArrayList<>();
		buildingMenuState = new BuildingMenuState();
		mapMenuState = new MenuState();
		unitMenuState = new UnitMenuState();
	}
	
	public void addBuildings(ArrayList<Building> buildings) {
		this.buildings.addAll(buildings);
	}
	
	public void addStructures(ArrayList<Structure> structures) {
		this.structures.addAll(structures);
	}
	
	public void setMovementMap(MovementMap movementMap) {
		this.movementMap = movementMap;
	}
	
	public HeroHandler getHeroHandler() {
		return heroHandler;
	}
	
	public ChosenObject getChosenObject() {
		return chosenObject;
	}
	
	public Cursor getCursor() {
		return cursor;
	}
	
	public MovementMap getMovementMap() {
		return movementMap;
	}
	
	public ArrayList<Building> getBuildings() {
		return buildings;
	}
	
	public ArrayList<Structure> getStructures() {
		return structures;
	}
	
	public MenuState getBuildingMenuState() {
		return buildingMenuState;
	}
	
	public MenuState getMapMenuState() {
		return mapMenuState;
	}
	
	public UnitMenuState getUnitMenuState() {
		return unitMenuState;
	}
}
