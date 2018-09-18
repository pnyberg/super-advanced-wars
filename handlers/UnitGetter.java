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
		for (int h = 0 ; h < portrait.getNumberOfHeroes() ; h++) {
			for (int k = 0 ; k < portrait.getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = portrait.getUnitFromHero(h, k);
				if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}

		return null;
	}

	public Unit getNonFriendlyUnit(int x, int y, Hero hero) {
		for (int h = 0 ; h < portrait.getNumberOfHeroes() ; h++) {
			if (portrait.getHero(h) == hero) {
				continue;
			}
			for (int k = 0 ; k < portrait.getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = portrait.getUnitFromHero(h, k);
				if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
					return unit;
				}
			}
		}

		return null;
	}

	public Unit getNonFriendlyUnit(int x, int y) {
		return getNonFriendlyUnit(x, y, portrait.getCurrentHero());
	}

	public Unit getFriendlyUnit(int x, int y) {
		for (int k = 0 ; k < portrait.getCurrentHeroTroopSize() ; k++) {
			Unit unit = portrait.getUnitFromCurrentHero(k);
			if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}

	public Unit getFriendlyUnitExceptSelf(Unit notUnit, int x, int y) {
		for (int k = 0 ; k < portrait.getCurrentHeroTroopSize() ; k++) {
			Unit unit = portrait.getUnitFromCurrentHero(k);
			if (unit.getX() == x && unit.getY() == y && unit != notUnit && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}
}
