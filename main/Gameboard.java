/**
 * Refactor-bug
 *  - HeroPortrait always in upper left corner
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
import map.GameLoader;
import map.GameLoadingObject;
import map.buildings.Building;
import map.structures.Structure;
import menus.building.BuildingMenu;
import menus.map.MapMenu;
import menus.unit.UnitMenu;
import routing.MovementCostCalculator;
import routing.RouteArrowPath;
import routing.RouteHandler;
import unitUtils.ContUnitHandler;
import units.Unit;

public class Gameboard extends JPanel implements KeyListener {
	private GameState gameState;
	private GameProperties gameProperties;
	private KeyListenerInputHandler keyListenerInputHandler;

	public Gameboard(int tileSize) {
		// Init heroes
		HeroHandler heroHandler = new HeroHandler();
		HeroFactory heroFactory = new HeroFactory();
		heroHandler.addHero(heroFactory.createHero(0));
		heroHandler.addHero(heroFactory.createHero(1));
		heroHandler.selectStartHero();
		
		// Load game
		GameLoader gameLoader = new GameLoader(heroHandler, tileSize);
		GameLoadingObject gameLoadingObject = gameLoader.loadMap("map-files/test_map.txt");
		gameProperties = gameLoadingObject.getGameProperties();
		gameState = gameLoadingObject.getInitalGameState();
		
		keyListenerInputHandler = new KeyListenerInputHandler(gameProperties, gameState);
		addKeyListener(this);
		init();
	}
	
	private void init() {
		Building.init(gameProperties.initialMoneyPerBuilding);
		TurnHandler turnHandler = new TurnHandler(gameProperties, gameState);
		turnHandler.startTurnActions(); // setup
		updatePortraitSideChoice();
		repaint();
	}
	
	private void updatePortraitSideChoice() {
		Cursor cursor = gameState.getCursor();
		HeroPortrait heroPortrait = new HeroPortrait(gameProperties.getMapDimension(), gameState);
		heroPortrait.updateSideChoice(cursor);
	}
	
	public int getBoardWidth() {
		int mapTileWidth = gameProperties.getGameMap().getTileWidth();
		return mapTileWidth * gameProperties.getTileSize();
	}

	public int getBoardHeight() {
		int mapTileHeight = gameProperties.getGameMap().getTileHeight();
		int infoBoxHeight = gameProperties.getInfoBoxGraphicMetrics().height;
		return mapTileHeight * gameProperties.getTileSize() + infoBoxHeight;
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
		ViewPainter mainViewPainter = new ViewPainter(gameProperties, gameState);
		Unit chosenUnit = gameState.getChosenObject().chosenUnit;
		mainViewPainter.paint(g);
		if (chosenUnit != null) {
			UnitMenu unitMenu = new UnitMenu(gameProperties.getTileSize(), gameState);
			ContUnitHandler contUnitHandler = new ContUnitHandler(gameProperties, gameState);
			AttackHandler attackHandler = new AttackHandler(gameProperties, gameState);
			if (!unitMenu.isVisible() && !contUnitHandler.unitIsDroppingOff() 
									&& !attackHandler.unitWantsToFire(chosenUnit)) {
				RouteArrowPath routeArrowPath = new RouteArrowPath(gameProperties, gameState);
				routeArrowPath.paintArrow(g);
			}

			chosenUnit.paint(g, gameProperties.getTileSize());
		}
		AttackRangeHandler attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		InfoBox infoBox = new InfoBox(gameProperties, gameState);
		HeroPortrait heroPortrait = new HeroPortrait(gameProperties.getMapDimension(), gameState);
		attackRangeHandler.paintRange(g);
		mainViewPainter.paintUnits(g, chosenUnit);
		paintMenusAndCursors(g);
		heroPortrait.paint(g);
		infoBox.paint(g);
	}
	
	private void paintMenusAndCursors(Graphics g) {
		MapMenu mapMenu = new MapMenu(gameProperties.getTileSize(), gameState);
		UnitMenu unitMenu = new UnitMenu(gameProperties.getTileSize(), gameState);
		BuildingMenu buildingMenu = new BuildingMenu(gameProperties, gameState);
		AttackHandler attackHandler = new AttackHandler(gameProperties, gameState);
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
			FiringCursor firingCursor = new FiringCursor(gameProperties, gameState);
			firingCursor.paint(g, cursor, chosenUnit);
		} else {
			gameState.getCursor().paint(g);
		}
	}
}