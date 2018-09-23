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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import area.Area;
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
	private MapDimension mapDimension;

	private Cursor cursor;

	private MapMenu mapMenu;
	private UnitMenu unitMenu;
	private BuildingMenu buildingMenu;

	private MovementMap movementMap;
	private RouteChecker routeChecker; 
	private RouteHandler routeHandler;
	private MapHandler mapHandler;
	private DamageHandler damageHandler;
	private AttackHandler attackHandler;
	private AttackRangeHandler attackRangeHandler;

	private Unit chosenUnit, rangeUnit;
	private Building selectedBuilding;
	
	public Gameboard(MapDimension mapDimension, HeroHandler heroHandler, GameProperties gameProperties) {
		this.mapDimension = mapDimension;

		cursor = new Cursor(0, 0, mapDimension.tileSize);

		chosenUnit = null;
		rangeUnit = null;

		addKeyListener(this);

		movementMap = new MovementMap(mapDimension);
		routeHandler = new RouteHandler(mapDimension, movementMap);
		mapHandler = new MapHandler(mapDimension, routeHandler, heroHandler, gameProperties);
		routeChecker = new RouteChecker(mapHandler, movementMap);
		damageHandler = new DamageHandler(heroHandler, mapHandler.getMap());
		attackRangeHandler = new AttackRangeHandler();

		mapMenu = new MapMenu(mapDimension.tileSize);
		unitMenu = new UnitMenu(mapDimension.tileSize);
		buildingMenu = new BuildingMenu(mapDimension.tileSize, mapHandler);
		
		startTurnActions();

		mapHandler.getHeroPortrait().updateSideChoice(cursor.getX(), cursor.getY());
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
				routeHandler.updateArrowPath(new Point(cursorX, cursorY - 1), chosenUnit, mapHandler);
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
			} else if (cursorY < (mapDimension.height - 1) && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY + 1), chosenUnit, mapHandler);
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
				routeHandler.updateArrowPath(new Point(cursorX - 1, cursorY), chosenUnit, mapHandler);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (mapDimension.width - 1) && !menuVisible && !unitIsDroppingOff() && !unitWantToFire()) {
				routeHandler.updateArrowPath(new Point(cursorX + 1, cursorY), chosenUnit, mapHandler);
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
					chosenUnit.getUnitSupply().useFuel(fuelUse);

					chosenUnit.regulateActive(false);
					chosenUnit = null;
					movementMap.clearMovementMap();
					routeHandler.clearArrowPoints();
				} else {
					// If all drop-slots are occupied, pressing 'A' won't do anything
				}
			} else if (unitWantToFire()) {
				Unit defendingUnit = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX, cursorY);
				damageHandler.handleAttack(chosenUnit, defendingUnit);
				chosenUnit.regulateAttack(false);

				int x = chosenUnit.getPoint().getX();
				int y = chosenUnit.getPoint().getY();
				cursor.setPosition(x, y);

				removeUnitIfDead(defendingUnit);
				removeUnitIfDead(chosenUnit);

				int fuelUse = calculateFuelUsed();
				chosenUnit.getUnitSupply().useFuel(fuelUse);

				chosenUnit.regulateActive(false);
				chosenUnit = null;
				movementMap.clearMovementMap();
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
					attackHandler.handleFiring();
				} else if (unitMenu.atEnterRow()) {
					Unit entryUnit = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnitExceptSelf(chosenUnit, cursorX, cursorY);
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
					int x = chosenUnit.getPoint().getX();
					int y = chosenUnit.getPoint().getY();

					Unit north = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x, y - 1);
					Unit east = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x + 1, y);
					Unit south = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x, y + 1);
					Unit west = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x - 1, y);

					replentishUnit(north);
					replentishUnit(east);
					replentishUnit(south);
					replentishUnit(west);
				} else if (unitMenu.atJoinRow()) {
					int x = chosenUnit.getPoint().getX();
					int y = chosenUnit.getPoint().getY();
					Unit unit = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnitExceptSelf(chosenUnit, x, y);
					
					unit.getUnitHealth().heal(chosenUnit.getUnitHealth().getHP());
					chosenUnit.getUnitHealth().kill();
					removeUnitIfDead(chosenUnit);
				}

				if (!unitIsDroppingOff() && !unitWantToFire()) {
					// using fuel
					int fuelUse = calculateFuelUsed();
					chosenUnit.getUnitSupply().useFuel(fuelUse);
					System.out.println("Fuel + Ammo: " + chosenUnit.getUnitSupply().getFuel() + 
										" - " + chosenUnit.getUnitSupply().getAmmo());

					chosenUnit.regulateActive(false);
					chosenUnit = null;
					movementMap.clearMovementMap();
					routeHandler.clearArrowPoints();
				}

				unitMenu.closeMenu();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.buySelectedTroop();
				buildingMenu.closeMenu();
			} else if (chosenUnit != null && movementMap.isAcceptedMove(cursorX, cursorY) && rangeUnit == null) {
				int x = chosenUnit.getPoint().getX();
				int y = chosenUnit.getPoint().getY();
				if (mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x, y) != null) {
					handleOpenUnitMenu(cursorX, cursorY);
				}
			} else if (!unitSelected && !unitSelectable(cursorX, cursorY)) {
				selectedBuilding = mapHandler.getMapGettersObject().buildingGetter.getFriendlyBuilding(cursorX, cursorY);

				if (selectedBuilding != null) {
					handleOpenBuildingMenu(cursorX, cursorY);
				}
			} else if (!unitSelected) {
				// @TODO 
				chosenUnit = mapHandler.getMapGettersObject().unitGetter.getAnyUnit(cursorX, cursorY);
//				chosenUnit = mapHandler.getFriendlyUnit(cursorX, cursorY);

				if (chosenUnit != null) {
					routeChecker.findPossibleMovementLocations(chosenUnit);
					routeHandler.addNewArrowPoint(chosenUnit.getPoint());
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (unitIsDroppingOff()) {
				int x = chosenUnit.getPoint().getX();
				int y = chosenUnit.getPoint().getY();
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
				int x = chosenUnit.getPoint().getX();
				int y = chosenUnit.getPoint().getY();
				cursor.setPosition(x, y);
				handleOpenUnitMenu(x, y);

				chosenUnit.regulateAttack(false);
			} else if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.closeMenu();
			} else if (chosenUnit != null) {
				// the start-position of the unit before movement
				Point unitStartPoint = routeHandler.getRouteArrowPath().getArrowPoint(0);
				int unitStartX = unitStartPoint.getX();
				int unitStartY = unitStartPoint.getY();

				if (unitMenu.isVisible()) {
					unitMenu.closeMenu();
					chosenUnit.moveTo(unitStartX, unitStartY);
				} else {
					cursor.setPosition(unitStartX, unitStartY);
					chosenUnit.moveTo(unitStartX, unitStartY);
					chosenUnit = null;
					movementMap.clearMovementMap();
					routeHandler.clearArrowPoints();
				}
			} else {
				rangeUnit = getAnyUnit(cursorX, cursorY);

				if (rangeUnit != null) {
					if (rangeUnit.getAttackType() == AttackType.DIRECT_ATTACK) {
						attackRangeHandler.findPossibleDirectAttackLocations(rangeUnit);
					} else if (rangeUnit.getAttackType() == AttackType.INDIRECT_ATTACK) {
						attackRangeHandler.createRangeAttackLocations(rangeUnit);
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

		mapHandler.getHeroPortrait().updateSideChoice(cursor.getX(), cursor.getY());
		repaint();
	}

	public void removeUnitIfDead(Unit unit) {
		if (!unit.getUnitHealth().isDead()) {
			return;
		}

		Hero unitsHero = mapHandler.getHeroPortrait().getHeroHandler().getHeroFromUnit(unit);
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

		int x = chosenUnit.getPoint().getX();
		int y = chosenUnit.getPoint().getY();

		if (y > 0 && validPosition(containedUnit, x, y - 1)) {
			y--;
		} else if (x < (mapDimension.width - 1) && validPosition(containedUnit, x + 1, y)) {
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
			cursor.setPosition(chosenUnit.getPoint().getX(), chosenUnit.getPoint().getY());
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

		int x = chosenUnit.getPoint().getX();
		int y = chosenUnit.getPoint().getY();

		if (y > 0 && validPosition(unit, x, y - 1)) {
			return true;
		} else if (x < (mapDimension.width - 1) && validPosition(unit, x + 1, y)) {
			return true;
		} else if (y < (mapDimension.height - 1) && validPosition(unit, x, y + 1)) {
			return true;
		} else if (x > 0 && validPosition(unit, x - 1, y)) {
			return true;
		}

		return false;
	}

	private boolean landerAtDroppingOffPosition(int x, int y) {
		Area[][] map = mapHandler.getMap();
		TerrainType areaValue = map[x][y].getTerrainType();

		if (areaValue == TerrainType.SHORE || areaValue == TerrainType.PORT) {
			return true;
		} 

		return false;
	}

	public void moveDroppingOffCursorClockwise() {
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
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

		if (xDiff == 1) {
			if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
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
			} else if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		}
	}

	public void moveDroppingOffCursorCounterclockwise() {
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
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
		
		if (xDiff == 1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 2, cursorY)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX, cursorY - 2)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}

	public void moveFiringCursorClockwise() {
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		if (xDiff == 1) {
			Unit leftDown = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			Unit leftOnly = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 2, cursorY);
			Unit leftUp = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			if (unitY < (mapDimension.height - 1) && leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (yDiff == 1) {
			Unit upLeft = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			Unit upOnly = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX, cursorY - 2);
			Unit upRight = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			if (unitX > 0 && upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			Unit rightDown = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			Unit rightOnly = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 2, cursorY);
			Unit rightUp = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			if (unitY > 0 && rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapDimension.width - 1) && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			Unit downLeft = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			Unit downOnly = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX, cursorY + 2);
			Unit downRight = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			if (unitX < (mapDimension.width - 1) && downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapDimension.height - 1) && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		}
	}

	public void moveFiringCursorCounterclockwise() {
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		if (xDiff == 1) {
			Unit leftUp = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			Unit leftOnly = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 2, cursorY);
			Unit leftDown = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			if (unitY > 0 && leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			Unit upRight = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			Unit upOnly = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX, cursorY - 2);
			Unit upLeft = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			if (unitX < (mapDimension.width - 1) && upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			Unit rightUp = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			Unit rightOnly = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 2, cursorY);
			Unit rightDown = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			if (unitY < (mapDimension.height - 1) && rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapDimension.width - 1) && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			Unit downRight = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			Unit downOnly = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX, cursorY + 2);
			Unit downLeft = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			if (unitX > 0 && downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapDimension.height - 1) && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}

	private boolean validPosition(Unit unit, int testX, int testY) {
		return !mapHandler.getAreaChecker().areaOccupiedByAny(unit, testX, testY) 
			&& routeChecker.allowedMovementPosition(testX, testY, unit.getMovementType());
	}

	private boolean unitSelectable(int x, int y) {
		return getAnyUnit(x, y) != null;
	}

	private Unit getAnyUnit(int x, int y) {
		return mapHandler.getMapGettersObject().unitGetter.getAnyUnit(x, y);
	}

	private void handleOpenUnitMenu(int cursorX, int cursorY) {
		boolean hurtAtSamePosition = hurtSameTypeUnitAtPosition(chosenUnit, cursorX, cursorY);
		if (!mapHandler.getAreaChecker().areaOccupiedByFriendly(chosenUnit, cursorX, cursorY) 
		|| unitEntryingContainerUnit(chosenUnit, cursorX, cursorY)
		|| hurtAtSamePosition) {
			// @TODO fix join
			if (hurtAtSamePosition) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowJoin();
			}

			if (!hurtAtSamePosition && attackRangeHandler.unitCanFire(cursorX, cursorY)) {
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
				if (((TCopter)chosenUnit).isFull() && mapHandler.isLand(cursorX, cursorY)) {
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

			if (!mapHandler.getAreaChecker().areaOccupiedByFriendly(chosenUnit, cursorX, cursorY)) {
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

		Unit unit = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x, y);

		if (unit instanceof APC && !((APC)unit).isFull()) {
			return true;
		}
		if (unit instanceof TCopter && !((TCopter)unit).isFull()) {
			return true;
		}
		return false;
	}

	private boolean landbasedEnterableUnitAtPosition(int x, int y) {
		Unit unit = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x, y);

		if (unit instanceof Lander && !((Lander)unit).isFull()) {
			return true;
		} 

		return false;
	}
	
	private boolean copterEnterableUnitAtPosition(int x, int y) {
		Unit unit = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x, y);

		if (unit instanceof Cruiser && !((Cruiser)unit).isFull()) {
			return true;
		} 

		return false;
	}

	private boolean mayAPCSUpply(int x, int y) {
		if (mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x + 1, y) != null) {
			return true;
		} else if (mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x, y + 1) != null) {
			return true;
		} else if (mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x - 1, y) != null) {
			return true;
		} else if (mapHandler.getMapGettersObject().unitGetter.getFriendlyUnit(x, y - 1) != null) {
			return true;
		}

		return false;
	}

	private boolean hurtSameTypeUnitAtPosition(Unit unit, int x, int y) {
		Unit testUnit = mapHandler.getMapGettersObject().unitGetter.getFriendlyUnitExceptSelf(unit, x, y);

		if (testUnit == null) {
			return false;
		}

		return testUnit.getUnitHealth().isHurt() && testUnit.getClass().equals(unit.getClass());
	}

	private void replentishUnit(Unit unit) {
		if (unit == null) {
			return;
		}
		
		unit.getUnitSupply().replentish();
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
		mapHandler.getHeroPortrait().getHeroHandler().nextHero();
	}

	private void startTurnActions() {
		mapHandler.updateCash();
		mapHandler.fuelMaintenance();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (rangeUnit != null) {
				rangeUnit = null;
				rangeMap = new boolean[mapDimension.width][mapDimension.height];
				repaint();
			}
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int y = 0 ; y < mapDimension.height ; y++) {
			for (int x = 0 ; x < mapDimension.width ; x++) {
				mapHandler.getMapPainter().paintArea(g, x, y, rangeMap[x][y]);
			}
		}

		if (chosenUnit != null) {
			if (!unitMenu.isVisible() && !unitIsDroppingOff() && !unitWantToFire()) {
				routeHandler.getRouteArrowPath().paintArrow(g);
			}

			chosenUnit.paint(g, mapDimension.tileSize);
		}

		attackRangeHandler.paintRange(g);

		mapHandler.getMapPainter().paintUnits(g, chosenUnit);

		// when the mapMenu is open the cursor is hidden
		if (mapMenu.isVisible()) {
			mapMenu.paint(g);
		} else if (unitMenu.isVisible()) {
			unitMenu.paint(g);
		} else if (buildingMenu.isVisible()) {
			buildingMenu.paint(g);
		} else if (unitWantToFire()) {
			attackRangeHandler.paintFiringCursor(g);
		} else {
			cursor.paint(g);
		}

		mapHandler.getMapPainter().paintPortrait(g);
	}
}