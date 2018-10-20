package map.structures;

import java.awt.Graphics;

import graphics.images.StructureImage;
import hero.Hero;
import point.Point;

public abstract class Structure {
	protected Point point;
	protected int hp;
	protected Hero owner;
	protected int tileSize;
	protected StructureImage structureImage;

	public Structure(Point point, Hero owner, int tileSize) {
		this.point = point;
		hp = 99;
		this.owner = owner;
		this.tileSize = tileSize;
	}

	public Structure(int tileX, int tileY, Hero owner, int tileSize) {
		this(new Point(tileX * tileSize, tileY * tileSize), owner, tileSize);
	}

	public void takeDamage(int damage) {
		hp -= damage;
		hp = Math.max(hp, 0);
	}
	
	public boolean isDestroyed() {
		return hp == 0;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public int getHP() {
		return hp;
	}
	
	public Hero getOwner() {
		return owner;
	}
	
	public StructureImage getStructureImage() {
		return structureImage;
	}
	
	public abstract void paint(Graphics g);
}