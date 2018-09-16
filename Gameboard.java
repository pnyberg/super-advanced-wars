import point.*;
import units.*;
import units.airMoving.BCopter;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.tireMoving.Missiles;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.APC;
import units.treadMoving.Artillery;
import units.treadMoving.MDTank;
import units.treadMoving.Neotank;
import units.treadMoving.Tank;
import menus.*;
import menus.building.BuildingMenu;
import menus.map.MapMenu;
import menus.unit.UnitMenu;
import heroes.*;
import handlers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import area.TerrainType;
import area.buildings.*;

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
public class Gameboard extends JPanel implements KeyListener {
	private boolean[][] rangeMap;

	private int mapWidth, mapHeight;

	private Cursor cursor;

	private MapMenu mapMenu;
	private UnitMenu unitMenu;
	private BuildingMenu buildingMenu;

	private RouteHandler routeHandler;
	private MapHandler mapHandler;
	private DamageHandler damageHandler;

	private Unit chosenUnit, rangeUnit;
	private Building selectedBuilding;
	
	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		rangeMap = new boolean[mapWidth][mapHeight];

		cursor = new Cursor(0, 0);

		chosenUnit = null;
		rangeUnit = null;

		addKeyListener(this);

		routeHandler = new RouteHandler(mapWidth, mapHeight);
		mapHandler = new MapHandler(mapWidth, mapHeight, routeHandler);
		damageHandler = new DamageHandler(mapHandler);

		mapMenu = new MapMenu(MapHandler.tileSize);
		unitMenu = new UnitMenu(MapHandler.tileSize);
		buildingMenu = new BuildingMenu(MapHandler.tileSize, mapHandler.getHeroPortrait(), mapHandler);
		
		startTurnActions();

		mapHandler.updatePortraitSideChoice(cursor.getX(), cursor.getY());
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		boolean menuVisible = mapMenu.isVisible() || unitMenu.isVisible() || buildingMenu.isVisible();
		boolean unitSelected = chosenUnit != null || rangeUnit != null;

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (unitIsDroppingOff()) {
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
				routeHandler.addArrowPoint(cursorX, cursorY - 1, chosenUnit, mapHandler);
				cursor.moveUp();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowUp();
			} else if (unitMenu.isVisible()) {
				unitMenu.moveArrowUp();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowUp();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (unitIsDroppingOff()) {
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
				routeHandler.addArrowPoint(cursorX, cursorY + 1, chosenUnit, mapHandler);
				cursor.moveDown();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowDown();
			} else if (unitMenu.isVisible()) {
				unitMenu.moveArrowDown();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0 && !menuVisible && !unitIsDroppingOff() && !unitWantToFire()) {
				routeHandler.addArrowPoint(cursorX - 1, cursorY, chosenUnit, mapHandler);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (mapWidth - 1) && !menuVisible && !unitIsDroppingOff() && !unitWantToFire()) {
				routeHandler.addArrowPoint(cursorX + 1, cursorY, chosenUnit, mapHandler);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (unitIsDroppingOff()) {
				if (unitCanBeDroppedOff()) {
					if (chosenUnit instanceof APC) {
						((APC)chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((APC)chosenUnit).removeUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
						exitingUnit.regulateActive(false);
					} else if (chosenUnit instanceof TCopter) {
						((TCopter)chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((TCopter)chosenUnit).removeUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
						exitingUnit.regulateActive(false);
					} else if (chosenUnit instanceof Lander) {
						((Lander)chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((Lander)chosenUnit).removeChosenUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
						exitingUnit.regulateActive(false);
					} else if (chosenUnit instanceof Cruiser) {
						((Cruiser)chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((Cruiser)chosenUnit).removeChosenUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
						exitingUnit.regulateActive(false);
					}

					int fuelUse = calculateFuelUsed();
					chosenUnit.useFuel(fuelUse);

					chosenUnit.regulateActive(false);
					chosenUnit = null;
					routeHandler.clearMovementMap();
					routeHandler.clearArrowPoints();
				} else {
					// If all drop-slots are occupied, pressing 'A' won't do anything
				}
			} else if (unitWantToFire()) {
				Unit defendingUnit = mapHandler.getNonFriendlyUnit(cursorX, cursorY);
				damageHandler.handleAttack(chosenUnit, defendingUnit);
				chosenUnit.regulateAttack(false);

				int x = chosenUnit.getX();
				int y = chosenUnit.getY();
				cursor.setPosition(x, y);

				removeUnitIfDead(defendingUnit);
				removeUnitIfDead(chosenUnit);

				int fuelUse = calculateFuelUsed();
				chosenUnit.useFuel(fuelUse);

				chosenUnit.regulateActive(false);
				chosenUnit = null;
				routeHandler.clearMovementMap();
				routeHandler.clearArrowPoints();
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
					} else if (chosenUnit instanceof Cruiser) {
						int index = unitMenu.getMenuIndex();
						((Cruiser)chosenUnit).chooseUnit(index);
					}
					handleDroppingOff();
				} else if (unitMenu.atFireRow()) {
					handleFiring();
				} else if (unitMenu.atEnterRow()) {
					Unit entryUnit = mapHandler.getFriendlyUnitExceptSelf(chosenUnit, cursorX, cursorY);
					if (entryUnit instanceof APC) {
						((APC)entryUnit).addUnit(chosenUnit);
					} else if (entryUnit instanceof TCopter) {
						((TCopter)entryUnit).addUnit(chosenUnit);
					} else if (entryUnit instanceof Lander) {
						((Lander)entryUnit).addUnit(chosenUnit);
					} else if (entryUnit instanceof Cruiser) {
						((Cruiser)entryUnit).addUnit(chosenUnit);
					}

					// @TODO cargo-unit enters other unit
				} else if (unitMenu.atSupplyRow()) {
					int x = chosenUnit.getX();
					int y = chosenUnit.getY();

					Unit north = mapHandler.getFriendlyUnit(x, y - 1);
					Unit east = mapHandler.getFriendlyUnit(x + 1, y);
					Unit south = mapHandler.getFriendlyUnit(x, y + 1);
					Unit west = mapHandler.getFriendlyUnit(x - 1, y);

					replentishUnit(north);
					replentishUnit(east);
					replentishUnit(south);
					replentishUnit(west);
				} else if (unitMenu.atJoinRow()) {
					int x = chosenUnit.getX();
					int y = chosenUnit.getY();
					Unit unit = mapHandler.getFriendlyUnitExceptSelf(chosenUnit, x, y);
					
					unit.heal(chosenUnit.getHP());
					chosenUnit.kill();
					removeUnitIfDead(chosenUnit);
				}

				if (!unitIsDroppingOff() && !unitWantToFire()) {
					// using fuel
					int fuelUse = calculateFuelUsed();
					chosenUnit.useFuel(fuelUse);
					System.out.println("Fuel + Ammo: " + chosenUnit.getFuel() + " - " + chosenUnit.getAmmo());

					chosenUnit.regulateActive(false);
					chosenUnit = null;
					routeHandler.clearMovementMap();
					routeHandler.clearArrowPoints();
				}

				unitMenu.closeMenu();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.buySelectedTroop();
				buildingMenu.closeMenu();
			} else if (chosenUnit != null && routeHandler.movementMap(cursorX, cursorY) && rangeUnit == null) {
				int x = chosenUnit.getX();
				int y = chosenUnit.getY();
				if (mapHandler.getFriendlyUnit(x, y) != null) {
					handleOpenUnitMenu(cursorX, cursorY);
				}
			} else if (!unitSelected && !unitSelectable(cursorX, cursorY)) {
				selectedBuilding = mapHandler.getFriendlyBuilding(cursorX, cursorY);

				if (selectedBuilding != null) {
					handleOpenBuildingMenu(cursorX, cursorY);
				}
			} else if (!unitSelected) {
				// @TODO 
				chosenUnit = mapHandler.getAnyUnit(cursorX, cursorY);
//				chosenUnit = mapHandler.getFriendlyUnit(cursorX, cursorY);

				if (chosenUnit != null) {
					routeHandler.findPossibleMovementLocations(chosenUnit, mapHandler);
					routeHandler.initArrowPoint(chosenUnit.getX(), chosenUnit.getY());
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (unitIsDroppingOff()) {
				int x = chosenUnit.getX();
				int y = chosenUnit.getY();
				cursor.setPosition(x, y);
				handleOpenUnitMenu(x, y);

				if (chosenUnit instanceof APC) {
					((APC)chosenUnit).regulateDroppingOff(false);
				} else if (chosenUnit instanceof TCopter) {
					((TCopter)chosenUnit).regulateDroppingOff(false);
				} else if (chosenUnit instanceof Lander) {
					((Lander)chosenUnit).regulateDroppingOff(false);
				} else if (chosenUnit instanceof Cruiser) {
					((Cruiser)chosenUnit).regulateDroppingOff(false);
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
				Point unitStartPoint = routeHandler.getArrowPoint(0);
				int unitStartX = unitStartPoint.getX();
				int unitStartY = unitStartPoint.getY();

				if (unitMenu.isVisible()) {
					unitMenu.closeMenu();
					chosenUnit.moveTo(unitStartX, unitStartY);
				} else {
					cursor.setPosition(unitStartX, unitStartY);
					chosenUnit.moveTo(unitStartX, unitStartY);
					chosenUnit = null;
					routeHandler.clearMovementMap();
					routeHandler.clearArrowPoints();
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

		mapHandler.updatePortraitSideChoice(cursor.getX(), cursor.getY());
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
			Unit north = mapHandler.getNonFriendlyUnit(x, y - 1);
			Unit east = mapHandler.getNonFriendlyUnit(x + 1, y);
			Unit south = mapHandler.getNonFriendlyUnit(x, y + 1);
			Unit west = mapHandler.getNonFriendlyUnit(x - 1, y);
			if (y > 0 && north != null && damageHandler.validTarget(chosenUnit, north)) {
				y--;
			} else if (x < (mapWidth - 1) && east != null && damageHandler.validTarget(chosenUnit, east)) {
				x++;
			} else if (south != null && damageHandler.validTarget(chosenUnit, south)) {
				y++;
			} else if (west != null && damageHandler.validTarget(chosenUnit, west)) {
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

		Hero unitsHero = mapHandler.getHeroPortrait().getHeroFromUnit(unit);
		unitsHero.getTroopHandler().removeTroop(unit);
	}

	public void handleDroppingOff() {
		Unit containedUnit = null;
		if (chosenUnit instanceof APC) {
			((APC)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((APC)chosenUnit).getUnit();
		} else if (chosenUnit instanceof TCopter) {
			((TCopter)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((TCopter)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			((Lander)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else if (chosenUnit instanceof Cruiser) {
			((Cruiser)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((Cruiser)chosenUnit).getChosenUnit();
		}

		if (containedUnit == null) {
			return;
		}

		int x = chosenUnit.getX();
		int y = chosenUnit.getY();

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

	public boolean unitIsDroppingOff() {
		if (chosenUnit instanceof APC) {
			if (((APC)chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenUnit instanceof TCopter) {
			if (((TCopter)chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenUnit instanceof Lander) {
			if (((Lander)chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenUnit instanceof Cruiser) {
			if (((Cruiser)chosenUnit).isDroppingOff()) {
				return true;
			}
		}

		return false;
	}

	public boolean unitCanBeDroppedOff() {
		if (chosenUnit instanceof APC) {
			((APC)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((APC)chosenUnit).getUnit());
		} else if (chosenUnit instanceof TCopter) {
			((TCopter)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((TCopter)chosenUnit).getUnit());
		} else if (chosenUnit instanceof Lander) {
			((Lander)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((Lander)chosenUnit).getChosenUnit());
		} else if (chosenUnit instanceof Cruiser) {
			((Cruiser)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((Cruiser)chosenUnit).getChosenUnit());
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
		} else if (y < (mapHeight - 1) && validPosition(unit, x, y + 1)) {
			return true;
		} else if (x > 0 && validPosition(unit, x - 1, y)) {
			return true;
		}

		return false;
	}

	private boolean landerAtDroppingOffPosition(int x, int y) {
		TerrainType areaValue = mapHandler.map(x, y);

		if (areaValue == TerrainType.SHORE || areaValue == TerrainType.PORT) {
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
		} else if (chosenUnit instanceof TCopter) {
			containedUnit = ((TCopter)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else if (chosenUnit instanceof Cruiser) {
			containedUnit = ((Cruiser)chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}

		int movementType = containedUnit.getMovementType();

		if (xDiff == 1) {
			if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 2, cursorY)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (yDiff == 1) {
			if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX, cursorY - 2)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
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
			containedUnit = ((APC)chosenUnit).getUnit();
		} else if (chosenUnit instanceof TCopter) {
			containedUnit = ((TCopter)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else if (chosenUnit instanceof Cruiser) {
			containedUnit = ((Cruiser)chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}
		
		int movementType = containedUnit.getMovementType();

		if (xDiff == 1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 2, cursorY)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX, cursorY - 2)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (unitX < (mapWidth - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
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
			Unit leftDown = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			Unit leftOnly = mapHandler.getNonFriendlyUnit(cursorX - 2, cursorY);
			Unit leftUp = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			if (unitY < (mapHeight - 1) && leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (yDiff == 1) {
			Unit upLeft = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			Unit upOnly = mapHandler.getNonFriendlyUnit(cursorX, cursorY - 2);
			Unit upRight = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			if (unitX > 0 && upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			Unit rightDown = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			Unit rightOnly = mapHandler.getNonFriendlyUnit(cursorX + 2, cursorY);
			Unit rightUp = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			if (unitY > 0 && rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapWidth - 1) && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			Unit downLeft = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			Unit downOnly = mapHandler.getNonFriendlyUnit(cursorX, cursorY + 2);
			Unit downRight = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			if (unitX < (mapWidth - 1) && downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
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
			Unit leftUp = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			Unit leftOnly = mapHandler.getNonFriendlyUnit(cursorX - 2, cursorY);
			Unit leftDown = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			if (unitY > 0 && leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			Unit upRight = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			Unit upOnly = mapHandler.getNonFriendlyUnit(cursorX, cursorY - 2);
			Unit upLeft = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			if (unitX < (mapWidth - 1) && upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			Unit rightUp = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			Unit rightOnly = mapHandler.getNonFriendlyUnit(cursorX + 2, cursorY);
			Unit rightDown = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			if (unitY < (mapHeight - 1) && rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapWidth - 1) && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			Unit downRight = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			Unit downOnly = mapHandler.getNonFriendlyUnit(cursorX, cursorY + 2);
			Unit downLeft = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			if (unitX > 0 && downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}

	private boolean validPosition(Unit unit, int testX, int testY) {
		return !mapHandler.areaOccupiedByAny(unit, testX, testY) 
			&& mapHandler.allowedMovementPosition(testX, testY, unit.getMovementType());
	}

	private boolean unitSelectable(int x, int y) {
		return getAnyUnit(x, y) != null;
	}

	private Unit getAnyUnit(int x, int y) {
		return mapHandler.getAnyUnit(x, y);
	}

	private void handleOpenUnitMenu(int cursorX, int cursorY) {
		boolean hurtAtSamePosition = hurtSameTypeUnitAtPosition(chosenUnit, cursorX, cursorY);
		if (!mapHandler.areaOccupiedByFriendly(chosenUnit, cursorX, cursorY) 
		|| unitEntryingContainerUnit(chosenUnit, cursorX, cursorY)
		|| hurtAtSamePosition) {
			// @TODO fix join
			if (hurtAtSamePosition) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowJoin();
			}

			if (!hurtAtSamePosition && unitCanFire(cursorX, cursorY)) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowFire();
			}

			if (chosenUnit instanceof Infantry || chosenUnit instanceof Mech) {
				if (footsoldierEnterableUnitAtPosition(cursorX, cursorY)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
			} else if (chosenUnit instanceof APC) {
				// should only be allowed this when close to a friendly unit
				if (mayAPCSUpply(cursorX, cursorY)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowSupply();
				}

				if (((APC)chosenUnit).isFull()) {
					Unit holdUnit = ((APC)chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenUnit instanceof TCopter) {
				if (((TCopter)chosenUnit).isFull() && mapHandler.unitOnLand(cursorX, cursorY)) {
					Unit holdUnit = ((TCopter)chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenUnit instanceof Lander) {
				if (landerAtDroppingOffPosition(cursorX, cursorY)) {
					for (int i = 0 ; i < ((Lander)chosenUnit).getNumberOfContainedUnits() ; i++) {
						Unit holdUnit = ((Lander)chosenUnit).getUnit(i);
						unitMenu.containedCargo(holdUnit);
					}
				}
			} else if (chosenUnit instanceof Cruiser) {
				for (int i = 0 ; i < ((Cruiser)chosenUnit).getNumberOfContainedUnits() ; i++) {
					Unit holdUnit = ((Cruiser)chosenUnit).getUnit(i);
					unitMenu.containedCargo(holdUnit);
				}
			}

			if (landbasedEnterableUnitAtPosition(cursorX, cursorY)) {
				if (!(chosenUnit instanceof Lander)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
			} else if (copterEnterableUnitAtPosition(cursorX, cursorY)) {
				if (!(chosenUnit instanceof Cruiser)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
			}

			if (!mapHandler.areaOccupiedByFriendly(chosenUnit, cursorX, cursorY)) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowWait();
			}

			unitMenu.openMenu(cursorX, cursorY);
			chosenUnit.moveTo(cursorX, cursorY);
		}
	}

	private void handleOpenBuildingMenu(int cursorX, int cursorY) {
		if (selectedBuilding instanceof City/* || selectedBuilding instanceof HQ*/) {
			return;
		}

		buildingMenu.openMenu(cursorX, cursorY);
	}

	private boolean unitEntryingContainerUnit(Unit unit, int x, int y) {
		if (unit instanceof Infantry || unit instanceof Mech) {
			return footsoldierEnterableUnitAtPosition(x, y);
		} else if (unit instanceof Recon ||
					unit instanceof Tank ||
					unit instanceof MDTank ||
					unit instanceof Neotank ||
					unit instanceof APC ||
					unit instanceof Artillery ||
					unit instanceof Rocket ||
					unit instanceof AAir ||
					unit instanceof Missiles) {
			return landbasedEnterableUnitAtPosition(x, y);
		} else if (unit instanceof BCopter || unit instanceof TCopter) {
			return copterEnterableUnitAtPosition(x, y);
		}

		return false;
	}

	private boolean footsoldierEnterableUnitAtPosition(int x, int y) {
		if (landbasedEnterableUnitAtPosition(x, y)) {
			return true;
		}

		Unit unit = mapHandler.getFriendlyUnit(x, y);

		if (unit instanceof APC && !((APC)unit).isFull()) {
			return true;
		}
		if (unit instanceof TCopter && !((TCopter)unit).isFull()) {
			return true;
		}
		return false;
	}

	private boolean landbasedEnterableUnitAtPosition(int x, int y) {
		Unit unit = mapHandler.getFriendlyUnit(x, y);

		if (unit instanceof Lander && !((Lander)unit).isFull()) {
			return true;
		} 

		return false;
	}
	
	private boolean copterEnterableUnitAtPosition(int x, int y) {
		Unit unit = mapHandler.getFriendlyUnit(x, y);

		if (unit instanceof Cruiser && !((Cruiser)unit).isFull()) {
			return true;
		} 

		return false;
	}

	private boolean mayAPCSUpply(int x, int y) {
		if (mapHandler.getFriendlyUnit(x + 1, y) != null) {
			return true;
		} else if (mapHandler.getFriendlyUnit(x, y + 1) != null) {
			return true;
		} else if (mapHandler.getFriendlyUnit(x - 1, y) != null) {
			return true;
		} else if (mapHandler.getFriendlyUnit(x, y - 1) != null) {
			return true;
		}

		return false;
	}

	private boolean hurtSameTypeUnitAtPosition(Unit unit, int x, int y) {
		Unit testUnit = mapHandler.getFriendlyUnitExceptSelf(unit, x, y);

		if (testUnit == null) {
			return false;
		}

		return testUnit.isHurt() && testUnit.getClass().equals(unit.getClass());
	}

	private boolean unitCanFire(int cursorX, int cursorY) {
		if (chosenUnit instanceof IndirectUnit) {
			return indirectUnitCanFire(cursorX, cursorY);
		} else if (chosenUnit instanceof APC
					|| chosenUnit instanceof Lander
					|| chosenUnit instanceof TCopter) {
			return false;
		}

		return directUnitCanFire(cursorX, cursorY);
	}

	private boolean indirectUnitCanFire(int cursorX, int cursorY) {
		IndirectUnit attackingUnit = (IndirectUnit)chosenUnit;

		int unitX = attackingUnit.getX();
		int unitY = attackingUnit.getY();
		int minRange = attackingUnit.getMinRange();
		int maxRange = attackingUnit.getMaxRange();


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
					Unit targetUnit = mapHandler.getNonFriendlyUnit(x, y);
					if (targetUnit != null && damageHandler.validTarget(attackingUnit, targetUnit)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean directUnitCanFire(int cursorX, int cursorY) {
		Unit northernFront = mapHandler.getNonFriendlyUnit(cursorX, cursorY - 1);
		Unit easternFront = mapHandler.getNonFriendlyUnit(cursorX + 1, cursorY);
		Unit southernFront = mapHandler.getNonFriendlyUnit(cursorX, cursorY + 1);
		Unit westernFront = mapHandler.getNonFriendlyUnit(cursorX - 1, cursorY);

		return (northernFront != null && damageHandler.validTarget(chosenUnit, northernFront)) 
			|| (easternFront != null && damageHandler.validTarget(chosenUnit, easternFront))
			|| (southernFront != null && damageHandler.validTarget(chosenUnit, southernFront))
			|| (westernFront != null && damageHandler.validTarget(chosenUnit, westernFront));
	}

	private void findPossibleDirectAttackLocations(Unit chosenUnit) {
		routeHandler.findPossibleMovementLocations(chosenUnit, mapHandler);

		for (int n = 0 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				if (routeHandler.movementMap(i, n)) {
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
		routeHandler.clearMovementMap();
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
				
				Unit target = mapHandler.getNonFriendlyUnit(x, y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange && 
						 target != null && damageHandler.validTarget(chosenUnit, target)) {
					Point p = new Point(x, y);
					indirectUnit.addFiringLocation(p);
				}
			}
		}
	}
	
	private void replentishUnit(Unit unit) {
		if (unit == null) {
			return;
		}
		
		unit.replentish();
	}

	private int calculateFuelUsed() {
		return routeHandler.getFuelFromArrows(chosenUnit, mapHandler);
	}

	private void endTurn() {
		endTurnActions();
		startTurnActions();
	}

	private void endTurnActions() {
		mapMenu.closeMenu();
		mapHandler.resetActiveVariable();
		mapHandler.changeHero();
	}

	private void startTurnActions() {
		mapHandler.updateCash();
		mapHandler.fuelMaintenance();
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
				mapHandler.paintArea(g, x, y, rangeMap[x][y]);
			}
		}

		if (chosenUnit != null) {
			if (!unitMenu.isVisible() && !unitIsDroppingOff() && !unitWantToFire()) {
				routeHandler.paintArrow(g);
			}

			chosenUnit.paint(g, mapHandler.tileSize);
		}

		paintRange(g);

		mapHandler.paintUnits(g, chosenUnit);

		// when the mapMenu is open the cursor is hidden
		if (mapMenu.isVisible()) {
			mapMenu.paint(g);
		} else if (unitMenu.isVisible()) {
			unitMenu.paint(g);
		} else if (buildingMenu.isVisible()) {
			buildingMenu.paint(g);
		} else if (unitWantToFire()) {
			paintFiringCursor(g);
		} else {
			cursor.paint(g);
		}

		mapHandler.paintPortrait(g);
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

	private void paintFiringCursor(Graphics g) {
		int tileSize = MapHandler.tileSize;

		int x = cursor.getX();
		int y = cursor.getY();

		int xDiff = x - chosenUnit.getX();
		int yDiff = y - chosenUnit.getY();

		Unit targetUnit = mapHandler.getNonFriendlyUnit(x, y);

		HeroPortrait portrait = mapHandler.getHeroPortrait();
		Hero chosenHero = portrait.getCurrentHero();
		Hero targetHero = portrait.getHeroFromUnit(targetUnit);
		
		TerrainType terrainType = mapHandler.map(x, y);
//		int areaDefenceValue = mapHandler.getDefenceValue(terrainType);

		int damage = damageHandler.getNonRNGDamageValue(chosenUnit, chosenHero, targetUnit, targetHero, terrainType); 
				
		int damageFieldWidth = (damage <= 9 ? 3 * tileSize / 5 : 
									(damage <= 99 ? 4 * tileSize / 5
										: tileSize - 3));
		int damageFieldHeight = 3 * tileSize / 5;

		int paintX = x * tileSize + 2;
		int paintY = y * tileSize + 2;
		int dmgFieldX = x * tileSize; // will be changed
		int dmgFieldY = y * tileSize; // will be changed

		g.setColor(Color.black);
		g.drawOval(paintX, paintY, tileSize - 4, tileSize - 4);
		g.drawOval(paintX + 2, paintY + 2, tileSize - 8, tileSize - 8);

		g.setColor(Color.white);
		g.drawOval(paintX + 1, paintY + 1, tileSize - 6, tileSize - 6);

		if (yDiff == -1) {
			dmgFieldX += tileSize;
			dmgFieldY += -damageFieldHeight;
		} else if (xDiff == 1) {
			dmgFieldX += tileSize;
			dmgFieldY += tileSize;
		} else if (yDiff == 1) {
			dmgFieldX += -damageFieldWidth;
			dmgFieldY += tileSize;
		} else { // xDiff == -1
			dmgFieldX += -damageFieldWidth;
			dmgFieldY += -damageFieldHeight;
		}

		g.setColor(Color.red);
		g.fillRect(dmgFieldX, dmgFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.black);
		g.drawRect(dmgFieldX, dmgFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.white);
		g.drawString("" + damage + "%", dmgFieldX + damageFieldWidth / 10, dmgFieldY + 2 * damageFieldHeight / 3);
	}
}