package main;

import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.TurnState;
import hero.Hero;
import hero.HeroHandler;
import map.buildings.BuildingHandler;
import map.structures.StructureHandler;

public class TurnHandler {
	private CashHandler cashHandler;
	private FuelHandler fuelHandler;
	private RepairHandler repairHandler;
	private HeroHandler heroHandler;
	private StructureHandler structureHandler;
	private TurnState turnState;

	public TurnHandler(GameProperties gameProperties, GameState gameState) {
		this.heroHandler = gameState.getHeroHandler();
		fuelHandler = new FuelHandler(gameProperties.fuelMaintenancePerTurn, heroHandler);
		this.structureHandler = new StructureHandler(gameState, gameProperties.getMapDimension());
		BuildingHandler buildingHandler = new BuildingHandler(gameState);
		cashHandler = new CashHandler(heroHandler, buildingHandler);
		repairHandler = new RepairHandler(heroHandler, buildingHandler);
		turnState = gameState.getTurnState();
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
		if (turnState.isFirstHeroOfTheDay()) {
			turnState.incrementDay();
			structureHandler.doStructureActions();
			turnState.setFirstHeroOfTheDay(false);
		}
	}

	private void endTurnActions() {
		resetActiveVariable();
		heroHandler.nextHero();
		if (heroHandler.getCurrentHero() == heroHandler.getHero(0)) {
			turnState.setFirstHeroOfTheDay(true);
		}
	}

	private void resetActiveVariable() {
		Hero hero = heroHandler.getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			hero.getTroopHandler().getTroop(k).regulateActive(true);
		}
	}
}