package main;

import gameObjects.GameProp;
import hero.Hero;
import hero.HeroPortrait;
import units.MovementType;
import units.Unit;

public class FuelHandler {
	private GameProp gameProp;
	private HeroHandler heroHandler;
	
	public FuelHandler(GameProp gameProp, HeroHandler heroHandler) {
		this.gameProp = gameProp;
		this.heroHandler = heroHandler;
	}

	public void fuelMaintenance() {
		Hero hero = heroHandler.getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			Unit unit = hero.getTroopHandler().getTroop(k);
			if (movementTypeConsumesFuelDaily(unit.getMovementType())) {
				unit.getUnitSupply().useFuel(gameProp.fuelMaintenancePerTurn);
			}
		}
	}
	
	private boolean movementTypeConsumesFuelDaily(MovementType movementType) {
		return movementType == MovementType.SHIP ||
				movementType == MovementType.TRANSPORT ||
				movementType == MovementType.AIR;
	}
}