package main;

import gameObjects.GameProp;
import hero.Hero;
import map.structures.StructureHandler;
import menus.map.MapMenu;

public class TurnHandler {
	private CashHandler cashHandler;
	private FuelHandler fuelHandler;
	private HeroHandler heroHandler;
	private StructureHandler structureHandler;
	private MapMenu mapMenu;
	private int day;
	private boolean firstHeroOfTheDay;

	public TurnHandler(GameProp gameProp, CashHandler cashHandler, HeroHandler heroHandler, StructureHandler structureHandler, MapMenu mapMenu) {
		this.cashHandler = cashHandler;
		fuelHandler = new FuelHandler(gameProp, heroHandler);
		this.heroHandler = heroHandler;
		this.structureHandler = structureHandler;
		this.mapMenu = mapMenu;
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

	public void endTurnActions() {
		mapMenu.closeMenu();
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