package map.structures;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.Direction;
import hero.Hero;
import map.area.TerrainType;

public class MiniCannon extends FiringStructure {
	private Direction direction;
	private final int damage = 30;
	
	public MiniCannon(int x, int y, Direction direction, Hero owner, int tileSize) {
		super(x, y, owner, tileSize);
		this.direction = direction;
	}
	
	public void fillRangeMap(boolean[][] rangeMap) {
		if (direction == Direction.SOUTH) {
			int rangeX = point.getX()/tileSize;
			int rangeY = point.getY()/tileSize + 1;
			for (int k = 1 ; k <= 5 ; k += 2) {
				for (int i = 0 ; i < k ; i++) {
					rangeMap[rangeX+i][rangeY] = true;
				}
				rangeX--;
				rangeY++;
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
		
		setCannonColor(g);
		if (direction == Direction.NORTH) {
			paintNorthMiniCannon(g);
		} else if (direction == Direction.EAST) {
			paintEastMiniCannon(g);
		} else if (direction == Direction.SOUTH) {
			paintSouthMiniCannon(g);
		} else if (direction == Direction.WEST) {
			paintWestMiniCannon(g);
		}
	}
	
	private void setCannonColor(Graphics g) {
		if (owner == null) {
			g.setColor(Color.darkGray);
		} else {
			g.setColor(owner.getColor());
		}
	}
	
	private void paintNorthMiniCannon(Graphics g) {
		g.fillOval(point.getX()+3*tileSize/10, point.getY()+tileSize/20, 2 * tileSize / 5, 2 * tileSize / 5);
		g.setColor(Color.black);
		g.drawOval(point.getX()+3*tileSize/10, point.getY()+tileSize/20, 2 * tileSize / 5, 2 * tileSize / 5);
		setCannonColor(g);
		g.fillOval(point.getX()+tileSize/8, point.getY()+tileSize/8, 3 * tileSize / 4, 6 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(point.getX()+tileSize/8, point.getY()+tileSize/8, 3 * tileSize / 4, 6 * tileSize / 8);
	}

	private void paintEastMiniCannon(Graphics g) {
		g.fillOval(point.getX()+5*tileSize/10, point.getY()+tileSize/5, 1 * tileSize / 5, 2 * tileSize / 5);
		g.setColor(Color.black);
		g.drawOval(point.getX()+5*tileSize/10, point.getY()+tileSize/5, 1 * tileSize / 5, 2 * tileSize / 5);
		setCannonColor(g);
		g.fillOval(point.getX()+tileSize/8, point.getY()+tileSize/16, 2 * tileSize / 4, 7 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(point.getX()+tileSize/8, point.getY()+tileSize/16, 2 * tileSize / 4, 7 * tileSize / 8);
	}

	private void paintSouthMiniCannon(Graphics g) {
		g.fillOval(point.getX()+tileSize/8, point.getY()+tileSize/16, 3 * tileSize / 4, 7 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(point.getX()+tileSize/8, point.getY()+tileSize/16, 3 * tileSize / 4, 7 * tileSize / 8);
		g.drawOval(point.getX()+3*tileSize/10, point.getY()+tileSize/5, 2 * tileSize / 5, 2 * tileSize / 5);
		g.fillOval(point.getX()+4*tileSize/10, point.getY()+3*tileSize/10, tileSize / 5, tileSize / 5);
	}

	private void paintWestMiniCannon(Graphics g) {
		g.fillOval(point.getX()+3*tileSize/10, point.getY()+tileSize/5, 1 * tileSize / 5, 2 * tileSize / 5);
		g.setColor(Color.black);
		g.drawOval(point.getX()+3*tileSize/10, point.getY()+tileSize/5, 1 * tileSize / 5, 2 * tileSize / 5);
		setCannonColor(g);
		g.fillOval(point.getX()+3*tileSize/8, point.getY()+tileSize/16, 2 * tileSize / 4, 7 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(point.getX()+3*tileSize/8, point.getY()+tileSize/16, 2 * tileSize / 4, 7 * tileSize / 8);
	}
}