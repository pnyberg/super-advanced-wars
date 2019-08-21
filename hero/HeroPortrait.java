package hero;

import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.GameState;
import gameObjects.MapDimension;
import graphics.HeroPortraitPainter;

public class HeroPortrait {
	private MapDimension mapDimension;
	private GameState gameState;
	private HeroPortraitPainter heroPortraitPainter;

	public HeroPortrait(MapDimension mapDimension, GameState gameState) {
		this.mapDimension = mapDimension;
		this.gameState = gameState;
		heroPortraitPainter = new HeroPortraitPainter(mapDimension);
	}

	public void updateSideChoice(Cursor cursor) {
		int cursorTileX = cursor.getX() / mapDimension.tileSize;
		int cursorTileY = cursor.getY() / mapDimension.tileSize;
		if (cursorTileY < 2) {
			boolean leftSide = gameState.heroPortraitOnLeftSide();
			if (leftSide && cursorTileX < 4) {
				gameState.setHeroPortraitOnLeftSide(false);
			} else if (!leftSide && cursorTileX >= mapDimension.getTileWidth() - 4) { 
				gameState.setHeroPortraitOnLeftSide(true);
			}
		}
	}
	
	public void paint(Graphics g) {
		Hero currentHero = gameState.getHeroHandler().getCurrentHero();
		if (gameState.heroPortraitOnLeftSide()) {
			heroPortraitPainter.paintLeftSide(g, currentHero);
		} else {
			heroPortraitPainter.paintRightSide(g, currentHero);
		}
	}
}