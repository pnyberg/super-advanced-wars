/**
 * TODO:
 *  - can an APC enter a Lander?
 *  - can a T-Copter enter a Cruiser?
 */
package main;

import java.awt.event.KeyEvent;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.DamageHandler;
import cursors.Cursor;
import cursors.FiringCursorHandler;
import gameObjects.GameProp;
import graphics.MapViewType;
import graphics.ViewPainter;
import hero.Hero;
import hero.heroPower.HeroPowerHandler;
import map.GameMap;
import map.UnitGetter;
import map.area.TerrainType;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.buildings.City;
import map.structures.Structure;
import map.structures.StructureHandler;
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
	private ViewPainter viewPainter;
	private UnitGetter unitGetter;
	private BuildingHandler buildingHandler;
	private StructureHandler structureHandler;
	private Cursor cursor;
	private UnitMenuHandler unitMenuHandler;
	private GameMap gameMap;
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
	private HeroPowerHandler heroPowerHandler;
	private UnitWorthCalculator unitWorthCalculator;
	
	public KeyListenerInputHandler(GameProp gameProp, GameMap gameMap, ViewPainter viewPainter, UnitGetter unitGetter, BuildingHandler buildingHandler, StructureHandler structureHandler, Cursor cursor, UnitMenuHandler unitMenuHandler, MapMenu mapMenu, BuildingMenu buildingMenu, ContUnitHandler containerUnitHandler, AttackHandler attackHandler, AttackRangeHandler attackRangeHandler, MovementMap movementMap, RouteHandler routeHandler, RouteChecker routeChecker, DamageHandler damageHandler, HeroHandler heroHandler, SupplyHandler supplyHandler, TurnHandler turnHandler, UnitWorthCalculator unitWorthCalculator) {
		this.gameProp = gameProp;
		this.gameMap = gameMap;
		this.viewPainter = viewPainter;
		this.unitGetter = unitGetter;
		this.buildingHandler = buildingHandler;
		this.structureHandler = structureHandler;
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
		captHandler = new CaptHandler(heroHandler);
		this.supplyHandler = supplyHandler;
		this.turnHandler = turnHandler;
		firingCursorHandler = new FiringCursorHandler(gameProp, cursor, unitGetter, damageHandler);
		heroPowerHandler = new HeroPowerHandler(heroHandler);
		this.unitWorthCalculator = unitWorthCalculator;
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
					Point point = ((IndirectUnit)gameProp.getChosenObject().chosenUnit).getPreviousFiringLocation();
					cursor.setPosition(point.getX(), point.getY());
				} else {
					firingCursorHandler.moveFiringCursorCounterclockwise();
				}
			} else if (cursorY > 0 && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY - gameProp.getMapDim().tileSize), gameProp.getChosenObject().chosenUnit);
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
					Point point = ((IndirectUnit)gameProp.getChosenObject().chosenUnit).getNextFiringLocation();
					cursor.setPosition(point.getX(), point.getY());
				} else {
					firingCursorHandler.moveFiringCursorClockwise();
				}
			} else if (cursorY < (gameProp.getMapDim().getTileHeight() - 1) * gameProp.getMapDim().tileSize && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY + gameProp.getMapDim().tileSize), gameProp.getChosenObject().chosenUnit);
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
				routeHandler.updateArrowPath(new Point(cursorX - gameProp.getMapDim().tileSize, cursorY), gameProp.getChosenObject().chosenUnit);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (gameProp.getMapDim().getTileWidth() - 1) * gameProp.getMapDim().tileSize && !menuVisible && !containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
				routeHandler.updateArrowPath(new Point(cursorX + gameProp.getMapDim().tileSize, cursorY), gameProp.getChosenObject().chosenUnit);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			handlePressedKeyA();
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			handlePressedKeyB(cursor);
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (gameProp.getChosenObject().chosenUnit == null) {
				mapMenu.openMenu(cursorX, cursorY);
			}
		}
	}
	
	private void handlePressedKeyA() {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();
		boolean unitSelected = gameProp.getChosenObject().chosenUnit != null || gameProp.getChosenObject().rangeUnit != null;

		if (containerUnitHandler.unitIsDroppingOff()) {
			handleDroppingOff();
		} else if (attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
			Unit defendingUnit = unitGetter.getNonFriendlyUnit(cursor.getX(), cursor.getY());
			Structure targetStructure = structureHandler.getStructure(cursor.getX(), cursor.getY());
			if (defendingUnit != null) {
				damageHandler.handleAttackingUnit(gameProp.getChosenObject().chosenUnit, defendingUnit);
				removeUnitIfDead(defendingUnit);
			} else if (targetStructure != null){
				damageHandler.handleAttackingStructure(gameProp.getChosenObject().chosenUnit, targetStructure);
				removeStructureIfDestroyed(targetStructure);
			} else {
				System.err.println("No legal target to attack was used!");
			}

			gameProp.getChosenObject().chosenUnit.regulateAttack(false);

			int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
			cursor.setPosition(x, y);

			removeUnitIfDead(gameProp.getChosenObject().chosenUnit);

			int fuelUse = routeHandler.getFuelFromArrows(gameProp.getChosenObject().chosenUnit);
			gameProp.getChosenObject().chosenUnit.getUnitSupply().useFuel(fuelUse);

			gameProp.getChosenObject().chosenUnit.regulateActive(false);
			if (gameProp.getChosenObject().chosenUnit instanceof IndirectUnit) {
				((IndirectUnit)gameProp.getChosenObject().chosenUnit).clearFiringLocations();
			}
			gameProp.getChosenObject().chosenUnit = null;
			movementMap.clearMovementMap();
			routeHandler.clearArrowPoints();
			attackRangeHandler.clearRangeMap();
		} else if (mapMenu.isVisible()) {
			if (mapMenu.atCoRow()) {
				viewPainter.setViewType(MapViewType.CO_MAP_MENU_VIEW);
				mapMenu.closeMenu();
			} else if (mapMenu.atPowerRow()) {
				heroPowerHandler.handlePower();
				mapMenu.closeMenu();
			} else if (mapMenu.atSuperPowerRow()) {
				heroPowerHandler.handleSuperPower();
				mapMenu.closeMenu();
			} else if (mapMenu.atEndRow()) {
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
				
				int joinHp = unit.getUnitHealth().getShowHP() + gameProp.getChosenObject().chosenUnit.getUnitHealth().getShowHP();
				if (joinHp > 10) {
					int joinFunds = (joinHp - 10) * unitWorthCalculator.getFullHealthUnitWorth(unit) / 10;
					heroHandler.getCurrentHero().manageCash(joinFunds);
				}
				unit.getUnitHealth().heal(gameProp.getChosenObject().chosenUnit.getUnitHealth().getHP());
				gameProp.getChosenObject().chosenUnit.getUnitHealth().kill();
				removeUnitIfDead(gameProp.getChosenObject().chosenUnit);
			} else if (unitMenuHandler.getUnitMenu().atEnterRow()) {
				Unit entryUnit = unitGetter.getFriendlyUnitExceptSelf(gameProp.getChosenObject().chosenUnit, cursorX, cursorY);
				if (entryUnit instanceof APC) {
					((APC)entryUnit).addUnit(gameProp.getChosenObject().chosenUnit);
				} else if (entryUnit.hasUnitContainer()) {
					entryUnit.getUnitContainer().addUnit(gameProp.getChosenObject().chosenUnit);
				} else if (entryUnit instanceof Lander) {
					((Lander)entryUnit).addUnit(gameProp.getChosenObject().chosenUnit);
				} else if (entryUnit instanceof Cruiser) {
					((Cruiser)entryUnit).addUnit(gameProp.getChosenObject().chosenUnit);
				}

				// @TODO cargo-unit enters other unit
			} else if (unitMenuHandler.getUnitMenu().atFireRow()) {
				attackHandler.setUpFiringTargets(gameProp.getChosenObject().chosenUnit, cursor);
			} else if (unitMenuHandler.getUnitMenu().atCaptRow()) {
				Building building = buildingHandler.getBuilding(cursor.getX(), cursor.getY());
				captHandler.captBuilding(gameProp.getChosenObject().chosenUnit, building);
				if (building.captingIsActive()) {
					gameProp.getChosenObject().chosenUnit.regulateCapting(true);
				} else if (gameProp.getChosenObject().chosenUnit.isCapting()) {
					gameProp.getChosenObject().chosenUnit.regulateCapting(false);
				}
			} else if (unitMenuHandler.getUnitMenu().atSupplyRow()) {
				int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
				int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();

				replentishIfUnitAtPosition(x, y - gameProp.getMapDim().tileSize);
				replentishIfUnitAtPosition(x + gameProp.getMapDim().tileSize, y);
				replentishIfUnitAtPosition(x, y + gameProp.getMapDim().tileSize);
				replentishIfUnitAtPosition(x - gameProp.getMapDim().tileSize, y);
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
		} else if (gameProp.getChosenObject().chosenUnit != null && movementMap.isAcceptedMove(cursorX / gameProp.getMapDim().tileSize, cursorY / gameProp.getMapDim().tileSize) && gameProp.getChosenObject().rangeUnit == null) {
			int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
			if (unitGetter.getFriendlyUnit(x, y) != null) {
				unitMenuHandler.handleOpenUnitMenu(cursor);
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
	
	private void replentishIfUnitAtPosition(int x, int y) {
		Unit unit = unitGetter.getFriendlyUnit(x, y);
		if (unit != null) {
			supplyHandler.replentishUnit(unit);
		}
	}
	
	private void handleDroppingOff() {
		if (containerUnitHandler.unitCanBeDroppedOff()) {
			if (gameProp.getChosenObject().chosenUnit instanceof APC) {
				((APC)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
				Unit exitingUnit = ((APC)gameProp.getChosenObject().chosenUnit).removeUnit();
				exitingUnit.moveTo(cursor.getX(), cursor.getY());
				exitingUnit.regulateActive(false);
			} else if (gameProp.getChosenObject().chosenUnit.hasUnitContainer()) {
				gameProp.getChosenObject().chosenUnit.getUnitContainer().regulateDroppingOff(false);
				Unit exitingUnit = gameProp.getChosenObject().chosenUnit.getUnitContainer().removeChosenUnit();
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

			int fuelUse = routeHandler.getFuelFromArrows(gameProp.getChosenObject().chosenUnit);
			gameProp.getChosenObject().chosenUnit.getUnitSupply().useFuel(fuelUse);

			gameProp.getChosenObject().chosenUnit.regulateActive(false);
			gameProp.getChosenObject().chosenUnit = null;
			movementMap.clearMovementMap();
			routeHandler.clearArrowPoints();
		} else {
			// If all drop-slots are occupied, pressing 'A' won't do anything
		}
	}

	private void handlePressedKeyB(Cursor cursor) {
		if (viewPainter.getMapViewType() == MapViewType.CO_MAP_MENU_VIEW) {
			viewPainter.setViewType(MapViewType.MAIN_MAP_MENU_VIEW);
		} else if (containerUnitHandler.unitIsDroppingOff()) {
			int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
			cursor.setPosition(x, y);
			unitMenuHandler.handleOpenUnitMenu(cursor);
	
			if (gameProp.getChosenObject().chosenUnit instanceof APC) {
				((APC)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
			} else if (gameProp.getChosenObject().chosenUnit.hasUnitContainer()) {
				gameProp.getChosenObject().chosenUnit.getUnitContainer().regulateDroppingOff(false);
			} else if (gameProp.getChosenObject().chosenUnit instanceof Lander) {
				((Lander)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
			} else if (gameProp.getChosenObject().chosenUnit instanceof Cruiser) {
				((Cruiser)gameProp.getChosenObject().chosenUnit).regulateDroppingOff(false);
			}
		} else if (attackHandler.unitWantsToFire(gameProp.getChosenObject().chosenUnit)) {
			if (gameProp.getChosenObject().chosenUnit instanceof IndirectUnit) {
				((IndirectUnit)gameProp.getChosenObject().chosenUnit).clearFiringLocations();
			}
			attackRangeHandler.clearRangeMap();
			int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();
			cursor.setPosition(x, y);
			unitMenuHandler.handleOpenUnitMenu(cursor);
	
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
		} else if (gameMap.getMap()[cursor.getX() / gameProp.getMapDim().tileSize][cursor.getY() / gameProp.getMapDim().tileSize].getTerrainType() == TerrainType.MINI_CANNON) {
			gameProp.getChosenObject().rangeStructure = structureHandler.getFiringStructure(cursor.getX(), cursor.getY());
			attackRangeHandler.importStructureAttackLocations(gameProp.getChosenObject().rangeStructure);
		} else {
			gameProp.getChosenObject().rangeUnit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
	
			if (gameProp.getChosenObject().rangeUnit != null) {
				if (gameProp.getChosenObject().rangeUnit.getAttackType() == AttackType.DIRECT_ATTACK) {
					attackRangeHandler.findPossibleDirectAttackLocations(gameProp.getChosenObject().rangeUnit);
				} else if (gameProp.getChosenObject().rangeUnit.getAttackType() == AttackType.INDIRECT_ATTACK) {
					attackRangeHandler.createRangeAttackLocations(gameProp.getChosenObject().rangeUnit);
				}
			}
		}
	}

	public void manageKeyReleasedInput(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (gameProp.getChosenObject().rangeUnit != null || gameProp.getChosenObject().rangeStructure != null) {
				gameProp.getChosenObject().rangeUnit = null;
				gameProp.getChosenObject().rangeStructure = null;
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
		if (unit.getUnitHealth().isDead()) {
			Hero unitsHero = heroHandler.getHeroFromUnit(unit);
			unitsHero.getTroopHandler().removeTroop(unit);
		}
	}
	
	private void removeStructureIfDestroyed(Structure targetStructure) {
		if (targetStructure.isDestroyed()) {
			int tileX = targetStructure.getPoint().getX() / gameProp.getMapDim().tileSize;
			int tileY = targetStructure.getPoint().getY() / gameProp.getMapDim().tileSize;
			gameMap.getMap()[tileX][tileY].setTerrainType(TerrainType.UMI);
			structureHandler.removeStructure(targetStructure);
		}
	}

	private boolean unitSelectable(int x, int y) {
		return unitGetter.getAnyUnit(x, y) != null;
	}
}
