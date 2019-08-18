// TODO: rewrite code to be more readable
package unitUtils;

import cursors.Cursor;
import gameObjects.GameProp;
import gameObjects.MapDim;
import map.GameMap;
import map.UnitGetter;
import map.area.AreaChecker;
import map.area.TerrainType;
import routing.RouteChecker;
import units.Unit;
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
	private GameMap gameMap;
	private Cursor cursor;
	private UnitGetter unitGetter;
	private AreaChecker areaChecker;
	private RouteChecker routeChecker;
	
	public ContUnitHandler(GameProp gameProp, GameMap gameMap, Cursor cursor, UnitGetter unitGetter, AreaChecker areaChecker, RouteChecker routeChecker) {
		this.gameProp = gameProp;
		this.gameMap = gameMap;
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
			containedUnit = ((APC)chosenUnit).getContainedUnit();
		} else if (chosenUnit.hasUnitContainer()) {
			chosenUnit.getUnitContainer().regulateDroppingOff(true);
			containedUnit = chosenUnit.getUnitContainer().getChosenUnit();
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

		int tileX = chosenUnit.getPoint().getX() / gameProp.getMapDim().tileSize;
		int tileY = chosenUnit.getPoint().getY() / gameProp.getMapDim().tileSize;

		if (tileY > 0 && validPosition(containedUnit, tileX, tileY - 1)) {
			tileY--;
		} else if (tileX < (gameProp.getMapDim().getTileWidth() - 1) && validPosition(containedUnit, tileX + 1, tileY)) {
			tileX++;
		} else if (tileY < (gameProp.getMapDim().getTileHeight() - 1) && validPosition(containedUnit, tileX, tileY + 1)) {
			tileY++;
		} else if (tileX > 0 && validPosition(containedUnit, tileX - 1, tileY)) {
			tileX--;
		} else {
			return; // cannot drop unit off anywhere
		}

		if (unitCanBeDroppedOff()) {
			cursor.setPosition(tileX * gameProp.getMapDim().tileSize, tileY * gameProp.getMapDim().tileSize);
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
		} else if (chosenUnit != null && chosenUnit.hasUnitContainer()) {
			if (chosenUnit.getUnitContainer().isDroppingOff()) {
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
			return unitCanBeDroppedOff(((APC)chosenUnit).getContainedUnit());
		} else if (chosenUnit.hasUnitContainer()) {
			chosenUnit.getUnitContainer().regulateDroppingOff(true);
			return unitCanBeDroppedOff(chosenUnit.getUnitContainer().getChosenUnit());
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
		int tileX = gameProp.getChosenObject().chosenUnit.getPoint().getX() / gameProp.getMapDim().tileSize;
		int tileY = gameProp.getChosenObject().chosenUnit.getPoint().getY() / gameProp.getMapDim().tileSize;

		if (tileY > 0 && validPosition(unit, tileX, tileY - 1)) {
			return true;
		} else if (tileX < (mapDim.getTileWidth() - 1) && validPosition(unit, tileX + 1, tileY)) {
			return true;
		} else if (tileY < (mapDim.getTileHeight() - 1) && validPosition(unit, tileX, tileY + 1)) {
			return true;
		} else if (tileX > 0 && validPosition(unit, tileX - 1, tileY)) {
			return true;
		}

		return false;
	}

	public boolean landerAtDroppingOffPosition(int x, int y) {
		int tileX = x / gameProp.getMapDim().tileSize;
		int tileY = y / gameProp.getMapDim().tileSize;
		TerrainType areaValue = gameMap.getMap()[tileX][tileY].getTerrainType();

		if (areaValue == TerrainType.SHOAL || areaValue == TerrainType.PORT) {
			return true;
		} 

		return false;
	}

	public void moveDroppingOffCursorClockwise() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDim mapDim = gameProp.getMapDim();
		int cursorTileX = cursor.getX() / gameProp.getMapDim().tileSize;
		int cursorTileY = cursor.getY() / gameProp.getMapDim().tileSize;
		int unitTileX = chosenUnit.getPoint().getX() / gameProp.getMapDim().tileSize;
		int unitTileY = chosenUnit.getPoint().getY() / gameProp.getMapDim().tileSize;
		int xTileDiff = cursorTileX - unitTileX;
		int yTileDiff = cursorTileY - unitTileY;
		Unit containedUnit = null;

		if (chosenUnit instanceof APC) {
			containedUnit = ((APC)chosenUnit).getContainedUnit();
		} else if (chosenUnit.hasUnitContainer()) {
			containedUnit = chosenUnit.getUnitContainer().getChosenUnit();
		} else if (chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else if (chosenUnit instanceof Cruiser) {
			containedUnit = ((Cruiser)chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}

		if (xTileDiff == 1) {
			if (unitTileY < (mapDim.getTileHeight() - 1) && validPosition(containedUnit, cursorTileX - 1, cursorTileY + 1)) {
				setCursorPosition(cursorTileX - 1, cursorTileY + 1);
			} else if (unitTileX > 0 && validPosition(containedUnit, cursorTileX - 2, cursorTileY)) {
				setCursorPosition(cursorTileX - 2, cursorTileY);
			} else if (unitTileY > 0 && validPosition(containedUnit, cursorTileX - 1, cursorTileY - 1)) {
				setCursorPosition(cursorTileX - 1, cursorTileY - 1);
			}
		} else if (yTileDiff == 1) {
			if (unitTileX > 0 && validPosition(containedUnit, cursorTileX - 1, cursorTileY - 1)) {
				setCursorPosition(cursorTileX - 1, cursorTileY - 1);
			} else if (unitTileY > 0 && validPosition(containedUnit, cursorTileX, cursorTileY - 2)) {
				setCursorPosition(cursorTileX, cursorTileY - 2);
			} else if (unitTileX < (mapDim.getTileWidth() - 1) && validPosition(containedUnit, cursorTileX + 1, cursorTileY - 1)) {
				setCursorPosition(cursorTileX + 1, cursorTileY - 1);
			}
		} else if (xTileDiff == -1) {
			if (unitTileY > 0 && validPosition(containedUnit, cursorTileX + 1, cursorTileY - 1)) {
				setCursorPosition(cursorTileX + 1, cursorTileY - 1);
			} else if (unitTileX < (mapDim.getTileWidth() - 1) && validPosition(containedUnit, cursorTileX + 2, cursorTileY)) {
				setCursorPosition(cursorTileX + 2, cursorTileY);
			} else if (unitTileY < (mapDim.getTileHeight() - 1) && validPosition(containedUnit, cursorTileX + 1, cursorTileY + 1)) {
				setCursorPosition(cursorTileX + 1, cursorTileY + 1);
			}
		} else { // yDiff == -1
			if (unitTileX < (mapDim.getTileWidth() - 1) && validPosition(containedUnit, cursorTileX + 1, cursorTileY + 1)) {
				setCursorPosition(cursorTileX + 1, cursorTileY + 1);
			} else if (unitTileY < (mapDim.getTileHeight() - 1) && validPosition(containedUnit, cursorTileX, cursorTileY + 2)) {
				setCursorPosition(cursorTileX, cursorTileY + 2);
			} else if (unitTileX > 0 && validPosition(containedUnit, cursorTileX - 1, cursorTileY + 1)) {
				setCursorPosition(cursorTileX - 1, cursorTileY + 1);
			}
		}
	}

	public void moveDroppingOffCursorCounterclockwise() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDim mapDim = gameProp.getMapDim();
		int unitTileX = chosenUnit.getPoint().getX() / gameProp.getMapDim().tileSize;
		int unitTileY = chosenUnit.getPoint().getY() / gameProp.getMapDim().tileSize;
		int cursorTileX = cursor.getX() / gameProp.getMapDim().tileSize;
		int cursorTileY = cursor.getY() / gameProp.getMapDim().tileSize;
		int xDiff = cursorTileX - unitTileX;
		int yDiff = cursorTileY - unitTileY;
		Unit containedUnit = null;

		if (chosenUnit instanceof APC) {
			containedUnit = ((APC)chosenUnit).getContainedUnit();
		} else if (chosenUnit.hasUnitContainer()) {
			containedUnit = chosenUnit.getUnitContainer().getChosenUnit();
		} else if (chosenUnit instanceof Lander) {
			containedUnit = ((Lander)chosenUnit).getChosenUnit();
		} else if (chosenUnit instanceof Cruiser) {
			containedUnit = ((Cruiser)chosenUnit).getChosenUnit();
		} else {
			return; // shouldn't be able to get here
		}
		
		if (xDiff == 1) {
			if (unitTileY > 0 && validPosition(containedUnit, cursorTileX - 1, cursorTileY - 1)) {
				setCursorPosition(cursorTileX - 1, cursorTileY - 1);
			} else if (unitTileX > 0 && validPosition(containedUnit, cursorTileX - 2, cursorTileY)) {
				setCursorPosition(cursorTileX - 2, cursorTileY);
			} else if (unitTileY < (mapDim.getTileHeight() - 1) && validPosition(containedUnit, cursorTileX - 1, cursorTileY + 1)) {
				setCursorPosition(cursorTileX - 1, cursorTileY + 1);
			}
		} else if (yDiff == 1) {
			if (unitTileX < (mapDim.getTileWidth() - 1) && validPosition(containedUnit, cursorTileX + 1, cursorTileY - 1)) {
				setCursorPosition(cursorTileX + 1, cursorTileY - 1);
			} else if (unitTileY > 0 && validPosition(containedUnit, cursorTileX, cursorTileY - 2)) {
				setCursorPosition(cursorTileX, cursorTileY - 2);
			} else if (unitTileX > 0 && validPosition(containedUnit, cursorTileX - 1, cursorTileY - 1)) {
				setCursorPosition(cursorTileX - 1, cursorTileY - 1);
			}
		} else if (xDiff == -1) {
			if (unitTileY < (mapDim.getTileHeight() - 1) && validPosition(containedUnit, cursorTileX + 1, cursorTileY + 1)) {
				setCursorPosition(cursorTileX + 1, cursorTileY + 1);
			} else if (unitTileX < (mapDim.getTileWidth() - 1) && validPosition(containedUnit, cursorTileX + 2, cursorTileY)) {
				setCursorPosition(cursorTileX + 2, cursorTileY);
			} else if (unitTileY > 0 && validPosition(containedUnit, cursorTileX + 1, cursorTileY - 1)) {
				setCursorPosition(cursorTileX + 1, cursorTileY - 1);
			}
		} else { // yDiff == -1
			if (unitTileX > 0 && validPosition(containedUnit, cursorTileX - 1, cursorTileY + 1)) {
				setCursorPosition(cursorTileX - 1, cursorTileY + 1);
			} else if (unitTileY < (mapDim.getTileHeight() - 1) && validPosition(containedUnit, cursorTileX, cursorTileY + 2)) {
				setCursorPosition(cursorTileX, cursorTileY + 2);
			} else if (unitTileX < (mapDim.getTileWidth() - 1) && validPosition(containedUnit, cursorTileX + 1, cursorTileY + 1)) {
				setCursorPosition(cursorTileX + 1, cursorTileY + 1);
			}
		}
	}
	
	private void setCursorPosition(int tileX, int tileY) {
		cursor.setPosition(tileX * gameProp.getMapDim().tileSize, tileY * gameProp.getMapDim().tileSize);
	}

	private boolean validPosition(Unit unit, int tileX, int tileY) {
		return !areaChecker.areaOccupiedByAny(unit, tileX * gameProp.getMapDim().tileSize, tileY * gameProp.getMapDim().tileSize) 
			&& routeChecker.allowedMovementPosition(tileX, tileY, unit.getMovementType());
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
		if (unit != null && unit.hasUnitContainer() && !unit.getUnitContainer().isFull()) {
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