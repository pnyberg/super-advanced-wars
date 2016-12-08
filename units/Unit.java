package units;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public abstract class Unit {
	// Movement-type
	public static final int 	INFANTRY = 0,
								MECH = 1,
								BAND = 2,
								TIRE = 3,
								SHIP = 4,
								TRANSPORT = 5,
								AIR = 6;

	// Unit-type
	public static final int 	FOOTMAN = 0,
								VEHICLE = 1,
								PLANE = 2,
								COPTER = 3,
								BOAT = 4,
								SUB = 5;

	public static final int numberOfUnitTypes = 18;

	// Attack-type
	public static final int 	NONE = 0,
								DIRECT_ATTACK = 1,
								INDIRECT_ATTACK = 2;

	protected static int price;
	protected static String typeName;

	protected int x, y, hp, currentFuel, maxFuel, currentAmmo, maxAmmo;
	protected boolean hidden, attacking, active;
	protected Color color, restingColor;

	protected int movement, movementType, attackType, unitClass;

	public Unit(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		restingColor = color.darker();

		hp = 100;
		hidden = false;
		attacking = false;
		active = false;

		maxFuel = 0;
		maxAmmo = 0;

		attackType = Unit.DIRECT_ATTACK;
	}

	public void replentish() {
		currentFuel = maxFuel;
		currentAmmo = maxAmmo;
	}

	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void takeDamage(int damage) {
		hp -= damage;
	}

	public void regulateActive(boolean active) {
		this.active = active;
	}

	public void regulateHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void regulateAttack(boolean attacking) {
		this.attacking = attacking;
	}

	public void useAmmo() {
		currentAmmo--;
	}

	public void useFuel(int fuel) {
		currentFuel -= fuel;
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

	public int getAmmo() {
		return currentAmmo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public int getFuel() {
		return currentFuel;
	}

	public int getMaxFuel() {
		return maxFuel;
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

	public int getUnitClass() {
		return unitClass;
	}

	public boolean isHurt() {
		return hp <= 90;
	}

	public boolean isDead() {
		return hp <= 0;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public boolean hasAmmo() {
		return currentAmmo > 0;
	}

	public boolean hasFuel(int fuelNeeded) {
		return currentFuel >= fuelNeeded;
	}

	public void paint(Graphics g, int tileSize) {
		paintUnit(g, tileSize);
		paintHP(g, tileSize);
	}

	protected abstract void paintUnit(Graphics g, int tileSize);

	protected void paintHP(Graphics g, int tileSize) {
		int showHP = (hp + 9) / 10;

		if (showHP == 10) {
			return;
		}

		int paintHPX = x * tileSize + (3 * tileSize) / 4;
		int paintHPY = y * tileSize + (3 * tileSize) / 4;

		Font currentFont = g.getFont();
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10));

		g.setColor(Color.black);
		g.fillRect(paintHPX, paintHPY, tileSize / 4, tileSize / 4);
		g.setColor(Color.white);
		g.drawString("" + showHP + "", paintHPX + 3, paintHPY + tileSize / 8 + 4);

		g.setFont(currentFont);
	}
}