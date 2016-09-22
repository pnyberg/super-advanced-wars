import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Hero {
	private int cash;
	private int starPower;
	private int powerAmount, superPowerAmount;
	private Color color;
	private ArrayList<Unit> troops;

	public Hero(int heroIndex) {
		cash = 0;
		starPower = 0;
		troops = new ArrayList<Unit>();

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

	public void addTroop(Unit unit) {
		troops.add(unit);
	}

	public Unit getTroop(int index) {
		return troops.get(index);
	}

	public int getTroopSize() {
		return troops.size();
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