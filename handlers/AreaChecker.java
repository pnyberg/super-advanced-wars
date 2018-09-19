package handlers;

import heroes.Hero;
import units.Unit;

public class AreaChecker {
	private HeroHandler heroHandler;
	private UnitGetter unitGetter;

	public AreaChecker(HeroHandler heroHandler, UnitGetter unitGetter) {
		this.heroHandler = heroHandler;
		this.unitGetter = unitGetter;
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