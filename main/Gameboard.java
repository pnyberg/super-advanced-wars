/**
 * Refactor-bugs
 *  - "missing arrows for movement"
 * 
 * TODO-list
 * - only one action/unit per turn
 * - change POWER/SUPER-text, maybe use a pre-written text?
 * - add so that MiniCannons can be attacked/destroyed
 *   - indirects fixed, need to fix directs
 * - fix so that the firing-damage-window doesn't go off screen when attacking top row or leftmost column
 * - why does the tanks movementmap-hang up?
 * - enter classes for HQ, ev Silo
 * - FOG
 * - removed recalculating route which also removes movement-control within accepted area (infantrys may take more than three steps)
 *   - infantry may go over two mountains (very bad)
 * - fix so not crashing on recalculating route
 * - first attack take ages to calculate
 * - refactor so that methods have "maximum" of three parameters
 * - can an APC enter a Lander?
 * - can a T-Copter enter a Cruiser?
 *
 * @TODO: substitute ArrayList with HashMap for better performance
 */
package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import cursors.Cursor;
import cursors.FiringCursor;
import gameObjects.GameMapAndCursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.GraphicMetrics;
import gameObjects.MapDimension;
import graphics.ViewPainter;
import hero.HeroFactory;
import hero.HeroPortrait;
import map.GameMap;
import map.MapLoader;
import map.MapLoadingObject;
import map.buildings.Building;
import map.structures.Structure;
import menus.building.BuildingMenu;
import menus.map.MapMenu;
import menus.unit.UnitMenu;
import routing.MovementCostCalculator;
import routing.RouteHandler;
import unitUtils.ContUnitHandler;
import units.Unit;

public class Gameboard extends JPanel implements KeyListener {
	private GameState gameState;
	private GameProperties gameProperties;
	private GameMap gameMap;
	private MapDimension mapDimension;
	private InfoBox infoBox;
	private MapMenu mapMenu;
	private UnitMenu unitMenu;
	private BuildingMenu buildingMenu;
	private HeroPortrait heroPortrait;
	private FiringCursor firingCursor;
	private ViewPainter mainViewPainter;
	private AttackHandler attackHandler;
	private TurnHandler turnHandler;
	private ContUnitHandler contUnitHandler;
	private RouteHandler routeHandler;
	private AttackRangeHandler attackRangeHandler;
	private KeyListenerInputHandler keyListenerInputHandler;

	public Gameboard(int tileSize) {
		gameState = new GameState(tileSize);
		
		// Init heroes
		HeroHandler heroHandler = gameState.getHeroHandler();
		HeroFactory heroFactory = new HeroFactory();
		heroHandler.addHero(heroFactory.createHero(0));
		heroHandler.addHero(heroFactory.createHero(1));
		heroHandler.selectStartHero();
		
		// Load map
		MapLoader mapLoader = new MapLoader();
		MapLoadingObject mapLoadingObject = mapLoader.loadMap("map-files/test_map.txt", heroHandler, tileSize);
		gameMap = mapLoadingObject.getGameMap();
		mapDimension = mapLoadingObject.getMapDimension();
		ArrayList<Building> buildings = mapLoadingObject.getBuildingList();
		gameState.addBuildings(buildings);
		ArrayList<Structure> structures = mapLoadingObject.getStructureList();
		gameState.addStructures(structures);
		gameState.setMovementMap(mapLoadingObject.getMovementMap());
		gameState.initRangeMap(mapDimension);
		
		// Game-properties
		gameProperties = new GameProperties(mapDimension, gameMap);

		// Create graphics 
		infoBox = new InfoBox(gameProperties, gameState);
		mapMenu = new MapMenu(tileSize, gameState);
		heroPortrait = new HeroPortrait(mapDimension, gameState);
		firingCursor = new FiringCursor(gameProperties, gameState);
		unitMenu = new UnitMenu(gameProperties.getTileSize(), gameState);
		buildingMenu = new BuildingMenu(gameProperties.getTileSize(), gameState, gameMap);
		mainViewPainter = new ViewPainter(gameProperties, gameState);

		// Create handlers
		attackHandler = new AttackHandler(gameProperties, gameState);
		turnHandler = new TurnHandler(gameProperties, gameState);
		contUnitHandler = new ContUnitHandler(gameProperties, gameState);
		routeHandler = new RouteHandler(gameProperties, gameState);
		attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);

		keyListenerInputHandler = new KeyListenerInputHandler(gameProperties, 
											gameState,
											mapMenu,
											turnHandler);
		addKeyListener(this);
		init();
	}
	
	private void init() {
		Building.init(gameProperties.initialMoneyPerBuilding);
		turnHandler.startTurnActions(); // setup
		updatePortraitSideChoice();
		repaint();
	}
	
	private void updatePortraitSideChoice() {
		Cursor cursor = gameState.getCursor();
		heroPortrait.updateSideChoice(cursor);
	}
	
	public int getBoardWidth() {
		return gameMap.getTileWidth() * mapDimension.tileSize;
	}

	public int getBoardHeight() {
		return gameMap.getTileHeight() * mapDimension.tileSize + infoBox.getHeight();
	}

	/**
	 * KeyListener-methods
	 */
	public void keyPressed(KeyEvent e) {
		keyListenerInputHandler.manageKeyPressedInput(e);
		updatePortraitSideChoice();
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		keyListenerInputHandler.manageKeyReleasedInput(e);
		repaint();
	}

	public void keyTyped(KeyEvent e) {}

	/**
	 * Paint-methods
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Unit chosenUnit = gameState.getChosenObject().chosenUnit;
		mainViewPainter.paint(g);
		if (chosenUnit != null) {
			if (!unitMenu.isVisible() && !contUnitHandler.unitIsDroppingOff() 
									&& !attackHandler.unitWantsToFire(chosenUnit)) {
				routeHandler.getRouteArrowPath().paintArrow(g);
			}

			chosenUnit.paint(g, mapDimension.tileSize);
		}
		attackRangeHandler.paintRange(g);
		mainViewPainter.paintUnits(g, chosenUnit);
		paintMenusAndCursors(g);
		heroPortrait.paint(g);
		infoBox.paint(g);
	}
	
	private void paintMenusAndCursors(Graphics g) {
		Unit chosenUnit = gameState.getChosenObject().chosenUnit;
		Cursor cursor = gameState.getCursor();
		// when the mapMenu is open the cursor is hidden
		if (mapMenu.isVisible()) {
			mapMenu.paint(g);
		} else if (unitMenu.isVisible()) {
			unitMenu.paint(g);
		} else if (buildingMenu.isVisible()) {
			buildingMenu.paint(g);
		} else if (attackHandler.unitWantsToFire(chosenUnit)) {
			firingCursor.paint(g, cursor, chosenUnit);
		} else {
			gameState.getCursor().paint(g);
		}
	}
}