package loading;

import gameObjects.GameProperties;
import gameObjects.GameState;

public class GameLoadingObject {
	private GameProperties gameProperties;
	private GameState gameState;
	
	public GameLoadingObject(GameProperties gameProperties, GameState gameState) {
		this.gameProperties = gameProperties;
		this.gameState = gameState;
	}
	
	public GameProperties getGameProperties() {
		return gameProperties;
	}
	
	public GameState getInitalGameState() {
		return gameState;
	}
}