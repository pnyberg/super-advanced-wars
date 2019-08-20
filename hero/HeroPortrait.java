package hero;

import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.DimensionObject;
import graphics.HeroPortraitPainter;
import main.HeroHandler;

public class HeroPortrait {
	private DimensionObject mapDimension;
	private HeroHandler heroHandler;
	private boolean leftSide;
	private HeroPortraitPainter heroPortraitPainter;

	public HeroPortrait(DimensionObject mapDimensionDim, HeroHandler heroHandler) {
		this.mapDimension = mapDimensionDim;
		this.heroHandler = heroHandler;
		leftSide = true;
		heroPortraitPainter = new HeroPortraitPainter(mapDimensionDim);
	}

	public void updateSideChoice(Cursor cursor) {
		int cursorTileX = cursor.getX() / mapDimension.tileSize;
		int cursorTileY = cursor.getY() / mapDimension.tileSize;
		if (cursorTileY < 2) {
			if (leftSide && cursorTileX < 4) {
				leftSide = false;
			} else if (!leftSide && cursorTileX >= mapDimension.getTileWidth() - 4) { 
				leftSide = true;
			}
		}
	}
	
	public void paint(Graphics g) {
		if (leftSide) {
			heroPortraitPainter.paintLeftSide(g, heroHandler.getCurrentHero());
		} else {
			heroPortraitPainter.paintRightSide(g, heroHandler.getCurrentHero());
		}
	}
}