/**
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
import combat.StructureAttackHandler;
import cursors.Cursor;
import gameObjects.ChosenObject;
import gameObjects.GameMapAndCursor;
import gameObjects.GameProperties;
import gameObjects.GraphicMetrics;
import gameObjects.MapDimension;
import hero.HeroFactory;
import hero.HeroPortrait;
import map.BuildingStructureHandlerObject;
import map.GameMap;
import map.MapLoader;
import map.MapLoadingObject;
import map.UnitGetter;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
import menus.map.MapMenu;
import menus.unit.UnitMenu;
import point.Point;
import unitUtils.ContUnitHandler;
import units.Unit;

public class Gameboard extends JPanel implements KeyListener {
	private final int tileSize = 40;
	private final int fuelMaintenancePerTurn = 5;
	private final int initialMoneyPerBuilding = 1000;

	private GameMap gameMap;
	private InternalStructureObject internalStructureObject;
	private MapMenu mapMenu;
	private HeroPortrait heroPortrait;
	private TurnHandler turnHandler;
	private KeyListenerInputHandler keyListenerInputHandler;
	private GameProperties gameProperties;

	public Gameboard() {
		HeroHandler heroHandler = new HeroHandler();
		HeroFactory heroFactory = new HeroFactory();
		heroHandler.addHero(heroFactory.createHero(0));
		heroHandler.addHero(heroFactory.createHero(1));
		heroHandler.selectStartHero();
		
		MapLoader mapLoader = new MapLoader(tileSize, heroHandler);
		MapLoadingObject mapLoadingObject = mapLoader.loadMap("map-files/test_map.txt");
		gameMap = mapLoadingObject.getGameMap();
		MapDimension mapDimension = mapLoadingObject.getMapDimension();
		ArrayList<Building> buildings = mapLoadingObject.getBuildingList();
		ArrayList<Structure> structures = mapLoadingObject.getStructureList();
		gameProperties = new GameProperties(fuelMaintenancePerTurn, mapDimension, new ChosenObject());

		Point point = new Point(0, gameMap.getTileHeight() * tileSize);
		Cursor cursor = new Cursor(0, 0, tileSize);
		UnitGetter unitGetter = new UnitGetter(heroHandler);
		BuildingHandler buildingHandler = new BuildingHandler(heroHandler, buildings);
		StructureAttackHandler structureAttackHandler = new StructureAttackHandler(mapDimension, unitGetter);
		StructureHandler structureHandler = new StructureHandler(structures, structureAttackHandler);
		BuildingStructureHandlerObject buildingStructureHandlerObject = new BuildingStructureHandlerObject(buildingHandler, structureHandler);
		GraphicMetrics infoBoxGraphicMetrics = new GraphicMetrics(point, mapDimension.getTileWidth() * tileSize, 3 * tileSize, tileSize);
		GameMapAndCursor gameMapAndCursor = new GameMapAndCursor(gameMap, cursor);
		InfoBox infoBox = new InfoBox(infoBoxGraphicMetrics, gameMapAndCursor, unitGetter, buildingStructureHandlerObject);
		internalStructureObject = new InternalStructureObject(gameProperties, infoBox, heroHandler, gameMapAndCursor, buildings, structures, unitGetter, 
												buildingStructureHandlerObject);
		mapMenu = new MapMenu(tileSize, heroHandler);
		heroPortrait = new HeroPortrait(mapDimension, heroHandler);
		turnHandler = new TurnHandler(fuelMaintenancePerTurn, heroHandler, buildingStructureHandlerObject);
		keyListenerInputHandler = new KeyListenerInputHandler(gameProperties, 
											gameMapAndCursor, 
											internalStructureObject.getMainViewPainter(), 
											unitGetter, 
											buildingStructureHandlerObject, 
											internalStructureObject.getUnitMenuHandler(), 
											mapMenu,
											internalStructureObject.getBuildingMenu(), 
											internalStructureObject.getContUnitHandler(), 
											internalStructureObject.getAttackHandler(), 
											internalStructureObject.getAttackRangeHandler(), 
											internalStructureObject.getMovementMap(), 
											internalStructureObject.getRouteHandler(), 
											internalStructureObject.getRouteChecker(), 
											internalStructureObject.getDamageHandler(), 
											heroHandler, 
											internalStructureObject.getSupplyHandler(), 
											turnHandler);
		addKeyListener(this);
		init();
	}
	
	private void init() {
		Building.init(initialMoneyPerBuilding);
		turnHandler.startTurnActions(); // setup
		updatePortraitSideChoice();
		repaint();
	}
	
	private void updatePortraitSideChoice() {
		Cursor cursor = internalStructureObject.getCursor();
		heroPortrait.updateSideChoice(cursor);
	}
	
	public int getBoardWidth() {
		return gameMap.getTileWidth() * gameProperties.getMapDim().tileSize;
	}

	public int getBoardHeight() {
		return gameMap.getTileHeight() * gameProperties.getMapDim().tileSize
				+ internalStructureObject.getInfoBox().getHeight();
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
		AttackHandler attackHandler = internalStructureObject.getAttackHandler();
		Unit chosenUnit = gameProperties.getChosenObject().chosenUnit;
		UnitMenu unitMenu = internalStructureObject.getUnitMenuHandler().getUnitMenu();
		ContUnitHandler contUnitHandler = internalStructureObject.getContUnitHandler();
		internalStructureObject.getMainViewPainter().paint(g);
		if (chosenUnit != null) {
			if (!unitMenu.isVisible() && !contUnitHandler.unitIsDroppingOff() 
									&& !attackHandler.unitWantsToFire(chosenUnit)) {
				internalStructureObject.getRouteHandler().getRouteArrowPath().paintArrow(g);
			}

			chosenUnit.paint(g, gameProperties.getMapDim().tileSize);
		}
		internalStructureObject.getAttackRangeHandler().paintRange(g);
		internalStructureObject.getMainViewPainter().paintUnits(g, chosenUnit);
		paintMenusAndCursors(g);
		heroPortrait.paint(g);
		internalStructureObject.getInfoBox().paint(g);
	}
	
	private void paintMenusAndCursors(Graphics g) {
		UnitMenu unitMenu = internalStructureObject.getUnitMenuHandler().getUnitMenu();
		AttackHandler attackHandler = internalStructureObject.getAttackHandler();
		Unit chosenUnit = gameProperties.getChosenObject().chosenUnit;
		Cursor cursor = internalStructureObject.getCursor();
		// when the mapMenu is open the cursor is hidden
		if (mapMenu.isVisible()) {
			mapMenu.paint(g);
		} else if (unitMenu.isVisible()) {
			unitMenu.paint(g);
		} else if (internalStructureObject.getBuildingMenu().isVisible()) {
			internalStructureObject.getBuildingMenu().paint(g);
		} else if (attackHandler.unitWantsToFire(chosenUnit)) {
			internalStructureObject.getFiringCursor().paint(g, cursor, chosenUnit);
		} else {
			internalStructureObject.getCursor().paint(g);
		}
	}
}