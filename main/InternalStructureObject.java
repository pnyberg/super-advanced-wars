package main;

import java.util.ArrayList;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.AttackValueCalculator;
import combat.DamageHandler;
import combat.DefenceValueCalculator;
import cursors.Cursor;
import cursors.FiringCursor;
import gameObjects.GameProperties;
import graphics.CommanderView;
import graphics.ViewPainter;
import hero.HeroPortrait;
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
	private CashHandler cashHandler;
	private CommanderView commanderView;
	private ContUnitHandler containerUnitHandler;
	private Cursor cursor;
	private DamageHandler damageHandler;
	private DefenceValueCalculator defenceValueCalculator;
	private FiringCursor firingCursor;
	private GameMap gameMap;
	private HeroPortrait heroPortrait;
	private InfoBox infoBox;
	private ViewPainter mainViewPainter;
	private boolean[][] moveabilityMatrix;
	private MovementCostCalculator movementCostCalculator;
	private MovementMap movementMap;
	private RepairHandler repairHandler;
	private RouteChecker routeChecker;
	private RouteHandler routeHandler;
	private SupplyHandler supplyHandler;
	private UnitMenuHandler unitMenuHandler;
	
	// TODO: rewrite with fewer parameters
	// TODO: rewrite the code to minimize decoupling
	public InternalStructureObject(GameProperties gameProp, InfoBox infoBox, HeroHandler heroHandler, 
									GameMap gameMap, Cursor cursor, ArrayList<Building> buildings, 
									ArrayList<Structure> structures, UnitGetter unitGetter, 
									UnitWorthCalculator unitWorthCalculator, 
									BuildingHandler buildingHandler,
									StructureHandler structureHandler) {
		int tileSize = gameProp.getMapDim().tileSize;
		
		// no previously required init
		attackValueCalculator = new AttackValueCalculator();
		this.cursor = cursor;
		defenceValueCalculator = new DefenceValueCalculator();
		this.gameMap = gameMap;
		heroPortrait = new HeroPortrait(gameProp.getMapDim(), heroHandler);
		this.infoBox = infoBox;
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
		movementMap = new MovementMap(gameProp.getMapDim());

		// required init from first init-round
		areaChecker = new AreaChecker(heroHandler, unitGetter, gameMap, moveabilityMatrix);
		buildingMenu = new BuildingMenu(tileSize, heroHandler, gameMap);
		cashHandler = new CashHandler(heroPortrait, buildings);
		commanderView = new CommanderView(gameProp.getMapDim(), heroHandler, attackValueCalculator, defenceValueCalculator);
		damageHandler = new DamageHandler(heroHandler, gameMap, attackValueCalculator, defenceValueCalculator, unitWorthCalculator);
		movementCostCalculator = new MovementCostCalculator(gameMap);
		supplyHandler = new SupplyHandler(unitGetter, tileSize);

		// required init from second init-round
		repairHandler = new RepairHandler(heroHandler, buildingHandler, unitWorthCalculator);
		routeChecker = new RouteChecker(gameProp.getMapDim(), heroHandler, gameMap, movementMap, moveabilityMatrix, areaChecker, movementCostCalculator);
		routeHandler = new RouteHandler(gameProp.getMapDim(), movementMap, movementCostCalculator);

		// required init from third init-round
		attackRangeHandler = new AttackRangeHandler(gameProp.getMapDim(), unitGetter, damageHandler, structureHandler, routeChecker, movementMap);
		containerUnitHandler = new ContUnitHandler(gameProp, gameMap, cursor, unitGetter, areaChecker, routeChecker); 
		firingCursor = new FiringCursor(gameProp.getMapDim(), unitGetter, heroHandler, damageHandler, structureHandler);

		// required init from fourth init-round
		attackHandler = new AttackHandler(gameProp.getMapDim(), unitGetter, attackRangeHandler, damageHandler, structureHandler);
		mainViewPainter = new ViewPainter(commanderView, heroHandler, gameProp.getMapDim(), gameMap, routeHandler, attackRangeHandler, buildingHandler, structureHandler);
		unitMenuHandler = new UnitMenuHandler(gameProp, containerUnitHandler, supplyHandler, unitGetter, areaChecker, buildingHandler, attackRangeHandler);
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
	
	public CashHandler getCashHandler() {
		return cashHandler;
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
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public HeroPortrait getHeroPortrait() {
		return heroPortrait;
	}
	
	public InfoBox getInfoBox() {
		return infoBox;
	}
	
	public MovementMap getMovementMap() {
		return movementMap;
	}
	
	public RepairHandler getRepairHandler() {
		return repairHandler;
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