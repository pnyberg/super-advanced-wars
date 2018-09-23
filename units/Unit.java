package units;

import java.awt.Color;
import java.awt.Graphics;

import point.Point;

public abstract class Unit {
	protected static int price;
	protected static String typeName;

	protected Point point;
	protected UnitHealth unitHealth;
	protected boolean hidden;
	protected boolean attacking;
	protected boolean active;
	protected UnitSupply unitSupply;
	protected Color color, restingColor;

	protected int movement;
	protected UnitCategory unitClass;
	protected AttackType attackType;
	protected MovementType movementType;

	public Unit(int x, int y, Color color) {
		point = new Point(x, y);
		this.color = color;
		restingColor = color.darker();

		unitHealth = new UnitHealth();
		hidden = false;
		attacking = false;
		active = false;

		attackType = AttackType.DIRECT_ATTACK;
	}

	public void moveTo(int x, int y) {
		point = new Point(x, y);
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

	public Point getPoint() {
		return point;
	}

	public UnitSupply getUnitSupply() {
		return unitSupply;
	}
	
	public UnitHealth getUnitHealth() {
		return unitHealth;
	}
	
	public int getMovement() {
		return movement;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public AttackType getAttackType() {
		return attackType;
	}

	public UnitCategory getUnitClass() {
		return unitClass;
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

	public void paint(Graphics g, int tileSize) {
		paintUnit(g, tileSize);
		unitHealth.paintHP(g, point.getX(), point.getY(), tileSize);
	}

	protected abstract void paintUnit(Graphics g, int tileSize);
}