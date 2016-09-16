import java.awt.Color;
import java.awt.Graphics;

public abstract class Unit {
	// Movement-type
	public static final int 	INFANTRY = 0,
								MECH = 1,
								BAND = 2,
								TIRE = 3,
								SHIP = 4,
								TRANSPORT = 5;

	// Attack-type
	public static final int 	NONE = 0,
								DIRECT_ATTACK = 1,
								INDIRECT_ATTACK = 2;

	protected int x, y;
	protected Color color;

	protected int movement;
	protected int movementType;
	protected int attackType;

	public Unit(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getMovement() {
		return movement;
	}

	public int getMovementType() {
		return movementType;
	}

	public int getAttackType() {
		return attackType;
	}

	public abstract void paint(Graphics g, int tileSize);
}