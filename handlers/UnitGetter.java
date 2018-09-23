package handlers;

import heroes.Hero;
import heroes.HeroPortrait;
import units.Unit;

public class UnitGetter {
	private HeroPortrait portrait;
	
	public UnitGetter(HeroPortrait portrait) {
		this.portrait = portrait;
	}

	public Unit getAnyUnit(int x, int y) {
		for (int h = 0 ; h < portrait.getHeroHandler().getNumberOfHeroes() ; h++) {
			for (int k = 0 ; k < portrait.getHeroHandler().getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = portrait.getHeroHandler().getUnitFromHero(h, k);
				if (unit.getPoint().getX() == x && unit.getPoint().getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}

		return null;
	}

	public Unit getNonFriendlyUnit(int x, int y, Hero hero) {
		for (int h = 0 ; h < portrait.getHeroHandler().getNumberOfHeroes() ; h++) {
			if (portrait.getHeroHandler().getHero(h) == hero) {
				continue;
			}
			for (int k = 0 ; k < portrait.getHeroHandler().getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = portrait.getHeroHandler().getUnitFromHero(h, k);
				if (unit.getPoint().getX() == x && unit.getPoint().getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}

		return null;
	}

	public Unit getNonFriendlyUnit(int x, int y) {
		return getNonFriendlyUnit(x, y, portrait.getHeroHandler().getCurrentHero());
	}

	public Unit getFriendlyUnit(int x, int y) {
		for (int k = 0 ; k < portrait.getHeroHandler().getCurrentHeroTroopSize() ; k++) {
			Unit unit = portrait.getHeroHandler().getUnitFromCurrentHero(k);
			if (unit.getPoint().getX() == x && unit.getPoint().getY() == y && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}

	public Unit getFriendlyUnitExceptSelf(Unit notUnit, int x, int y) {
		for (int k = 0 ; k < portrait.getHeroHandler().getCurrentHeroTroopSize() ; k++) {
			Unit unit = portrait.getHeroHandler().getUnitFromCurrentHero(k);
			if (unit.getPoint().getX() == x && unit.getPoint().getY() == y && unit != notUnit && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}
}
