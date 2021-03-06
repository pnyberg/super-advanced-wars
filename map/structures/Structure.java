package map.structures;

import java.awt.Graphics;

import graphics.images.structures.StructureImage;
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

	public Structure(int x, int y, Hero owner, int tileSize) {
		this(new Point(x, y), owner, tileSize);
	}

	public void takeDamage(int damage) {
		hp = Math.max(hp - damage, 0);
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