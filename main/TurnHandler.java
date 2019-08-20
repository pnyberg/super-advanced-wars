package main;

import hero.Hero;
import map.BuildingStructureHandlerObject;
import map.structures.StructureHandler;

public class TurnHandler {
	private CashHandler cashHandler;
	private FuelHandler fuelHandler;
	private RepairHandler repairHandler;
	private HeroHandler heroHandler;
	private StructureHandler structureHandler;
	private int day;
	private boolean firstHeroOfTheDay;

	public TurnHandler(int fuelMaintenancePerTurn, HeroHandler heroHandler, BuildingStructureHandlerObject buildingStructureHandlerObject) {
		fuelHandler = new FuelHandler(fuelMaintenancePerTurn, heroHandler);
		this.heroHandler = heroHandler;
		this.structureHandler = buildingStructureHandlerObject.structureHandler;
		cashHandler = new CashHandler(heroHandler, buildingStructureHandlerObject.buildingHandler);
		repairHandler = new RepairHandler(heroHandler, buildingStructureHandlerObject.buildingHandler);
		day = 1;
		firstHeroOfTheDay = false;
	}
	
	public void endTurn() {
		endTurnActions();
		startTurnActions();
	}

	public void startTurnActions() {
		cashHandler.updateCash();
		fuelHandler.fuelMaintenance();
		repairHandler.repairUnits();
		if (heroHandler.getCurrentHero().isPowerActive()) {
			heroHandler.getCurrentHero().setPowerActive(false);
		} else if (heroHandler.getCurrentHero().isSuperPowerActive()) {
			heroHandler.getCurrentHero().setSuperPowerActive(false);
		}
		if (firstHeroOfTheDay) {
			day++;
			structureHandler.doStructureActions();
			firstHeroOfTheDay = false;
		}
	}

	private void endTurnActions() {
		resetActiveVariable();
		heroHandler.nextHero();
		if (heroHandler.getCurrentHero() == heroHandler.getHero(0)) {
			firstHeroOfTheDay = true;
		}
	}

	private void resetActiveVariable() {
		Hero hero = heroHandler.getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			hero.getTroopHandler().getTroop(k).regulateActive(true);
		}
	}
}