package heroes;

import units.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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
	
	public AttackDefenceObject attackDefenceObject() {
		return attackDefenceObject;
	}
	
	public HeroPowerMeter getHeroPowerMeter() {
		return heroPowerMeter;
	}
	
	public TroopHandler geTroopHandler() {
		return troopHandler;
	}

	public void paint(Graphics g, int x, int y) {
		// to do
	}
}