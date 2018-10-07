package units;

import cursors.Cursor;
import gameObjects.ChosenObject;
import gameObjects.MapDim;
import map.UnitGetter;
import map.area.Area;
import map.area.AreaChecker;
import map.area.TerrainType;
import routing.RouteChecker;
import units.airMoving.BCopter;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.tireMoving.Missiles;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.APC;
import units.treadMoving.Artillery;
import units.treadMoving.MDTank;
import units.treadMoving.Neotank;
import units.treadMoving.Tank;

public class ContUnitHandler {
	private MapDim mapDimension;
	private ChosenObject chosenObject;
	private Area[][] map;
	private Cursor cursor;
	private UnitGetter unitGetter;
	private AreaChecker areaChecker;
	private RouteChecker routeChecker;
	
	public ContUnitHandler(MapDim mapDimension, ChosenObject chosenObject, Area[][] map, Cursor cursor, UnitGetter unitGetter, AreaChecker areaChecker, RouteChecker routeChecker) {
		this.mapDimension = mapDimension;
		this.chosenObject = chosenObject;
		this.map = map;
		this.cursor = cursor;
		this.unitGetter = unitGetter;
		this.areaChecker = areaChecker;
		this.routeChecker = routeChecker;
	}

	public void handleDroppingOff() {
		Unit containedUnit = null;
		if (chosenObject.chosenUnit instanceof APC) {
			((APC)chosenObject.chosenUnit).regulateDroppingOff(true);
			containedUnit = ((APC)chosenObject.chosenUnit).getUnit();
		} else if (chosenObject.chosenUnit instanceof TCopter) {
			((TCopter)chosenObject.chosenUnit).regulateDroppingOff(true);
			containedUnit = ((TCopter)chosenObject.chosenUnit).getUnit();
		} else if (chosenObject.chosenUnit instanceof Lander) {
			((Lander)chosenObject.chosenUnit).regulateDroppingOff(true);
			containedUnit = ((Lander)chosenObject.chosenUnit).getChosenUnit();
		} else if (chosenObject.chosenUnit instanceof Cruiser) {
			((Cruiser)chosenObject.chosenUnit).regulateDroppingOff(true);
			containedUnit = ((Cruiser)chosenObject.chosenUnit).getChosenUnit();
		}

		if (containedUnit == null) {
			return;
		}

		int x = chosenObject.chosenUnit.getPoint().getX();
		int y = chosenObject.chosenUnit.getPoint().getY();

		if (y > 0 && validPosition(containedUnit, x, y - 1)) {
			y--;
		} else if (x < (mapDimension.width - 1) && validPosition(containedUnit, x + 1, y)) {
			x++;
		} else if (validPosition(containedUnit, x, y + 1)) {
			y++;
		} else if (validPosition(containedUnit, x - 1, y)) {
			x--;
		} else {
			return; // cannot drop unit off anywhere
		}

		if (unitCanBeDroppedOff()) {
			cursor.setPosition(x, y);
		} else {
			cursor.setPosition(chosenObject.chosenUnit.getPoint().getX(), chosenObject.chosenUnit.getPoint().getY());
		}
	}

	public boolean unitIsDroppingOff() {
		if (chosenObject.chosenUnit instanceof APC) {
			if (((APC)chosenObject.chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenObject.chosenUnit instanceof TCopter) {
			if (((TCopter)chosenObject.chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenObject.chosenUnit instanceof Lander) {
			if (((Lander)chosenObject.chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenObject.chosenUnit instanceof Cruiser) {
			if (((Cruiser)chosenObject.chosenUnit).isDroppingOff()) {
				return true;
			}
		}

		return false;
	}

	public boolean unitCanBeDroppedOff() {
		if (chosenObject.chosenUnit instanceof APC) {
			((APC)chosenObject.chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((APC)chosenObject.chosenUnit).getUnit());
		} else if (chosenObject.chosenUnit instanceof TCopter) {
			((TCopter)chosenObject.chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((TCopter)chosenObject.chosenUnit).getUnit());
		} else if (chosenObject.chosenUnit instanceof Lander) {
			((Lander)chosenObject.chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((Lander)chosenObject.chosenUnit).getChosenUnit());
		} else if (chosenObject.chosenUnit instanceof Cruiser) {
			((Cruiser)chosenObject.chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((Cruiser)chosenObject.chosenUnit).getChosenUnit());
		}

		return false;
	}

	private boolean unitCanBeDroppedOff(Unit unit) {
		if (unit == null) {
			return false;
		}

		int x = chosenObject.chosenUnit.getPoint().getX();
		int y = chosenObject.chosenUnit.getPoint().getY();

		if (y > 0 && validPosition(unit, x, y - 1)) {
			return true;
		} else if (x < (mapDimension.width - 1) && validPosition(unit, x + 1, y)) {
			return true;
		} else if (y < (mapDimension.height - 1) && validPosition(unit, x, y + 1)) {
			return true;
		} else if (x > 0 && validPosition(unit, x - 1, y)) {
			return true;
		}

		return false;
	}

	public boolean landerAtDroppingOffPosition(int x, int y) {
		TerrainType areaValue = map[x][y].getTerrainType();

		if (areaValue == TerrainType.SHORE || areaValue == TerrainType.PORT) {
			return true;
		} 

		return false;
	}

	public void moveDroppingOffCursorClockwise() {
		int unitX = chosenObject.chosenUnit.getPoint().getX();
		int unitY = chosenObject.chosenUnit.getPoint().getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		Unit containedUnit = null;

		if (chosenObject.chosenUnit instanceof APC) {
			containedUnit = ((APC)chosenObject.chosenUnit).getUnit();
		} else if (chosenObject.chosenUnit instanceof TCopter) {
			containedUnit = ((TCopter)chosenObject.chosenUnit).getUnit();
		} else if (chosenObject.chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenObject.chosenUnit).getChosenUnit();
		} else if (chosenObject.chosenUnit instanceof Cruiser) {
			containedUnit = ((Cruiser)chosenObject.chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}

		if (xDiff == 1) {
			if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 2, cursorY)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (yDiff == 1) {
			if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX, cursorY - 2)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		}
	}

	public void moveDroppingOffCursorCounterclockwise() {
		int unitX = chosenObject.chosenUnit.getPoint().getX();
		int unitY = chosenObject.chosenUnit.getPoint().getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		Unit containedUnit = null;

		if (chosenObject.chosenUnit instanceof APC) {
			containedUnit = ((APC)chosenObject.chosenUnit).getUnit();
		} else if (chosenObject.chosenUnit instanceof TCopter) {
			containedUnit = ((TCopter)chosenObject.chosenUnit).getUnit();
		} else if (chosenObject.chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenObject.chosenUnit).getChosenUnit();
		} else if (chosenObject.chosenUnit instanceof Cruiser) {
			containedUnit = ((Cruiser)chosenObject.chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}
		
		if (xDiff == 1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 2, cursorY)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX, cursorY - 2)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapDimension.height - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (unitX < (mapDimension.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}

	private boolean validPosition(Unit unit, int testX, int testY) {
		return !areaChecker.areaOccupiedByAny(unit, testX, testY) 
			&& routeChecker.allowedMovementPosition(testX, testY, unit.getMovementType());
	}

	public boolean unitEntryingContainerUnit(Unit unit, int x, int y) {
		if (unit instanceof Infantry || unit instanceof Mech) {
			return footsoldierEnterableUnitAtPosition(x, y);
		} else if (unit instanceof Recon ||
					unit instanceof Tank ||
					unit instanceof MDTank ||
					unit instanceof Neotank ||
					unit instanceof APC ||
					unit instanceof Artillery ||
					unit instanceof Rocket ||
					unit instanceof AAir ||
					unit instanceof Missiles) {
			return landbasedEnterableUnitAtPosition(x, y);
		} else if (unit instanceof BCopter || unit instanceof TCopter) {
			return copterEnterableUnitAtPosition(x, y);
		}

		return false;
	}

	public boolean footsoldierEnterableUnitAtPosition(int x, int y) {
		if (landbasedEnterableUnitAtPosition(x, y)) {
			return true;
		}

		Unit unit = unitGetter.getFriendlyUnit(x, y);

		if (unit instanceof APC && !((APC)unit).isFull()) {
			return true;
		}
		if (unit instanceof TCopter && !((TCopter)unit).isFull()) {
			return true;
		}
		return false;
	}

	public boolean landbasedEnterableUnitAtPosition(int x, int y) {
		Unit unit = unitGetter.getFriendlyUnit(x, y);

		if (unit instanceof Lander && !((Lander)unit).isFull()) {
			return true;
		} 

		return false;
	}
	
	public boolean copterEnterableUnitAtPosition(int x, int y) {
		Unit unit = unitGetter.getFriendlyUnit(x, y);

		if (unit instanceof Cruiser && !((Cruiser)unit).isFull()) {
			return true;
		} 

		return false;
	}
}