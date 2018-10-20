package map.area;

import java.awt.Graphics;

import graphics.images.AreaImage;
import point.Point;

public class Area {
	private Point point;
	private TerrainType terrainType;
	private AreaImage areaImage;
	
	public Area(Point point, TerrainType terrainType, int tileSize) {
		this.point = point;
		this.terrainType = terrainType;
		areaImage = new AreaImage(terrainType, tileSize);
	}
	
	public void setTerrainType(TerrainType terrainType) {
		this.terrainType = terrainType;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public TerrainType getTerrainType() {
		return terrainType;
	}
	
	public AreaImage getAreaImage() {
		return areaImage;
	}
	
	public void paint(Graphics g, boolean areaMovementAble, boolean rangeAble) {
		areaImage.paint(g, point.getX(), point.getY(), areaMovementAble, rangeAble);
	}
}