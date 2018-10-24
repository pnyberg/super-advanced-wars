package menus.unit;

import combat.AttackRangeHandler;
import cursors.Cursor;
import gameObjects.GameProp;
import main.SupplyHandler;
import map.UnitGetter;
import map.area.AreaChecker;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import point.Point;
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
	private AreaChecker areaChecker;
	private BuildingHandler buildingHandler;
	private AttackRangeHandler attackRangeHandler;

	public UnitMenuHandler(GameProp gameProp, ContUnitHandler containerUnitHandler, SupplyHandler supplyHandler, UnitGetter unitGetter, AreaChecker areaChecker, BuildingHandler buildingHandler, AttackRangeHandler attackRangeHandler) {
		unitMenu = new UnitMenu(gameProp.getMapDim().tileSize);
		this.gameProp = gameProp;
		this.containerUnitHandler = containerUnitHandler;
		this.supplyHandler = supplyHandler;
		this.unitGetter = unitGetter;
		this.areaChecker = areaChecker;
		this.buildingHandler = buildingHandler;
		this.attackRangeHandler = attackRangeHandler;
		this.containerUnitHandler = containerUnitHandler; 
	}

	public void handleOpenUnitMenu(Cursor cursor) {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		boolean hurtAtSamePosition = unitGetter.hurtSameTypeUnitAtPosition(chosenUnit, cursor.getX(), cursor.getY());
		
		if (!areaChecker.areaOccupiedByFriendly(chosenUnit, cursor.getX(), cursor.getY()) || hurtAtSamePosition
				|| containerUnitHandler.unitEntryingContainerUnit(chosenUnit, cursor.getX(), cursor.getY())) {
			if (hurtAtSamePosition) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().join = true;
			} else if (attackRangeHandler.unitCanFire(chosenUnit, cursor)) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().fire = true;
			}

			if (chosenUnit instanceof Infantry || chosenUnit instanceof Mech) {
				if (containerUnitHandler.footsoldierEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().enter = true;
				}
				if (buildingHandler.isNonFriendlyBuilding(cursor.getX(), cursor.getY()) 
						&& unitGetter.getFriendlyUnitExceptSelf(chosenUnit, cursor.getX(), cursor.getY()) == null) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().capt = true;
				}
			} else if (chosenUnit instanceof APC) {
				// should only be allowed this when close to a friendly unit
				if (supplyHandler.mayAPCSUpply(cursor.getX(), cursor.getY())) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().supply = true;
				}

				if (((APC)chosenUnit).isFull()) {
					Unit holdUnit = ((APC)chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenUnit instanceof TCopter) {
				int cursorTileX = cursor.getX() / gameProp.getMapDim().tileSize;
				int cursorTileY = cursor.getY() / gameProp.getMapDim().tileSize;
				if (((TCopter)chosenUnit).isFull() && areaChecker.isLand(cursorTileX, cursorTileY)) {
					Unit holdUnit = ((TCopter)chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			} else if (chosenUnit instanceof Lander) {
				if (containerUnitHandler.landerAtDroppingOffPosition(cursor.getX(), cursor.getY())) {
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

			if (containerUnitHandler.landbasedEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
				if (!(chosenUnit instanceof Lander)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().enter = true;
				}
			} else if (containerUnitHandler.copterEnterableUnitAtPosition(cursor.getX(), cursor.getY())) {
				if (!(chosenUnit instanceof Cruiser)) {
					unitMenu.getUnitMenuRowEntryBooleanHandler().enter = true;
				}
			}

			if (!areaChecker.areaOccupiedByFriendly(chosenUnit, cursor.getX(), cursor.getY())) {
				unitMenu.getUnitMenuRowEntryBooleanHandler().wait = true;
			}

			unitMenu.openMenu(cursor.getX(), cursor.getY());
			
			if (chosenUnit.getPoint().getX() != cursor.getX() || chosenUnit.getPoint().getY() != cursor.getY()) {
				if (chosenUnit.isCapting()) {
					Point point = chosenUnit.getPoint();
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