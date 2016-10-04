import java.awt.Graphics;

public abstract class Building {
	private static int income;

	protected Hero owner;
	protected int x, y, captingValue;

	public static void init(int income) {
		this.income = income;
	}

	public Building(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setOwnership(Hero hero) {
		owner = hero;
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

	public abstract void paint(Graphics g);
}