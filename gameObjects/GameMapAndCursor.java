package gameObjects;

import cursors.Cursor;
import map.GameMap;

public class GameMapAndCursor {
	public final GameMap gameMap;
	public final Cursor cursor;
	
	public GameMapAndCursor(GameMap gameMap, Cursor cursor) {
		this.gameMap = gameMap;
		this.cursor = cursor;
	}
}