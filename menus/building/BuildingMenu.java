/**
 * TODO: 
 *  - Add so that item's can't be clicked if they cost to much
 */
package menus.building;

import units.*;
import map.GameMap;
import map.area.TerrainType;
import menus.BuildingMenuState;
import menus.Menu;
import menus.unit.UnitCreatingFactory;

import java.awt.Graphics;

import gameObjects.GameProperties;
import gameObjects.GameState;
import hero.*;

public class BuildingMenu extends Menu {
	private int priceAlign;
	private BuildingItemFactory buildingItemFactory;
	private UnitCreatingFactory unitCreatingFactory;
	private HeroHandler heroHandler;
	private BuildingMenuPainter buildingMenuPainter;
	private GameMap gameMap;

	public BuildingMenu(GameProperties gameProperties, GameState gameState) {
		super(gameState.getBuildingMenuState(), gameProperties.getTileSize());
		this.heroHandler = gameState.getHeroHandler();
		dimensionValues.menuRowWidth = 118;
		priceAlign = 70;
		buildingItemFactory = new BuildingItemFactory();
		unitCreatingFactory = new UnitCreatingFactory(tileSize);
		buildingMenuPainter = new BuildingMenuPainter(heroHandler, dimensionValues, priceAlign);
		this.gameMap = gameProperties.getGameMap();
	}

	public void openMenu(int x, int y) {
		super.openMenu(x, y);
		int tileX = x / tileSize;
		int tileY = y / tileSize;
		TerrainType terrainType = gameMap.getTerrainType(tileX, tileY);
		if (terrainType == TerrainType.FACTORY || terrainType == TerrainType.AIRPORT || terrainType == TerrainType.PORT) {
			((BuildingMenuState)menuState).setBuildingMenuType(terrainType);
		}
	}

	public void closeMenu() {
		super.closeMenu();
		((BuildingMenuState)menuState).resetBuildingMenuType();
	}

	public int getNumberOfActiveRows() {
		BuildingItem[] menuItems = getMenuItems();
		if (menuItems == null) {
			return 0;
		}
		return menuItems.length;
	}

	public void buySelectedTroop() {
		Hero currentHero = heroHandler.getCurrentHero();
		BuildingItem selectedMenuItem = getMenuItems()[menuState.getMenuIndex()];
		currentHero.spendCash(selectedMenuItem.getPrice());
		Unit unit = createUnitFromIndex(currentHero);
		currentHero.getTroopHandler().addTroop(unit);
	}

	private Unit createUnitFromIndex(Hero hero) {
		int x = menuState.getX();
		int y = menuState.getY();
		String unitName = getMenuItems()[menuState.getMenuIndex()].getName();
		return unitCreatingFactory.createUnit(unitName, x, y, hero.getColor());
	}
	
	private BuildingItem[] getMenuItems() {
		TerrainType currentBuildingMenuType = ((BuildingMenuState)menuState).getBuildingMenuType();
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
		int x = menuState.getX();
		int y = menuState.getY();
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