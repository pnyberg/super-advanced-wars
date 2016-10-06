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
			color = Color.red; // magenta
		} else if (heroIndex == 1) {
			powerAmount = 4;
			superPowerAmount = 7;
			color = Color.orange;
		}
	}

	public void manageCash(int cashDiff) {
		cash += cashDiff;
	}

	public void addTroop(Unit unit) {
		troops.add(unit);
	}

	public void removeTroop(Unit unit) {
		for (int i = 0 ; i < troops.size() ; i++) {
			if (troops.get(i) == unit) {
				troops.remove(i);
				break;
			}
		}
	}

	public int getAttackValue(int unitIndex) {
		return 100; // @TODO default for now
	}

	public int getDefenceValue(int unitIndex) {
		return 100; // @TODO default for now
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