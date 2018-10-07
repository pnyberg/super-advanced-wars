package main;

import heroes.Hero;
import menus.map.MapMenu;

public class TurnHandler {
	private CashHandler cashHandler;
	private FuelHandler fuelHandler;
	private HeroHandler heroHandler;
	private MapMenu mapMenu;

	public TurnHandler(CashHandler cashHandler, FuelHandler fuelHandler, HeroHandler heroHandler, MapMenu mapMenu) {
		this.cashHandler = cashHandler;
		this.fuelHandler = fuelHandler;
		this.heroHandler = heroHandler;
		this.mapMenu = mapMenu;
	}
	
	public void endTurn() {
		endTurnActions();
		startTurnActions();
	}

	public void startTurnActions() {
		cashHandler.updateCash();
		fuelHandler.fuelMaintenance();
	}

	public void endTurnActions() {
		mapMenu.closeMenu();
		resetActiveVariable();
		heroHandler.nextHero();
	}

	private void resetActiveVariable() {
		Hero hero = heroHandler.getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			hero.getTroopHandler().getTroop(k).regulateActive(true);
		}
	}
}