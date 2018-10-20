package map.area;

import java.awt.Color;
import java.awt.Graphics;

import point.Point;

public class Area {
	private Point position;
	private TerrainType terrainType;
	private int tileSize; 
	
	public Area(Point position, TerrainType terrainType, int tileSize) {
		this.position = position;
		this.terrainType = terrainType;
		this.tileSize = tileSize;
	}
	
	public Area(int tileX, int tileY, TerrainType terrainType, int tileSize) {
		this(new Point(tileX * tileSize, tileY * tileSize), terrainType, tileSize);
	}
	
	public void setTerrainType(TerrainType terrainType) {
		this.terrainType = terrainType;
	}
	
	public TerrainType getTerrainType() {
		return terrainType;
	}
	
	public void paint(Graphics g, boolean areaMovementAble, boolean rangeAble) {
		setColor(g, areaMovementAble);
		
		g.fillRect(position.getX(), position.getY(), tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(position.getX(), position.getY(), tileSize, tileSize);

		if (terrainType == TerrainType.REEF && !rangeAble) {
			g.fillRect(position.getX() + tileSize / 4, position.getY() + tileSize / 4, tileSize / 4, tileSize / 4);
			g.fillRect(position.getX() + 5 * tileSize / 8, position.getY() + 5 * tileSize / 8, tileSize / 4, tileSize / 4);
		} else if (terrainType == TerrainType.UMI && !rangeAble) {
			g.fillRect(position.getX() + tileSize / 4, position.getY() + tileSize / 4, tileSize / 4, tileSize / 4);
			g.fillRect(position.getX() + 5 * tileSize / 8, position.getY() + 5 * tileSize / 8, tileSize / 4, tileSize / 4);
		}
	}
	
	private void setColor(Graphics g, boolean areaMovementAble) {
		if (terrainType == TerrainType.ROAD) {
			if (areaMovementAble) {
				g.setColor(Color.lightGray);
			} else {
				g.setColor(Color.gray);
			}
		} else if (terrainType == TerrainType.PLAIN) {
			if (areaMovementAble) {
				g.setColor(new Color(255,250,205)); // lighter yellow
			} else {
				g.setColor(new Color(204,204,0)); // darker yellow
			}
		} else if (terrainType == TerrainType.WOOD) {
			if (areaMovementAble) {
				g.setColor(new Color(50,205,50)); // limegreen
			} else {
				g.setColor(new Color(0,128,0)); // green
			}
		} else if (terrainType == TerrainType.MOUNTAIN) {
			if (areaMovementAble) {
				g.setColor(new Color(205,133,63)); // lighter brown
			} else {
				g.setColor(new Color(142,101,64)); // brown
			}
		} else if (terrainType == TerrainType.SEA) {
			if (areaMovementAble) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else if (terrainType == TerrainType.REEF) {
			if (areaMovementAble) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else if (terrainType == TerrainType.SHORE) {
			if (areaMovementAble) {
				g.setColor(new Color(30,144,145)); // lighter blue
			} else {
				g.setColor(new Color(30,144,105)); // lighter blue
			}
		} else if (terrainType == TerrainType.UMI) {
			if (areaMovementAble) {
				g.setColor(new Color(255,250,205)); // lighter yellow
			} else {
				g.setColor(new Color(204,204,0)); // darker yellow
			}
		}
	}
}
