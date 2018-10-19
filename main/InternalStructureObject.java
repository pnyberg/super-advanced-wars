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
import map.GameMap;
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
	private MapInitiator mapInitiator;
	private MapMenu mapMenu;
	private ViewPainter mainViewPainter;
	private boolean[][] moveabilityMatrix;
	private MovementCostCalculator movementCostCalculator;
	private MovementMap movementMap;
	private RepairHandler repairHandler;
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
	
	public InternalStructureObject(GameProp gameProp, HeroHandler heroHandler, GameMap gameMap, ArrayList<Building> buildings, ArrayList<Structure> structures) {
		// no previously required init
		attackValueCalculator = new AttackValueCalculator();
		cursor = new Cursor(0, 0, gameProp.getMapDim().tileSize);
		defenceValueCalculator = new DefenceValueCalculator();
		heroPortrait = new HeroPortrait(gameProp.getMapDim(), heroHandler);
		mapMenu = new MapMenu(gameProp.getMapDim().tileSize, heroHandler);
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
		movementMap = new MovementMap(gameProp.getMapDim());
		unitGetter = new UnitGetter(heroHandler, gameProp.getMapDim().tileSize);
		unitWorthCalculator = new UnitWorthCalculator();

		// required init from first init-round
		areaChecker = new AreaChecker(heroHandler, unitGetter, gameMap, moveabilityMatrix);
		buildingHandler = new BuildingHandler(heroHandler, buildings);
		buildingMenu = new BuildingMenu(gameProp.getMapDim().tileSize, heroHandler, gameMap);
		cashHandler = new CashHandler(heroPortrait, buildings);
		commanderView = new CommanderView(gameProp.getMapDim(), heroHandler, attackValueCalculator, defenceValueCalculator);
		damageHandler = new DamageHandler(heroHandler, gameMap, attackValueCalculator, defenceValueCalculator, unitWorthCalculator);
		movementCostCalculator = new MovementCostCalculator(gameMap);
		structureAttackHandler = new StructureAttackHandler(gameProp.getMapDim(), unitGetter);
		supplyHandler = new SupplyHandler(unitGetter);
		unitPositionChecker = new UnitPositionChecker(unitGetter);

		// required init from second init-round
		firingCursor = new FiringCursor(gameProp.getMapDim(), gameMap, unitGetter, heroHandler, damageHandler);
		repairHandler = new RepairHandler(gameProp.getMapDim(), heroHandler, buildingHandler, unitWorthCalculator);
		routeChecker = new RouteChecker(gameProp.getMapDim(), heroHandler, gameMap, movementMap, moveabilityMatrix, areaChecker, movementCostCalculator);
		routeHandler = new RouteHandler(gameProp.getMapDim(), movementMap, movementCostCalculator);
		structureHandler = new StructureHandler(structures, structureAttackHandler, unitWorthCalculator);

		// required init from third init-round
		attackRangeHandler = new AttackRangeHandler(gameProp.getMapDim(), unitGetter, damageHandler, routeChecker, movementMap);
		containerUnitHandler = new ContUnitHandler(gameProp, gameMap, cursor, unitGetter, areaChecker, routeChecker); 
		//mapInitiator = new MapInitiator(gameProp.getMapDim(), buildingHandler, structureHandler, heroHandler, gameMap, buildings, structures);
		turnHandler = new TurnHandler(gameProp, cashHandler, repairHandler, heroHandler, structureHandler, mapMenu);

		// required init from fourth init-round
		attackHandler = new AttackHandler(gameProp.getMapDim(), unitGetter, attackRangeHandler, damageHandler);
		mainViewPainter = new ViewPainter(commanderView, heroHandler, gameProp.getMapDim(), gameMap, routeHandler, attackRangeHandler, buildingHandler, structureHandler);
		unitMenuHandler = new UnitMenuHandler(gameProp, containerUnitHandler, supplyHandler, unitGetter, unitPositionChecker, areaChecker, buildingHandler, attackRangeHandler);

		// required init from fifth init-round
		keyListenerInputHandler = new KeyListenerInputHandler(gameProp, gameMap, mainViewPainter, unitGetter, buildingHandler, structureHandler, cursor, unitMenuHandler, mapMenu, buildingMenu, containerUnitHandler, attackHandler, attackRangeHandler, movementMap, routeHandler, routeChecker, damageHandler, heroHandler, supplyHandler, turnHandler, unitWorthCalculator);
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
	
	public MapMenu getMapMenu() {
		return mapMenu;
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

	public ViewPainter getMainViewPainter() {
		return mainViewPainter;
	}		
}