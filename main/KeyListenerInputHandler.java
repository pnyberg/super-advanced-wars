package main;

import java.awt.event.KeyEvent;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.DamageHandler;
import cursors.Cursor;
import cursors.FiringCursorHandler;
import gameObjects.ChosenObject;
import gameObjects.MapDim;
import heroes.Hero;
import map.BuildingGetter;
import map.UnitGetter;
import map.area.buildings.City;
import menus.building.BuildingMenu;
import menus.map.MapMenu;
import menus.unit.UnitMenuHandler;
import point.Point;
import routing.MovementMap;
import routing.RouteChecker;
import routing.RouteHandler;
import units.*;
import units.airMoving.*;
import units.seaMoving.*;
import units.treadMoving.*;

public class KeyListenerInputHandler {
	private MapDim mapDim;
	private UnitGetter unitGetter;
	private BuildingGetter buildingGetter;
	private ChosenObject chosenObject;
	private Cursor cursor;
	private UnitMenuHandler unitMenuHandler;
	private MapMenu mapMenu;
	private BuildingMenu buildingMenu;
	private ContUnitHandler containerUnitHandler;
	private AttackHandler attackHandler;
	private AttackRangeHandler attackRangeHandler;
	private MovementMap movementMap;
	private RouteHandler routeHandler;
	private RouteChecker routeChecker;
	private DamageHandler damageHandler;
	private HeroHandler heroHandler;
	private SupplyHandler supplyHandler;
	private TurnHandler turnHandler;
	private FiringCursorHandler firingCursorHandler;
	
	public KeyListenerInputHandler(MapDim mapDim, UnitGetter unitGetter, BuildingGetter buildingGetter, ChosenObject chosenObject, Cursor cursor, UnitMenuHandler unitMenuHandler, MapMenu mapMenu, BuildingMenu buildingMenu, ContUnitHandler containerUnitHandler, AttackHandler attackHandler, AttackRangeHandler attackRangeHandler, MovementMap movementMap, RouteHandler routeHandler, RouteChecker routeChecker, DamageHandler damageHandler, HeroHandler heroHandler, SupplyHandler supplyHandler, TurnHandler turnHandler) {
		this.mapDim = mapDim;
		this.unitGetter = unitGetter;
		this.buildingGetter = buildingGetter;
		this.chosenObject = chosenObject;
		this.cursor = cursor;
		this.unitMenuHandler = unitMenuHandler;
		this.mapMenu = mapMenu;
		this.buildingMenu = buildingMenu;
		this.containerUnitHandler = containerUnitHandler;
		this.attackHandler = attackHandler;
		this.attackRangeHandler = attackRangeHandler;
		this.movementMap = movementMap;
		this.routeHandler = routeHandler;
		this.routeChecker = routeChecker;
		this.damageHandler = damageHandler;
		this.heroHandler = heroHandler;
		this.supplyHandler = supplyHandler;
		this.turnHandler = turnHandler;
		firingCursorHandler = new FiringCursorHandler(mapDim, chosenObject, cursor, unitGetter, damageHandler);
	}
	
	public void manageKeyPressedInput(KeyEvent e) {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		boolean menuVisible = mapMenu.isVisible() || unitMenuHandler.getUnitMenu().isVisible() || buildingMenu.isVisible();
		boolean unitSelected = chosenObject.chosenUnit != null || chosenObject.rangeUnit != null;

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (containerUnitHandler.unitIsDroppingOff()) {
				if (containerUnitHandler.unitCanBeDroppedOff()) {
					containerUnitHandler.moveDroppingOffCursorCounterclockwise();
				}
			} else if (attackHandler.unitWantsToFire(chosenObject.chosenUnit)) {
				if (chosenObject.chosenUnit instanceof IndirectUnit) {
					Point p = ((IndirectUnit)chosenObject.chosenUnit).getPreviousFiringLocation();
					cursorX = p.getX();
					cursorY = p.getY();
					cursor.setPosition(cursorX, cursorY);
				} else {
					firingCursorHandler.moveFiringCursorCounterclockwise();
				}
			} else if (cursorY > 0 && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY - 1), chosenObject.chosenUnit);
				cursor.moveUp();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowUp();
			} else if (unitMenuHandler.getUnitMenu().isVisible()) {
				unitMenuHandler.getUnitMenu().moveArrowUp();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowUp();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (containerUnitHandler.unitIsDroppingOff()) {
				if (containerUnitHandler.unitCanBeDroppedOff()) {
					containerUnitHandler.moveDroppingOffCursorClockwise();
				}
			} else if (attackHandler.unitWantsToFire(chosenObject.chosenUnit)) {
				if (chosenObject.chosenUnit instanceof IndirectUnit) {
					Point p = ((IndirectUnit)chosenObject.chosenUnit).getNextFiringLocation();
					cursorX = p.getX();
					cursorY = p.getY();
					cursor.setPosition(cursorX, cursorY);
				} else {
					firingCursorHandler.moveFiringCursorClockwise();
				}
			} else if (cursorY < (mapDim.height - 1) && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY + 1), chosenObject.chosenUnit);
				cursor.moveDown();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowDown();
			} else if (unitMenuHandler.getUnitMenu().isVisible()) {
				unitMenuHandler.getUnitMenu().moveArrowDown();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0 && !menuVisible && !containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(chosenObject.chosenUnit)) {
				routeHandler.updateArrowPath(new Point(cursorX - 1, cursorY), chosenObject.chosenUnit);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (mapDim.width - 1) && !menuVisible && !containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(chosenObject.chosenUnit)) {
				routeHandler.updateArrowPath(new Point(cursorX + 1, cursorY), chosenObject.chosenUnit);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (containerUnitHandler.unitIsDroppingOff()) {
				if (containerUnitHandler.unitCanBeDroppedOff()) {
					if (chosenObject.chosenUnit instanceof APC) {
						((APC)chosenObject.chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((APC)chosenObject.chosenUnit).removeUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
						exitingUnit.regulateActive(false);
					} else if (chosenObject.chosenUnit instanceof TCopter) {
						((TCopter)chosenObject.chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((TCopter)chosenObject.chosenUnit).removeUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
						exitingUnit.regulateActive(false);
					} else if (chosenObject.chosenUnit instanceof Lander) {
						((Lander)chosenObject.chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((Lander)chosenObject.chosenUnit).removeChosenUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
						exitingUnit.regulateActive(false);
					} else if (chosenObject.chosenUnit instanceof Cruiser) {
						((Cruiser)chosenObject.chosenUnit).regulateDroppingOff(false);
						Unit exitingUnit = ((Cruiser)chosenObject.chosenUnit).removeChosenUnit();
						exitingUnit.moveTo(cursor.getX(), cursor.getY());
						exitingUnit.regulateActive(false);
					}

					int fuelUse = routeHandler.getFuelFromArrows(chosenObject.chosenUnit);;
					chosenObject.chosenUnit.getUnitSupply().useFuel(fuelUse);

					chosenObject.chosenUnit.regulateActive(false);
					chosenObject.chosenUnit = null;
					movementMap.clearMovementMap();
					routeHandler.clearArrowPoints();
				} else {
					// If all drop-slots are occupied, pressing 'A' won't do anything
				}
			} else if (attackHandler.unitWantsToFire(chosenObject.chosenUnit)) {
				Unit defendingUnit = unitGetter.getNonFriendlyUnit(cursorX, cursorY);
				damageHandler.handleAttack(chosenObject.chosenUnit, defendingUnit);
				chosenObject.chosenUnit.regulateAttack(false);

				int x = chosenObject.chosenUnit.getPoint().getX();
				int y = chosenObject.chosenUnit.getPoint().getY();
				cursor.setPosition(x, y);

				removeUnitIfDead(defendingUnit);
				removeUnitIfDead(chosenObject.chosenUnit);

				int fuelUse = routeHandler.getFuelFromArrows(chosenObject.chosenUnit);;
				chosenObject.chosenUnit.getUnitSupply().useFuel(fuelUse);

				chosenObject.chosenUnit.regulateActive(false);
				chosenObject.chosenUnit = null;
				movementMap.clearMovementMap();
				routeHandler.clearArrowPoints();
				if (chosenObject.chosenUnit instanceof IndirectUnit) {
					((IndirectUnit)chosenObject.chosenUnit).clearFiringLocations();
				}
			} else if (mapMenu.isVisible()) {
				if (mapMenu.atEndRow()) {
					turnHandler.endTurn();
				}
			} else if (unitMenuHandler.getUnitMenu().isVisible()) {
				if (unitMenuHandler.getUnitMenu().atUnitRow()) {
					if (chosenObject.chosenUnit instanceof Lander) {
						int index = unitMenuHandler.getUnitMenu().getMenuIndex();
						((Lander)chosenObject.chosenUnit).chooseUnit(index);
					} else if (chosenObject.chosenUnit instanceof Cruiser) {
						int index = unitMenuHandler.getUnitMenu().getMenuIndex();
						((Cruiser)chosenObject.chosenUnit).chooseUnit(index);
					}
					containerUnitHandler.handleDroppingOff();
				} else if (unitMenuHandler.getUnitMenu().atFireRow()) {
					attackHandler.handleFiring(chosenObject.chosenUnit, cursor);
				} else if (unitMenuHandler.getUnitMenu().atEnterRow()) {
					Unit entryUnit = unitGetter.getFriendlyUnitExceptSelf(chosenObject.chosenUnit, cursorX, cursorY);
					if (entryUnit instanceof APC) {
						((APC)entryUnit).addUnit(chosenObject.chosenUnit);
					} else if (entryUnit instanceof TCopter) {
						((TCopter)entryUnit).addUnit(chosenObject.chosenUnit);
					} else if (entryUnit instanceof Lander) {
						((Lander)entryUnit).addUnit(chosenObject.chosenUnit);
					} else if (entryUnit instanceof Cruiser) {
						((Cruiser)entryUnit).addUnit(chosenObject.chosenUnit);
					}

					// @TODO cargo-unit enters other unit
				} else if (unitMenuHandler.getUnitMenu().atSupplyRow()) {
					int x = chosenObject.chosenUnit.getPoint().getX();
					int y = chosenObject.chosenUnit.getPoint().getY();

					Unit north = unitGetter.getFriendlyUnit(x, y - 1);
					Unit east = unitGetter.getFriendlyUnit(x + 1, y);
					Unit south = unitGetter.getFriendlyUnit(x, y + 1);
					Unit west = unitGetter.getFriendlyUnit(x - 1, y);

					supplyHandler.replentishUnit(north);
					supplyHandler.replentishUnit(east);
					supplyHandler.replentishUnit(south);
					supplyHandler.replentishUnit(west);
				} else if (unitMenuHandler.getUnitMenu().atJoinRow()) {
					int x = chosenObject.chosenUnit.getPoint().getX();
					int y = chosenObject.chosenUnit.getPoint().getY();
					Unit unit = unitGetter.getFriendlyUnitExceptSelf(chosenObject.chosenUnit, x, y);
					
					unit.getUnitHealth().heal(chosenObject.chosenUnit.getUnitHealth().getHP());
					chosenObject.chosenUnit.getUnitHealth().kill();
					removeUnitIfDead(chosenObject.chosenUnit);
				}

				if (!containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(chosenObject.chosenUnit)) {
					// using fuel
					int fuelUse = routeHandler.getFuelFromArrows(chosenObject.chosenUnit);
					chosenObject.chosenUnit.getUnitSupply().useFuel(fuelUse);

					chosenObject.chosenUnit.regulateActive(false);
					chosenObject.chosenUnit = null;
					movementMap.clearMovementMap();
					routeHandler.clearArrowPoints();
				}

				unitMenuHandler.getUnitMenu().closeMenu();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.buySelectedTroop();
				buildingMenu.closeMenu();
			} else if (chosenObject.chosenUnit != null && movementMap.isAcceptedMove(cursorX, cursorY) && chosenObject.rangeUnit == null) {
				int x = chosenObject.chosenUnit.getPoint().getX();
				int y = chosenObject.chosenUnit.getPoint().getY();
				if (unitGetter.getFriendlyUnit(x, y) != null) {
					unitMenuHandler.handleOpenUnitMenu(cursorX, cursorY);
				}
			} else if (!unitSelected && !unitSelectable(cursorX, cursorY)) {
				chosenObject.selectedBuilding = buildingGetter.getFriendlyBuilding(cursorX, cursorY);

				if (chosenObject.selectedBuilding != null) {
					handleOpenBuildingMenu(cursorX, cursorY);
				}
			} else if (!unitSelected) {
				// @TODO 
				chosenObject.chosenUnit = unitGetter.getAnyUnit(cursorX, cursorY);

				if (chosenObject.chosenUnit != null) {
					routeChecker.findPossibleMovementLocations(chosenObject.chosenUnit);
					routeHandler.addNewArrowPoint(chosenObject.chosenUnit.getPoint());
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (containerUnitHandler.unitIsDroppingOff()) {
				int x = chosenObject.chosenUnit.getPoint().getX();
				int y = chosenObject.chosenUnit.getPoint().getY();
				cursor.setPosition(x, y);
				unitMenuHandler.handleOpenUnitMenu(x, y);

				if (chosenObject.chosenUnit instanceof APC) {
					((APC)chosenObject.chosenUnit).regulateDroppingOff(false);
				} else if (chosenObject.chosenUnit instanceof TCopter) {
					((TCopter)chosenObject.chosenUnit).regulateDroppingOff(false);
				} else if (chosenObject.chosenUnit instanceof Lander) {
					((Lander)chosenObject.chosenUnit).regulateDroppingOff(false);
				} else if (chosenObject.chosenUnit instanceof Cruiser) {
					((Cruiser)chosenObject.chosenUnit).regulateDroppingOff(false);
				}
			} else if (attackHandler.unitWantsToFire(chosenObject.chosenUnit)) {
				if (chosenObject.chosenUnit instanceof IndirectUnit) {
					((IndirectUnit)chosenObject.chosenUnit).clearFiringLocations();
				}
				int x = chosenObject.chosenUnit.getPoint().getX();
				int y = chosenObject.chosenUnit.getPoint().getY();
				cursor.setPosition(x, y);
				unitMenuHandler.handleOpenUnitMenu(x, y);

				chosenObject.chosenUnit.regulateAttack(false);
			} else if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.closeMenu();
			} else if (chosenObject.chosenUnit != null) {
				// the start-position of the unit before movement
				Point unitStartPoint = routeHandler.getRouteArrowPath().getArrowPoint(0);
				int unitStartX = unitStartPoint.getX();
				int unitStartY = unitStartPoint.getY();

				if (unitMenuHandler.getUnitMenu().isVisible()) {
					unitMenuHandler.getUnitMenu().closeMenu();
					chosenObject.chosenUnit.moveTo(unitStartX, unitStartY);
				} else {
					cursor.setPosition(unitStartX, unitStartY);
					chosenObject.chosenUnit.moveTo(unitStartX, unitStartY);
					chosenObject.chosenUnit = null;
					movementMap.clearMovementMap();
					routeHandler.clearArrowPoints();
				}
			} else {
				chosenObject.rangeUnit = unitGetter.getAnyUnit(cursorX, cursorY);

				if (chosenObject.rangeUnit != null) {
					if (chosenObject.rangeUnit.getAttackType() == AttackType.DIRECT_ATTACK) {
						attackHandler.getAttackRangeHandler().findPossibleDirectAttackLocations(chosenObject.rangeUnit);
					} else if (chosenObject.rangeUnit.getAttackType() == AttackType.INDIRECT_ATTACK) {
						attackHandler.getAttackRangeHandler().createRangeAttackLocations(chosenObject.rangeUnit);
					}
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (chosenObject.chosenUnit == null) {
				mapMenu.openMenu(cursorX, cursorY);
			}
		}
	}

	public void manageKeyReleasedInput(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (chosenObject.rangeUnit != null) {
				chosenObject.rangeUnit = null;
				attackRangeHandler.clearRangeMap();
			}
		}
	}
	
	private void handleOpenBuildingMenu(int cursorX, int cursorY) {
		if (chosenObject.selectedBuilding instanceof City/* || selectedBuilding instanceof HQ*/) {
			return;
		}
		buildingMenu.openMenu(cursorX, cursorY);
	}

	private void removeUnitIfDead(Unit unit) {
		if (!unit.getUnitHealth().isDead()) {
			return;
		}

		Hero unitsHero = heroHandler.getHeroFromUnit(unit);
		unitsHero.getTroopHandler().removeTroop(unit);
	}

	private boolean unitSelectable(int x, int y) {
		return unitGetter.getAnyUnit(x, y) != null;
	}
}
