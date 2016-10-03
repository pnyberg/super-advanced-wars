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
								TRANSPORT = 5;

	public static final int numberOfUnitTypes = 9;

	// Attack-type
	public static final int 	NONE = 0,
								DIRECT_ATTACK = 1,
								INDIRECT_ATTACK = 2;

	protected int x, y, hp;
	protected boolean hidden, attacking;
	protected Color color;

	protected int movement;
	protected int movementType;
	protected int attackType;

	public Unit(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;

		hp = 100;
		hidden = false;
		attacking = false;
	}

	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void takeDamage(int damage) {
		hp -= damage;
	}

	public void regulateHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void regulateAttack(boolean attacking) {
		this.attacking = attacking;
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

	public int getMovement() {
		return movement;
	}

	public int getMovementType() {
		return movementType;
	}

	public int getAttackType() {
		return attackType;
	}

	public boolean isHurt() {
		return hp <= 90;
	}

	public boolean isDead() {
		return hp <= 0;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean isAttacking() {
		return attacking;
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