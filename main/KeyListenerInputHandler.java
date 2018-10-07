package main;

import java.awt.event.KeyEvent;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.DamageHandler;
import cursors.Cursor;
import cursors.FiringCursorHandler;
import gameObjects.ChosenObject;
import gameObjects.GameProp;
import gameObjects.MapDim;
import heroes.Hero;
import map.UnitGetter;
import map.buildings.BuildingHandler;
import map.buildings.City;
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
	private GameProp gameProp;
	private UnitGetter unitGetter;
	private BuildingHandler buildingHandler;
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
	private CaptHandler captHandler;
	private SupplyHandler supplyHandler;
	private TurnHandler turnHandler;
	private FiringCursorHandler firingCursorHandler;
	
	public KeyListenerInputHandler(GameProp gameProp, UnitGetter unitGetter, BuildingHandler buildingHandler, Cursor cursor, UnitMenuHandler unitMenuHandler, MapMenu mapMenu, BuildingMenu buildingMenu, ContUnitHandler containerUnitHandler, AttackHandler attackHandler, AttackRangeHandler attackRangeHandler, MovementMap movementMap, RouteHandler routeHandler, RouteChecker routeChecker, DamageHandler damageHandler, HeroHandler heroHandler, SupplyHandler supplyHandler, TurnHandler turnHandler) {
		this.gameProp = gameProp;
		this.unitGetter = unitGetter;
		this.buildingHandler = buildingHandler;
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
		captHandler = new CaptHandler(buildingHandler, heroHandler);
		this.supplyHandler = supplyHandler;
		this.turnHandler = turnHandler;
		firingCursorHandler = new FiringCursorHandler(gameProp, cursor, unitGetter, damageHandler);
	}
	
	public void manageKeyPressedInput(KeyEvent e) {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		boolean menuVisible = mapMenu.isVisible() || unitMenuHandler.getUnitMenu().isVisible() || buildingMenu.isVisible();

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (containerUnitHandler.unitIsDroppingOff()) {
				if (containerUnitHandler.unitCanBeDroppedOff()) {
					containerUnitHandler.moveDroppingOffCursorCounterclockwise();
				}
			} else if (attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
				if (gameProp.getChosenObject().chosenUnit instanceof IndirectUnit) {
					Point p = ((IndirectUnit)gameProp.getChosenObject().chosenUnit).getPreviousFiringLocation();
					cursorX = p.getX();
					cursorY = p.getY();
					cursor.setPosition(cursorX, cursorY);
				} else {
					firingCursorHandler.moveFiringCursorCounterclockwise();
				}
			} else if (cursorY > 0 && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY - 1), gameProp.getChosenObject().chosenUnit);
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
			} else if (attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
				if (gameProp.getChosenObject().chosenUnit instanceof IndirectUnit) {
					Point p = ((IndirectUnit)gameProp.getChosenObject().chosenUnit).getNextFiringLocation();
					cursorX = p.getX();
					cursorY = p.getY();
					cursor.setPosition(cursorX, cursorY);
				} else {
					firingCursorHandler.moveFiringCursorClockwise();
				}
			} else if (cursorY < (gameProp.getMapDim().height - 1) && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY + 1), gameProp.getChosenObject().chosenUnit);
				cursor.moveDown();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowDown();
			} else if (unitMenuHandler.getUnitMenu().isVisible()) {
				unitMenuHandler.getUnitMenu().moveArrowDown();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0 && !menuVisible && !containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
				routeHandler.updateArrowPath(new Point(cursorX - 1, cursorY), gameProp.getChosenObject().chosenUnit);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (gameProp.getMapDim().width - 1) && !menuVisible && !containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
				routeHandler.updateArrowPath(new Point(cursorX + 1, cursorY), gameProp.getChosenObject().chosenUnit);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			handleAcceptKeyInput();
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (containerUnitHandler.unitIsDroppingOff()) {
				int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
				int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
				cursor.setPosition(x, y);
				unitMenuHandler.handleOpenUnitMenu(x, y);

				if (gameProp.getChosenObject().chosenUnit instanceof APC) {
					((APC)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
				} else if (gameProp.getChosenObject().chosenUnit instanceof TCopter) {
					((TCopter)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
				} else if (gameProp.getChosenObject().chosenUnit instanceof Lander) {
					((Lander)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
				} else if (gameProp.getChosenObject().chosenUnit instanceof Cruiser) {
					((Cruiser)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
				}
			} else if (attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
				if (gameProp.getChosenObject().chosenUnit instanceof IndirectUnit) {
					((IndirectUnit)gameProp.getChosenObject().chosenUnit).clearFiringLocations();
				}
				int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
				int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
				cursor.setPosition(x, y);
				unitMenuHandler.handleOpenUnitMenu(x, y);

				gameProp.getChosenObject().chosenUnit.regulateAttack(false);
			} else if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.closeMenu();
			} else if (gameProp.getChosenObject().chosenUnit != null) {
				// the start-position of the unit before movement
				Point unitStartPoint = routeHandler.getRouteArrowPath().getArrowPoint(0);
				int unitStartX = unitStartPoint.getX();
				int unitStartY = unitStartPoint.getY();

				if (unitMenuHandler.getUnitMenu().isVisible()) {
					unitMenuHandler.getUnitMenu().closeMenu();
					gameProp.getChosenObject().chosenUnit.moveTo(unitStartX, unitStartY);
				} else {
					cursor.setPosition(unitStartX, unitStartY);
					gameProp.getChosenObject().chosenUnit.moveTo(unitStartX, unitStartY);
					gameProp.getChosenObject().chosenUnit = null;
					movementMap.clearMovementMap();
					routeHandler.clearArrowPoints();
				}
			} else {
				gameProp.getChosenObject().rangeUnit = unitGetter.getAnyUnit(cursorX, cursorY);

				if (gameProp.getChosenObject().rangeUnit != null) {
					if (gameProp.getChosenObject().rangeUnit.getAttackType() == AttackType.DIRECT_ATTACK) {
						attackHandler.getAttackRangeHandler().findPossibleDirectAttackLocations(gameProp.getChosenObject().rangeUnit);
					} else if (gameProp.getChosenObject().rangeUnit.getAttackType() == AttackType.INDIRECT_ATTACK) {
						attackHandler.getAttackRangeHandler().createRangeAttackLocations(gameProp.getChosenObject().rangeUnit);
					}
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (gameProp.getChosenObject().chosenUnit == null) {
				mapMenu.openMenu(cursorX, cursorY);
			}
		}
	}
	
	private void handleAcceptKeyInput() {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();
		boolean unitSelected = gameProp.getChosenObject().chosenUnit != null || gameProp.getChosenObject().rangeUnit != null;

		if (containerUnitHandler.unitIsDroppingOff()) {
			if (containerUnitHandler.unitCanBeDroppedOff()) {
				if (gameProp.getChosenObject().chosenUnit instanceof APC) {
					((APC)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
					Unit exitingUnit = ((APC)gameProp.getChosenObject().chosenUnit).removeUnit();
					exitingUnit.moveTo(cursor.getX(), cursor.getY());
					exitingUnit.regulateActive(false);
				} else if (gameProp.getChosenObject().chosenUnit instanceof TCopter) {
					((TCopter)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
					Unit exitingUnit = ((TCopter)gameProp.getChosenObject().chosenUnit).removeUnit();
					exitingUnit.moveTo(cursor.getX(), cursor.getY());
					exitingUnit.regulateActive(false);
				} else if (gameProp.getChosenObject().chosenUnit instanceof Lander) {
					((Lander)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
					Unit exitingUnit = ((Lander)gameProp.getChosenObject().chosenUnit).removeChosenUnit();
					exitingUnit.moveTo(cursor.getX(), cursor.getY());
					exitingUnit.regulateActive(false);
				} else if (gameProp.getChosenObject().chosenUnit instanceof Cruiser) {
					((Cruiser)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
					Unit exitingUnit = ((Cruiser)gameProp.getChosenObject().chosenUnit).removeChosenUnit();
					exitingUnit.moveTo(cursor.getX(), cursor.getY());
					exitingUnit.regulateActive(false);
				}

				int fuelUse = routeHandler.getFuelFromArrows(gameProp.getChosenObject().chosenUnit);;
				gameProp.getChosenObject().chosenUnit.getUnitSupply().useFuel(fuelUse);

				gameProp.getChosenObject().chosenUnit.regulateActive(false);
				gameProp.getChosenObject().chosenUnit = null;
				movementMap.clearMovementMap();
				routeHandler.clearArrowPoints();
			} else {
				// If all drop-slots are occupied, pressing 'A' won't do anything
			}
		} else if (attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
			Unit defendingUnit = unitGetter.getNonFriendlyUnit(cursorX, cursorY);
			damageHandler.handleAttack(gameProp.getChosenObject().chosenUnit, defendingUnit);
			gameProp.getChosenObject().chosenUnit.regulateAttack(false);

			int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
			cursor.setPosition(x, y);

			removeUnitIfDead(defendingUnit);
			removeUnitIfDead(gameProp.getChosenObject().chosenUnit);

			int fuelUse = routeHandler.getFuelFromArrows(gameProp.getChosenObject().chosenUnit);;
			gameProp.getChosenObject().chosenUnit.getUnitSupply().useFuel(fuelUse);

			gameProp.getChosenObject().chosenUnit.regulateActive(false);
			gameProp.getChosenObject().chosenUnit = null;
			movementMap.clearMovementMap();
			routeHandler.clearArrowPoints();
			if (gameProp.getChosenObject().chosenUnit instanceof IndirectUnit) {
				((IndirectUnit)gameProp.getChosenObject().chosenUnit).clearFiringLocations();
			}
		} else if (mapMenu.isVisible()) {
			if (mapMenu.atEndRow()) {
				turnHandler.endTurn();
			}
		} else if (unitMenuHandler.getUnitMenu().isVisible()) {
			if (unitMenuHandler.getUnitMenu().atUnitRow()) {
				if (gameProp.getChosenObject().chosenUnit instanceof Lander) {
					int index = unitMenuHandler.getUnitMenu().getMenuIndex();
					((Lander)gameProp.getChosenObject().chosenUnit).chooseUnit(index);
				} else if (gameProp.getChosenObject().chosenUnit instanceof Cruiser) {
					int index = unitMenuHandler.getUnitMenu().getMenuIndex();
					((Cruiser)gameProp.getChosenObject().chosenUnit).chooseUnit(index);
				}
				containerUnitHandler.handleDroppingOff();
			} else if (unitMenuHandler.getUnitMenu().atJoinRow()) {
				int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
				int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
				Unit unit = unitGetter.getFriendlyUnitExceptSelf(gameProp.getChosenObject().chosenUnit, x, y);
				
				unit.getUnitHealth().heal(gameProp.getChosenObject().chosenUnit.getUnitHealth().getHP());
				gameProp.getChosenObject().chosenUnit.getUnitHealth().kill();
				removeUnitIfDead(gameProp.getChosenObject().chosenUnit);
			} else if (unitMenuHandler.getUnitMenu().atEnterRow()) {
				Unit entryUnit = unitGetter.getFriendlyUnitExceptSelf(gameProp.getChosenObject().chosenUnit, cursorX, cursorY);
				if (entryUnit instanceof APC) {
					((APC)entryUnit).addUnit(gameProp.getChosenObject().chosenUnit);
				} else if (entryUnit instanceof TCopter) {
					((TCopter)entryUnit).addUnit(gameProp.getChosenObject().chosenUnit);
				} else if (entryUnit instanceof Lander) {
					((Lander)entryUnit).addUnit(gameProp.getChosenObject().chosenUnit);
				} else if (entryUnit instanceof Cruiser) {
					((Cruiser)entryUnit).addUnit(gameProp.getChosenObject().chosenUnit);
				}

				// @TODO cargo-unit enters other unit
			} else if (unitMenuHandler.getUnitMenu().atFireRow()) {
				attackHandler.handleFiring(gameProp.getChosenObject().chosenUnit, cursor);
			} else if (unitMenuHandler.getUnitMenu().atCaptRow()) {
				captHandler.captBuilding(gameProp.getChosenObject().chosenUnit, cursor);
			} else if (unitMenuHandler.getUnitMenu().atSupplyRow()) {
				int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
				int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();

				Unit north = unitGetter.getFriendlyUnit(x, y - 1);
				Unit east = unitGetter.getFriendlyUnit(x + 1, y);
				Unit south = unitGetter.getFriendlyUnit(x, y + 1);
				Unit west = unitGetter.getFriendlyUnit(x - 1, y);

				supplyHandler.replentishUnit(north);
				supplyHandler.replentishUnit(east);
				supplyHandler.replentishUnit(south);
				supplyHandler.replentishUnit(west);
			}

			if (!containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
				// using fuel
				int fuelUse = routeHandler.getFuelFromArrows(gameProp.getChosenObject().chosenUnit);
				gameProp.getChosenObject().chosenUnit.getUnitSupply().useFuel(fuelUse);

				gameProp.getChosenObject().chosenUnit.regulateActive(false);
				gameProp.getChosenObject().chosenUnit = null;
				movementMap.clearMovementMap();
				routeHandler.clearArrowPoints();
			}

			unitMenuHandler.getUnitMenu().closeMenu();
		} else if (buildingMenu.isVisible()) {
			buildingMenu.buySelectedTroop();
			buildingMenu.closeMenu();
		} else if (gameProp.getChosenObject().chosenUnit != null && movementMap.isAcceptedMove(cursorX, cursorY) && gameProp.getChosenObject().rangeUnit == null) {
			int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
			if (unitGetter.getFriendlyUnit(x, y) != null) {
				unitMenuHandler.handleOpenUnitMenu(cursorX, cursorY);
			}
		} else if (!unitSelected && !unitSelectable(cursorX, cursorY)) {
			gameProp.getChosenObject().selectedBuilding = buildingHandler.getFriendlyBuilding(cursorX, cursorY);

			if (gameProp.getChosenObject().selectedBuilding != null) {
				handleOpenBuildingMenu(cursorX, cursorY);
			}
		} else if (!unitSelected) {
			gameProp.getChosenObject().chosenUnit = unitGetter.getAnyUnit(cursorX, cursorY);

			if (gameProp.getChosenObject().chosenUnit != null) {
				routeChecker.findPossibleMovementLocations(gameProp.getChosenObject().chosenUnit);
				routeHandler.addNewArrowPoint(gameProp.getChosenObject().chosenUnit.getPoint());
			}
		}
	}

	public void manageKeyReleasedInput(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (gameProp.getChosenObject().rangeUnit != null) {
				gameProp.getChosenObject().rangeUnit = null;
				attackRangeHandler.clearRangeMap();
			}
		}
	}
	
	private void handleOpenBuildingMenu(int cursorX, int cursorY) {
		if (gameProp.getChosenObject().selectedBuilding instanceof City/* || selectedBuilding instanceof HQ*/) {
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
