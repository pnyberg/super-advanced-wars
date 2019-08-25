/**
 * TODO:
 *  - paint Hero
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
	private PassiveHeroAbilities passiveHeroAbilities;
	private HeroPower heroPower;
	private TroopHandler troopHandler;

	public Hero(Color color, PassiveHeroAbilities passiveHeroAbilities, HeroPower heroPower) {
		cash = 0;
		this.color = color;
		powerActive = false;
		superPowerActive = false;
		this.passiveHeroAbilities = passiveHeroAbilities;
		this.heroPower = heroPower;
		troopHandler = new TroopHandler();
	}

	public void earnCash(int amount) {
		cash += amount;
		if (cash >= 1000000) {
			cash = 999999;
		}
	}
	
	public void spendCash(int amount) {
		cash -= amount;
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

	public int getStandardAttackValue(int unitIndex) {
		return passiveHeroAbilities.getAttackValue(unitIndex);
	}

	public int getStandardDefenceValue(int unitIndex) {
		return passiveHeroAbilities.getDefenceValue(unitIndex);
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
	
	public HeroPower getHeroPower() {
		return heroPower;
	}
	
	public HeroPowerMeter getHeroPowerMeter() {
		return heroPower.getHeroPowerMeter();
	}
	
	public TroopHandler getTroopHandler() {
		return troopHandler;
	}

	public void paint(Graphics g, int x, int y) {
		// TODO: paint the face of the hero
	}
}