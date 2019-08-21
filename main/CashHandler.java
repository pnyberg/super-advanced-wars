package main;

import hero.Hero;
import hero.HeroHandler;
import map.buildings.Building;
import map.buildings.BuildingHandler;

public class CashHandler {
	private HeroHandler heroHandler;
	private BuildingHandler buildingHandler;

	public CashHandler(HeroHandler heroHandler, BuildingHandler buildingHandler) {
		this.heroHandler = heroHandler;
		this.buildingHandler = buildingHandler;
	}
	
	public void updateCash() {
		int numberOfCashgivingBuildings = 0;
		Hero hero = heroHandler.getCurrentHero();
		for (Building building : buildingHandler.getAllBuildings()) {
			if (building.getOwner() == hero) {
				numberOfCashgivingBuildings++;
			}
		}
		int newCash = Building.getIncome() * numberOfCashgivingBuildings;
		hero.earnCash(newCash);
	}
}