package units;

import cursors.Cursor;
import gameObjects.ChosenObject;
import gameObjects.GameProp;
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
	private GameProp gameProp;
	private Area[][] map;
	private Cursor cursor;
	private UnitGetter unitGetter;
	private AreaChecker areaChecker;
	private RouteChecker routeChecker;
	
	public ContUnitHandler(GameProp gameProp, Area[][] map, Cursor cursor, UnitGetter unitGetter, AreaChecker areaChecker, RouteChecker routeChecker) {
		this.gameProp = gameProp;
		this.map = map;
		this.cursor = cursor;
		this.unitGetter = unitGetter;
		this.areaChecker = areaChecker;
		this.routeChecker = routeChecker;
	}

	public void handleDroppingOff() {
		Unit containedUnit = null;
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		if (chosenUnit instanceof APC) {
			((APC)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((APC)chosenUnit).getUnit();
		} else if (chosenUnit instanceof TCopter) {
			((TCopter)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((TCopter)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			((Lander)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else if (chosenUnit instanceof Cruiser) {
			((Cruiser)chosenUnit).regulateDroppingOff(true);
			containedUnit = ((Cruiser)chosenUnit).getChosenUnit();
		}

		if (containedUnit == null) {
			return;
		}

		int x = chosenUnit.getPoint().getX();
		int y = chosenUnit.getPoint().getY();

		if (y > 0 && validPosition(containedUnit, x, y - 1)) {
			y--;
		} else if (x < (gameProp.getMapDim().width - 1) && validPosition(containedUnit, x + 1, y)) {
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
			cursor.setPosition(chosenUnit.getPoint().getX(), chosenUnit.getPoint().getY());
		}
	}

	public boolean unitIsDroppingOff() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		if (chosenUnit instanceof APC) {
			if (((APC)chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenUnit instanceof TCopter) {
			if (((TCopter)chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenUnit instanceof Lander) {
			if (((Lander)chosenUnit).isDroppingOff()) {
				return true;
			}
		} else if (chosenUnit instanceof Cruiser) {
			if (((Cruiser)chosenUnit).isDroppingOff()) {
				return true;
			}
		}

		return false;
	}

	public boolean unitCanBeDroppedOff() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		if (chosenUnit instanceof APC) {
			((APC)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((APC)chosenUnit).getUnit());
		} else if (chosenUnit instanceof TCopter) {
			((TCopter)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((TCopter)chosenUnit).getUnit());
		} else if (chosenUnit instanceof Lander) {
			((Lander)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((Lander)chosenUnit).getChosenUnit());
		} else if (chosenUnit instanceof Cruiser) {
			((Cruiser)chosenUnit).regulateDroppingOff(true);
			return unitCanBeDroppedOff(((Cruiser)chosenUnit).getChosenUnit());
		}

		return false;
	}

	private boolean unitCanBeDroppedOff(Unit unit) {
		if (unit == null) {
			return false;
		}
		MapDim mapDim = gameProp.getMapDim();
		int x = gameProp.getChosenObject().chosenUnit.getPoint().getX();
		int y = gameProp.getChosenObject().chosenUnit.getPoint().getY();

		if (y > 0 && validPosition(unit, x, y - 1)) {
			return true;
		} else if (x < (mapDim.width - 1) && validPosition(unit, x + 1, y)) {
			return true;
		} else if (y < (mapDim.height - 1) && validPosition(unit, x, y + 1)) {
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
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDim mapDim = gameProp.getMapDim();
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();
		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;
		Unit containedUnit = null;

		if (chosenUnit instanceof APC) {
			containedUnit = ((APC)chosenUnit).getUnit();
		} else if (chosenUnit instanceof TCopter) {
			containedUnit = ((TCopter)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else if (chosenUnit instanceof Cruiser) {
			containedUnit = ((Cruiser)chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}

		if (xDiff == 1) {
			if (unitY < (mapDim.height - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
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
			} else if (unitX < (mapDim.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapDim.width - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (unitY < (mapDim.height - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			if (unitX < (mapDim.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapDim.height - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		}
	}

	public void moveDroppingOffCursorCounterclockwise() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDim mapDim = gameProp.getMapDim();
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();
		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;
		Unit containedUnit = null;

		if (chosenUnit instanceof APC) {
			containedUnit = ((APC)chosenUnit).getUnit();
		} else if (chosenUnit instanceof TCopter) {
			containedUnit = ((TCopter)chosenUnit).getUnit();
		} else if (chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else if (chosenUnit instanceof Cruiser) {
			containedUnit = ((Cruiser)chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}
		
		if (xDiff == 1) {
			if (unitY > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 2, cursorY)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (unitY < (mapDim.height - 1) && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			if (unitX < (mapDim.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX, cursorY - 2)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY - 1)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY < (mapDim.height - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapDim.width - 1) && validPosition(containedUnit, cursorX + 2, cursorY)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (unitY > 0 && validPosition(containedUnit, cursorX + 1, cursorY - 1)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			if (unitX > 0 && validPosition(containedUnit, cursorX - 1, cursorY + 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapDim.height - 1) && validPosition(containedUnit, cursorX, cursorY + 2)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (unitX < (mapDim.width - 1) && validPosition(containedUnit, cursorX + 1, cursorY + 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}

	private boolean validPosition(Unit unit, int testX, int testY) {
		return !areaChecker.areaOccupiedByAny(unit, testX * gameProp.getMapDim().tileSize, testY * gameProp.getMapDim().tileSize) 
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