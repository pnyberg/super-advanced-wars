package gameObjects;

public class DimensionObject {
	private int tileWidth;
	private int tileHeight;
	public final int tileSize;
	
	public DimensionObject(int tileWidth, int tileHeight, int tileSize) {
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