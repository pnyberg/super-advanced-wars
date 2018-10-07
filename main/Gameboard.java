package main;
/**
 * TODO-list
 * - capting
 * - only one action/unit per turn
 * - enter classes for HQ, ev Silo
 * - FOG
 * - hero-abilities and powers
 * - removed recalculating route which also removes movement-control within accepted area (infantrys may take more than three steps)
 *   - infantry may go over two mountains (very bad)
 * - fix so not crashing on recalculating route
 * - first attack take ages to calculate
 *
 * @TODO: substitute ArrayList with HashMap for better performance
 */
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import combat.AttackHandler;
import cursors.Cursor;
import gameObjects.GameProp;
import gameObjects.MapDim;
import menus.unit.UnitMenu;
import units.ContUnitHandler;
import units.Unit;

public class Gameboard extends JPanel implements KeyListener {
	private InternalStructureObject internalStructureObject;
	private MapDim mapDim;
	
	public Gameboard(MapDim mapDim, HeroHandler heroHandler, GameProp gameProp) {
		internalStructureObject = new InternalStructureObject(mapDim, heroHandler, gameProp); 
		this.mapDim = mapDim;
		
		addKeyListener(this);
		init();
	}
	
	private void init() {
		int mapIndex = 0;
		internalStructureObject.getMapInitiator().loadMap(mapIndex);
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
		Unit chosenUnit = internalStructureObject.getChosenObject().chosenUnit;
		Cursor cursor = internalStructureObject.getCursor();
		UnitMenu unitMenu = internalStructureObject.getUnitMenu();
		ContUnitHandler contUnitHandler = internalStructureObject.getContUnitHandler();

		internalStructureObject.getMapPainter().paintMap(g);
		if (chosenUnit != null) {
			if (!unitMenu.isVisible() && !contUnitHandler.unitIsDroppingOff() 
									&& !attackHandler.unitWantsToFire(chosenUnit)) {
				internalStructureObject.getRouteHandler().getRouteArrowPath().paintArrow(g);
			}

			chosenUnit.paint(g, mapDim.tileSize);
		}

		internalStructureObject.getAttackRangeHandler().paintRange(g);
		internalStructureObject.getMapPainter().paintUnits(g, chosenUnit);

		// when the mapMenu is open the cursor is hidden
		if (internalStructureObject.getMapMenu().isVisible()) {
			internalStructureObject.getMapMenu().paint(g);
		} else if (internalStructureObject.getUnitMenu().isVisible()) {
			internalStructureObject.getUnitMenu().paint(g);
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