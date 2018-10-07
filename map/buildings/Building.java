package map.buildings;

import java.awt.Graphics;

import hero.*;

public abstract class Building {
	private static int income;
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
		captingValue = 0;
	}

	public void capt(int captingValue) {
		this.captingValue += captingValue;

		if (this.captingValue > 20) {
			this.captingValue = 20;
		}
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

	public boolean isCapted() {
		return captingValue == 20;
	}

	public Hero getOwner() {
		return owner;
	}

	public abstract void paint(Graphics g, int tileSize);
}