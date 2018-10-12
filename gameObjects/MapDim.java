package gameObjects;

public class MapDim {
	private int width;
	private int height;
	public final int tileSize;
	
	public MapDim(int width, int height, int tileSize) {
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
	}
	
	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}