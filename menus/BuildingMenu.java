package menus;

import units.*;
import units.airMoving.BCopter;
import units.airMoving.Bomber;
import units.airMoving.Fighter;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Battleship;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.tireMoving.Missiles;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.APC;
import units.treadMoving.Artillery;
import units.treadMoving.MDTank;
import units.treadMoving.Neotank;
import units.treadMoving.Tank;
import heroes.*;
import handlers.*;

import java.awt.Graphics;

/**
 * TODO: Add so that item's can't be clicked if they cost to much
 */
public class BuildingMenu extends Menu {
	private final int priceAlign = 70;
	private boolean factory;
	private boolean port;
	private boolean airport;
	private BuildingItemFactory buildingItemFactory;
	private UnitCreatingFactory unitCreatingFactory;

	public BuildingMenu(int tileSize) {
		super(tileSize);

		menuWidth = (tileSize * 9 / 3) - 2;
		factory = false;
		port = false;
		airport = false;
		buildingItemFactory = new BuildingItemFactory();
		unitCreatingFactory = new UnitCreatingFactory();
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
	}

	public void closeMenu() {
		super.closeMenu();

		factory = false;
		airport = false;
		port = false;
	}

	public int getNumberOfRows() {
		if (factory) {
			return buildingItemFactory.getStandardFactoryItems().length;
		} else if (port) {
			return buildingItemFactory.getStandardPortItems().length;
		} else if (airport) {
			return buildingItemFactory.getStandardAirportItems().length;
		}
		return 0;
	}

	public void buySelectedTroop(HeroPortrait portrait) {
		Hero currentHero = portrait.getCurrentHero();

		if (factory) {
			currentHero.manageCash(-buildingItemFactory.getStandardFactoryItems()[menuIndex].getPrice());
		} else if (port) {
			currentHero.manageCash(-buildingItemFactory.getStandardPortItems()[menuIndex].getPrice());
		} else if (airport) {
			currentHero.manageCash(-buildingItemFactory.getStandardAirportItems()[menuIndex].getPrice());
		} else {
			return;
		}

		Unit unit = createUnitFromIndex(currentHero);
		currentHero.addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		String unitName = "";

		if (factory) {
			unitName = buildingItemFactory.getStandardFactoryItems()[menuIndex].getName();
		} else if (port) {
			unitName = buildingItemFactory.getStandardPortItems()[menuIndex].getName();
		} else if (airport) {
			unitName = buildingItemFactory.getStandardAirportItems()[menuIndex].getName();
		}

		return unitCreatingFactory.createUnit(unitName, x, y, hero.getColor());
	}

	public void paint(Graphics g) {
		int menuY = y * tileSize + tileSize / 2 + yAlign;
		BuildingItem[] items = new BuildingItem[0];

		if (factory) {
			items = buildingItemFactory.getStandardFactoryItems();
		} else if (airport) {
			items = buildingItemFactory.getStandardAirportItems();
		} else if (port) {
			items = buildingItemFactory.getStandardPortItems();
		}

		paintMenuBackground(g);

		for (int k = 0 ; k < items.length ; k++) {
			paintMenuItem(g, menuY + menuRowHeight * (k + 1), items[k].getName(), items[k].getPrice());
		}

		paintArrow(g);
	}

	private void paintMenuItem(Graphics g, int y, String text, int price) {
		int menuX = x * tileSize + tileSize / 2 + xAlign;
		int extraPriceAlign = (price >= 10000 ? 0 : 8);

		g.drawString(text, menuX, y);
		g.drawString("" + price + "", menuX + priceAlign + extraPriceAlign, y);
	}
}