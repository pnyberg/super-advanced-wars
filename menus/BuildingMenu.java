package menus;

import units.*;
import heroes.*;
import handlers.*;

import java.awt.Graphics;

/**
 * Add so that item's can't be clicked if they cost to much
 */
public class BuildingMenu extends Menu {
	private final int priceAlign = 70;
	private boolean factory, port, airport;

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
			numberOfRows = BuildingItem.getFactoryItems().length;
		} else if (port) {
			numberOfRows = BuildingItem.getPortItems().length;
		} else if (airport) {
			numberOfRows = BuildingItem.getAirportItems().length;
		}
	}

	public void buySelectedTroop(HeroPortrait portrait) {
		Hero currentHero = portrait.getCurrentHero();
		int cash = currentHero.getCash();

		if (factory) {
			currentHero.manageCash(-BuildingItem.getFactoryItems()[menuIndex].getPrice());
		} else if (port) {
			currentHero.manageCash(-BuildingItem.getPortItems()[menuIndex].getPrice());
		} else if (airport) {
			currentHero.manageCash(-BuildingItem.getAirportItems()[menuIndex].getPrice());
		} else {
			return;
		}

		Unit unit = createUnitFromIndex(currentHero);
		currentHero.addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		String unitName = "";

		if (factory) {
			unitName = BuildingItem.getFactoryItems()[menuIndex].getName();
		} else if (port) {
			unitName = BuildingItem.getPortItems()[menuIndex].getName();
		} else if (airport) {
			unitName = BuildingItem.getAirportItems()[menuIndex].getName();
		}

		if (unitName.equals(Infantry.getTypeName())) {
			return new Infantry(x, y, hero.getColor());
		} else if (unitName.equals(Mech.getTypeName())) {
			return new Mech(x, y, hero.getColor());
		} else if (unitName.equals(Recon.getTypeName())) {
			return new Recon(x, y, hero.getColor());
		} else if (unitName.equals(Tank.getTypeName())) {
			return new Tank(x, y, hero.getColor());
		} else if (unitName.equals(MDTank.getTypeName())) {
			return new MDTank(x, y, hero.getColor());
		} else if (unitName.equals(Neotank.getTypeName())) {
			return new Neotank(x, y, hero.getColor());
		} else if (unitName.equals(APC.getTypeName())) {
			return new APC(x, y, hero.getColor());
		} else if (unitName.equals(Artillery.getTypeName())) {
			return new Artillery(x, y, hero.getColor());
		} else if (unitName.equals(Rocket.getTypeName())) {
			return new Rocket(x, y, hero.getColor());
		} else if (unitName.equals(AAir.getTypeName())) {
			return new AAir(x, y, hero.getColor());
		} else if (unitName.equals(Missiles.getTypeName())) {
			return new Missiles(x, y, hero.getColor());
		} else if (unitName.equals(Fighter.getTypeName())) {
			return new Fighter(x, y, hero.getColor());
		} else if (unitName.equals(Bomber.getTypeName())) {
			return new Bomber(x, y, hero.getColor());
		} else if (unitName.equals(BCopter.getTypeName())) {
			return new BCopter(x, y, hero.getColor());
		} else if (unitName.equals(TCopter.getTypeName())) {
			return new TCopter(x, y, hero.getColor());
		} else if (unitName.equals(Battleship.getTypeName())) {
			return new Battleship(x, y, hero.getColor());
		} else if (unitName.equals("Cruiser")) {
			return new Cruiser(x, y, hero.getColor());
		} else if (unitName.equals(Lander.getTypeName())) {
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
			items = BuildingItem.getFactoryItems();
		} else if (airport) {
			items = BuildingItem.getAirportItems();
		} else if (port) {
			items = BuildingItem.getPortItems();
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