package main;

import gameObjects.GameProp;
import heroes.Hero;
import heroes.HeroPortrait;
import units.MovementType;
import units.Unit;

public class FuelHandler {
	private GameProp gameProp;
	private HeroPortrait heroPortrait;
	
	public FuelHandler(GameProp gameProp, HeroPortrait heroPortrait) {
		this.gameProp = gameProp;
		this.heroPortrait = heroPortrait;
	}

	public void fuelMaintenance() {
		Hero hero = heroPortrait.getHeroHandler().getCurrentHero();
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