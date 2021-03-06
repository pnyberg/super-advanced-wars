package menus.unit;

import combat.AttackHandler;
import cursors.Cursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import main.SupplyHandler;
import map.UnitGetter;
import map.area.AreaChecker;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import point.Point;
import unitUtils.ContUnitHandler;
import units.Unit;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Cruiser;
import units.treadMoving.APC;

public class UnitMenuHandler {
	private GameProperties gameProperties;
	private GameState gameState;
	private UnitMenu unitMenu;
	private ContUnitHandler containerUnitHandler;
	private SupplyHandler supplyHandler;
	private UnitGetter unitGetter;
	private AreaChecker areaChecker;
	private BuildingHandler buildingHandler;
	private AttackHandler attackHandler;

	// TODO: rewrite with fewer parameters
	public UnitMenuHandler(GameProperties gameProperties, GameState gameState, AttackHandler attackHandler) {
		this.gameProperties = gameProperties;
		this.gameState = gameState;
		this.attackHandler = attackHandler;
		unitMenu = new UnitMenu(gameProperties.getMapDimension().tileSize, gameState);
		containerUnitHandler = new ContUnitHandler(gameProperties, gameState);
		supplyHandler = new SupplyHandler(gameState, gameProperties.getMapDimension().tileSize);
		unitGetter = new UnitGetter(gameState.getHeroHandler());
		areaChecker = new AreaChecker(gameState.getHeroHandler(), gameProperties.getGameMap());
		buildingHandler = new BuildingHandler(gameState);
	}

	public void handleOpenUnitMenu(Cursor cursor) {
		Unit chosenUnit = gameState.getChosenUnit();
		boolean hurtAtSamePosition = unitGetter.hurtSameTypeUnitAtPosition(chosenUnit, cursor.getX(), cursor.getY());
		
		if (hurtAtSamePosition) {
			unitMenu.enableJoinOption();
		} else if (attackHandler.unitCanFire(chosenUnit, cursor)) {
			unitMenu.enableFireOption();
		}

		if (allowEnterOption(cursor)) {
			unitMenu.enableEnterOption();
		}

		if (chosenUnit instanceof Infantry || chosenUnit instanceof Mech) {
			if (unitCanCaptAtPosition(chosenUnit, cursor.getX(), cursor.getY())) {
				unitMenu.enableCaptOption();
			}
		}
		if (chosenUnit instanceof APC && supplyHandler.apcMaySupply(chosenUnit, cursor.getX(), cursor.getY())) {
			unitMenu.enableSupplyOption();
		} 
		if (unitCanDropOffUnits(cursor)) {
			int cursorTileX = cursor.getX() / gameProperties.getMapDimension().tileSize;
			int cursorTileY = cursor.getY() / gameProperties.getMapDimension().tileSize;
			// TODO: rewrite so it covers TCopter, APC, Lander, Cruiser
			if (containerUnitHandler.landerAtDroppingOffPosition(cursor.getX(), cursor.getY())) {
				for (int i = 0 ; i < chosenUnit.getUnitContainer().getNumberOfContainedUnits() ; i++) {
					Unit holdUnit = chosenUnit.getUnitContainer().getUnit(i);
					unitMenu.addContainedCargoRow(holdUnit);
				}
			} else if (chosenUnit.getUnitContainer().isFull() && areaChecker.isLand(cursorTileX, cursorTileY)) {
				for (int i = 0 ; i < chosenUnit.getUnitContainer().getNumberOfContainedUnits() ; i++) {
					Unit holdUnit = chosenUnit.getUnitContainer().getUnit(i);
					unitMenu.addContainedCargoRow(holdUnit);
				}
			} else if (chosenUnit instanceof Cruiser) {
				for (int i = 0 ; i < chosenUnit.getUnitContainer().getNumberOfContainedUnits() ; i++) {
					Unit holdUnit = chosenUnit.getUnitContainer().getUnit(i);
					unitMenu.addContainedCargoRow(holdUnit);
				}
			}
		}

		if (!areaChecker.areaOccupiedByFriendly(chosenUnit, cursor.getX(), cursor.getY())) {
			unitMenu.enableWaitOption();
		}

		unitMenu.openMenu(cursor.getX(), cursor.getY());
		
		if (chosenUnit.getPosition().getX() != cursor.getX() || chosenUnit.getPosition().getY() != cursor.getY()) {
			if (chosenUnit.isCapting()) {
				Point point = chosenUnit.getPosition();
				Building building = buildingHandler.getBuilding(point.getX(), point.getY());
				building.resetCapting();
			}
			chosenUnit.moveTo(cursor.getX(), cursor.getY());
		}
	}
	
	private boolean allowEnterOption(Cursor cursor) {
		Unit chosenUnit = gameState.getChosenUnit();
		if (chosenUnit instanceof Infantry || chosenUnit instanceof Mech) {
			if (containerUnitHandler.footsoldierEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
				return true;
			}
		}
		if (containerUnitHandler.landbasedEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
			return true;
		}
		if (containerUnitHandler.copterEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
			return true;
		}
		return false;
	}
	
	private boolean unitCanDropOffUnits(Cursor cursor) {
		Unit chosenUnit = gameState.getChosenUnit();
		if (!chosenUnit.hasUnitContainer()) {
			return false;
		}
		if (chosenUnit instanceof TCopter && containerUnitHandler.copterEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
			return false;
		}
		if (chosenUnit instanceof APC && containerUnitHandler.landbasedEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
			return false;
		}
		return true;
	}
	
	public boolean unitCanMoveToPosition(int x, int y) {
		Unit chosenUnit = gameState.getChosenUnit();
		if (!areaChecker.areaOccupiedByFriendly(chosenUnit, x, y)) {
			return true;
		}
		if (containerUnitHandler.unitEntryingContainerUnit(chosenUnit, x, y)) {
			return true;
		}
		return unitGetter.hurtSameTypeUnitAtPosition(chosenUnit, x, y);
	}
	
	private boolean unitCanCaptAtPosition(Unit unit, int x, int y) {
		if (unitGetter.getFriendlyUnitExceptSelf(unit, x, y) != null) {
			return false;
		}
		return buildingHandler.isNonFriendlyBuilding(x, y);
	}
	
	public UnitMenu getUnitMenu() {
		return unitMenu;
	}
}