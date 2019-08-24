package gameObjects;

import java.util.ArrayList;

import cursors.Cursor;
import hero.HeroHandler;
import map.buildings.Building;
import map.structures.Structure;
import menus.BuildingMenuState;
import menus.MenuState;
import menus.UnitMenuState;
import point.Point;
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
	private boolean[][] rangeMap;
	private ArrayList<Point> arrowPoints;
	private TurnState turnState;
	private boolean heroPortraitLeftSide;
	
	public GameState(MapDimension mapDimension, HeroHandler heroHandler, ArrayList<Building> buildings, ArrayList<Structure> structures) {
		this.heroHandler = heroHandler;
		this.buildings = buildings;
		this.structures = structures;
		chosenObject = new ChosenObject();
		cursor = new Cursor(0, 0, mapDimension.tileSize);
		buildingMenuState = new BuildingMenuState();
		mapMenuState = new MenuState();
		unitMenuState = new UnitMenuState();
		movementMap = new MovementMap(mapDimension);		
		rangeMap = new boolean[mapDimension.getTileWidth()][mapDimension.getTileHeight()];
		arrowPoints = new ArrayList<Point>();
		turnState = new TurnState();
		heroPortraitLeftSide = true;
	}
	
	public void addBuildings(ArrayList<Building> buildings) {
		this.buildings.addAll(buildings);
	}
	
	public void addStructures(ArrayList<Structure> structures) {
		this.structures.addAll(structures);
	}
	
	public void enableRangeMapLocation(int tileX, int tileY) {
		rangeMap[tileX][tileY] = true;
	}
	
	public void resetRangeMap() {
		rangeMap = new boolean[rangeMap.length][rangeMap[0].length];
	}
	
	public void setRangeMap(boolean[][] rangeMap) {
		this.rangeMap = rangeMap;
	}
	
	public void setMovementMap(MovementMap movementMap) {
		this.movementMap = movementMap;
	}
	
	public void setHeroPortraitOnLeftSide(boolean leftSide) {
		this.heroPortraitLeftSide = leftSide;
	}
	
	public boolean heroPortraitOnLeftSide() {
		return heroPortraitLeftSide;
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

	public boolean[][] getRangeMap() {
		return rangeMap;
	}
	
	public boolean getRangeMapLocation(int tileX, int tileY) {
		return rangeMap[tileX][tileY];
	}
	
	public ArrayList<Point> getArrowPoints() {
		return arrowPoints;
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
	
	public TurnState getTurnState() {
		return turnState;
	}
}
