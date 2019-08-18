package unitUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UnitHealth {
	private int hp;
	private int tileSize;
	
	public UnitHealth(int tileSize) {
		hp = 100;
		this.tileSize = tileSize; 
	}

	public void heal(int health) {
		hp += health;
		hp = Math.min(hp, 100);
	}
	
	public void takeDamage(int damage) {
		hp -= damage;
		if (hp < 0) {
			hp = 0;
		}
	}
	
	public void takeNonLethalDamage(int damage) {
		hp -= damage;
		if (hp <= 0) {
			hp = 1;
		}
	}
	
	public void kill() {
		hp = 0;
	}
	
	public boolean atFullHealth() {
		return hp == 100;
	}

	public boolean isVisiblyHurt() {
		return hp <= 90;
	}

	public boolean isDead() {
		return hp == 0;
	}

	public int getHP() {
		return hp;
	}
	
	public int getShowHP() {
		return (hp + 9) / 10;
	}

	public void paintHP(Graphics g, int x, int y) {
		if (getShowHP() == 10) {
			return;
		}
		int paintHPX = x  + (3 * tileSize) / 4;
		int paintHPY = y  + (3 * tileSize) / 4;

		Font currentFont = g.getFont();
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
		g.setColor(Color.black);
		g.fillRect(paintHPX, paintHPY, tileSize / 4, tileSize / 4);
		g.setColor(Color.white);
		g.drawString("" + getShowHP() + "", paintHPX + 3, paintHPY + tileSize / 8 + 4);
		g.setFont(currentFont);
	}
}