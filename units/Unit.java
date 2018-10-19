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
	protected boolean capting;
	protected UnitSupply unitSupply;
	protected Color color, restingColor;

	protected int movement;
	protected UnitCategory unitClass;
	protected AttackType attackType;
	protected MovementType movementType;

	public Unit(int x, int y, Color color, int tileSize) {
		point = new Point(x, y);
		this.color = color;
		restingColor = color.darker();

		unitHealth = new UnitHealth(tileSize);
		hidden = false;
		attacking = false;
		active = false;
		capting = false;

		attackType = AttackType.DIRECT_ATTACK;
	}

	public void moveTo(int x, int y) {
		if (point.getX() != x || point.getY() != y) {
			capting = false;
		}
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

	public void regulateCapting(boolean capting) {
		this.capting = capting;
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

	public boolean isCapting() {
		return capting;
	}

	public void paint(Graphics g, int tileSize) {
		paintUnit(g, tileSize);
		unitHealth.paintHP(g, point.getX(), point.getY());
		if (capting) {
			paintCaptFlag(g, tileSize);
		}
	}
	
	private void paintCaptFlag(Graphics g, int tileSize) {
		g.setColor(Color.white);
		g.fillRect(point.getX() + tileSize / 10, point.getY() + 3 * tileSize / 5, tileSize / 3, tileSize / 3);
		g.setColor(Color.black);
		g.drawRect(point.getX() + tileSize / 10, point.getY() + 3 * tileSize / 5, tileSize / 3, tileSize / 3);

		g.fillRect(point.getX() + tileSize / 4, point.getY() + 17 * tileSize / 25, tileSize / 8, tileSize / 12);
		g.drawLine(point.getX() + tileSize / 4, point.getY() + 11 * tileSize / 15, point.getX() + tileSize / 4, point.getY() + 12 * tileSize / 15);
		g.fillRect(point.getX() + tileSize / 5, point.getY() + 12 * tileSize / 15, tileSize / 6, tileSize / 12);
	}

	protected abstract void paintUnit(Graphics g, int tileSize);
}