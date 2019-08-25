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
		Hero hero = heroHandler.getCurrentHero();
		int numberOfCashgivingBuildings = buildingHandler.getNumberOfCashgeneratingBuildings(hero);
		int newCash = Building.getIncome() * numberOfCashgivingBuildings;
		hero.earnCash(newCash);
	}
}