/**
 * TODO:
 *  - rewrite code to make it more readable
 */
package main;

import java.awt.event.KeyEvent;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.AttackValueCalculator;
import combat.DamageHandler;
import combat.DefenceValueCalculator;
import cursors.Cursor;
import cursors.FiringCursor;
import cursors.FiringCursorHandler;
import gameObjects.GameMapAndCursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import graphics.CommanderView;
import graphics.MapViewType;
import graphics.ViewPainter;
import hero.Hero;
import hero.heroPower.HeroPowerHandler;
import map.BuildingStructureHandlerObject;
import map.GameMap;
import map.UnitGetter;
import map.area.AreaChecker;
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
import routing.MoveabilityMatrixFactory;
import routing.MovementCostCalculator;
import routing.MovementMap;
import routing.RouteChecker;
import routing.RouteHandler;
import unitUtils.AttackType;
import unitUtils.ContUnitHandler;
import unitUtils.UnitWorthCalculator;
import units.*;
import units.airMoving.*;
import units.seaMoving.*;
import units.treadMoving.*;

public class KeyListenerInputHandler {
	private GameProperties gameProp;
	private GameState gameState;
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
	private CaptHandler captHandler;
	private SupplyHandler supplyHandler;
	private TurnHandler turnHandler;
	private FiringCursorHandler firingCursorHandler;
	private HeroPowerHandler heroPowerHandler;
	private UnitWorthCalculator unitWorthCalculator;
	
	public KeyListenerInputHandler(GameProperties gameProperties, GameState gameState, MapMenu mapMenu, TurnHandler turnHandler) {
		this.gameProp = gameProperties;
		this.gameState = gameState;
		this.gameMap = gameProperties.getGameMap();
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
		this.buildingHandler = new BuildingHandler(gameState);
		this.structureHandler = new StructureHandler(gameState, gameProperties.getMapDimension());
		this.cursor = gameState.getCursor();
		this.mapMenu = mapMenu;
		captHandler = new CaptHandler(gameState.getHeroHandler());
		this.turnHandler = turnHandler;
	
		int tileSize = gameProperties.getMapDimension().tileSize;
		
		attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		viewPainter = new ViewPainter(gameProperties, gameState);
		routeChecker = new RouteChecker(gameProperties, gameState);
		
		// no previously required init
		this.cursor = gameState.getCursor();
		GameMap gameMap = gameProperties.getGameMap();
		movementMap = gameState.getMovementMap();

		// required init from first init-round
		AreaChecker areaChecker = new AreaChecker(gameState.getHeroHandler(), gameMap);
		buildingMenu = new BuildingMenu(tileSize, gameState.getHeroHandler(), gameMap);
		damageHandler = new DamageHandler(gameState.getHeroHandler(), gameMap);
		MovementCostCalculator movementCostCalculator = new MovementCostCalculator(gameMap);
		supplyHandler = new SupplyHandler(gameState, tileSize);

		// required init from second init-round
		routeHandler = new RouteHandler(gameProperties.getMapDimension(), movementMap, movementCostCalculator);

		// required init from third init-round
		containerUnitHandler = new ContUnitHandler(gameProperties, gameState);

		// required init from fourth init-round
		attackHandler = new AttackHandler(gameProperties, gameState);
		unitMenuHandler = new UnitMenuHandler(gameProperties, gameState, containerUnitHandler, supplyHandler, areaChecker, attackRangeHandler);

		firingCursorHandler = new FiringCursorHandler(gameProperties, gameState, cursor, unitGetter, damageHandler);
		heroPowerHandler = new HeroPowerHandler(gameState.getHeroHandler());
		unitWorthCalculator = new UnitWorthCalculator();
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
			} else if (attackHandler.unitWantsToFire(gameState.getChosenObject().chosenUnit)) {
				if (gameState.getChosenObject().chosenUnit instanceof IndirectUnit) {
					Point point = ((IndirectUnit)gameState.getChosenObject().chosenUnit).getPreviousFiringLocation();
					cursor.setPosition(point.getX(), point.getY());
				} else {
					firingCursorHandler.moveFiringCursorCounterclockwise();
				}
			} else if (cursorY > 0 && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY - gameProp.getMapDimension().tileSize), gameState.getChosenObject().chosenUnit);
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
			} else if (attackHandler.unitWantsToFire(gameState.getChosenObject().chosenUnit)) {
				if (gameState.getChosenObject().chosenUnit instanceof IndirectUnit) {
					Point point = ((IndirectUnit)gameState.getChosenObject().chosenUnit).getNextFiringLocation();
					cursor.setPosition(point.getX(), point.getY());
				} else {
					firingCursorHandler.moveFiringCursorClockwise();
				}
			} else if (cursorY < (gameProp.getMapDimension().getTileHeight() - 1) * gameProp.getMapDimension().tileSize && !menuVisible) {
				routeHandler.updateArrowPath(new Point(cursorX, cursorY + gameProp.getMapDimension().tileSize), gameState.getChosenObject().chosenUnit);
				cursor.moveDown();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowDown();
			} else if (unitMenuHandler.getUnitMenu().isVisible()) {
				unitMenuHandler.getUnitMenu().moveArrowDown();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0 && !menuVisible && !containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(gameState.getChosenObject().chosenUnit)) {
				routeHandler.updateArrowPath(new Point(cursorX - gameProp.getMapDimension().tileSize, cursorY), gameState.getChosenObject().chosenUnit);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (gameProp.getMapDimension().getTileWidth() - 1) * gameProp.getMapDimension().tileSize && !menuVisible && !containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(gameState.getChosenObject().chosenUnit)) {
				routeHandler.updateArrowPath(new Point(cursorX + gameProp.getMapDimension().tileSize, cursorY), gameState.getChosenObject().chosenUnit);
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
			} else if (gameState.getChosenObject().chosenUnit == null) {
				mapMenu.openMenu(cursorX, cursorY);
			}
		}
	}
	
	private void handlePressedKeyA() {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();
		boolean unitSelected = gameState.getChosenObject().chosenUnit != null || gameState.getChosenObject().rangeUnit != null;

		if (containerUnitHandler.unitIsDroppingOff()) {
			handleDroppingOff();
		} else if (attackHandler.unitWantsToFire(gameState.getChosenObject().chosenUnit)) {
			Unit defendingUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY());
			Structure targetStructure = structureHandler.getStructure(cursor.getX(), cursor.getY());
			if (defendingUnit != null) {
				damageHandler.handleAttackingUnit(gameState.getChosenObject().chosenUnit, defendingUnit);
				removeUnitIfDead(defendingUnit);
			} else if (targetStructure != null){
				damageHandler.handleAttackingStructure(gameState.getChosenObject().chosenUnit, targetStructure);
				removeStructureIfDestroyed(targetStructure);
			} else {
				System.err.println("No legal target to attack was used!");
			}

			gameState.getChosenObject().chosenUnit.regulateAttack(false);

			int x = gameState.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameState.getChosenObject().chosenUnit.getPoint().getY();
			cursor.setPosition(x, y);

			removeUnitIfDead(gameState.getChosenObject().chosenUnit);

			int fuelUse = routeHandler.getFuelFromArrows(gameState.getChosenObject().chosenUnit);
			gameState.getChosenObject().chosenUnit.getUnitSupply().useFuel(fuelUse);

			gameState.getChosenObject().chosenUnit.regulateActive(false);
			if (gameState.getChosenObject().chosenUnit instanceof IndirectUnit) {
				((IndirectUnit)gameState.getChosenObject().chosenUnit).clearFiringLocations();
			}
			gameState.getChosenObject().chosenUnit = null;
			movementMap.clearMovementMap();
			routeHandler.clearArrowPoints();
			attackRangeHandler.clearRangeMap();
		} else if (mapMenu.isVisible()) {
			if (mapMenu.atCoRow()) {
				viewPainter.setViewType(MapViewType.CO_MAP_MENU_VIEW);
				mapMenu.closeMenu();
			} else if (mapMenu.atPowerRow()) {
				heroPowerHandler.activatePower();
				mapMenu.closeMenu();
			} else if (mapMenu.atSuperPowerRow()) {
				heroPowerHandler.activateSuperPower();
				mapMenu.closeMenu();
			} else if (mapMenu.atEndRow()) {
				mapMenu.closeMenu();
				turnHandler.endTurn();
			}
		} else if (unitMenuHandler.getUnitMenu().isVisible()) {
			if (unitMenuHandler.getUnitMenu().atUnitRow()) {
				if (gameState.getChosenObject().chosenUnit instanceof Lander) {
					int index = unitMenuHandler.getUnitMenu().getMenuIndex();
					((Lander)gameState.getChosenObject().chosenUnit).chooseUnit(index);
				} else if (gameState.getChosenObject().chosenUnit instanceof Cruiser) {
					int index = unitMenuHandler.getUnitMenu().getMenuIndex();
					((Cruiser)gameState.getChosenObject().chosenUnit).chooseUnit(index);
				}
				containerUnitHandler.handleDroppingOff();
			} else if (unitMenuHandler.getUnitMenu().atJoinRow()) {
				int x = gameState.getChosenObject().chosenUnit.getPoint().getX();
				int y = gameState.getChosenObject().chosenUnit.getPoint().getY();
				Unit unit = unitGetter.getFriendlyUnitExceptSelf(gameState.getChosenObject().chosenUnit, x, y);
				
				int joinHp = unit.getUnitHealth().getShowHP() + gameState.getChosenObject().chosenUnit.getUnitHealth().getShowHP();
				if (joinHp > 10) {
					int joinFunds = (joinHp - 10) * unitWorthCalculator.getFullHealthUnitWorth(unit) / 10;
					gameState.getHeroHandler().getCurrentHero().manageCash(joinFunds);
				}
				unit.getUnitHealth().heal(gameState.getChosenObject().chosenUnit.getUnitHealth().getHP());
				gameState.getChosenObject().chosenUnit.getUnitHealth().kill();
				removeUnitIfDead(gameState.getChosenObject().chosenUnit);
			} else if (unitMenuHandler.getUnitMenu().atEnterRow()) {
				Unit entryUnit = unitGetter.getFriendlyUnitExceptSelf(gameState.getChosenObject().chosenUnit, cursorX, cursorY);
				if (entryUnit instanceof APC) {
					((APC)entryUnit).addUnit(gameState.getChosenObject().chosenUnit);
				} else if (entryUnit.hasUnitContainer()) {
					entryUnit.getUnitContainer().addUnit(gameState.getChosenObject().chosenUnit);
				} else if (entryUnit instanceof Lander) {
					((Lander)entryUnit).addUnit(gameState.getChosenObject().chosenUnit);
				} else if (entryUnit instanceof Cruiser) {
					((Cruiser)entryUnit).addUnit(gameState.getChosenObject().chosenUnit);
				}

				// @TODO cargo-unit enters other unit
			} else if (unitMenuHandler.getUnitMenu().atFireRow()) {
				attackHandler.setUpFiringTargets(gameState.getChosenObject().chosenUnit, cursor);
			} else if (unitMenuHandler.getUnitMenu().atCaptRow()) {
				Building building = buildingHandler.getBuilding(cursor.getX(), cursor.getY());
				captHandler.captBuilding(gameState.getChosenObject().chosenUnit, building);
				if (building.captingIsActive()) {
					gameState.getChosenObject().chosenUnit.regulateCapting(true);
				} else if (gameState.getChosenObject().chosenUnit.isCapting()) {
					gameState.getChosenObject().chosenUnit.regulateCapting(false);
				}
			} else if (unitMenuHandler.getUnitMenu().atSupplyRow()) {
				int x = gameState.getChosenObject().chosenUnit.getPoint().getX();
				int y = gameState.getChosenObject().chosenUnit.getPoint().getY();

				replentishIfUnitAtPosition(x, y - gameProp.getMapDimension().tileSize);
				replentishIfUnitAtPosition(x + gameProp.getMapDimension().tileSize, y);
				replentishIfUnitAtPosition(x, y + gameProp.getMapDimension().tileSize);
				replentishIfUnitAtPosition(x - gameProp.getMapDimension().tileSize, y);
			}

			if (!containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(gameState.getChosenObject().chosenUnit)) {
				// using fuel
				int fuelUse = routeHandler.getFuelFromArrows(gameState.getChosenObject().chosenUnit);
				gameState.getChosenObject().chosenUnit.getUnitSupply().useFuel(fuelUse);

				gameState.getChosenObject().chosenUnit.regulateActive(false);
				gameState.getChosenObject().chosenUnit = null;
				movementMap.clearMovementMap();
				routeHandler.clearArrowPoints();
			}

			unitMenuHandler.getUnitMenu().closeMenu();
		} else if (buildingMenu.isVisible()) {
			buildingMenu.buySelectedTroop();
			buildingMenu.closeMenu();
		} else if (gameState.getChosenObject().chosenUnit != null && movementMap.isAcceptedMove(cursorX / gameProp.getMapDimension().tileSize, cursorY / gameProp.getMapDimension().tileSize) && gameState.getChosenObject().rangeUnit == null) {
			int x = gameState.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameState.getChosenObject().chosenUnit.getPoint().getY();
			if (unitGetter.getFriendlyUnit(x, y) != null) {
				unitMenuHandler.handleOpenUnitMenu(cursor);
			}
		} else if (!unitSelected && !unitSelectable(cursorX, cursorY)) {
			gameState.getChosenObject().selectedBuilding = buildingHandler.getFriendlyBuilding(cursorX, cursorY);

			if (gameState.getChosenObject().selectedBuilding != null) {
				handleOpenBuildingMenu(cursorX, cursorY);
			}
		} else if (!unitSelected) {
			gameState.getChosenObject().chosenUnit = unitGetter.getAnyUnit(cursorX, cursorY);

			if (gameState.getChosenObject().chosenUnit != null) {
				routeChecker.findPossibleMovementLocations(gameState.getChosenObject().chosenUnit);
				routeHandler.addNewArrowPoint(gameState.getChosenObject().chosenUnit.getPoint());
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
			if (gameState.getChosenObject().chosenUnit instanceof APC) {
				((APC)gameState.getChosenObject().chosenUnit).regulateDroppingOff(false);
				Unit exitingUnit = ((APC)gameState.getChosenObject().chosenUnit).removeUnit();
				exitingUnit.moveTo(cursor.getX(), cursor.getY());
				exitingUnit.regulateActive(false);
			} else if (gameState.getChosenObject().chosenUnit.hasUnitContainer()) {
				gameState.getChosenObject().chosenUnit.getUnitContainer().regulateDroppingOff(false);
				Unit exitingUnit = gameState.getChosenObject().chosenUnit.getUnitContainer().removeChosenUnit();
				exitingUnit.moveTo(cursor.getX(), cursor.getY());
				exitingUnit.regulateActive(false);
			} else if (gameState.getChosenObject().chosenUnit instanceof Lander) {
				((Lander)gameState.getChosenObject().chosenUnit).regulateDroppingOff(false);
				Unit exitingUnit = ((Lander)gameState.getChosenObject().chosenUnit).removeChosenUnit();
				exitingUnit.moveTo(cursor.getX(), cursor.getY());
				exitingUnit.regulateActive(false);
			} else if (gameState.getChosenObject().chosenUnit instanceof Cruiser) {
				((Cruiser)gameState.getChosenObject().chosenUnit).regulateDroppingOff(false);
				Unit exitingUnit = ((Cruiser)gameState.getChosenObject().chosenUnit).removeChosenUnit();
				exitingUnit.moveTo(cursor.getX(), cursor.getY());
				exitingUnit.regulateActive(false);
			}

			int fuelUse = routeHandler.getFuelFromArrows(gameState.getChosenObject().chosenUnit);
			gameState.getChosenObject().chosenUnit.getUnitSupply().useFuel(fuelUse);

			gameState.getChosenObject().chosenUnit.regulateActive(false);
			gameState.getChosenObject().chosenUnit = null;
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
			int x = gameState.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameState.getChosenObject().chosenUnit.getPoint().getY();
			cursor.setPosition(x, y);
			unitMenuHandler.handleOpenUnitMenu(cursor);
	
			if (gameState.getChosenObject().chosenUnit instanceof APC) {
				((APC)gameState.getChosenObject().chosenUnit).regulateDroppingOff(false);
			} else if (gameState.getChosenObject().chosenUnit.hasUnitContainer()) {
				gameState.getChosenObject().chosenUnit.getUnitContainer().regulateDroppingOff(false);
			} else if (gameState.getChosenObject().chosenUnit instanceof Lander) {
				((Lander)gameState.getChosenObject().chosenUnit).regulateDroppingOff(false);
			} else if (gameState.getChosenObject().chosenUnit instanceof Cruiser) {
				((Cruiser)gameState.getChosenObject().chosenUnit).regulateDroppingOff(false);
			}
		} else if (attackHandler.unitWantsToFire(gameState.getChosenObject().chosenUnit)) {
			if (gameState.getChosenObject().chosenUnit instanceof IndirectUnit) {
				((IndirectUnit)gameState.getChosenObject().chosenUnit).clearFiringLocations();
			}
			attackRangeHandler.clearRangeMap();
			int x = gameState.getChosenObject().chosenUnit.getPoint().getX();
			int y = gameState.getChosenObject().chosenUnit.getPoint().getY();
			cursor.setPosition(x, y);
			unitMenuHandler.handleOpenUnitMenu(cursor);
	
			gameState.getChosenObject().chosenUnit.regulateAttack(false);
		} else if (mapMenu.isVisible()) {
			mapMenu.closeMenu();
		} else if (buildingMenu.isVisible()) {
			buildingMenu.closeMenu();
		} else if (gameState.getChosenObject().chosenUnit != null) {
			// the start-position of the unit before movement
			Point unitStartPoint = routeHandler.getRouteArrowPath().getArrowPoint(0);
			int unitStartX = unitStartPoint.getX();
			int unitStartY = unitStartPoint.getY();
	
			if (unitMenuHandler.getUnitMenu().isVisible()) {
				unitMenuHandler.getUnitMenu().closeMenu();
				gameState.getChosenObject().chosenUnit.moveTo(unitStartX, unitStartY);
			} else {
				cursor.setPosition(unitStartX, unitStartY);
				gameState.getChosenObject().chosenUnit.moveTo(unitStartX, unitStartY);
				gameState.getChosenObject().chosenUnit = null;
				movementMap.clearMovementMap();
				routeHandler.clearArrowPoints();
			}
		} else if (gameMap.getMap()[cursor.getX() / gameProp.getMapDimension().tileSize][cursor.getY() / gameProp.getMapDimension().tileSize].getTerrainType() == TerrainType.MINI_CANNON) {
			gameState.getChosenObject().rangeStructure = structureHandler.getFiringStructure(cursor.getX(), cursor.getY());
			attackRangeHandler.importStructureAttackLocations(gameState.getChosenObject().rangeStructure);
		} else {
			gameState.getChosenObject().rangeUnit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
	
			if (gameState.getChosenObject().rangeUnit != null) {
				if (gameState.getChosenObject().rangeUnit.getAttackType() == AttackType.DIRECT_ATTACK) {
					attackRangeHandler.findPossibleDirectAttackLocations(gameState.getChosenObject().rangeUnit);
				} else if (gameState.getChosenObject().rangeUnit.getAttackType() == AttackType.INDIRECT_ATTACK) {
					attackRangeHandler.fillRangeAttackMap(gameState.getChosenObject().rangeUnit);
				}
			}
		}
	}

	public void manageKeyReleasedInput(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (gameState.getChosenObject().rangeUnit != null || gameState.getChosenObject().rangeStructure != null) {
				gameState.getChosenObject().rangeUnit = null;
				gameState.getChosenObject().rangeStructure = null;
				attackRangeHandler.clearRangeMap();
			}
		}
	}
	
	private void handleOpenBuildingMenu(int cursorX, int cursorY) {
		if (gameState.getChosenObject().selectedBuilding instanceof City/* || selectedBuilding instanceof HQ*/) {
			return;
		}
		buildingMenu.openMenu(cursorX, cursorY);
	}

	private void removeUnitIfDead(Unit unit) {
		if (unit.getUnitHealth().isDead()) {
			Hero unitsHero = gameState.getHeroHandler().getHeroFromUnit(unit);
			unitsHero.getTroopHandler().removeTroop(unit);
		}
	}
	
	private void removeStructureIfDestroyed(Structure targetStructure) {
		if (targetStructure.isDestroyed()) {
			int tileX = targetStructure.getPoint().getX() / gameProp.getMapDimension().tileSize;
			int tileY = targetStructure.getPoint().getY() / gameProp.getMapDimension().tileSize;
			gameMap.getArea(tileX, tileY).setTerrainType(TerrainType.UMI);
			structureHandler.removeStructure(targetStructure);
		}
	}

	private boolean unitSelectable(int x, int y) {
		return unitGetter.getAnyUnit(x, y) != null;
	}
}
