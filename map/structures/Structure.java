package map.structures;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Structure {
	protected int x;
	protected int y;
	protected int hp;
	protected Color color;
	protected int tileSize;

	public Structure(int x, int y, Color color, int tileSize) {
		this.x = x;
		this.y = y;
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
	
	public int getHP() {
		return hp;
	}
	
	public Color getColor() {
		return color;
	}
	
	public abstract void paint(Graphics g);
}