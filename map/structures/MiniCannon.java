package map.structures;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.Direction;
import graphics.images.structures.DirectionalStructureImage;
import hero.Hero;

public class MiniCannon extends FiringStructure {
	private Direction direction;
	private int damage;
	private int maxWidthRange;
	
	public MiniCannon(int x, int y, Direction direction, Hero owner, int tileSize) {
		super(x, y, owner, tileSize);
		this.direction = direction;
		damage = 30;
		maxWidthRange = 7;
		structureImage = new DirectionalStructureImage(direction, tileSize);
	}
	
	// TODO: rewrite code to make it readable
	public boolean[][] getFiringRangeMap(int mapTileWidth, int mapTileHeight) {
		boolean[][] rangeMap = new boolean[mapTileWidth][mapTileHeight];
		if (direction == Direction.SOUTH) {
			int rangeTileX = point.getX()/tileSize;
			int rangeTileY = point.getY()/tileSize + 1;
			for (int k = 1 ; k <= maxWidthRange ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					if (0 <= (rangeTileX+i) && (rangeTileX+i) < rangeMap.length && rangeTileY < rangeMap[0].length) {
						rangeMap[rangeTileX+i][rangeTileY] = true;
					}
				}
				rangeTileX--;
				rangeTileY++;
			}
		} else if (direction == Direction.EAST) {
			int rangeTileX = point.getX()/tileSize + 1;
			int rangeTileY = point.getY()/tileSize;
			for (int k = 1 ; k <= maxWidthRange ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					if (rangeTileX < rangeMap.length && 0 <= (rangeTileY+i) && (rangeTileY+i) < rangeMap[0].length) {
						rangeMap[rangeTileX][rangeTileY+i] = true;
					}
				}
				rangeTileX++;
				rangeTileY--;
			}
		} else if (direction == Direction.NORTH) {
			int rangeTileX = point.getX()/tileSize;
			int rangeTileY = point.getY()/tileSize - 1;
			for (int k = 1 ; k <= maxWidthRange ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					if (0 <= (rangeTileX+i) && (rangeTileX+i) < rangeMap.length && 0 <= rangeTileY) {
						rangeMap[rangeTileX+i][rangeTileY] = true;
					}
				}
				rangeTileX--;
				rangeTileY--;
			}
		} else if (direction == Direction.WEST) {
			int rangeTileX = point.getX()/tileSize - 1;
			int rangeTileY = point.getY()/tileSize;
			for (int k = 1 ; k <= maxWidthRange ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					if (0 <= rangeTileX && 0 <= (rangeTileY+i) && (rangeTileY+i) < rangeMap[0].length) {
						rangeMap[rangeTileX][rangeTileY+i] = true;
					}
				}
				rangeTileX--;
				rangeTileY--;
			}
		}
		return rangeMap;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public int getDamage() {
		return damage;
	}
		
	public void paint(Graphics g) {
		g.setColor(new Color(204,204,0)); // darker yellow
		g.fillRect(point.getX(), point.getY(), tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(point.getX(), point.getY(), tileSize, tileSize);
		
		Color cannonColor = null;
		if (owner == null) {
			cannonColor = Color.darkGray;
		} else {
			cannonColor = owner.getColor();
		}
		structureImage.paint(g, point.getX(), point.getY(), cannonColor);
	}
}