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
import gameObjects.GameProperties;
import gameObjects.MapDim;
import hero.HeroFactory;
import map.GameMap;
import map.MapLoader;
import map.MapLoadingObject;
import map.UnitGetter;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
import menus.unit.UnitMenu;
import point.Point;
import unitUtils.ContUnitHandler;
import unitUtils.UnitWorthCalculator;
import units.Unit;

public class Gameboard extends JPanel implements KeyListener {
	private InternalStructureObject internalStructureObject;
	private GameProperties gameProperties;
	private final int tileSize = 40;
	private final int fuelMaintenancePerTurn = 5;
	
	public Gameboard() {
		HeroHandler heroHandler = new HeroHandler();
		HeroFactory heroFactory = new HeroFactory();
		heroHandler.addHero(heroFactory.createHero(0));
		heroHandler.addHero(heroFactory.createHero(1));
		heroHandler.selectStartHero();
		
		MapLoader mapLoader = new MapLoader(tileSize, heroHandler);
		MapLoadingObject mapLoadingObject = mapLoader.loadMap("map-files/test_map.txt");
		GameMap gameMap = mapLoadingObject.getGameMap();
		MapDim mapDim = mapLoadingObject.getMapDim();
		ArrayList<Building> buildings = mapLoadingObject.getBuildingList();
		ArrayList<Structure> structures = mapLoadingObject.getStructureList();
		gameProperties = new GameProperties(fuelMaintenancePerTurn, mapDim, new ChosenObject());

		Point point = new Point(0, gameMap.getTileHeight() * tileSize);
		Cursor cursor = new Cursor(0, 0, tileSize);
		UnitGetter unitGetter = new UnitGetter(heroHandler);
		BuildingHandler buildingHandler = new BuildingHandler(heroHandler, buildings);
		StructureAttackHandler structureAttackHandler = new StructureAttackHandler(mapDim, unitGetter);
		UnitWorthCalculator unitWorthCalculator = new UnitWorthCalculator();
		StructureHandler structureHandler = new StructureHandler(structures, structureAttackHandler, unitWorthCalculator);
		InfoBox infoBox = new InfoBox(point, mapDim.getTileWidth() * tileSize, 3 * tileSize, tileSize, gameMap, 
										cursor, unitGetter, buildingHandler, structureHandler);
		internalStructureObject = new InternalStructureObject(gameProperties, infoBox, heroHandler, gameMap, 
												cursor, buildings, structures, unitGetter, 
												structureAttackHandler, unitWorthCalculator, 
												buildingHandler, structureHandler); 
		addKeyListener(this);
		init();
	}
	
	private void init() {
		Building.init(1000);
		internalStructureObject.getTurnHandler().startTurnActions();

		updatePortraitSideChoice();
		repaint();
	}
	
	private void updatePortraitSideChoice() {
		Cursor cursor = internalStructureObject.getCursor();
		internalStructureObject.getHeroPortrait().updateSideChoice(cursor);
	}
	
	public int getBoardWidth() {
		return internalStructureObject.getGameMap().getTileWidth() * gameProperties.getMapDim().tileSize;
	}

	public int getBoardHeight() {
		return internalStructureObject.getGameMap().getTileHeight() * gameProperties.getMapDim().tileSize
				+ internalStructureObject.getInfoBox().getHeight();
	}

	/**
	 * KeyListener-methods
	 */
	public void keyPressed(KeyEvent e) {
		internalStructureObject.getKeyListenerInputHandler().manageKeyPressedInput(e);
		updatePortraitSideChoice();
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		internalStructureObject.getKeyListenerInputHandler().manageKeyReleasedInput(e);
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
		internalStructureObject.getHeroPortrait().paint(g);
		internalStructureObject.getInfoBox().paint(g);
	}
	
	private void paintMenusAndCursors(Graphics g) {
		UnitMenu unitMenu = internalStructureObject.getUnitMenuHandler().getUnitMenu();
		AttackHandler attackHandler = internalStructureObject.getAttackHandler();
		Unit chosenUnit = gameProperties.getChosenObject().chosenUnit;
		Cursor cursor = internalStructureObject.getCursor();
		// when the mapMenu is open the cursor is hidden
		if (internalStructureObject.getMapMenu().isVisible()) {
			internalStructureObject.getMapMenu().paint(g);
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