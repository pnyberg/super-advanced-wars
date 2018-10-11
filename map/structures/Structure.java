package map.structures;

import java.awt.Color;
import java.awt.Graphics;

import hero.Hero;

public abstract class Structure {
	protected int x;
	protected int y;
	protected int hp;
	protected Hero owner;
	protected int tileSize;

	public Structure(int x, int y, Hero owner, int tileSize) {
		this.x = x;
		this.y = y;
		hp = 99;
		this.owner = owner;
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
	
	public Hero getOwner() {
		return owner;
	}
	
	public abstract void paint(Graphics g);
}