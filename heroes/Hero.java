package heroes;

import units.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Hero {
	private int cash;
	private int attackValue, defenceValue;
	private double starPower;
	private int powerAmount, superPowerAmount;
	private Color color;
	private ArrayList<Unit> troops;

	public Hero(int heroIndex) {
		cash = 0;
		starPower = 0;
		troops = new ArrayList<Unit>();

		initHero(heroIndex);
	}

	private void initHero(int heroIndex) {
		if (heroIndex == 0) {
			attackValue = 100;
			defenceValue = 100;
			powerAmount = 3;
			superPowerAmount = 6;
			color = Color.red; // magenta
		} else if (heroIndex == 1) {
			attackValue = 110;
			defenceValue = 110;
			powerAmount = 4;
			superPowerAmount = 7;
			color = Color.orange;
		}
	}

	public void manageCash(int cashDiff) {
		cash += cashDiff;

		if (cash >= 1000000) {
			cash = 999999;
		} else if (cash < 0) {
			cash = 0;
		}
	}

	public void addTroop(Unit unit) {
		troops.add(unit);
	}

	public void removeTroop(Unit unit) {
		for (int i = 0 ; i < troops.size() ; i++) {
			if (troops.get(i) == unit) {
				troops.remove(i);
				break;
			}
		}
	}

	public int getAttackValue(int unitIndex) {
		return attackValue;
	}

	public int getDefenceValue(int unitIndex) {
		return defenceValue;
	}

	public Unit getTroop(int index) {
		return troops.get(index);
	}

	public int getTroopSize() {
		return troops.size();
	}

	public int getCash() {
		return cash;
	}

	public Color getColor() {
		return color;
	}

	public double getStarPower() {
		return starPower;
	}

	public int getRequiredPower() {
		return powerAmount;
	}

	public int getRequiredSuperPower() {
		return superPowerAmount;
	}
	
	public boolean powerUsable() {
		return starPower >= powerAmount;
	}

	public boolean superPowerUsable() {
		return starPower >= superPowerAmount;
	}

	public void paint(Graphics g, int x, int y) {
		// to do
	}
}