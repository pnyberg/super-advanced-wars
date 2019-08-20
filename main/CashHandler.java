package main;

import hero.Hero;
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
		int numberOfCashgivers = 0;
		Hero hero = heroHandler.getCurrentHero();
		for (Building building : buildingHandler.getAllBuildings()) {
			if (building.getOwner() == hero) {
				numberOfCashgivers++;
			}
		}
		int newCash = Building.getIncome() * numberOfCashgivers;
		hero.manageCash(newCash);
	}
}