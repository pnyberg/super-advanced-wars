import point.*;
import units.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 * TODO-list
 * - capting
 * - enter classes for HQ, ev Silo
 * - creating units
 * - fuel and ammo
 * - FOG
 * - show possible damage before firing
 * - hero-abilities and powers
 * - removed recalculating route which also removes movement-control within accepted area (infantrys may take more than three steps)
 *   - infantry may go over two mountains (very bad)
 * - not crashing on recalculating route
 * - fix battle-simulations (not counterattack for indirect etc)
 * - fix join mechanic 
 *
 * @TODO: substitute ArrayList with HashMap for better performance
 */
public class Gameboard extends JPanel implements KeyListener {
	private boolean[][] rangeMap;

	private int mapWidth, mapHeight;

	private Cursor cursor;

	private MapMenu mapMenu;
	private UnitMenu unitMenu;
	private BuildingMenu buildingMenu;

	private Unit chosenUnit, rangeUnit;
	private Building selectedBuilding;

	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		rangeMap = new boolean[mapWidth][mapHeight];

		cursor = new Cursor(0, 0);

		mapMenu = new MapMenu(MapHandler.tileSize);
		unitMenu = new UnitMenu(MapHandler.tileSize);
		buildingMenu = new BuildingMenu(MapHandler.tileSize);

		chosenUnit = null;
		rangeUnit = null;

		addKeyListener(this);

		MapHandler.initMapHandler(mapWidth, mapHeight);
		RouteHandler.initMovementMap(mapWidth, mapHeight);
		DamageHandler.init();

		startTurnActions();

		MapHandler.updatePortraitSideChoice(cursor.getX(), cursor.getY());
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		boolean menuVisible = mapMenu.isVisible() || unitMenu.isVisible() || buildingMenu.isVisible();
		boolean unitSelected = chosenUnit != null || rangeUnit != null;

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (!unitIsntDroppingOff()) {
				if (unitCanBeDroppedOff()) {
					moveDroppingOffCursorCounterclockwise();
				}
			} else if (unitWantToFire()) {
				if (chosenUnit instanceof IndirectUnit) {
					Point p = ((IndirectUnit)chosenUnit).getPreviousFiringLocation();
					cursorX = p.getX();
					cursorY = p.getY();
					cursor.setPosition(cursorX, cursorY);
				} else {
					moveFiringCursorCounterclockwise();
				}
			} else if (cursorY > 0 && !menuVisible) {
				RouteHandler.addArrowPoint(cursorX, cursorY - 1, chosenUnit);
				cursor.moveUp();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowUp();
			} else if (unitMenu.isVisible()) {
				unitMenu.moveArrowUp();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowUp();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!unitIsntDroppingOff()) {
				if (unitCanBeDroppedOff()) {
					moveDroppingOffCursorClockwise();
				}
			} else if (unitWantToFire()) {
				if (chosenUnit instanceof IndirectUnit) {
					Point p = ((IndirectUnit)chosenUnit).getNextFiringLocation();
					cursorX = p.getX();
					cursorY = p.getY();
					cursor.setPosition(cursorX, cursorY);
				} else {
					moveFiringCursorClockwise();
				}
			} else if (cursorY < (mapHeight - 1) && !menuVisible) {
				RouteHandler.addArrowPoint(cursorX, cursorY + 1, chosenUnit);
				cursor.moveDown();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowDown();
			} else if (unitMenu.isVisible()) {
				unitMenu.moveArrowDown();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0 && !menuVisible && unitIsntDroppingOff() && !unitWantToFire()) {
				RouteHandler.addArrowPoint(cursorX - 1, cursorY, chosenUnit);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (mapWidth - 1) && !menuVisible && unitIsntDroppingOff() && !unitWantToFire()) {
				RouteHandler.addArrowPoint(cursorX + 1, cursorY, chosenUnit);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (!unitIsntDroppingOff()) {
				if (unitCanBeDroppedOff()) {
					if (chosenUnit instanceof APC) {
						((APC)chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((APC)chosenUnit).removeUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
					} else if (chosenUnit instanceof Lander) {
						((Lander)chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((Lander)chosenUnit).removeChosenUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
					}

					chosenUnit = null;
					RouteHandler.clearMovementMap();
					RouteHandler.clearArrowPoints();
				}
			} else if (unitWantToFire()) {
				Unit defendingUnit = MapHandler.getNonFriendlyUnit(cursorX, cursorY);
				DamageHandler.handleAttack(chosenUnit, defendingUnit);
				chosenUnit.regulateAttack(false);

				int x = chosenUnit.getX();
				int y = chosenUnit.getY();
				cursor.setPosition(x, y);

				removeUnitIfDead(defendingUnit);
				removeUnitIfDead(chosenUnit);

				chosenUnit = null;
				RouteHandler.clearMovementMap();
				RouteHandler.clearArrowPoints();
				if (chosenUnit instanceof IndirectUnit) {
					((IndirectUnit)chosenUnit).clearFiringLocations();
				}
			} else if (mapMenu.isVisible()) {
				if (mapMenu.atEndRow()) {
					endTurn();
				}
			} else if (unitMenu.isVisible()) {
				if (unitMenu.atUnitRow()) {
					if (chosenUnit instanceof Lander) {
						int index = unitMenu.getMenuIndex();
						((Lander)chosenUnit).chooseUnit(index);
					}
					handleDroppingOff();
				} else if (unitMenu.atFireRow()) {
					handleFiring();
				} else if (unitMenu.atEnterRow()) {
					Unit entryUnit = MapHandler.getFriendlyUnitExceptSelf(chosenUnit, cursorX, cursorY);
					if (entryUnit instanceof APC) {
						((APC)entryUnit).addUnit(chosenUnit);
					} else if (entryUnit instanceof Lander) {
						((Lander)entryUnit).addUnit(chosenUnit);
					}
					// @TODO unit enters other unit
				}

				if (unitIsntDroppingOff() && !unitWantToFire()) {
					chosenUnit = null;
					RouteHandler.clearMovementMap();
					RouteHandler.clearArrowPoints();
				}

				unitMenu.closeMenu();
			} else if (buildingMenu.isVisible()) {
				HeroPortrait portrait = MapHandler.getHeroPortrait();
				buildingMenu.buySelectedTroop(portrait);
				buildingMenu.closeMenu();
			} else if (chosenUnit != null && RouteHandler.movementMap(cursorX, cursorY) && rangeUnit == null) {
				handleOpenUnitMenu(cursorX, cursorY);
			} else if (!unitSelected && !unitSelectable(cursorX, cursorY)) {
				selectedBuilding = MapHandler.getFriendlyBuilding(cursorX, cursorY);

				if (selectedBuilding != null) {
					handleOpenBuildingMenu(cursorX, cursorY);
				}
			} else if (!unitSelected) {
				chosenUnit = MapHandler.getFriendlyUnit(cursorX, cursorY);

				if (chosenUnit != null) {
					RouteHandler.findPossibleMovementLocations(chosenUnit);

					RouteHandler.initArrowPoint(chosenUnit.getX(), chosenUnit.getY());
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (!unitIsntDroppingOff()) {
				int x = chosenUnit.getX();
				int y = chosenUnit.getY();
				cursor.setPosition(x, y);
				handleOpenUnitMenu(x, y);

				if (chosenUnit instanceof APC) {
					((APC)chosenUnit).regulateDroppingOff(false);
				} else if (chosenUnit instanceof Lander) {
					((Lander)chosenUnit).regulateDroppingOff(false);
				}
			} else if (unitWantToFire()) {
				if (chosenUnit instanceof IndirectUnit) {
					((IndirectUnit)chosenUnit).clearFiringLocations();
				}
				int x = chosenUnit.getX();
				int y = chosenUnit.getY();
				cursor.setPosition(x, y);
				handleOpenUnitMenu(x, y);

				chosenUnit.regulateAttack(false);
			} else if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.closeMenu();
			} else if (chosenUnit != null) {
				// the start-position of the unit before movement
				Point unitStartPoint = RouteHandler.getArrowPoint(0);
				int unitStartX = unitStartPoint.getX();
				int unitStartY = unitStartPoint.getY();

				if (unitMenu.isVisible()) {
					unitMenu.closeMenu();
					chosenUnit.moveTo(unitStartX, unitStartY);
				} else {
					cursor.setPosition(unitStartX, unitStartY);
					chosenUnit.moveTo(unitStartX, unitStartY);
					chosenUnit = null;
					RouteHandler.clearMovementMap();
					RouteHandler.clearArrowPoints();
				}
			} else {
				rangeUnit = getAnyUnit(cursorX, cursorY);

				if (rangeUnit != null) {
					if (rangeUnit.getAttackType() == Unit.DIRECT_ATTACK) {
						findPossibleDirectAttackLocations(rangeUnit);
					} else if (rangeUnit.getAttackType() == Unit.INDIRECT_ATTACK) {
						createRangeAttackLocations(rangeUnit);
					}
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (chosenUnit == null) {
				mapMenu.openMenu(cursorX, cursorY);
			}
		}

		MapHandler.updatePortraitSideChoice(cursor.getX(), cursor.getY());
		repaint();
	}

	public void handleFiring() {
		chosenUnit.regulateAttack(true);

		int x = chosenUnit.getX();
		int y = chosenUnit.getY();

		if (chosenUnit instanceof IndirectUnit) {
			IndirectUnit indirectUnit = (IndirectUnit)chosenUnit;
			calculatePossibleAttackLocations(indirectUnit);
			Point p = indirectUnit.getNextFiringLocation();
			x = p.getX();
			y = p.getY();
		} else {
			if (y > 0 && MapHandler.getNonFriendlyUnit(x, y - 1) != null) {
				y--;
			} else if (x < (mapWidth - 1) && MapHandler.getNonFriendlyUnit(x + 1, y) != null) {
				x++;
			} else if (MapHandler.getNonFriendlyUnit(x, y + 1) != null) {
				y++;
			} else if (MapHandler.getNonFriendlyUnit(x - 1, y) != null) {
				x--;
			} else {
				return; // cannot drop unit off anywhere
			}
		}

		cursor.setPosition(x, y);
	}

	public void removeUnitIfDead(Unit unit) {
		if (!unit.isDead()) {
			return;
		}

		Hero unitsHero = MapHandler.getHeroPortrait().getHeroFromUnit(unit);
		unitsHero.removeTroop(unit);
	}

	public void handleDroppingOff() {
		Unit containedUnit = null;
		if (chosenUnit instanceof APC) {
			((APC)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((APC)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			((Lander)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		}

		if (containedUnit == null) {
			return;
		}

		int x = chosenUnit.getX();
		int y = chosenUnit.getY();
		int movementType = containedUnit.getMovementType();

		if (y > 0 && validPosition(containedUnit, x, y - 1)) {
			y--;
		} else if (x < (mapWidth - 1) && validPosition(containedUnit, x + 1, y)) {
			x++;
		} else if (validPosition(containedUnit, x, y + 1)) {
			y++;
		} else if (validPosition(containedUnit, x - 1, y)) {
			x--;
		} else {
			return; // cannot drop unit off anywhere
		}

		if (unitCanBeDroppedOff()) {
			cursor.setPosition(x, y);
		} else {
			cursor.setPosition(chosenUnit.getX(), chosenUnit.getY());
		}
	}

	public boolean unitWantToFire() {
		if (chosenUnit == null) {
			return false;
		}

		return chosenUnit.isAttacking();
	}

	public boolean unitIsntDroppingOff() {
		if (chosenUnit instanceof APC) {
			if (((APC)chosenUnit).isDroppingOff()) {
				return false;
			}
		} else if (chosenUnit instanceof Lander) {
			if (((Lander)chosenUnit).isDroppingOff()) {
				return false;
			}
		}

		return true;
	}

	public boolean unitCanBeDroppedOff() {
		if (chosenUnit instanceof APC) {
			((APC)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((APC)chosenUnit).getUnit());
		} else if (chosenUnit instanceof Lander) {
			((Lander)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((Lander)chosenUnit).getChosenUnit());
		}

		return false;
	}

	private boolean unitCanBeDroppedOff(Unit unit) {
		if (unit == null) {
			return false;
		}

		int x = chosenUnit.getX();
		int y = chosenUnit.getY();

		if (y > 0 && validPosition(unit, x, y - 1)) {
			return true;
		} else if (x < (mapWidth - 1) && validPosition(unit, x + 1, y)) {
			return true;
		} else if (validPosition(unit, x, y + 1)) {
			return true;
		} else if (validPosition(unit, x - 1, y)) {
			return true;
		}

		return false;
	}

	private boolean landerAtDroppingOffPosition(int x, int y) {
		int areaValue = MapHandler.map(x, y);

		if (areaValue == MapHandler.SHORE || areaValue == MapHandler.PORT) {
			return true;
		} 

		return false;
	}

	public void moveDroppingOffCursorClockwise() {
		int unitX = chosenUnit.getX();
		int unitY = chosenUnit.getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		Unit containedUnit = null;

		if (chosenUnit instanceof APC) {
			containedUnit = ((APC)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}

		int movementType = containedUnit.getMovementType();

		if (xDiff == 1) {
			if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 2, cursorY)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (yDiff == 1) {
			if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX, cursorY - 2)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		}
	}

	public void moveDroppingOffCursorCounterclockwise() {
		int unitX = chosenUnit.getX();
		int unitY = chosenUnit.getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		Unit containedUnit = null;

		if (chosenUnit instanceof APC) {
			((APC)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			((Lander)chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}

		int movementType = containedUnit.getMovementType();

		if (xDiff == 1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 2, cursorY)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX, cursorY - 2)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}

	public void moveFiringCursorClockwise() {
		int unitX = chosenUnit.getX();
		int unitY = chosenUnit.getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		if (xDiff == 1) {
			if (unitY < (mapHeight - 1) && MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY + 1) != null) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitX > 0 && MapHandler.getNonFriendlyUnit(cursorX - 2, cursorY) != null) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY - 1) != null) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (yDiff == 1) {
			if (unitX > 0 && MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY - 1) != null) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitY > 0 && MapHandler.getNonFriendlyUnit(cursorX, cursorY - 2) != null) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY - 1) != null) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY > 0 && MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY - 1) != null) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapWidth - 1) && MapHandler.getNonFriendlyUnit(cursorX + 2, cursorY) != null) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY + 1) != null) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			if (unitX < (mapWidth - 1) && MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY + 1) != null) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && MapHandler.getNonFriendlyUnit(cursorX, cursorY + 2) != null) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY + 1) != null) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		}
	}

	public void moveFiringCursorCounterclockwise() {
		int unitX = chosenUnit.getX();
		int unitY = chosenUnit.getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;


		if (xDiff == 1) {
			if (unitY > 0 && MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY - 1) != null) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && MapHandler.getNonFriendlyUnit(cursorX - 2, cursorY) != null) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY + 1) != null) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			if (unitX < (mapWidth - 1) && MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY - 1) != null) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && MapHandler.getNonFriendlyUnit(cursorX, cursorY - 2) != null) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY - 1) != null) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY < (mapHeight - 1) && MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY + 1) != null) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapWidth - 1) && MapHandler.getNonFriendlyUnit(cursorX + 2, cursorY) != null) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY - 1) != null) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			if (unitX > 0 && MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY + 1) != null) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && MapHandler.getNonFriendlyUnit(cursorX, cursorY + 2) != null) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY + 1) != null) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}

	private boolean validPosition(Unit unit, int testX, int testY) {
		return !MapHandler.areaOccupiedByAny(unit, testX, testY) && MapHandler.allowedMovementPosition(testX, testY, unit.getMovementType());
	}

	private boolean unitSelectable(int x, int y) {
		return getAnyUnit(x, y) != null;
	}

	private Unit getAnyUnit(int x, int y) {
		return MapHandler.getAnyUnit(x, y);
	}

	private void handleOpenUnitMenu(int cursorX, int cursorY) {
		if (!MapHandler.areaOccupiedByFriendly(chosenUnit, cursorX, cursorY) || unitEntryingContainerUnit(chosenUnit, cursorX, cursorY)) {
			// @TODO
			if (hurtSameTypeUnitAtPosition(chosenUnit, cursorX, cursorY)) {
				unitMenu.unitMayJoin();
			}

			if (unitCanFire(cursorX, cursorY)) {
				unitMenu.unitMayFire();
			}

			if (chosenUnit instanceof Infantry || chosenUnit instanceof Mech) {
				if (footsoldierEnterableUnitAtPosition(cursorX, cursorY)) {
					unitMenu.unitMayEnter();
				}
			} else if (chosenUnit instanceof APC) {
				// should only be allowed this when close to a friendly unit
				if (mayAPCSUpply(cursorX, cursorY)) {
					unitMenu.unitMaySupply();
				}

				if (((APC)chosenUnit).isFull()) {
					Unit holdUnit = ((APC)chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenUnit instanceof Lander) {
				if (landerAtDroppingOffPosition(cursorX, cursorY)) {
					for (int i = 0 ; i < ((Lander)chosenUnit).getNumberOfContainedUnits() ; i++) {
						Unit holdUnit = ((Lander)chosenUnit).getUnit(i);
						unitMenu.containedCargo(holdUnit);
					}
				}
			}

			if (landbasedEnterableUnitAtPosition(cursorX, cursorY)) {
				if (!(chosenUnit instanceof Lander)) {
					unitMenu.unitMayEnter();
				} else {
					// if the chosenUnit is a lander 
				}
			}

			if (!MapHandler.areaOccupiedByFriendly(chosenUnit, cursorX, cursorY)) {
				unitMenu.unitMayWait();
			}

			unitMenu.openMenu(cursorX, cursorY);
			chosenUnit.moveTo(cursorX, cursorY);
		}
	}

	private void handleOpenBuildingMenu(int cursorX, int cursorY) {
		if (selectedBuilding instanceof Factory) {
			buildingMenu.openMenu(cursorX, cursorY);
		}
	}

	private boolean unitEntryingContainerUnit(Unit unit, int x, int y) {
		if (unit instanceof Infantry || unit instanceof Mech) {
			return footsoldierEnterableUnitAtPosition(x, y);
		} else if (unit instanceof Recon ||
					unit instanceof APC ||
					unit instanceof Artillery ||
					unit instanceof Tank ||
					unit instanceof Rocket) {
			return landbasedEnterableUnitAtPosition(x, y);
		}

		return false;
	}

	private boolean footsoldierEnterableUnitAtPosition(int x, int y) {
		if (landbasedEnterableUnitAtPosition(x, y)) {
			return true;
		}

		Unit unit = MapHandler.getFriendlyUnit(x, y);

		if (unit instanceof APC && !((APC)unit).isFull()) {
			return true;
		}
		return false;
	}

	private boolean landbasedEnterableUnitAtPosition(int x, int y) {
		Unit unit = MapHandler.getFriendlyUnit(x, y);

		if (unit instanceof Lander && !((Lander)unit).isFull()) {
			return true;
		} 

		return false;
	}

	private boolean mayAPCSUpply(int x, int y) {
		if (MapHandler.getFriendlyUnit(x + 1, y) != null) {
			return true;
		} else if (MapHandler.getFriendlyUnit(x, y + 1) != null) {
			return true;
		} else if (MapHandler.getFriendlyUnit(x - 1, y) != null) {
			return true;
		} else if (MapHandler.getFriendlyUnit(x, y - 1) != null) {
			return true;
		}

		return false;
	}

	private boolean hurtSameTypeUnitAtPosition(Unit unit, int x, int y) {
		Unit testUnit = MapHandler.getFriendlyUnit(x, y);

		if (testUnit == null) {
			return false;
		}

		return testUnit.isHurt() && testUnit.getClass().equals(unit.getClass());
	}

	private boolean unitCanFire(int cursorX, int cursorY) {
		if (chosenUnit instanceof IndirectUnit) {
			return indirectUnitCanFire(cursorX, cursorY);
		} else if (chosenUnit instanceof APC
					|| chosenUnit instanceof Lander) {
			return false;
		}

		return directUnitCanFire(cursorX, cursorY);
	}

	private boolean indirectUnitCanFire(int cursorX, int cursorY) {
		IndirectUnit unit = (IndirectUnit)chosenUnit;

		int unitX = unit.getX();
		int unitY = unit.getY();
		int minRange = unit.getMinRange();
		int maxRange = unit.getMaxRange();


		if (unitX != cursorX || unitY != cursorY) {
			return false;
		}

		for (int y = unitY - maxRange ; y <= (unitY + maxRange) ; y++) {
			if (y < 0) {
				continue;
			} else if (y >= mapHeight) {
				break;
			}
			for (int x = unitX - maxRange ; x <= (unitX + maxRange) ; x++) {
				if (x < 0) {
					continue;
				} else if (x >= mapWidth) {
					break;
				}

				int distanceFromUnit = Math.abs(unitX - x) + Math.abs(unitY - y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					if (MapHandler.getNonFriendlyUnit(x, y) != null) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean directUnitCanFire(int cursorX, int cursorY) {
		Unit northernFront = MapHandler.getNonFriendlyUnit(cursorX, cursorY - 1);
		Unit easternFront = MapHandler.getNonFriendlyUnit(cursorX + 1, cursorY);
		Unit southernFront = MapHandler.getNonFriendlyUnit(cursorX, cursorY + 1);
		Unit westernFront = MapHandler.getNonFriendlyUnit(cursorX - 1, cursorY);

		return northernFront != null || easternFront != null 
			|| southernFront != null || westernFront != null;
	}

	private void findPossibleDirectAttackLocations(Unit chosenUnit) {
		RouteHandler.findPossibleMovementLocations(chosenUnit);

		for (int n = 0 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				if (RouteHandler.movementMap(i, n)) {
					if (i > 0) {
						rangeMap[i - 1][n] = true;
					}
					if (i < (mapWidth - 1)) {
						rangeMap[i + 1][n] = true;
					}
					if (n > 0) {
						rangeMap[i][n - 1] = true;
					}
					if (n < (mapHeight - 1)) {
						rangeMap[i][n + 1] = true;
					}
				}
			}
		}

		RouteHandler.clearMovementMap();
	}

	private void createRangeAttackLocations(Unit chosenUnit) {
		IndirectUnit unit = (IndirectUnit)chosenUnit;

		int unitX = unit.getX();
		int unitY = unit.getY();
		int minRange = unit.getMinRange();
		int maxRange = unit.getMaxRange();

		for (int y = unitY - maxRange ; y <= (unitY + maxRange) ; y++) {
			if (y < 0) {
				continue;
			} else if (y >= mapHeight) {
				break;
			}
			for (int x = unitX - maxRange ; x <= (unitX + maxRange) ; x++) {
				if (x < 0) {
					continue;
				} else if (x >= mapWidth) {
					break;
				}

				int distanceFromUnit = Math.abs(unitX - x) + Math.abs(unitY - y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					rangeMap[x][y] = true;
				}
			}
		}
	}

	private void calculatePossibleAttackLocations(IndirectUnit indirectUnit) {
		int unitX = indirectUnit.getX();
		int unitY = indirectUnit.getY();
		int minRange = indirectUnit.getMinRange();
		int maxRange = indirectUnit.getMaxRange();

		for (int y = unitY - maxRange ; y <= (unitY + maxRange) ; y++) {
			if (y < 0) {
				continue;
			} else if (y >= mapHeight) {
				break;
			}
			for (int x = unitX - maxRange ; x <= (unitX + maxRange) ; x++) {
				if (x < 0) {
					continue;
				} else if (x >= mapWidth) {
					break;
				}

				int distanceFromUnit = Math.abs(unitX - x) + Math.abs(unitY - y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange && MapHandler.getNonFriendlyUnit(x, y) != null) {
					Point p = new Point(x, y);
					indirectUnit.addFiringLocation(p);
				}
			}
		}
	}

	private void endTurn() {
		endTurnActions();
		startTurnActions();
	}

	private void endTurnActions() {
		mapMenu.closeMenu();
		MapHandler.resetActiveVariable();
		MapHandler.changeHero();
	}

	private void startTurnActions() {
		MapHandler.updateCash();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (rangeUnit != null) {
				rangeUnit = null;
				rangeMap = new boolean[mapWidth][mapHeight];
				repaint();
			}
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int y = 0 ; y < mapHeight ; y++) {
			for (int x = 0 ; x < mapWidth ; x++) {
				MapHandler.paintArea(g, x, y, rangeMap[x][y]);
			}
		}

		if (chosenUnit != null) {
			if (!unitMenu.isVisible() && unitIsntDroppingOff() && !unitWantToFire()) {
				RouteHandler.paintArrow(g);
			}

			chosenUnit.paint(g, MapHandler.tileSize);
		}

		paintRange(g);

		MapHandler.paintUnits(g, chosenUnit);

		// when the mapMenu is open the cursor is hidden
		if (mapMenu.isVisible()) {
			mapMenu.paint(g);
		} else if (unitMenu.isVisible()) {
			unitMenu.paint(g);
		} else if (buildingMenu.isVisible()) {
			buildingMenu.paint(g);
		} else {
			cursor.paint(g);
		}

		MapHandler.paintPortrait(g);
	}

	private void paintRange(Graphics g) {
		int tileSize = MapHandler.tileSize;

		for (int n = 0 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				if (rangeMap[i][n]) {
					int paintX = i * tileSize;
					int paintY = n * tileSize;

					g.setColor(Color.red);
					g.fillRect(paintX, paintY, tileSize, tileSize);
					g.setColor(Color.black);
					g.drawRect(paintX, paintY, tileSize, tileSize);
				}
			}
		}
	}
}