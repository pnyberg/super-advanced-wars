package main;
import java.util.ArrayList;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.DamageHandler;
import cursors.Cursor;
import cursors.FiringCursor;
import gameObjects.ChosenObject;
import gameObjects.GameProp;
import gameObjects.MapDim;
import graphics.MapPainter;
import heroes.HeroPortrait;
import map.BuildingGetter;
import map.MapInitiator;
import map.UnitGetter;
import map.UnitPositionChecker;
import map.area.Area;
import map.area.AreaChecker;
import map.area.buildings.Building;
import menus.building.BuildingMenu;
import menus.map.MapMenu;
import menus.unit.UnitMenu;
import menus.unit.UnitMenuHandler;
import routing.MoveabilityMatrixFactory;
import routing.MovementCostCalculator;
import routing.MovementMap;
import routing.RouteChecker;
import routing.RouteHandler;
import units.ContUnitHandler;

public class InternalStructureObject {
	private AreaChecker areaChecker;
	private AttackHandler attackHandler;
	private AttackRangeHandler attackRangeHandler;
	private ArrayList<Building> buildings;
	private BuildingGetter buildingGetter;
	private BuildingMenu buildingMenu;
	private CashHandler cashHandler;
	private ChosenObject chosenObject;
	private ContUnitHandler containerUnitHandler;
	private Cursor cursor;
	private DamageHandler damageHandler;
	private FiringCursor firingCursor;
	private FuelHandler fuelHandler;
	private HeroHandler heroHandler;
	private HeroPortrait heroPortrait;
	private KeyListenerInputHandler keyListenerInputHandler;
	private Area[][] map;
	private MapDim mapDimension;
	private MapInitiator mapInitiator;
	private MapMenu mapMenu;
	private MapPainter mapPainter;
	private boolean[][] moveabilityMatrix;
	private MovementCostCalculator movementCostCalculator;
	private MovementMap movementMap;
	private RouteChecker routeChecker;
	private RouteHandler routeHandler;
	private SupplyHandler supplyHandler;
	private TurnHandler turnHandler;
	private UnitGetter unitGetter;
	private UnitMenu unitMenu;
	private UnitMenuHandler unitMenuHandler;
	private UnitPositionChecker unitPositionChecker;
	
	public InternalStructureObject(MapDim mapDimension, HeroHandler heroHandler, GameProp gameProp) {
		// no previously required init
		buildings = new ArrayList<Building>();
		chosenObject = new ChosenObject();
		cursor = new Cursor(0, 0, mapDimension.tileSize);
		heroPortrait = new HeroPortrait(mapDimension, heroHandler);
		map = new Area[mapDimension.width][mapDimension.height];
		this.mapDimension = mapDimension;
		mapMenu = new MapMenu(mapDimension.tileSize);
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
		movementMap = new MovementMap(mapDimension);
		unitMenu = new UnitMenu(mapDimension.tileSize);
		
		// required init from first init-round
		buildingGetter = new BuildingGetter(heroPortrait, buildings);
		buildingMenu = new BuildingMenu(mapDimension.tileSize, heroHandler, map);
		cashHandler = new CashHandler(heroPortrait, buildings);
		damageHandler = new DamageHandler(heroHandler, map);
		fuelHandler = new FuelHandler(gameProp, heroPortrait);
		movementCostCalculator = new MovementCostCalculator(map);
		unitGetter = new UnitGetter(heroPortrait);

		// required init from second init-round
		areaChecker = new AreaChecker(heroHandler, unitGetter, map, moveabilityMatrix);
		routeHandler = new RouteHandler(mapDimension, movementMap, movementCostCalculator);
		supplyHandler = new SupplyHandler(unitGetter);
		turnHandler = new TurnHandler(cashHandler, fuelHandler, heroHandler, mapMenu);
		unitPositionChecker = new UnitPositionChecker(unitGetter);
		firingCursor = new FiringCursor(mapDimension, map, unitGetter, heroHandler, damageHandler);
		mapInitiator = new MapInitiator(mapDimension, buildingGetter, heroHandler, map, buildings, heroPortrait);

		// required init from third init-round
		routeChecker = new RouteChecker(mapDimension, heroHandler, map, movementMap, moveabilityMatrix, areaChecker, movementCostCalculator);

		// required init from fourth init-round
		containerUnitHandler = new ContUnitHandler(mapDimension, chosenObject, map, cursor, unitGetter, areaChecker, routeChecker); 
		attackRangeHandler = new AttackRangeHandler(mapDimension, unitGetter, damageHandler, routeChecker, movementMap);

		// required init from fifth init-round
		mapPainter = new MapPainter(heroHandler, mapDimension, map, routeHandler, attackRangeHandler, buildingGetter);
		unitMenuHandler = new UnitMenuHandler(mapDimension, unitMenu, chosenObject, containerUnitHandler, supplyHandler, unitPositionChecker, areaChecker, attackRangeHandler);
		attackHandler = new AttackHandler(mapDimension, unitGetter, attackRangeHandler, damageHandler);

		// required init from sixth init-round
		keyListenerInputHandler = new KeyListenerInputHandler(mapDimension, unitGetter, buildingGetter, chosenObject, cursor, unitMenuHandler, mapMenu, buildingMenu, containerUnitHandler, attackHandler, attackRangeHandler, movementMap, routeHandler, routeChecker, damageHandler, heroHandler, supplyHandler, turnHandler);		
	}
	
	public AttackHandler getAttackHandler() {
		return attackHandler;
	}
	
	public AttackRangeHandler getAttackRangeHandler() {
		return attackRangeHandler;
	}
	
	public BuildingMenu getBuildingMenu() {
		return buildingMenu;
	}
	
	public ChosenObject getChosenObject() {
		return chosenObject;
	}
	
	public ContUnitHandler getContUnitHandler() {
		return containerUnitHandler;
	}

	public Cursor getCursor() {
		return cursor;
	}
	
	public FiringCursor getFiringCursor() {
		return firingCursor;
	}
	
	public HeroPortrait getHeroPortrait() {
		return heroPortrait;
	}
	
	public KeyListenerInputHandler getKeyListenerInputHandler() {
		return keyListenerInputHandler;
	}
	
	public MapDim getMapDimension() {
		return mapDimension;
	}
	
	public MapInitiator getMapInitiator() {
		return mapInitiator;
	}
	
	public MapMenu getMapMenu() {
		return mapMenu;
	}
	
	public MapPainter getMapPainter() {
		return mapPainter;
	}
		
	public RouteHandler getRouteHandler() {
		return routeHandler;
	}

	public TurnHandler getTurnHandler() {
		return turnHandler;
	}
	
	public UnitMenu getUnitMenu() {
		return unitMenu;
	}
}