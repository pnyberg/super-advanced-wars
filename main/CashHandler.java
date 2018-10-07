package main;

import java.util.ArrayList;

import hero.Hero;
import hero.HeroPortrait;
import map.buildings.Building;

public class CashHandler {
	private HeroPortrait heroPortrait;
	private ArrayList<Building> buildings;

	public CashHandler(HeroPortrait heroPortrait, ArrayList<Building> buildings) {
		this.heroPortrait = heroPortrait;
		this.buildings = buildings;
	}
	
	public void updateCash() {
		int numberOfCashgivers = 0;
		Hero hero = heroPortrait.getHeroHandler().getCurrentHero();
		for (Building building : buildings) {
			if (building.getOwner() == hero) {
				numberOfCashgivers++;
			}
		}
		int newCash = Building.getIncome() * numberOfCashgivers;
		hero.manageCash(newCash);
	}
}