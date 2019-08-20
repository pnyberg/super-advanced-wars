package main;

import java.util.ArrayList;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.AttackValueCalculator;
import combat.DamageHandler;
import combat.DefenceValueCalculator;
import cursors.Cursor;
import cursors.FiringCursor;
import gameObjects.GameMapAndCursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import graphics.CommanderView;
import graphics.ViewPainter;
import hero.HeroPortrait;
import map.BuildingStructureHandlerObject;
import map.GameMap;
import map.UnitGetter;
import map.area.AreaChecker;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
import menus.building.BuildingMenu;
import menus.unit.UnitMenuHandler;
import routing.MoveabilityMatrixFactory;
import routing.MovementCostCalculator;
import routing.MovementMap;
import routing.RouteChecker;
import routing.RouteHandler;
import unitUtils.ContUnitHandler;
import unitUtils.UnitWorthCalculator;

public class InternalStructureObject {
	private AreaChecker areaChecker;
	private AttackHandler attackHandler;
	private AttackRangeHandler attackRangeHandler;
	private AttackValueCalculator attackValueCalculator;
	private BuildingMenu buildingMenu;
	private CommanderView commanderView;
	private ContUnitHandler containerUnitHandler;
	private Cursor cursor;
	private DamageHandler damageHandler;
	private DefenceValueCalculator defenceValueCalculator;
	private FiringCursor firingCursor;
	private ViewPainter mainViewPainter;
	private boolean[][] moveabilityMatrix;
	private MovementCostCalculator movementCostCalculator;
	private MovementMap movementMap;
	private RouteChecker routeChecker;
	private RouteHandler routeHandler;
	private SupplyHandler supplyHandler;
	private UnitMenuHandler unitMenuHandler;
	
	// TODO: rewrite with fewer parameters
	// TODO: rewrite the code to minimize decoupling
	public InternalStructureObject(GameProperties gameProperties, GameState gameState) {
		int tileSize = gameProperties.getMapDimension().tileSize;
		HeroHandler heroHandler = gameState.getHeroHandler();
		
		// no previously required init
		attackValueCalculator = new AttackValueCalculator();
		this.cursor = gameState.getCursor();
		defenceValueCalculator = new DefenceValueCalculator();
		GameMap gameMap = gameProperties.getGameMap();
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
		movementMap = new MovementMap(gameProperties.getMapDimension());

		// required init from first init-round
		areaChecker = new AreaChecker(heroHandler, gameMap, moveabilityMatrix);
		buildingMenu = new BuildingMenu(tileSize, heroHandler, gameMap);
		commanderView = new CommanderView(gameProperties.getMapDimension(), heroHandler, attackValueCalculator, defenceValueCalculator);
		damageHandler = new DamageHandler(heroHandler, gameMap, attackValueCalculator, defenceValueCalculator);
		movementCostCalculator = new MovementCostCalculator(gameMap);
		supplyHandler = new SupplyHandler(gameState, tileSize);

		// required init from second init-round
		routeChecker = new RouteChecker(gameProperties.getMapDimension(), heroHandler, gameMap, movementMap, moveabilityMatrix, areaChecker, movementCostCalculator);
		routeHandler = new RouteHandler(gameProperties.getMapDimension(), movementMap, movementCostCalculator);

		// required init from third init-round
		attackRangeHandler = new AttackRangeHandler(gameProperties.getMapDimension(), gameState, damageHandler, routeChecker, movementMap);
		containerUnitHandler = new ContUnitHandler(gameProperties, gameState, gameMap, areaChecker, routeChecker); 
		firingCursor = new FiringCursor(gameProperties.getMapDimension(), gameState, damageHandler);

		// required init from fourth init-round
		attackHandler = new AttackHandler(gameProperties.getMapDimension(), gameState, attackRangeHandler, damageHandler);
		mainViewPainter = new ViewPainter(commanderView, gameProperties.getMapDimension(), gameState, gameMap, routeHandler, attackRangeHandler);
		unitMenuHandler = new UnitMenuHandler(gameProperties, gameState, containerUnitHandler, supplyHandler, areaChecker, attackRangeHandler);
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
	
	public DamageHandler getDamageHandler() {
		return damageHandler;
	}
	
	public FiringCursor getFiringCursor() {
		return firingCursor;
	}
	
	public MovementMap getMovementMap() {
		return movementMap;
	}
	
	public RouteChecker getRouteChecker() {
		return routeChecker;
	}

	public RouteHandler getRouteHandler() {
		return routeHandler;
	}
	
	public SupplyHandler getSupplyHandler() {
		return supplyHandler;
	}
	
	public UnitMenuHandler getUnitMenuHandler() {
		return unitMenuHandler;
	}

	public ViewPainter getMainViewPainter() {
		return mainViewPainter;
	}		
}