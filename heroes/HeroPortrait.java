package heroes;

import units.*;

import java.awt.Graphics;
import java.util.ArrayList;

import graphics.HeroPortraitPainter;

public class HeroPortrait {
	private ArrayList<Hero> heroes;
	private Hero currentHero;
	private int heroIndex;

	private boolean leftSide;
	private HeroPortraitPainter heroPortraitPainter;

	public HeroPortrait(int mapWidth) {
		heroes = new ArrayList<Hero>();
		currentHero = null;
		heroIndex = 0;
		leftSide = true;
		heroPortraitPainter = new HeroPortraitPainter(mapWidth);
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

	public void updateSideChoice(int cursorX, int cursorY) {
		if (cursorY >= 2) {
			return;
		}

		if (leftSide && cursorX < 4) {
			leftSide = false;
		} else if (!leftSide && cursorX >= 16) {
			leftSide = true;
		}
	}

	public int getNumberOfHeroes() {
		return heroes.size();
	}

	public Hero getHeroFromUnit(Unit testUnit) {
		for (int h = 0 ; h < getNumberOfHeroes() ; h++) {
			for (int k = 0 ; k < getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = heroes.get(h).getTroopHandler().getTroop(k);
				if (unit.getX() == testUnit.getX() && unit.getY() == testUnit.getY() && !unit.isHidden()) {
					return getHero(h);
				}
			}
		}
		return null;
	}

	public Hero getHero(int index) {
		return heroes.get(index);
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public void paint(Graphics g) {
		if (leftSide) {
			heroPortraitPainter.paintLeftSide(g, currentHero);
		} else {
			heroPortraitPainter.paintRightSide(g, currentHero);
		}
	}
}