/**
 * TODO: Add so that item's can't be clicked if they cost to much
 */
package menus.building;

import units.*;
import main.HeroHandler;
import map.area.Area;
import map.area.TerrainType;
import menus.Menu;
import menus.unit.UnitCreatingFactory;

import java.awt.Graphics;

import hero.*;

public class BuildingMenu extends Menu {
	private final int priceAlign = 70;
	private boolean factory;
	private boolean port;
	private boolean airport;
	private BuildingItemFactory buildingItemFactory;
	private UnitCreatingFactory unitCreatingFactory;
	private HeroHandler heroHandler;
	private BuildingMenuPainter buildingMenuPainter;
	private Area[][] map;

	public BuildingMenu(int tileSize, HeroHandler heroHandler, Area[][] map) {
		super(tileSize);
		this.heroHandler = heroHandler;
		dimensionValues.menuRowWidth = 118;
		factory = false;
		port = false;
		airport = false;
		buildingItemFactory = new BuildingItemFactory();
		unitCreatingFactory = new UnitCreatingFactory(tileSize);
		buildingMenuPainter = new BuildingMenuPainter(heroHandler, dimensionValues, priceAlign);
		this.map = map;
	}

	public void openMenu(int x, int y) {
		super.openMenu(x, y);
		TerrainType terrainType = map[x][y].getTerrainType();
		if (terrainType == TerrainType.FACTORY) {
			factory = true;
		} else if (terrainType == TerrainType.AIRPORT) {
			airport = true;
		} else if (terrainType == TerrainType.PORT) {
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
		Hero currentHero = heroHandler.getCurrentHero();
		currentHero.manageCash(-getStandardItems()[menuIndex].getPrice());
		Unit unit = createUnitFromIndex(currentHero);
		currentHero.getTroopHandler().addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		String unitName = getStandardItems()[menuIndex].getName();
		return unitCreatingFactory.createUnit(unitName, x * tileSize, y * tileSize, hero.getColor());
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