import units.*;

import java.awt.Graphics;

public class BuildingMenu extends Menu {
	private final int priceAlign = 70;
	private boolean factory, port, airport;

	private final BuildingItem[] factoryItems = {	
									new BuildingItem("Infantry", 1000),
									new BuildingItem("Mech", 3000),
									new BuildingItem("Recon", 4000),
									new BuildingItem("Tank", 7000),
									new BuildingItem("Artillery", 6000)/*,
									new FactoryItem("Missiles", 12000)*/
								};
	private final BuildingItem[] portItems = {	
									new BuildingItem("Battleship", 28000),
									new BuildingItem("Lander", 12000)
								};

	private final BuildingItem[] airportItems = {	
//									new BuildingItem("Bship", 28000),
								};

	public BuildingMenu(int tileSize) {
		super(tileSize);

		menuWidth = (tileSize * 9 / 3) - 2;
		factory = false;
		port = false;
		airport = false;
	}

	public void openMenu(int x, int y) {
		super.openMenu(x, y);

		int terrainType = MapHandler.map(x, y);
		System.out.println(terrainType);

		if (terrainType == MapHandler.FACTORY) {
			factory = true;
		} else if (terrainType == MapHandler.AIRPORT) {
			airport = true;
		} else if (terrainType == MapHandler.PORT) {
			port = true;
		}

		updateNumberOfRows();
	}

	public void closeMenu() {
		super.closeMenu();

		factory = false;
		airport = false;
		port = false;
	}

	protected void updateNumberOfRows() {
		if (factory) {
			numberOfRows = factoryItems.length;
		} else if (port) {
			numberOfRows = portItems.length;
		} else if (airport) {
			numberOfRows = airportItems.length;
		}
	}

	public void buySelectedTroop(HeroPortrait portrait) {
		Hero currentHero = portrait.getCurrentHero();
		int cash = currentHero.getCash();

		if (factory) {
			currentHero.manageCash(-factoryItems[menuIndex].getPrice());
		} else if (port) {
			currentHero.manageCash(-portItems[menuIndex].getPrice());
		} else if (airport) {
			currentHero.manageCash(-airportItems[menuIndex].getPrice());
		} else {
			return;
		}

		Unit unit = createUnitFromIndex(currentHero);
		currentHero.addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		String unitName = "";

		if (factory) {
			unitName = factoryItems[menuIndex].getName();
		} else if (port) {
			unitName = portItems[menuIndex].getName();
		} else if (airport) {
			unitName = airportItems[menuIndex].getName();
		}

		if (unitName.equals("Infantry")) {
			return new Infantry(x, y, hero.getColor());
		} else if (unitName.equals("Mech")) {
			return new Mech(x, y, hero.getColor());
		} else if (unitName.equals("Recon")) {
			return new Recon(x, y, hero.getColor());
		} else if (unitName.equals("Tank")) {
			return new Tank(x, y, hero.getColor());
//		} else if (unitName.equals("MD Tank")) {
//			return new MDTank(x, y, hero.getColor());
//		} else if (unitName.equals("Neotank")) {
//			return new Neotank(x, y, hero.getColor());
		} else if (unitName.equals("APC")) {
			return new APC(x, y, hero.getColor());
		} else if (unitName.equals("Artillery")) {
			return new Artillery(x, y, hero.getColor());
		} else if (unitName.equals("Rocket")) {
			return new Rocket(x, y, hero.getColor());
//		} else if (unitName.equals("A-air")) {
//			return new Aair(x, y, hero.getColor());
//		} else if (unitName.equals("Missiles")) {
//			return new Missiles(x, y, hero.getColor());
//		} else if (unitName.equals("Fighter")) {
//			return new Fighter(x, y, hero.getColor());
//		} else if (unitName.equals("Bomber")) {
//			return new Bomber(x, y, hero.getColor());
//		} else if (unitName.equals("B Copter")) {
//			return new BCopter(x, y, hero.getColor());
//		} else if (unitName.equals("T Copter")) {
//			return new TCopter(x, y, hero.getColor());
		} else if (unitName.equals("Battleship")) {
			return new Battleship(x, y, hero.getColor());
//		} else if (unitName.equals("Cruiser")) {
//			return new Cruiser(x, y, hero.getColor());
		} else if (unitName.equals("Lander")) {
			return new Lander(x, y, hero.getColor());
//		} else if (unitName.equals("Sub")) {
//			return new Sub(x, y, hero.getColor());
		}

		return null;
	}

	public void paint(Graphics g) {
		menuHeight = 10 + numberOfRows * menuRowHeight;

		int menuX = x * tileSize + tileSize / 2;
		int menuY = y * tileSize + tileSize / 2;

		BuildingItem[] items = new BuildingItem[0];

		if (factory) {
			items = factoryItems;
		} else if (airport) {
			items = airportItems;
		} else if (port) {
			items = portItems;
		}

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
	}
}