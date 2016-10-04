import java.awt.Graphics;

public abstract class Building {
	private static int income;

	private int captingValue;

	public static void init(int income) {
		this.income = income;
	}

	public void capt(int captingValue) {
		this.captingValue += captingValue;

		if (this.captingValue > 20) {
			this.captingValue = 20;
		}
	}

	public boolean isCapted() {
		return captingValue == 20;
	}

	public static int getIncome() {
		return income;
	}

	public abstract void paint(Graphics g);
}