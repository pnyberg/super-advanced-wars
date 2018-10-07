package map.area;

import hero.Hero;
import main.HeroHandler;
import map.UnitGetter;
import units.Unit;
import units.UnitType;

public class AreaChecker {
	private HeroHandler heroHandler;
	private UnitGetter unitGetter;
	private Area[][] map;
	private boolean[][] moveabilityMatrix;

	public AreaChecker(HeroHandler heroHandler, UnitGetter unitGetter, Area[][] map, boolean[][] moveabilityMatrix) {
		this.heroHandler = heroHandler;
		this.unitGetter = unitGetter;
		this.map = map;
		this.moveabilityMatrix = moveabilityMatrix;
	}

	public boolean isLand(int x, int y) {
		TerrainType terrainType = map[x][y].getTerrainType();
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

	public boolean areaOccupiedByNonFriendly(int x, int y) {
		return areaOccupiedByNonFriendly(x, y, heroHandler.getCurrentHero());
	}
}