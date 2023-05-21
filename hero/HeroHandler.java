package hero;

import java.util.ArrayList;

import units.Unit;

public class HeroHandler {
	private ArrayList<Hero> heroes;
	private int heroIndex;
	private int coViewHeroIndex;
	
	public HeroHandler() {
		heroes = new ArrayList<Hero>();
		heroIndex = 0;
		coViewHeroIndex = 0;
	}

	public void nextHero() {
		heroIndex = (heroIndex + 1) % heroes.size();
	}

	public void resetCoViewHero() {
		coViewHeroIndex = 0;
	}

	public void prevCoViewHero() {
		coViewHeroIndex = (coViewHeroIndex - 1 + heroes.size()) % heroes.size();
	}

	public void nextCoViewHero() {
		coViewHeroIndex = (coViewHeroIndex + 1) % heroes.size();
	}

	public void addHero(Hero hero) {
		heroes.add(hero);
	}
	
	public int getNumberOfHeroes() {
		return heroes.size();
	}

	public Hero getHero(int index) {
		return heroes.get(index);
	}

	public Hero getCurrentHero() {
		return heroes.get(heroIndex);
	}

	public Hero getCoViewHero() {
		return heroes.get(coViewHeroIndex);
	}

	public Hero getHeroFromUnit(Unit testUnit) {
		for (int h = 0 ; h < getNumberOfHeroes() ; h++) {
			for (int k = 0 ; k < getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = heroes.get(h).getTroopHandler().getTroop(k);
				if (testUnit.getPosition().isSamePosition(unit.getPosition()) && !unit.isHidden()) {
					return getHero(h);
				}
			}
		}
		return null;
	}

	public Unit getUnitFromHero(int heroIndex, int index) {
		return getHero(heroIndex).getTroopHandler().getTroop(index);
	}

	public Unit getUnitFromCurrentHero(int index) {
		return getCurrentHero().getTroopHandler().getTroop(index);
	}

	public int getTroopSize(int heroIndex) {
		return getHero(heroIndex).getTroopHandler().getTroopSize();
	}

	public int getCurrentHeroTroopSize() {
		return getCurrentHero().getTroopHandler().getTroopSize();
	}
}