package main;
import java.util.ArrayList;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.AttackValueCalculator;
import combat.DamageHandler;
import combat.DefenceValueCalculator;
import combat.StructureAttackHandler;
import cursors.Cursor;
import cursors.FiringCursor;
import gameObjects.ChosenObject;
import gameObjects.GameProp;
import gameObjects.MapDim;
import graphics.CommanderView;
import graphics.ViewPainter;
import hero.HeroPortrait;
import map.MapInitiator;
import map.UnitGetter;
import map.UnitPositionChecker;
import map.area.Area;
import map.area.AreaChecker;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
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
import units.UnitWorthCalculator;

public class InternalStructureObject {
	private AreaChecker areaChecker;
	private AttackHandler attackHandler;
	private AttackRangeHandler attackRangeHandler;
	private AttackValueCalculator attackValueCalculator;
	private ArrayList<Building> buildings;
	private ArrayList<Structure> structures;
	private BuildingHandler buildingHandler;
	private BuildingMenu buildingMenu;
	private CashHandler cashHandler;
	private CommanderView commanderView;
	private ContUnitHandler containerUnitHandler;
	private Cursor cursor;
	private DamageHandler damageHandler;
	private DefenceValueCalculator defenceValueCalculator;
	private FiringCursor firingCursor;
	private HeroPortrait heroPortrait;
	private KeyListenerInputHandler keyListenerInputHandler;
	private Area[][] map;
	private MapInitiator mapInitiator;
	private MapMenu mapMenu;
	private ViewPainter mainViewPainter;
	private boolean[][] moveabilityMatrix;
	private MovementCostCalculator movementCostCalculator;
	private MovementMap movementMap;
	private RouteChecker routeChecker;
	private RouteHandler routeHandler;
	private StructureAttackHandler structureAttackHandler;
	private StructureHandler structureHandler;
	private SupplyHandler supplyHandler;
	private TurnHandler turnHandler;
	private UnitGetter unitGetter;
	private UnitMenuHandler unitMenuHandler;
	private UnitPositionChecker unitPositionChecker;
	private UnitWorthCalculator unitWorthCalculator;
	
	public InternalStructureObject(GameProp gameProp, HeroHandler heroHandler) {
		// no previously required init
		buildings = new ArrayList<Building>();
		cursor = new Cursor(0, 0, gameProp.getMapDim().tileSize);
		heroPortrait = new HeroPortrait(gameProp.getMapDim(), heroHandler);
		map = new Area[gameProp.getMapDim().width][gameProp.getMapDim().height];
		mapMenu = new MapMenu(gameProp.getMapDim().tileSize, heroHandler);
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
		movementMap = new MovementMap(gameProp.getMapDim());
		structureAttackHandler = new StructureAttackHandler(gameProp.getMapDim(), heroHandler);
		structures = new ArrayList<Structure>();
		unitGetter = new UnitGetter(heroHandler, gameProp.getMapDim().tileSize);
		attackValueCalculator = new AttackValueCalculator();
		defenceValueCalculator = new DefenceValueCalculator();
		unitWorthCalculator = new UnitWorthCalculator();

		// required init from first init-round
		buildingHandler = new BuildingHandler(heroHandler, buildings);
		buildingMenu = new BuildingMenu(gameProp.getMapDim().tileSize, heroHandler, map);
		cashHandler = new CashHandler(heroPortrait, buildings);
		damageHandler = new DamageHandler(heroHandler, map, attackValueCalculator, defenceValueCalculator, unitWorthCalculator);
		movementCostCalculator = new MovementCostCalculator(map);
		areaChecker = new AreaChecker(heroHandler, unitGetter, map, moveabilityMatrix);
		structureHandler = new StructureHandler(structures, structureAttackHandler, unitWorthCalculator);
		supplyHandler = new SupplyHandler(unitGetter);
		unitPositionChecker = new UnitPositionChecker(unitGetter);
		commanderView = new CommanderView(gameProp.getMapDim(), heroHandler, attackValueCalculator, defenceValueCalculator);

		// required init from second init-round
		routeHandler = new RouteHandler(gameProp.getMapDim(), movementMap, movementCostCalculator);
		turnHandler = new TurnHandler(gameProp, cashHandler, heroHandler, structureHandler, mapMenu);
		firingCursor = new FiringCursor(gameProp.getMapDim(), map, unitGetter, heroHandler, damageHandler);
		mapInitiator = new MapInitiator(gameProp.getMapDim(), buildingHandler, structureHandler, heroHandler, map, buildings, structures, heroPortrait);
		routeChecker = new RouteChecker(gameProp.getMapDim(), heroHandler, map, movementMap, moveabilityMatrix, areaChecker, movementCostCalculator);

		// required init from third init-round
		containerUnitHandler = new ContUnitHandler(gameProp, map, cursor, unitGetter, areaChecker, routeChecker); 
		attackRangeHandler = new AttackRangeHandler(gameProp.getMapDim(), unitGetter, damageHandler, routeChecker, movementMap);

		// required init from fourth init-round
		mainViewPainter = new ViewPainter(commanderView, heroHandler, gameProp.getMapDim(), map, routeHandler, attackRangeHandler, buildingHandler, structureHandler);
		unitMenuHandler = new UnitMenuHandler(gameProp, containerUnitHandler, supplyHandler, unitGetter, unitPositionChecker, areaChecker, buildingHandler, attackRangeHandler);
		attackHandler = new AttackHandler(gameProp.getMapDim(), unitGetter, attackRangeHandler, damageHandler);

		// required init from fifth init-round
		keyListenerInputHandler = new KeyListenerInputHandler(gameProp, map, mainViewPainter, unitGetter, buildingHandler, structureHandler, cursor, unitMenuHandler, mapMenu, buildingMenu, containerUnitHandler, attackHandler, attackRangeHandler, movementMap, routeHandler, routeChecker, damageHandler, heroHandler, supplyHandler, turnHandler);		
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
	
	public MapInitiator getMapInitiator() {
		return mapInitiator;
	}
	
	public MapMenu getMapMenu() {
		return mapMenu;
	}
	
	public ViewPainter getMainViewPainter() {
		return mainViewPainter;
	}
		
	public RouteHandler getRouteHandler() {
		return routeHandler;
	}

	public TurnHandler getTurnHandler() {
		return turnHandler;
	}
	
	public UnitMenuHandler getUnitMenuHandler() {
		return unitMenuHandler;
	}
}