package hero;

import java.util.ArrayList;

import units.Unit;

public class HeroHandler {
	private ArrayList<Hero> heroes;
	private Hero currentHero;
	private int heroIndex;
	
	public HeroHandler() {
		heroes = new ArrayList<Hero>();
		currentHero = null;
		heroIndex = 0;
	}

	public void selectStartHero() {
		currentHero = heroes.get(heroIndex);
	}

	public void nextHero() {
		heroIndex = (heroIndex + 1) % heroes.size();
		currentHero = heroes.get(heroIndex);
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
		return currentHero;
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