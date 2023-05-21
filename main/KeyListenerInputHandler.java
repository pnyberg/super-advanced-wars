/**
 * TODO:
 *  - rewrite code to make it more readable
 */
package main;

import java.awt.event.KeyEvent;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import combat.DamageHandler;
import cursors.Cursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import graphics.ViewType;
import hero.Hero;
import hero.heroPower.HeroPowerHandler;
import map.GameMap;
import map.UnitGetter;
import map.area.AreaChecker;
import map.area.TerrainType;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.FiringStructure;
import map.structures.Structure;
import map.structures.StructureHandler;
import menus.building.BuildingMenu;
import menus.map.MapMenu;
import menus.unit.UnitMenuHandler;
import point.Point;
import routing.MovementMap;
import routing.RouteChecker;
import routing.RouteHandler;
import unitUtils.AttackType;
import unitUtils.ContUnitHandler;
import unitUtils.UnitWorthCalculator;
import units.*;
import units.seaMoving.*;

public class KeyListenerInputHandler {
	private final int upArrowKeyEventIndex = KeyEvent.VK_UP;
	private final int rightArrowKeyEventIndex = KeyEvent.VK_RIGHT;
	private final int downArrowKeyEventIndex = KeyEvent.VK_DOWN;
	private final int leftArrowKeyEventIndex = KeyEvent.VK_LEFT;
	private final int aButtonKeyEventIndex = KeyEvent.VK_A;
	private final int bButtonKeyEventIndex = KeyEvent.VK_B;
	private final int startButtonKeyEventIndex = KeyEvent.VK_S;
	private final int selectButtonKeyEventIndex = KeyEvent.VK_F;
	private final int leftBumberKeyEventIndex = KeyEvent.VK_Q;
	private final int rightBumberKeyEventIndex = KeyEvent.VK_W;
	
	private GameProperties gameProperties;
	private GameState gameState;
	private UnitGetter unitGetter;
	private BuildingHandler buildingHandler;
	private StructureHandler structureHandler;
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
	private CaptHandler captHandler;
	private TurnHandler turnHandler;
	private HeroPowerHandler heroPowerHandler;
	private UnitWorthCalculator unitWorthCalculator;
	
	public KeyListenerInputHandler(GameProperties gameProperties, GameState gameState) {
		this.gameProperties = gameProperties;
		this.gameState = gameState;
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
		this.buildingHandler = new BuildingHandler(gameState);
		this.structureHandler = new StructureHandler(gameState, gameProperties.getMapDimension());
		this.cursor = gameState.getCursor();
		mapMenu = new MapMenu(gameProperties.getTileSize(), gameState);
		captHandler = new CaptHandler(gameState.getHeroHandler());
		turnHandler = new TurnHandler(gameProperties, gameState);
	
		attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		routeChecker = new RouteChecker(gameProperties, gameState);
		GameMap gameMap = gameProperties.getGameMap();
		movementMap = gameState.getMovementMap();
		buildingMenu = new BuildingMenu(gameProperties, gameState);
		damageHandler = new DamageHandler(gameState.getHeroHandler(), gameMap);
		routeHandler = new RouteHandler(gameProperties, gameState);
		containerUnitHandler = new ContUnitHandler(gameProperties, gameState);
		attackHandler = new AttackHandler(gameProperties, gameState);
		unitMenuHandler = new UnitMenuHandler(gameProperties, gameState, attackHandler);

		heroPowerHandler = new HeroPowerHandler(gameState.getHeroHandler());
		unitWorthCalculator = new UnitWorthCalculator();
	}
	
	public void manageKeyPressedInput(KeyEvent e) {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		if(e.getKeyCode() == upArrowKeyEventIndex) {
			handlePressedUpArrow();
		} else if(e.getKeyCode() == downArrowKeyEventIndex) {
			handlePressedDownArrow();
		} else if(e.getKeyCode() == leftArrowKeyEventIndex) {
			if(gameState.getMapViewType() == ViewType.CO_VIEW) {
				gameState.getHeroHandler().prevCoViewHero();
			} else if(gameState.getMapViewType() == ViewType.MAP_VIEW) {
				if(cursorCanMoveLeft()) {
					cursor.moveLeft();
					updateArrowPathWithNewCursorPosition();
				}
			}
		} else if(e.getKeyCode() == rightArrowKeyEventIndex) {
			if(gameState.getMapViewType() == ViewType.CO_VIEW) {
				gameState.getHeroHandler().nextCoViewHero();
			} else if(gameState.getMapViewType() == ViewType.MAP_VIEW) {
				if(cursorCanMoveRight()) {
					cursor.moveRight();
					updateArrowPathWithNewCursorPosition();
				}
			}
		} else if(e.getKeyCode() == aButtonKeyEventIndex) {
			handlePressedButtonA();
		} else if(e.getKeyCode() == bButtonKeyEventIndex) {
			handlePressedButtonB(cursor);
		} else if(e.getKeyCode() == startButtonKeyEventIndex) {
			if(mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if(gameState.getChosenUnit() == null) {
				mapMenu.openMenu(cursorX, cursorY);
			}
		} else if(e.getKeyCode() == rightBumberKeyEventIndex) {
			handlePressedRightBumber();
		}
	}
	
	public void manageKeyReleasedInput(KeyEvent e) {
		if (e.getKeyCode() == bButtonKeyEventIndex) {
			if (gameState.rangeShooterChosen()) {
				gameState.setChosenRangeUnit(null);
				gameState.setChosenRangeStructure(null);
				gameState.resetRangeMap();
			}
		}
	}
	
	private void handlePressedUpArrow() {
		if(gameState.getMapViewType() == ViewType.CO_VIEW) {
			// do nothing
		} else if(gameState.getMapViewType() == ViewType.MAP_VIEW) {
			Unit chosenUnit = gameState.getChosenUnit();
			if (containerUnitHandler.unitIsDroppingOff()) {
				if (containerUnitHandler.unitCanDropOffUnit()) {
					Point point = chosenUnit.getUnitContainer().getPreviousDropOffLocation();
					cursor.setPosition(point.getX(), point.getY());
				}
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowUp();
			} else if (unitMenuHandler.getUnitMenu().isVisible()) {
				unitMenuHandler.getUnitMenu().moveArrowUp();
			} else if (buildingMenu.isVisible()) {
				buildingMenu.moveArrowUp();
			} else if (attackHandler.unitWantsToFire(chosenUnit)) {
				Point firingLocationPosition = chosenUnit.getPreviousFiringLocation();
				cursor.setPosition(firingLocationPosition);
			} else if (cursor.getY() > 0) {
				cursor.moveUp();
				updateArrowPathWithNewCursorPosition();
			}
		}
	}
	
	private void handlePressedDownArrow() {
		Unit chosenUnit = gameState.getChosenUnit();
		int tileSize = gameProperties.getMapDimension().tileSize;

		if(gameState.getMapViewType() == ViewType.CO_VIEW) {
			// do nothing
		} else if(gameState.getMapViewType() == ViewType.MAP_VIEW) {
			if(containerUnitHandler.unitIsDroppingOff()) {
				if (containerUnitHandler.unitCanDropOffUnit()) {
					Point point = chosenUnit.getUnitContainer().getNextDropOffLocation();
					cursor.setPosition(point.getX(), point.getY());
				}
			} else if(mapMenu.isVisible()) {
				mapMenu.moveArrowDown();
			} else if(unitMenuHandler.getUnitMenu().isVisible()) {
				unitMenuHandler.getUnitMenu().moveArrowDown();
			} else if(buildingMenu.isVisible()) {
				buildingMenu.moveArrowDown();
			} else if(attackHandler.unitWantsToFire(chosenUnit)) {
				Point point = chosenUnit.getNextFiringLocation();
				cursor.setPosition(point.getX(), point.getY());
			} else if(cursor.getY() < (gameProperties.getMapDimension().getTileHeight() - 1) * tileSize) {
				cursor.moveDown();
				updateArrowPathWithNewCursorPosition();
			}
		}
	}
	
	// TODO: refactor later
	private void handlePressedButtonA() {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();
		boolean unitSelected = gameState.getChosenUnit() != null || gameState.getChosenRangeUnit() != null;

		if (containerUnitHandler.unitIsDroppingOff()) {
			handleDroppingOff();
		} else if (attackHandler.unitWantsToFire(gameState.getChosenUnit())) {
			Unit defendingUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY());
			Structure targetStructure = structureHandler.getStructure(cursor.getX(), cursor.getY());
			if (defendingUnit != null) {
				damageHandler.handleAttackingUnit(gameState.getChosenUnit(), defendingUnit);
				removeUnitIfDead(defendingUnit);
			} else if (targetStructure != null){
				damageHandler.handleAttackingStructure(gameState.getChosenUnit(), targetStructure);
				removeStructureIfDestroyed(targetStructure);
			} else {
				System.err.println("No legal target to attack was used!");
			}

			gameState.getChosenUnit().regulateAttack(false);

			int x = gameState.getChosenUnit().getPosition().getX();
			int y = gameState.getChosenUnit().getPosition().getY();
			cursor.setPosition(x, y);

			removeUnitIfDead(gameState.getChosenUnit());

			int fuelUse = routeHandler.getFuelFromArrows(gameState.getChosenUnit());
			gameState.getChosenUnit().getUnitSupply().useFuel(fuelUse);

			gameState.getChosenUnit().regulateActive(false);
			gameState.getChosenUnit().clearFiringLocations();
			gameState.setChosenUnit(null);
			movementMap.clearMovementMap();
			routeHandler.clearArrowPoints();
			gameState.resetRangeMap();
		} else if (mapMenu.isVisible()) {
			mapMenuRowPressed();
		} else if (unitMenuHandler.getUnitMenu().isVisible()) {
			if (unitMenuHandler.getUnitMenu().atUnitRow()) {
				if (gameState.getChosenUnit().hasUnitContainer()) {
					int index = unitMenuHandler.getUnitMenu().getMenuIndex();
					gameState.getChosenUnit().getUnitContainer().chooseUnit(index);
				}
				containerUnitHandler.handleDroppingOff();
			} else if (unitMenuHandler.getUnitMenu().atJoinRow()) {
				int x = gameState.getChosenUnit().getPosition().getX();
				int y = gameState.getChosenUnit().getPosition().getY();
				Unit unit = unitGetter.getFriendlyUnitExceptSelf(gameState.getChosenUnit(), x, y);
				
				int joinHp = unit.getUnitHealth().getShowHP() + gameState.getChosenUnit().getUnitHealth().getShowHP();
				if (joinHp > 10) {
					int joinFunds = (joinHp - 10) * unitWorthCalculator.getFullHealthUnitWorth(unit) / 10;
					gameState.getHeroHandler().getCurrentHero().earnCash(joinFunds);
				}
				unit.getUnitHealth().heal(gameState.getChosenUnit().getUnitHealth().getHP());
				gameState.getChosenUnit().getUnitHealth().kill();
				removeUnitIfDead(gameState.getChosenUnit());
			} else if (unitMenuHandler.getUnitMenu().atEnterRow()) {
				Unit entryUnit = unitGetter.getFriendlyUnitExceptSelf(gameState.getChosenUnit(), cursorX, cursorY);
				if (entryUnit.hasUnitContainer()) {
					entryUnit.getUnitContainer().addUnit(gameState.getChosenUnit());
				}
			} else if (unitMenuHandler.getUnitMenu().atFireRow()) {
				Unit chosenUnit = gameState.getChosenUnit();
				chosenUnit.regulateAttack(true);
				attackHandler.setUpFiringTargets(chosenUnit);
				Point startFiringLocation = chosenUnit.getNextFiringLocation();
				cursor.setPosition(startFiringLocation);
			} else if (unitMenuHandler.getUnitMenu().atCaptRow()) {
				Building building = buildingHandler.getBuilding(cursor.getX(), cursor.getY());
				captHandler.captBuilding(gameState.getChosenUnit(), building);
				if (building.captingIsActive()) {
					gameState.getChosenUnit().regulateCapting(true);
				} else if (gameState.getChosenUnit().isCapting()) {
					gameState.getChosenUnit().regulateCapting(false);
				}
			} else if (unitMenuHandler.getUnitMenu().atSupplyRow()) {
				int x = gameState.getChosenUnit().getPosition().getX();
				int y = gameState.getChosenUnit().getPosition().getY();
				replentishSurroundingUnits(x, y);
			}

			if (!containerUnitHandler.unitIsDroppingOff() && !attackHandler.unitWantsToFire(gameState.getChosenUnit())) {
				// using fuel
				int fuelUse = routeHandler.getFuelFromArrows(gameState.getChosenUnit());
				gameState.getChosenUnit().getUnitSupply().useFuel(fuelUse);
				gameState.getChosenUnit().regulateActive(false);
				gameState.setChosenUnit(null);
				movementMap.clearMovementMap();
				routeHandler.clearArrowPoints();
			}
			unitMenuHandler.getUnitMenu().closeMenu();
		} else if (buildingMenu.isVisible()) {
			buildingMenu.buySelectedTroop();
			buildingMenu.closeMenu();
		} else if (gameState.getChosenUnit() != null && // TODO: rewrite this guard
				movementMap.isAcceptedMove(cursorX / gameProperties.getMapDimension().tileSize, 
											cursorY / gameProperties.getMapDimension().tileSize) && 
				gameState.getChosenRangeUnit() == null) {
			int chosenUnitX = gameState.getChosenUnit().getPosition().getX();
			int chosenUnitY = gameState.getChosenUnit().getPosition().getY();
			if (unitGetter.getFriendlyUnit(chosenUnitX, chosenUnitY) != null && unitMenuHandler.unitCanMoveToPosition(cursorX, cursorY)) {
				unitMenuHandler.handleOpenUnitMenu(cursor);
			}
		} else if (!unitSelected && !unitSelectable(cursorX, cursorY)) {
			Building chosenBuilding = buildingHandler.getFriendlyBuilding(cursorX, cursorY);
			if (chosenBuilding != null && chosenBuilding.isBuildableBuilding()) {
				buildingMenu.openMenu(cursorX, cursorY);
			}
		} else if (!unitSelected) {
			gameState.setChosenUnit(unitGetter.getAnyUnit(cursorX, cursorY));

			if (gameState.getChosenUnit() != null) {
				routeChecker.retrievePossibleMovementLocations(gameState.getChosenUnit());
				routeHandler.addNewArrowPoint(gameState.getChosenUnit().getPosition());
			}
		}
	}
	
	// TODO: refactor later
	private void handlePressedButtonB(Cursor cursor) {
		if (gameState.getMapViewType() == ViewType.CO_VIEW) {
			gameState.setViewType(ViewType.MAP_VIEW);
		} else if (containerUnitHandler.unitIsDroppingOff()) {
			int x = gameState.getChosenUnit().getPosition().getX();
			int y = gameState.getChosenUnit().getPosition().getY();
			cursor.setPosition(x, y);
			unitMenuHandler.handleOpenUnitMenu(cursor);
	
			if (gameState.getChosenUnit().hasUnitContainer()) {
				gameState.getChosenUnit().getUnitContainer().regulateDroppingOff(false);
				gameState.getChosenUnit().getUnitContainer().clearDropOffLocations();
			}
		} else if (attackHandler.unitWantsToFire(gameState.getChosenUnit())) {
			gameState.getChosenUnit().clearFiringLocations();
			gameState.resetRangeMap();
			int x = gameState.getChosenUnit().getPosition().getX();
			int y = gameState.getChosenUnit().getPosition().getY();
			cursor.setPosition(x, y);
			unitMenuHandler.handleOpenUnitMenu(cursor);
	
			gameState.getChosenUnit().regulateAttack(false);
		} else if (mapMenu.isVisible()) {
			mapMenu.closeMenu();
		} else if (buildingMenu.isVisible()) {
			buildingMenu.closeMenu();
		} else if (gameState.getChosenUnit() != null) {
			// the start-position of the unit before movement
			Point unitStartPoint = routeHandler.getRouteArrowPath().getArrowPoint(0);
			int unitStartX = unitStartPoint.getX();
			int unitStartY = unitStartPoint.getY();
	
			if (unitMenuHandler.getUnitMenu().isVisible()) {
				unitMenuHandler.getUnitMenu().closeMenu();
				gameState.getChosenUnit().moveTo(unitStartX, unitStartY);
			} else {
				cursor.setPosition(unitStartX, unitStartY);
				gameState.getChosenUnit().moveTo(unitStartX, unitStartY);
				gameState.setChosenUnit(null);
				movementMap.clearMovementMap();
				routeHandler.clearArrowPoints();
			}
		} else if (isFiringStructureAtCursorLocation()) {
			FiringStructure firingStructure = structureHandler.getFiringStructure(cursor.getX(), cursor.getY());
			gameState.setChosenRangeStructure(firingStructure);
			attackRangeHandler.importStructureAttackLocations(firingStructure);
		} else {
			Unit rangeUnit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
			gameState.setChosenRangeUnit(rangeUnit); 
	
			if (gameState.getChosenRangeUnit() != null) {
				if (gameState.getChosenRangeUnit().getAttackType() == AttackType.DIRECT_ATTACK) {
					attackHandler.findPossibleDirectAttackLocations(gameState.getChosenRangeUnit());
				} else if (gameState.getChosenRangeUnit().getAttackType() == AttackType.INDIRECT_ATTACK) {
					attackHandler.fillRangeAttackMap(gameState.getChosenRangeUnit());
				}
			}
		}
	}

	private void handlePressedRightBumber() {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		if (unit != null) {
			gameState.setViewType(ViewType.UNIT_INFO_VIEW);
		} else {
			gameState.setViewType(ViewType.TERRAIN_INFO_VIEW);
		}
	}
	
	private boolean cursorCanMoveLeft() {
		if (!cursorIsInMoveableState()) {
			return false;
		}
		return cursor.getX() > 0;
	}
	
	private boolean cursorCanMoveRight() {
		int tileSize = gameProperties.getMapDimension().tileSize;
		if (!cursorIsInMoveableState()) {
			return false;
		}
		return (cursor.getX() < (gameProperties.getMapDimension().getTileWidth() - 1) * tileSize); 
	}
	
	private boolean cursorIsInMoveableState() {
		boolean menuVisible = mapMenu.isVisible() || unitMenuHandler.getUnitMenu().isVisible() || buildingMenu.isVisible();
		if (menuVisible) {
			return false;
		}
		if (containerUnitHandler.unitIsDroppingOff()) {
			return false;
		}
		if (attackHandler.unitWantsToFire(gameState.getChosenUnit())) {
			return false;
		}
		return true;
	}
	
	private boolean isFiringStructureAtCursorLocation() {
		int tileX = cursor.getX() / gameProperties.getMapDimension().tileSize;
		int tileY = cursor.getY() / gameProperties.getMapDimension().tileSize;
		TerrainType terrainType = gameProperties.getGameMap().getArea(tileX, tileY).getTerrainType(); 
		return terrainType == TerrainType.MINI_CANNON;
	}
	
	private void updateArrowPathWithNewCursorPosition() {
		Point newArrowPathPosition = new Point(cursor.getX(), cursor.getY());
		routeHandler.updateCurrentArrowPath(newArrowPathPosition, gameState.getChosenUnit());
	}
	
	private void replentishSurroundingUnits(int x, int y) {
		SupplyHandler supplyHandler = new SupplyHandler(gameState, gameProperties.getMapDimension().tileSize);

		Unit unitToTheNorth = unitGetter.getFriendlyUnit(x, y - gameProperties.getMapDimension().tileSize);
		Unit unitToTheEast = unitGetter.getFriendlyUnit(x + gameProperties.getMapDimension().tileSize, y);
		Unit unitToTheSouth = unitGetter.getFriendlyUnit(x, y + gameProperties.getMapDimension().tileSize);
		Unit unitToTheWest = unitGetter.getFriendlyUnit(x - gameProperties.getMapDimension().tileSize, y);

		if (unitToTheNorth != null) {
			supplyHandler.replentishUnit(unitToTheNorth);
		}
		if (unitToTheEast != null) {
			supplyHandler.replentishUnit(unitToTheEast);
		}
		if (unitToTheSouth != null) {
			supplyHandler.replentishUnit(unitToTheSouth);
		}
		if (unitToTheWest != null) {
			supplyHandler.replentishUnit(unitToTheWest);
		}
	}

	private void handleDroppingOff() {
		if (containerUnitHandler.unitCanDropOffUnit()) {
			gameState.getChosenUnit().getUnitContainer().regulateDroppingOff(false);
			gameState.getChosenUnit().getUnitContainer().clearDropOffLocations();
			Unit exitingUnit = gameState.getChosenUnit().getUnitContainer().removeChosenUnit();
			exitingUnit.moveTo(cursor.getX(), cursor.getY());
			exitingUnit.regulateActive(false);

			int fuelUse = routeHandler.getFuelFromArrows(gameState.getChosenUnit());
			gameState.getChosenUnit().getUnitSupply().useFuel(fuelUse);

			gameState.getChosenUnit().regulateActive(false);
			gameState.setChosenUnit(null);
			movementMap.clearMovementMap();
			routeHandler.clearArrowPoints();
		} else {
			// If all drop-slots are occupied, pressing 'A' won't do anything
		}
	}

	private void mapMenuRowPressed() {
		if (mapMenu.atCoRow()) {
			gameState.setViewType(ViewType.CO_VIEW);
		} else if (mapMenu.atIntelRow()) {
			// TODO:
		} else if (mapMenu.atPowerRow()) {
			heroPowerHandler.activatePower();
		} else if (mapMenu.atSuperPowerRow()) {
			heroPowerHandler.activateSuperPower();
		} else if (mapMenu.atOptionsRow()) {
			// TODO:
		} else if (mapMenu.atSaveRow()) {
			// TODO:
		} else if (mapMenu.atEndRow()) {
			turnHandler.endTurn();
		}
		mapMenu.closeMenu();
	}

	private void removeUnitIfDead(Unit unit) {
		if (unit.getUnitHealth().isDead()) {
			// reset capting if active
			Point point = unit.getPosition();
			Building building = buildingHandler.getBuilding(point.getX(), point.getY());
			if (building != null) {
				building.resetCapting();
			}
			// remove unit
			Hero unitsHero = gameState.getHeroHandler().getHeroFromUnit(unit);
			unitsHero.getTroopHandler().removeTroop(unit);
		}
	}
	
	private void removeStructureIfDestroyed(Structure targetStructure) {
		if (targetStructure.isDestroyed()) {
			int tileX = targetStructure.getPoint().getX() / gameProperties.getMapDimension().tileSize;
			int tileY = targetStructure.getPoint().getY() / gameProperties.getMapDimension().tileSize;
			gameProperties.getGameMap().getArea(tileX, tileY).setTerrainType(TerrainType.UMI);
			structureHandler.removeStructure(targetStructure);
		}
	}

	private boolean unitSelectable(int x, int y) {
		return unitGetter.getAnyUnit(x, y) != null;
	}
}
