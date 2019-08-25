package map.buildings;

import java.util.ArrayList;

import gameObjects.GameState;
import hero.Hero;
import hero.HeroHandler;

public class BuildingHandler {
	private HeroHandler heroHandler;
	private ArrayList<Building> buildings;
	
	public BuildingHandler(GameState gameState) {
		this.heroHandler = gameState.getHeroHandler();
		this.buildings = gameState.getBuildings();
	}
	
	public boolean isNonFriendlyBuilding(int x, int y) {
		Building building = getBuilding(x, y);
		return building != null && building.getOwner() != heroHandler.getCurrentHero();
	}
	
	public int getNumberOfCashgeneratingBuildings(Hero owner) {
		int numberOfCashgivingBuildings = 0;
		for (Building building : buildings) {
			if (building.getOwner() == owner) {
				numberOfCashgivingBuildings++;
			}
		}
		return numberOfCashgivingBuildings;
	}

	public Building getBuilding(int x, int y) {
		for (Building building : buildings) {
			if (building.getX() == x && building.getY() == y) {
				return building;
			}
		}
		return null;
	}

	public Building getFriendlyBuilding(int x, int y) {
		Building building = getBuilding(x, y);
		if (building != null && building.getOwner() == heroHandler.getCurrentHero()) {
			return building;
		}
		return null;
	}
}