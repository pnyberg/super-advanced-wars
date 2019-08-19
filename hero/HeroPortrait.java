package hero;

import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.MapDimension;
import graphics.HeroPortraitPainter;
import main.HeroHandler;

public class HeroPortrait {
	private MapDimension mapDim;
	private HeroHandler heroHandler;
	private boolean leftSide;
	private HeroPortraitPainter heroPortraitPainter;

	public HeroPortrait(MapDimension mapDim, HeroHandler heroHandler) {
		this.mapDim = mapDim;
		this.heroHandler = heroHandler;
		leftSide = true;
		heroPortraitPainter = new HeroPortraitPainter(mapDim);
	}

	public void updateSideChoice(Cursor cursor) {
		int cursorTileX = cursor.getX() / mapDim.tileSize;
		int cursorTileY = cursor.getY() / mapDim.tileSize;
		if (cursorTileY < 2) {
			if (leftSide && cursorTileX < 4) {
				leftSide = false;
			} else if (!leftSide && cursorTileX >= mapDim.getTileWidth() - 4) { 
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