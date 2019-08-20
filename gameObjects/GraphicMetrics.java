package gameObjects;

import point.Point;

public class GraphicMetrics {
	public final Point anchorPoint;
	public final int width;
	public final int height;
	public final int tileSize;

	public GraphicMetrics(Point anchorPoint, int width, int height, int tileSize) {
		this.anchorPoint = anchorPoint;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
	}
}
