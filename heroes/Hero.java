package heroes;

import java.awt.Color;
import java.awt.Graphics;

public class Hero {
	private int cash;
	private Color color;
	private AttackDefenceObject attackDefenceObject;
	private HeroPowerMeter heroPowerMeter;
	private TroopHandler troopHandler;

	public Hero(Color color, AttackDefenceObject attackDefenceObject, HeroPowerMeter heroPowerMeter) {
		cash = 0;
		this.color = color;
		this.attackDefenceObject = attackDefenceObject;
		this.heroPowerMeter = heroPowerMeter;
		troopHandler = new TroopHandler();
	}

	public void manageCash(int cashDiff) {
		cash += cashDiff;

		if (cash >= 1000000) {
			cash = 999999;
		} else if (cash < 0) {
			cash = 0;
		}
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
	
	public HeroPowerMeter getHeroPowerMeter() {
		return heroPowerMeter;
	}
	
	public TroopHandler getTroopHandler() {
		return troopHandler;
	}

	public void paint(Graphics g, int x, int y) {
		// to do
	}
}