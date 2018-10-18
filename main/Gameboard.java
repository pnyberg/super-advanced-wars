/**
 * TODO-list
 * - only one action/unit per turn
 * - change POWER/SUPER-text, maybe use a pre-written text?
 * - add so that MiniCannons can be attacked/destroyed
 * - why does the tanks movementmap-hang up?
 * - enter classes for HQ, ev Silo
 * - FOG
 * - removed recalculating route which also removes movement-control within accepted area (infantrys may take more than three steps)
 *   - infantry may go over two mountains (very bad)
 * - fix so not crashing on recalculating route
 * - first attack take ages to calculate
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
import cursors.Cursor;
import gameObjects.GameProp;
import map.GameMap;
import map.MapLoader;
import map.buildings.Building;
import map.structures.Structure;
import menus.unit.UnitMenu;
import units.ContUnitHandler;
import units.Unit;

public class Gameboard extends JPanel implements KeyListener {
	private InternalStructureObject internalStructureObject;
	private GameProp gameProp;
	
	public Gameboard(GameProp gameProp, HeroHandler heroHandler) {
		// TODO: while testing map-loader
		GameMap gameMap = new GameMap(0, 0);
		ArrayList<Building> buildings = new ArrayList<>();
		ArrayList<Structure> structures = new ArrayList<>();
		MapLoader mapLoader = new MapLoader(gameProp.getMapDim(), gameMap, heroHandler, buildings, structures);
		mapLoader.loadMap("map-files/test_map.txt");
		internalStructureObject = new InternalStructureObject(gameProp, heroHandler, gameMap, buildings, structures); 
		this.gameProp = gameProp;
		
		addKeyListener(this);
		init();
	}
	
	private void init() {
		//internalStructureObject.getMapInitiator();
		internalStructureObject.getTurnHandler().startTurnActions();

		updatePortraitSideChoice();
		repaint();
	}
	
	private void updatePortraitSideChoice() {
		Cursor cursor = internalStructureObject.getCursor();
		internalStructureObject.getHeroPortrait().updateSideChoice(cursor.getX(), cursor.getY());
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
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		Cursor cursor = internalStructureObject.getCursor();
		UnitMenu unitMenu = internalStructureObject.getUnitMenuHandler().getUnitMenu();
		ContUnitHandler contUnitHandler = internalStructureObject.getContUnitHandler();

		internalStructureObject.getMainViewPainter().paint(g);
		if (chosenUnit != null) {
			if (!unitMenu.isVisible() && !contUnitHandler.unitIsDroppingOff() 
									&& !attackHandler.unitWantsToFire(chosenUnit)) {
				internalStructureObject.getRouteHandler().getRouteArrowPath().paintArrow(g);
			}

			chosenUnit.paint(g, gameProp.getMapDim().tileSize);
		}

		internalStructureObject.getAttackRangeHandler().paintRange(g);
		internalStructureObject.getMainViewPainter().paintUnits(g, chosenUnit);

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
		internalStructureObject.getHeroPortrait().paint(g);
	}
}