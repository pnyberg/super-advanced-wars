package main;

import gameObjects.GameProperties;
import hero.Hero;
import map.structures.StructureHandler;
import menus.map.MapMenu;

public class TurnHandler {
	private CashHandler cashHandler;
	private FuelHandler fuelHandler;
	private RepairHandler repairHandler;
	private HeroHandler heroHandler;
	private StructureHandler structureHandler;
	private int day;
	private boolean firstHeroOfTheDay;

	// TODO: rewrite with fewer parameters
	public TurnHandler(GameProperties gameProp, CashHandler cashHandler, RepairHandler repairHandler, HeroHandler heroHandler, StructureHandler structureHandler) {
		this.cashHandler = cashHandler;
		fuelHandler = new FuelHandler(gameProp, heroHandler);
		this.repairHandler = repairHandler;
		this.heroHandler = heroHandler;
		this.structureHandler = structureHandler;
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