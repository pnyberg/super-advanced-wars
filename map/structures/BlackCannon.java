package map.structures;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.Direction;

public class BlackCannon {
	private int x;
	private int y;
	private Direction direction;
	private int hp;
	private Color color;
	private int tileSize;
	
	public BlackCannon(int x, int y, Direction direction, Color color, int tileSize) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		hp = 99;
		this.color = color;
		this.tileSize = tileSize;
	}
	
	public void takeDamage(int damage) {
		hp -= damage;
		hp = Math.max(hp, 0);
	}
	
	public boolean isDestroyed() {
		return hp == 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public int getHP() {
		return hp;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x+tileSize/8, y+tileSize/16, 3 * tileSize / 4, 7 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(x+tileSize/8, y+tileSize/16, 3 * tileSize / 4, 7 * tileSize / 8);
		g.drawOval(x+3*tileSize/10, y+tileSize/5, 2 * tileSize / 5, 2 * tileSize / 5);
		g.fillOval(x+4*tileSize/10, y+3*tileSize/10, tileSize / 5, tileSize / 5);
	}
}