package hero;

import java.awt.Graphics;

import gameObjects.MapDim;
import graphics.HeroPortraitPainter;
import main.HeroHandler;

public class HeroPortrait {
	private HeroHandler heroHandler;
	private boolean leftSide;
	private HeroPortraitPainter heroPortraitPainter;

	public HeroPortrait(MapDim mapDimension, HeroHandler heroHandler) {
		this.heroHandler = heroHandler;
		leftSide = true;
		heroPortraitPainter = new HeroPortraitPainter(mapDimension);
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
	
	public HeroHandler getHeroHandler() {
		return heroHandler;
	}

	public void paint(Graphics g) {
		if (leftSide) {
			heroPortraitPainter.paintLeftSide(g, heroHandler.getCurrentHero());
		} else {
			heroPortraitPainter.paintRightSide(g, heroHandler.getCurrentHero());
		}
	}
}