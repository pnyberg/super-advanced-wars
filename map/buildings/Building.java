package map.buildings;

import java.awt.Graphics;

import hero.*;

public abstract class Building {
	private static int income;
	private static int maxCaptingValue = 20;
	protected Hero owner;
	protected int x;
	protected int y;
	protected int captingValue;

	// static since all buildings should generate the same amount of cash
	public static void init(int income) {
		Building.income = income;
	}

	public Building(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setOwnership(Hero hero) {
		owner = hero;
		resetCapting();
	}

	public void capt(int captingValue) {
		this.captingValue += captingValue;

		if (this.captingValue > maxCaptingValue) {
			this.captingValue = maxCaptingValue;
		}
	}
	
	public void resetCapting() {
		captingValue = 0;
	}

	public static int getIncome() {
		return income;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean captingIsActive() {
		return 0 < captingValue && captingValue < maxCaptingValue;
	}

	public boolean isCapted() {
		return captingValue == maxCaptingValue;
	}

	public Hero getOwner() {
		return owner;
	}

	public abstract void paint(Graphics g, int tileSize);
}