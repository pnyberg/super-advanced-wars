import units.*;

import java.awt.Graphics;

public class BuildingMenu extends Menu {
	private final int priceAlign = 70;

	private final BuildingItem[] factoryItems = {	
									new BuildingItem("Infantry", 1000),
									new BuildingItem("Mech", 3000),
									new BuildingItem("Recon", 4000),
									new BuildingItem("Tank", 7000),
									new BuildingItem("Artillery", 6000)/*,
									new FactoryItem("Missiles", 12000)*/
								};
	private final BuildingItem[] portItems = {	
									new BuildingItem("Lander", 12000),
									new BuildingItem("Bship", 28000),
								};

	private final BuildingItem[] airportItems = {	
//									new BuildingItem("Bship", 28000),
								};

	public BuildingMenu(int tileSize) {
		super(tileSize);

		menuWidth = (tileSize * 9 / 3) - 2;
	}

	protected void updateNumberOfRows() {
		numberOfRows = factoryItems.length;
	}

	public void buySelectedTroop(HeroPortrait portrait) {
		Hero currentHero = portrait.getCurrentHero();
		int cash = currentHero.getCash();

		currentHero.manageCash(-factoryItems[menuIndex].getPrice());

		Unit unit = createUnitFromIndex(currentHero);
		currentHero.addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		String unitName = factoryItems[menuIndex].getName();

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

		for (int k = 0 ; k < factoryItems.length ; k++) {
			paintMenuItem(g, menuY + yAlign + menuRowHeight * (k + 1), factoryItems[k].getName(), factoryItems[k].getPrice());
		}

		paintArrow(g);
	}

	private void paintMenuItem(Graphics g, int y, String text, int price) {
		int menuX = x * tileSize + tileSize / 2;

		int extraPriceAlign = (price >= 10000 ? 0 : 8);

		g.drawString(text, menuX + xAlign, y);
		g.drawString("" + price + "", menuX + xAlign + priceAlign + extraPriceAlign, y);
	}
}