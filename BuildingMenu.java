import units.*;

import java.awt.Graphics;

public class BuildingMenu extends Menu {
	private final int priceAlign = 70;

	private final FactoryItem[] items = {	new FactoryItem("Infantry", 1000),
											new FactoryItem("Mech", 3000),
											new FactoryItem("Recon", 4000),
											new FactoryItem("Tank", 7000),
											new FactoryItem("Artillery", 6000)/*,
											new FactoryItem("Missiles", 12000)*/
										};

	public BuildingMenu(int tileSize) {
		super(tileSize);

		menuWidth = (tileSize * 9 / 3) - 2;
	}

	protected void updateNumberOfRows() {
		numberOfRows = items.length;
	}

	public void buySelectedTroop(HeroPortrait portrait) {
		Hero currentHero = portrait.getCurrentHero();
		int cash = currentHero.getCash();

		currentHero.manageCash(-items[menuIndex].getPrice());

		Unit unit = createUnitFromIndex(currentHero);
		currentHero.addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		String unitName = items[menuIndex].getName();

		if (unitName.equals("Infantry")) {
			return new Infantry(x, y, hero.getColor());
		} else if (unitName.equals("Mech")) {
			return new Mech(x, y, hero.getColor());
		} else if (unitName.equals("Recon")) {
			return new Recon(x, y, hero.getColor());
		} else if (unitName.equals("Tank")) {
			return new Tank(x, y, hero.getColor());
		} else if (unitName.equals("Artillery")) {
			return new Artillery(x, y, hero.getColor());
//		} else if (unitName.equals("Missiles")) {
//			return new Missiles(x, y, hero.getColor());
		}

		return null;
	}

	public void paint(Graphics g) {
		menuHeight = 10 + numberOfRows * menuRowHeight;

		int menuX = x * tileSize + tileSize / 2;
		int menuY = y * tileSize + tileSize / 2;

		paintMenuBackground(g);

		int rowHelpIndex = 3;

		for (int k = 0 ; k < items.length ; k++) {
			paintMenuItem(g, menuY + yAlign + menuRowHeight * (k + 1), items[k].getName(), items[k].getPrice());
		}

		paintArrow(g);
	}

	private void paintMenuItem(Graphics g, int y, String text, int price) {
		int menuX = x * tileSize + tileSize / 2;

		int extraPriceAlign = (price >= 10000 ? 0 : 8);

		g.drawString(text, menuX + xAlign, y);
		g.drawString("" + price + "", menuX + xAlign + priceAlign + extraPriceAlign, y);

//		g.drawString("End turn", menuX + xAlign, menuY + yAlign + menuRowHeight * (rowHelpIndex + 2));
	}
}