package units;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UnitHealth {
	private int hp;
	
	public UnitHealth() {
		hp = 100;
	}

	public void heal(int health) {
		hp += health;
		
		hp = Math.min(hp, 100);
	}
	
	public void takeDamage(int damage) {
		hp -= damage;
	}
	
	public void kill() {
		hp = 0;
	}

	public boolean isHurt() {
		return hp <= 90;
	}

	public boolean isDead() {
		return hp <= 0;
	}

	public int getHP() {
		return hp;
	}

	protected void paintHP(Graphics g, int x, int y, int tileSize) {
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