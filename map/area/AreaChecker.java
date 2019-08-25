package map.area;

import hero.Hero;
import hero.HeroHandler;
import map.GameMap;
import map.UnitGetter;
import routing.MoveabilityMatrixFactory;
import unitUtils.UnitType;
import units.Unit;

public class AreaChecker {
	private UnitGetter unitGetter;
	private GameMap gameMap;
	private boolean[][] moveabilityMatrix;

	public AreaChecker(HeroHandler heroHandler, GameMap gridMap) {
		this.unitGetter = new UnitGetter(heroHandler);
		this.gameMap = gridMap;
		moveabilityMatrix = new MoveabilityMatrixFactory().getMoveabilityMatrix();
	}

	public boolean isLand(int tileX, int tileY) {
		TerrainType terrainType = gameMap.getTerrainType(tileX, tileY);
		return moveabilityMatrix[UnitType.INFANTRY.unitIndex()][terrainType.terrainTypeIndex()];
	}

	public boolean areaOccupiedByAny(Unit chosenUnit, int x, int y) {
		Unit testAnyUnit = unitGetter.getAnyUnit(x, y);
		if (chosenUnit == testAnyUnit) {
			return false;
		}
		return testAnyUnit != null;
	}

	public boolean areaOccupiedByFriendly(Unit chosenUnit, int x, int y) {
		Unit testFriendlyUnit = unitGetter.getFriendlyUnit(x, y);
		if (chosenUnit == testFriendlyUnit) {
			return false;
		}
		return testFriendlyUnit != null;
	}

	public boolean areaOccupiedByNonFriendly(int x, int y, Hero hero) {
		Unit testAnyUnit = unitGetter.getNonFriendlyUnit(x, y, hero);
		return testAnyUnit != null;
	}
}