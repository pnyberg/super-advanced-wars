package gameObjects;

public class MapDimension {
	private int tileWidth;
	private int tileHeight;
	public final int tileSize;
	
	public MapDimension(int tileWidth, int tileHeight, int tileSize) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileSize = tileSize;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
}