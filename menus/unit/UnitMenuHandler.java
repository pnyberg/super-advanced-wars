package menus.unit;

import combat.AttackRangeHandler;
import gameObjects.ChosenObject;
import gameObjects.GameProp;
import gameObjects.MapDim;
import main.SupplyHandler;
import map.UnitGetter;
import map.UnitPositionChecker;
import map.area.AreaChecker;
import map.buildings.BuildingHandler;
import units.ContUnitHandler;
import units.Unit;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class UnitMenuHandler {
	private UnitMenu unitMenu;
	private GameProp gameProp;
	private ContUnitHandler containerUnitHandler;
	private SupplyHandler supplyHandler;
	private UnitGetter unitGetter;
	private UnitPositionChecker unitPositionChecker;
	private AreaChecker areaChecker;
	private BuildingHandler buildingHandler;
	private AttackRangeHandler attackRangeHandler;

	public UnitMenuHandler(GameProp gameProp, ContUnitHandler containerUnitHandler, SupplyHandler supplyHandler, UnitGetter unitGetter, UnitPositionChecker unitPositionChecker, AreaChecker areaChecker, BuildingHandler buildingHandler, AttackRangeHandler attackRangeHandler) {
		unitMenu = new UnitMenu(gameProp.getMapDim().tileSize);
		this.gameProp = gameProp;
		this.containerUnitHandler = containerUnitHandler;
		this.supplyHandler = supplyHandler;
		this.unitGetter = unitGetter;
		this.unitPositionChecker = unitPositionChecker;
		this.areaChecker = areaChecker;
		this.buildingHandler = buildingHandler;
		this.attackRangeHandler = attackRangeHandler;
		this.containerUnitHandler = containerUnitHandler; 
	}

	public void handleOpenUnitMenu(int cursorX, int cursorY) {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		boolean hurtAtSamePosition = unitPositionChecker.hurtSameTypeUnitAtPosition(chosenUnit, cursorX, cursorY);
		if (!areaChecker.areaOccupiedByFriendly(chosenUnit, cursorX, cursorY) 
		|| containerUnitHandler.unitEntryingContainerUnit(chosenUnit, cursorX, cursorY)
		|| hurtAtSamePosition) {
			// @TODO fix join
			if (hurtAtSamePosition) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowJoin();
			}

			if (!hurtAtSamePosition && attackRangeHandler.unitCanFire(chosenUnit, cursorX, cursorY)) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowFire();
			}

			if (chosenUnit instanceof Infantry || chosenUnit instanceof Mech) {
				if (containerUnitHandler.footsoldierEnterableUnitAtPosition(cursorX, cursorY)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
				if (buildingHandler.isNonFriendlyBuilding(cursorX, cursorY) && unitGetter.getFriendlyUnitExceptSelf(chosenUnit, cursorX, cursorY) == null) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowCapt();
				}
			} else if (chosenUnit instanceof APC) {
				// should only be allowed this when close to a friendly unit
				if (supplyHandler.mayAPCSUpply(cursorX, cursorY)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowSupply();
				}

				if (((APC)chosenUnit).isFull()) {
					Unit holdUnit = ((APC)chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenUnit instanceof TCopter) {
				if (((TCopter)chosenUnit).isFull() && areaChecker.isLand(cursorX, cursorY)) {
					Unit holdUnit = ((TCopter)chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenUnit instanceof Lander) {
				if (containerUnitHandler.landerAtDroppingOffPosition(cursorX, cursorY)) {
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

			if (containerUnitHandler.landbasedEnterableUnitAtPosition(cursorX, cursorY)) {
				if (!(chosenUnit instanceof Lander)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
			} else if (containerUnitHandler.copterEnterableUnitAtPosition(cursorX, cursorY)) {
				if (!(chosenUnit instanceof Cruiser)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
			}

			if (!areaChecker.areaOccupiedByFriendly(chosenUnit, cursorX, cursorY)) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowWait();
			}

			unitMenu.openMenu(cursorX, cursorY);
			chosenUnit.moveTo(cursorX, cursorY);
		}
	}
	
	public UnitMenu getUnitMenu() {
		return unitMenu;
	}
}