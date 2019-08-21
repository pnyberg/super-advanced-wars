package hero;

import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.GameState;
import gameObjects.MapDimension;
import graphics.HeroPortraitPainter;
import main.HeroHandler;

public class HeroPortrait {
	private MapDimension mapDimension;
	private HeroHandler heroHandler;
	private boolean leftSide;
	private HeroPortraitPainter heroPortraitPainter;

	public HeroPortrait(MapDimension mapDimensionDim, GameState gameState) {
		this.mapDimension = mapDimensionDim;
		this.heroHandler = gameState.getHeroHandler();
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