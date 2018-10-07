/**
 * TODO:
 *  - paint Hero
 *  - shouldn't be able to get to negative cash (so check should be removed)
 */
package hero;

import java.awt.Color;
import java.awt.Graphics;

import hero.heroPower.HeroPower;
import hero.heroPower.HeroPowerMeter;

public class Hero {
	private int cash;
	private Color color;
	private boolean powerActive;
	private boolean superPowerActive;
	private AttackDefenceObject attackDefenceObject;
	private HeroPower heroPower;
	private TroopHandler troopHandler;

	public Hero(Color color, AttackDefenceObject attackDefenceObject, HeroPower heroPower) {
		cash = 0;
		this.color = color;
		powerActive = false;
		superPowerActive = false;
		this.attackDefenceObject = attackDefenceObject;
		this.heroPower = heroPower;
		troopHandler = new TroopHandler();
	}

	public void manageCash(int cashDiff) {
		cash += cashDiff;
		if (cash >= 1000000) {
			cash = 999999;
		} else if (cash < 0) { // TODO: shouldn't be able to happen
			cash = 0;
		}
	}
	
	public void setPowerActive(boolean powerActive) {
		if (!powerActive) {
			heroPower.getHeroPowerMeter().clearStarPower();
		}
		this.powerActive = powerActive;
	}

	public void setSuperPowerActive(boolean superPowerActive) {
		if (!superPowerActive) {
			heroPower.getHeroPowerMeter().clearStarPower();
		}
		this.superPowerActive = superPowerActive;
	}

	public boolean isPowerActive() {
		return powerActive;
	}

	public boolean isSuperPowerActive() {
		return superPowerActive;
	}

	public int getCash() {
		return cash;
	}

	public Color getColor() {
		return color;
	}
	
	public AttackDefenceObject getAttackDefenceObject() {
		return attackDefenceObject;
	}
	
	public HeroPower getHeroPower() {
		return heroPower;
	}
	
	public TroopHandler getTroopHandler() {
		return troopHandler;
	}

	public void paint(Graphics g, int x, int y) {
		// to do
	}
}