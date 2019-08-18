/**
 * TODO: 
 *  - Add so that item's can't be clicked if they cost to much
 */
package menus.building;

import units.*;
import main.HeroHandler;
import map.GameMap;
import map.area.TerrainType;
import menus.Menu;
import menus.unit.UnitCreatingFactory;

import java.awt.Graphics;

import hero.*;

public class BuildingMenu extends Menu {
	private int priceAlign;
	private TerrainType currentBuildingMenuType;
	private BuildingItemFactory buildingItemFactory;
	private UnitCreatingFactory unitCreatingFactory;
	private HeroHandler heroHandler;
	private BuildingMenuPainter buildingMenuPainter;
	private GameMap gridMap;

	public BuildingMenu(int tileSize, HeroHandler heroHandler, GameMap gridMap) {
		super(tileSize);
		this.heroHandler = heroHandler;
		dimensionValues.menuRowWidth = 118;
		priceAlign = 70;
		currentBuildingMenuType = null;
		buildingItemFactory = new BuildingItemFactory();
		unitCreatingFactory = new UnitCreatingFactory(tileSize);
		buildingMenuPainter = new BuildingMenuPainter(heroHandler, dimensionValues, priceAlign);
		this.gridMap = gridMap;
	}

	public void openMenu(int x, int y) {
		super.openMenu(x, y);
		int tileX = x / tileSize;
		int tileY = y / tileSize;
		TerrainType terrainType = gridMap.getMap()[tileX][tileY].getTerrainType();
		if (terrainType == TerrainType.FACTORY || terrainType == TerrainType.AIRPORT || terrainType == TerrainType.PORT) {
			currentBuildingMenuType = terrainType;
		}
	}

	public void closeMenu() {
		super.closeMenu();
		currentBuildingMenuType = null;
	}

	public int getNumberOfRows() {
		BuildingItem[] menuItems = getMenuItems();
		if (menuItems == null) {
			return 0;
		}
		return menuItems.length;
	}

	public void buySelectedTroop() {
		Hero currentHero = heroHandler.getCurrentHero();
		currentHero.manageCash(-getMenuItems()[menuIndex].getPrice());
		Unit unit = createUnitFromIndex(currentHero);
		currentHero.getTroopHandler().addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		String unitName = getMenuItems()[menuIndex].getName();
		return unitCreatingFactory.createUnit(unitName, x, y, hero.getColor());
	}
	
	private BuildingItem[] getMenuItems() {
		if (currentBuildingMenuType == TerrainType.FACTORY) {
			return buildingItemFactory.getStandardFactoryItems();
		} else if (currentBuildingMenuType == TerrainType.AIRPORT) {
			return buildingItemFactory.getStandardAirportItems();
		} else if (currentBuildingMenuType == TerrainType.PORT) {
			return buildingItemFactory.getStandardPortItems();
		}
		return null;
	}

	public void paint(Graphics g) {
		int yAdjust = dimensionValues.getTileSize() / 2 + dimensionValues.getAlignY();
		BuildingItem[] items = getMenuItems();
		if (items == null) {
			items = new BuildingItem[0];
		}

		paintMenuBackground(g);
		buildingMenuPainter.paint(g, x, y + yAdjust, items);
		paintArrow(g);
	}
}