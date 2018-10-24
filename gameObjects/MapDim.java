package gameObjects;

public class MapDim {
	private int tileWidth;
	private int tileHeight;
	public final int tileSize;
	
	public MapDim(int tileWidth, int tileHeight, int tileSize) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileSize = tileSize;
	}
	
	public void setDimension(int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
}