package hero;

import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.MapDim;
import graphics.HeroPortraitPainter;
import main.HeroHandler;

public class HeroPortrait {
	private HeroHandler heroHandler;
	private boolean leftSide;
	private HeroPortraitPainter heroPortraitPainter;
	private MapDim mapDim;

	public HeroPortrait(MapDim mapDim, HeroHandler heroHandler) {
		this.heroHandler = heroHandler;
		leftSide = true;
		heroPortraitPainter = new HeroPortraitPainter(mapDim);
		this.mapDim = mapDim;
	}

	public void updateSideChoice(Cursor cursor) {
		int cursorTileX = cursor.getX() / mapDim.tileSize;
		int cursorTileY = cursor.getY() / mapDim.tileSize;

		if (cursorTileY < 2) {
			if (leftSide && cursorTileX < 4) {
				leftSide = false;
			} else if (!leftSide && cursorTileX >= 16) {
				leftSide = true;
			}
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