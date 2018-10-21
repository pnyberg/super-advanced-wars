package map.structures;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.Direction;
import graphics.images.structures.DirectionalStructureImage;
import hero.Hero;

public class MiniCannon extends FiringStructure {
	private Direction direction;
	private final int damage = 30;
	private final int maxWidthRange = 7;
	
	public MiniCannon(int x, int y, Direction direction, Hero owner, int tileSize) {
		super(x, y, owner, tileSize);
		this.direction = direction;
		structureImage = new DirectionalStructureImage(direction, tileSize);
	}
	
	public void fillRangeMap(boolean[][] rangeMap) {
		if (direction == Direction.SOUTH) {
			int rangeX = point.getX()/tileSize;
			int rangeY = point.getY()/tileSize + 1;
			for (int k = 1 ; k <= maxWidthRange ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					if (0 <= (rangeX+i) && (rangeX+i) < rangeMap.length && rangeY < rangeMap[0].length) {
						rangeMap[rangeX+i][rangeY] = true;
					}
				}
				rangeX--;
				rangeY++;
			}
		} else if (direction == Direction.EAST) {
			int rangeX = point.getX()/tileSize + 1;
			int rangeY = point.getY()/tileSize;
			for (int k = 1 ; k <= maxWidthRange ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					if (rangeX < rangeMap.length && 0 <= (rangeY+i) && (rangeY+i) < rangeMap[0].length) {
						rangeMap[rangeX][rangeY+i] = true;
					}
				}
				rangeX++;
				rangeY--;
			}
		} else if (direction == Direction.NORTH) {
			int rangeX = point.getX()/tileSize;
			int rangeY = point.getY()/tileSize - 1;
			for (int k = 1 ; k <= maxWidthRange ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					if (0 <= (rangeX+i) && (rangeX+i) < rangeMap.length && 0 <= rangeY) {
						rangeMap[rangeX+i][rangeY] = true;
					}
				}
				rangeX--;
				rangeY--;
			}
		} else if (direction == Direction.WEST) {
			int rangeX = point.getX()/tileSize - 1;
			int rangeY = point.getY()/tileSize;
			for (int k = 1 ; k <= maxWidthRange ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					if (0 <= rangeX && 0 <= (rangeY+i) && (rangeY+i) < rangeMap[0].length) {
						rangeMap[rangeX][rangeY+i] = true;
					}
				}
				rangeX--;
				rangeY--;
			}
		}
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