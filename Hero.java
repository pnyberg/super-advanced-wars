import java.awt.Color;
import java.awt.Graphics;

public class Hero {
	private int cash;
	private int starPower;
	private int powerAmount, superPowerAmount;
	private Color color;

	public Hero(int heroIndex) {
		cash = 0;
		starPower = 0;

		initHero(heroIndex);
	}

	private void initHero(int heroIndex) {
		if (heroIndex == 0) {
			powerAmount = 3;
			superPowerAmount = 6;
			color = Color.magenta;
		} else if (heroIndex == 1) {
			powerAmount = 4;
			superPowerAmount = 7;
			color = Color.orange;
		}
	}

	public int getCash() {
		return cash;
	}

	public Color getColor() {
		return color;
	}

	public int getStarPower() {
		return starPower;
	}

	public int getRequiredPower() {
		return powerAmount;
	}

	public int getRequiredSuperPower() {
		return superPowerAmount;
	}

	public void paint(Graphics g, int x, int y) {
		// to do
	}
}