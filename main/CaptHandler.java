package main;

import cursors.Cursor;
import hero.Hero;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import units.Unit;

public class CaptHandler {
	private BuildingHandler buildingHandler;
	private HeroHandler heroHandler;
	
	public CaptHandler(BuildingHandler buildingHandler, HeroHandler heroHandler) {
		this.buildingHandler = buildingHandler;
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