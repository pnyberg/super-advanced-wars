/**
 * TODO: Add so that item's can't be clicked if they cost to much
 */
package menus.building;

import units.*;
import heroes.*;
import menus.Menu;
import menus.unit.UnitCreatingFactory;
import handlers.*;

import java.awt.Graphics;

public class BuildingMenu extends Menu {
	private final int priceAlign = 70;
	private boolean factory;
	private boolean port;
	private boolean airport;
	private BuildingItemFactory buildingItemFactory;
	private UnitCreatingFactory unitCreatingFactory;
	private HeroPortrait heroPortrait;
	private BuildingMenuPainter buildingMenuPainter;

	public BuildingMenu(int tileSize, HeroPortrait heroPortrait) {
		super(tileSize);
		this.heroPortrait = heroPortrait;
		dimensionValues.menuRowWidth = 118;
		factory = false;
		port = false;
		airport = false;
		buildingItemFactory = new BuildingItemFactory();
		unitCreatingFactory = new UnitCreatingFactory();
		buildingMenuPainter = new BuildingMenuPainter(heroPortrait, dimensionValues, priceAlign);
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

	public void buySelectedTroop() {
		Hero currentHero = heroPortrait.getCurrentHero();
		currentHero.manageCash(-getStandardItems()[menuIndex].getPrice());
		Unit unit = createUnitFromIndex(currentHero);
		currentHero.getTroopHandler().addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		String unitName = getStandardItems()[menuIndex].getName();
		return unitCreatingFactory.createUnit(unitName, x, y, hero.getColor());
	}
	
	private BuildingItem[] getStandardItems() {
		if (factory) {
			return buildingItemFactory.getStandardFactoryItems();
		} else if (port) {
			return buildingItemFactory.getStandardPortItems();
		} else if (airport) {
			return buildingItemFactory.getStandardAirportItems();
		}
		return null;
	}

	public void paint(Graphics g) {
		int menuY = y * dimensionValues.getTileSize() + dimensionValues.getTileSize() / 2 + dimensionValues.getAlignY();
		BuildingItem[] items = new BuildingItem[0];
		if (factory) {
			items = buildingItemFactory.getStandardFactoryItems();
		} else if (airport) {
			items = buildingItemFactory.getStandardAirportItems();
		} else if (port) {
			items = buildingItemFactory.getStandardPortItems();
		}

		paintMenuBackground(g);
		buildingMenuPainter.paint(g, x, menuY, items);
		paintArrow(g);
	}
}