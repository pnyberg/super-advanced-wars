package main;

import gameObjects.GameProp;
import hero.Hero;
import menus.map.MapMenu;

public class TurnHandler {
	private CashHandler cashHandler;
	private FuelHandler fuelHandler;
	private HeroHandler heroHandler;
	private MapMenu mapMenu;

	public TurnHandler(GameProp gameProp, CashHandler cashHandler, HeroHandler heroHandler, MapMenu mapMenu) {
		this.cashHandler = cashHandler;
		fuelHandler = new FuelHandler(gameProp, heroHandler);
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
		if (heroHandler.getCurrentHero().isPowerActive()) {
			heroHandler.getCurrentHero().setPowerActive(false);
		} else if (heroHandler.getCurrentHero().isSuperPowerActive()) {
			heroHandler.getCurrentHero().setSuperPowerActive(false);
		}
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