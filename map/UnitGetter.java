package map;

import hero.Hero;
import hero.HeroHandler;
import units.Unit;

public class UnitGetter {
	private HeroHandler heroHandler;
	
	public UnitGetter(HeroHandler heroHandler) {
		this.heroHandler = heroHandler;
	}

	public Unit getAnyUnit(int x, int y) {
		for (int heroIndex = 0 ; heroIndex < heroHandler.getNumberOfHeroes() ; heroIndex++) {
			Unit unit = getUnitForHero(x, y, heroIndex);
			if (unit != null) {
				return unit;
			}
		}
		return null;
	}

	public Unit getNonFriendlyUnit(int x, int y, Hero hero) {
		for (int heroIndex = 0 ; heroIndex < heroHandler.getNumberOfHeroes() ; heroIndex++) {
			if (heroHandler.getHero(heroIndex) == hero) {
				continue;
			}
			Unit unit = getUnitForHero(x, y, heroIndex);
			if (unit != null) {
				return unit;
			}
		}
		return null;
	}

	public Unit getNonFriendlyUnitForCurrentHero(int x, int y) {
		return getNonFriendlyUnit(x, y, heroHandler.getCurrentHero());
	}
	
	private Unit getUnitForHero(int x, int y, int heroIndex) {
		for (int k = 0 ; k < heroHandler.getTroopSize(heroIndex) ; k++) {
			Unit unit = heroHandler.getUnitFromHero(heroIndex, k);
			if (unit.getPosition().getX() == x && unit.getPosition().getY() == y) {
				if (unit.isHidden()) {
					continue;
				}
				return unit;
			}
		}
		return null;
	}

	public Unit getFriendlyUnit(int x, int y) {
		for (int heroIndex = 0 ; heroIndex < heroHandler.getCurrentHeroTroopSize() ; heroIndex++) {
			Unit unit = heroHandler.getUnitFromCurrentHero(heroIndex);
			if (unit.getPosition().getX() == x && unit.getPosition().getY() == y) {
				if (unit.isHidden()) {
					continue;
				}
				return unit;
			}
		}
		return null;
	}

	public Unit getFriendlyUnitExceptSelf(Unit notUnit, int x, int y) {
		for (int k = 0 ; k < heroHandler.getCurrentHeroTroopSize() ; k++) {
			Unit unit = heroHandler.getUnitFromCurrentHero(k);
			if (unit.getPosition().getX() == x && unit.getPosition().getY() == y) {
				if (unit.isHidden() || unit == notUnit) {
					continue;
				}
				return unit;
			}
		}
		return null;
	}

	public boolean hurtSameTypeUnitAtPosition(Unit unit, int x, int y) {
		Unit testUnit = getFriendlyUnitExceptSelf(unit, x, y);
		if (testUnit == null) {
			return false;
		}
		return testUnit.getUnitHealth().isVisiblyHurt() && testUnit.getClass().equals(unit.getClass());
	}
}