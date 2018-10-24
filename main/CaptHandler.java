package main;

import hero.Hero;
import map.buildings.Building;
import units.Unit;

public class CaptHandler {
	private HeroHandler heroHandler;
	
	public CaptHandler(HeroHandler heroHandler) {
		this.heroHandler = heroHandler;
	}
	
	public void captBuilding(Unit chosenUnit, Building building) {
		building.capt(chosenUnit.getUnitHealth().getShowHP());
		if (building.isCapted()) {
			Hero hero = heroHandler.getHeroFromUnit(chosenUnit);
			building.setOwnership(hero);
		}
	}
}