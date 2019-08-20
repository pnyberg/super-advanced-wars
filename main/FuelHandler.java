package main;

import hero.Hero;
import unitUtils.MovementType;
import units.Unit;

public class FuelHandler {
	private int fuelMaintenancePerTurn;
	private HeroHandler heroHandler;
	
	public FuelHandler(int fuelMaintenancePerTurn, HeroHandler heroHandler) {
		this.fuelMaintenancePerTurn = fuelMaintenancePerTurn;
		this.heroHandler = heroHandler;
	}

	public void fuelMaintenance() {
		Hero hero = heroHandler.getCurrentHero();
		for (int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			Unit unit = hero.getTroopHandler().getTroop(k);
			if (movementTypeConsumesFuelDaily(unit.getMovementType())) {
				unit.getUnitSupply().useFuel(fuelMaintenancePerTurn);
			}
		}
	}
	
	private boolean movementTypeConsumesFuelDaily(MovementType movementType) {
		return movementType == MovementType.SHIP
			|| movementType == MovementType.TRANSPORT
			|| movementType == MovementType.AIR;
	}
}