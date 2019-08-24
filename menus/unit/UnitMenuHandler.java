package menus.unit;

import combat.AttackHandler;
import combat.AttackRangeHandler;
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
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class UnitMenuHandler {
	private UnitMenu unitMenu;
	private GameProperties gameProp;
	private GameState gameState;
	private ContUnitHandler containerUnitHandler;
	private SupplyHandler supplyHandler;
	private UnitGetter unitGetter;
	private AreaChecker areaChecker;
	private BuildingHandler buildingHandler;
	private AttackHandler attackHandler;

	public UnitMenuHandler(GameProperties gameProp, GameState gameState, ContUnitHandler containerUnitHandler, SupplyHandler supplyHandler, AreaChecker areaChecker, AttackHandler attackHandler) {
		unitMenu = new UnitMenu(gameProp.getMapDimension().tileSize, gameState);
		this.gameProp = gameProp;
		this.gameState = gameState;
		this.containerUnitHandler = containerUnitHandler;
		this.supplyHandler = supplyHandler;
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
		this.areaChecker = areaChecker;
		this.buildingHandler = new BuildingHandler(gameState);
		this.attackHandler = attackHandler;
		this.containerUnitHandler = containerUnitHandler; 
	}

	// TODO: rewrite code to make it more readable
	public void handleOpenUnitMenu(Cursor cursor) {
		Unit chosenUnit = gameState.getChosenObject().chosenUnit;
		boolean hurtAtSamePosition = unitGetter.hurtSameTypeUnitAtPosition(chosenUnit, cursor.getX(), cursor.getY());
		
		if (!areaChecker.areaOccupiedByFriendly(chosenUnit, cursor.getX(), cursor.getY()) || hurtAtSamePosition
				|| containerUnitHandler.unitEntryingContainerUnit(chosenUnit, cursor.getX(), cursor.getY())) {
			if (hurtAtSamePosition) {
				unitMenu.enableJoinOption();
			} else if (attackHandler.unitCanFire(chosenUnit, cursor)) {
				unitMenu.enableFireOption();
			}

			if (chosenUnit instanceof Infantry || chosenUnit instanceof Mech) {
				if (containerUnitHandler.footsoldierEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
					unitMenu.enableEnterOption();
				}
				if (buildingHandler.isNonFriendlyBuilding(cursor.getX(), cursor.getY()) 
						&& unitGetter.getFriendlyUnitExceptSelf(chosenUnit, cursor.getX(), cursor.getY()) == null) {
					unitMenu.enableCaptOption();
				}
			} else if (chosenUnit instanceof APC) {
				// should only be allowed this when close to a friendly unit
				if (supplyHandler.apcMaySupply(cursor.getX(), cursor.getY())) {
					unitMenu.enableSupplyOption();
				}
				if (chosenUnit.getUnitContainer().isFull()) {
					Unit holdUnit = chosenUnit.getUnitContainer().getChosenUnit();
					unitMenu.addContainedCargoRow(holdUnit);
				}
			} else if (chosenUnit.hasUnitContainer()) {
				int cursorTileX = cursor.getX() / gameProp.getMapDimension().tileSize;
				int cursorTileY = cursor.getY() / gameProp.getMapDimension().tileSize;
				if (containerUnitHandler.landerAtDroppingOffPosition(cursor.getX(), cursor.getY())) {
					for (int i = 0 ; i < chosenUnit.getUnitContainer().getNumberOfContainedUnits() ; i++) {
						Unit holdUnit = chosenUnit.getUnitContainer().getUnit(i);
						unitMenu.addContainedCargoRow(holdUnit);
					}
				} else if (chosenUnit.getUnitContainer().isFull() && areaChecker.isLand(cursorTileX, cursorTileY)) {
					Unit holdUnit = chosenUnit.getUnitContainer().getChosenUnit();
					unitMenu.addContainedCargoRow(holdUnit);
				} 
			} else if (chosenUnit instanceof Cruiser) {
				for (int i = 0 ; i < ((Cruiser)chosenUnit).getNumberOfContainedUnits() ; i++) {
					Unit holdUnit = ((Cruiser)chosenUnit).getUnit(i);
					unitMenu.addContainedCargoRow(holdUnit);
				}
			}

			if (containerUnitHandler.landbasedEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
				if (!(chosenUnit instanceof Lander)) {
					unitMenu.enableEnterOption();
				}
			} else if (containerUnitHandler.copterEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
				if (!(chosenUnit instanceof Cruiser)) {
					unitMenu.enableEnterOption();
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
	}
	
	public UnitMenu getUnitMenu() {
		return unitMenu;
	}
}