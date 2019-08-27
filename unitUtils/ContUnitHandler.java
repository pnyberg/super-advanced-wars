package unitUtils;

import cursors.Cursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import hero.Hero;
import map.GameMap;
import map.UnitGetter;
import map.area.AreaChecker;
import map.area.TerrainType;
import point.Point;
import routing.RouteChecker;
import units.Unit;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;

public class ContUnitHandler {
	private GameProperties gameProperties;
	private GameState gameState;
	private GameMap gameMap;
	private Cursor cursor;
	private UnitGetter unitGetter;
	private AreaChecker areaChecker;
	private RouteChecker routeChecker;
	
	public ContUnitHandler(GameProperties gameProperties, GameState gameState) {
		this.gameProperties = gameProperties;
		this.gameState = gameState;
		this.gameMap = gameProperties.getGameMap();
		this.cursor = gameState.getCursor();
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
		this.areaChecker = new AreaChecker(gameState.getHeroHandler(), gameMap);
		this.routeChecker = new RouteChecker(gameProperties, gameState);
	}

	public void handleDroppingOff() {
		Unit chosenUnit = gameState.getChosenUnit();
		chosenUnit.getUnitContainer().regulateDroppingOff(true);
		Unit containedUnit = chosenUnit.getUnitContainer().getChosenUnit();
		int tileSize = gameProperties.getMapDimension().tileSize;
		int tileX = chosenUnit.getPosition().getX() / tileSize;
		int tileY = chosenUnit.getPosition().getY() / tileSize;
		int mapTileWidth = gameProperties.getMapDimension().getTileWidth();
		int mapTileHeight = gameProperties.getMapDimension().getTileHeight();

		if (tileX < (mapTileWidth - 1) && validDropOffPosition(containedUnit, tileX + 1, tileY)) {
			int x = (tileX + 1) * tileSize;
			int y = tileY * tileSize;
			chosenUnit.getUnitContainer().addDropOffLocation(new Point(x, y));
		}
		if (tileY < (mapTileHeight - 1) && validDropOffPosition(containedUnit, tileX, tileY + 1)) {
			int x = tileX * tileSize;
			int y = (tileY + 1) * tileSize;
			chosenUnit.getUnitContainer().addDropOffLocation(new Point(x, y));
		}
		if (tileX > 0 && validDropOffPosition(containedUnit, tileX - 1, tileY)) {
			int x = (tileX - 1) * tileSize;
			int y = tileY * tileSize;
			chosenUnit.getUnitContainer().addDropOffLocation(new Point(x, y));
		}
		if (tileY > 0 && validDropOffPosition(containedUnit, tileX, tileY - 1)) {
			int x = tileX * tileSize;
			int y = (tileY - 1) * tileSize;
			chosenUnit.getUnitContainer().addDropOffLocation(new Point(x, y));
		}
		
		if (unitCanDropOffUnit()) {
			Point dropOffLocation = chosenUnit.getUnitContainer().getNextDropOffLocation();
			cursor.setPosition(dropOffLocation.getX(), dropOffLocation.getY());
		} else {
			cursor.setPosition(chosenUnit.getPosition().getX(), chosenUnit.getPosition().getY());
		}
	}

	public boolean unitIsDroppingOff() {
		Unit chosenUnit = gameState.getChosenUnit();
		if (chosenUnit == null) {
			return false;
		}
		if (!chosenUnit.hasUnitContainer()) {
			return false;
		}
		return chosenUnit.getUnitContainer().isDroppingOff();
	}

	public boolean unitCanDropOffUnit() {
		Unit chosenUnit = gameState.getChosenUnit();
		if (chosenUnit.hasUnitContainer()) {
			chosenUnit.getUnitContainer().regulateDroppingOff(true);
			return unitCanBeDroppedOff(chosenUnit.getUnitContainer().getChosenUnit());
		}
		return false;
	}

	private boolean unitCanBeDroppedOff(Unit unit) {
		if (unit == null) {
			return false;
		}
		MapDimension mapDimension = gameProperties.getMapDimension();
		int tileX = gameState.getChosenUnit().getPosition().getX() / gameProperties.getMapDimension().tileSize;
		int tileY = gameState.getChosenUnit().getPosition().getY() / gameProperties.getMapDimension().tileSize;

		if (tileY > 0 && validDropOffPosition(unit, tileX, tileY - 1)) {
			return true;
		} else if (tileX < (mapDimension.getTileWidth() - 1) && validDropOffPosition(unit, tileX + 1, tileY)) {
			return true;
		} else if (tileY < (mapDimension.getTileHeight() - 1) && validDropOffPosition(unit, tileX, tileY + 1)) {
			return true;
		} else if (tileX > 0 && validDropOffPosition(unit, tileX - 1, tileY)) {
			return true;
		}

		return false;
	}

	public boolean landerAtDroppingOffPosition(int x, int y) {
		int tileX = x / gameProperties.getMapDimension().tileSize;
		int tileY = y / gameProperties.getMapDimension().tileSize;
		TerrainType areaValue = gameMap.getTerrainType(tileX, tileY);
		if (areaValue == TerrainType.SHOAL || areaValue == TerrainType.PORT) {
			return true;
		} 
		return false;
	}

	private boolean validDropOffPosition(Unit unit, int tileX, int tileY) {
		int x = tileX * gameProperties.getMapDimension().tileSize;
		int y = tileY * gameProperties.getMapDimension().tileSize;
		if (areaChecker.areaOccupiedByAny(unit, x, y)) {
			return false;
		}
		Hero currentHero = gameState.getHeroHandler().getCurrentHero();
		return routeChecker.allowedMovementPosition(tileX, tileY, unit.getMovementType(), currentHero);
	}

	public boolean unitEntryingContainerUnit(Unit unit, int x, int y) {
		if (unit.getUnitCategory() == UnitCategory.FOOTMAN) {
			return footsoldierEnterableUnitAtPosition(x, y);
		} else if (unit.getUnitCategory() == UnitCategory.VEHICLE) {
			return landbasedEnterableUnitAtPosition(x, y);
		} else if (unit.getUnitCategory() == UnitCategory.COPTER) {
			return copterEnterableUnitAtPosition(x, y);
		}
		return false;
	}

	public boolean footsoldierEnterableUnitAtPosition(int x, int y) {
		if (landbasedEnterableUnitAtPosition(x, y)) {
			return true;
		}
		Unit unit = unitGetter.getFriendlyUnit(x, y);
		if (unit != null && unit.hasUnitContainer() && !unit.getUnitContainer().isFull()) {
			return true;
		}
		return false;
	}

	public boolean landbasedEnterableUnitAtPosition(int x, int y) {
		Unit unit = unitGetter.getFriendlyUnit(x, y);
		if (unit instanceof Lander && !unit.getUnitContainer().isFull()) {
			return true;
		}
		return false;
	}
	
	public boolean copterEnterableUnitAtPosition(int x, int y) {
		Unit unit = unitGetter.getFriendlyUnit(x, y);
		Unit chosenUnit = gameState.getChosenUnit();
		if (unit instanceof Cruiser && !unit.getUnitContainer().isFull() && unit != chosenUnit) {
			return true;
		} 
		return false;
	}
}