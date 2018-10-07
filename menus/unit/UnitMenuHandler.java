package menus.unit;

import combat.AttackRangeHandler;
import gameObjects.ChosenObject;
import gameObjects.MapDim;
import main.SupplyHandler;
import map.UnitPositionChecker;
import map.area.AreaChecker;
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
	private ChosenObject chosenObject;
	private ContUnitHandler containerUnitHandler;
	private SupplyHandler supplyHandler;
	private UnitPositionChecker unitPositionChecker;
	private AreaChecker areaChecker;
	private AttackRangeHandler attackRangeHandler;

	public UnitMenuHandler(MapDim mapDimension, UnitMenu unitMenu, ChosenObject chosenObject, ContUnitHandler containerUnitHandler, SupplyHandler supplyHandler, UnitPositionChecker unitPositionChecker, AreaChecker areaChecker, AttackRangeHandler attackRangeHandler) {
		this.unitMenu = unitMenu;
		this.chosenObject = chosenObject;
		this.containerUnitHandler = containerUnitHandler;
		this.supplyHandler = supplyHandler;
		this.unitPositionChecker = unitPositionChecker;
		this.areaChecker = areaChecker;
		this.attackRangeHandler = attackRangeHandler;
		this.containerUnitHandler = containerUnitHandler; 
	}

	public void handleOpenUnitMenu(int cursorX, int cursorY) {
		boolean hurtAtSamePosition = unitPositionChecker.hurtSameTypeUnitAtPosition(chosenObject.chosenUnit, cursorX, cursorY);
		if (!areaChecker.areaOccupiedByFriendly(chosenObject.chosenUnit, cursorX, cursorY) 
		|| containerUnitHandler.unitEntryingContainerUnit(chosenObject.chosenUnit, cursorX, cursorY)
		|| hurtAtSamePosition) {
			// @TODO fix join
			if (hurtAtSamePosition) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowJoin();
			}

			if (!hurtAtSamePosition && attackRangeHandler.unitCanFire(chosenObject.chosenUnit, cursorX, cursorY)) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowFire();
			}

			if (chosenObject.chosenUnit instanceof Infantry || chosenObject.chosenUnit instanceof Mech) {
				if (containerUnitHandler.footsoldierEnterableUnitAtPosition(cursorX, cursorY)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
			} else if (chosenObject.chosenUnit instanceof APC) {
				// should only be allowed this when close to a friendly unit
				if (supplyHandler.mayAPCSUpply(cursorX, cursorY)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowSupply();
				}

				if (((APC)chosenObject.chosenUnit).isFull()) {
					Unit holdUnit = ((APC)chosenObject.chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenObject.chosenUnit instanceof TCopter) {
				if (((TCopter)chosenObject.chosenUnit).isFull() && areaChecker.isLand(cursorX, cursorY)) {
					Unit holdUnit = ((TCopter)chosenObject.chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenObject.chosenUnit instanceof Lander) {
				if (containerUnitHandler.landerAtDroppingOffPosition(cursorX, cursorY)) {
					for (int i = 0 ; i < ((Lander)chosenObject.chosenUnit).getNumberOfContainedUnits() ; i++) {
						Unit holdUnit = ((Lander)chosenObject.chosenUnit).getUnit(i);
						unitMenu.containedCargo(holdUnit);
					}
				}
			} else if (chosenObject.chosenUnit instanceof Cruiser) {
				for (int i = 0 ; i < ((Cruiser)chosenObject.chosenUnit).getNumberOfContainedUnits() ; i++) {
					Unit holdUnit = ((Cruiser)chosenObject.chosenUnit).getUnit(i);
					unitMenu.containedCargo(holdUnit);
				}
			}

			if (containerUnitHandler.landbasedEnterableUnitAtPosition(cursorX, cursorY)) {
				if (!(chosenObject.chosenUnit instanceof Lander)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
			} else if (containerUnitHandler.copterEnterableUnitAtPosition(cursorX, cursorY)) {
				if (!(chosenObject.chosenUnit instanceof Cruiser)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().allowEnter();
				}
			}

			if (!areaChecker.areaOccupiedByFriendly(chosenObject.chosenUnit, cursorX, cursorY)) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().allowWait();
			}

			unitMenu.openMenu(cursorX, cursorY);
			chosenObject.chosenUnit.moveTo(cursorX, cursorY);
		}
	}
	
	public UnitMenu getUnitMenu() {
		return unitMenu;
	}
}