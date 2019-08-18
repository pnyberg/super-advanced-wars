package map;

import hero.Hero;
import main.HeroHandler;
import units.Unit;

public class UnitGetter {
	private HeroHandler heroHandler;
	
	public UnitGetter(HeroHandler heroHandler) {
		this.heroHandler = heroHandler;
	}

	public Unit getAnyUnit(int x, int y) {
		for (int h = 0 ; h < heroHandler.getNumberOfHeroes() ; h++) {
			for (int k = 0 ; k < heroHandler.getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = heroHandler.getUnitFromHero(h, k);
				if (unit.getPoint().getX() == x && unit.getPoint().getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}
		return null;
	}

	public Unit getNonFriendlyUnit(int x, int y, Hero hero) {
		for (int h = 0 ; h < heroHandler.getNumberOfHeroes() ; h++) {
			if (heroHandler.getHero(h) == hero) {
				continue;
			}
			for (int k = 0 ; k < heroHandler.getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = heroHandler.getUnitFromHero(h, k);
				if (unit.getPoint().getX() == x && unit.getPoint().getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}
		return null;
	}

	public Unit getNonFriendlyUnitForCurrentHero(int x, int y) {
		return getNonFriendlyUnit(x, y, heroHandler.getCurrentHero());
	}

	public Unit getFriendlyUnit(int x, int y) {
		for (int k = 0 ; k < heroHandler.getCurrentHeroTroopSize() ; k++) {
			Unit unit = heroHandler.getUnitFromCurrentHero(k);
			if (unit.getPoint().getX() == x && unit.getPoint().getY() == y && !unit.isHidden()) {
				return unit;
			}
		}
		return null;
	}

	public Unit getFriendlyUnitExceptSelf(Unit notUnit, int x, int y) {
		for (int k = 0 ; k < heroHandler.getCurrentHeroTroopSize() ; k++) {
			Unit unit = heroHandler.getUnitFromCurrentHero(k);
			if (unit.getPoint().getX() == x && unit.getPoint().getY() == y && unit != notUnit && !unit.isHidden()) {
				return unit;
			}
		}
		return null;
	}

	public boolean hurtSameTypeUnitAtPosition(Unit unit, int x, int y) {
		Unit testUnit = getFriendlyUnitExceptSelf(unit, x, y);
		return testUnit != null && testUnit.getUnitHealth().isVisiblyHurt() && testUnit.getClass().equals(unit.getClass());
	}
}