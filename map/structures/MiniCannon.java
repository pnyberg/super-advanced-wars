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
			int rangeX = x/tileSize;
			int rangeY = y/tileSize + 1;
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
		g.fillRect(x, y, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(x, y, tileSize, tileSize);
		g.setColor(owner.getColor());

		g.fillOval(x+tileSize/8, y+tileSize/16, 3 * tileSize / 4, 7 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(x+tileSize/8, y+tileSize/16, 3 * tileSize / 4, 7 * tileSize / 8);
		g.drawOval(x+3*tileSize/10, y+tileSize/5, 2 * tileSize / 5, 2 * tileSize / 5);
		g.fillOval(x+4*tileSize/10, y+3*tileSize/10, tileSize / 5, tileSize / 5);
	}
}