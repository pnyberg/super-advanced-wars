/**
 * TODO:
 *  - paint Hero
 */
package hero;

import java.awt.Color;
import java.awt.Graphics;

import hero.heroPower.HeroPower;

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

	public void manageCash(int cashDiff) {
		cash += cashDiff;
		if (cash >= 1000000) {
			cash = 999999;
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
	
	public PassiveHeroAbilities getPassiveHeroAbilities() {
		return passiveHeroAbilities;
	}
	
	public HeroPower getHeroPower() {
		return heroPower;
	}
	
	public TroopHandler getTroopHandler() {
		return troopHandler;
	}

	public void paint(Graphics g, int x, int y) {
		// TODO: paint the face of the hero
	}
}