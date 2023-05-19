package main;

import hero.Hero;
import hero.HeroHandler;
import unitUtils.MovementType;
import units.Unit;

public class FuelHandler {
	private int fuelMaintenancePerTurn;
	
	public FuelHandler(int fuelMaintenancePerTurn) {
		this.fuelMaintenancePerTurn = fuelMaintenancePerTurn;
	}

	public void fuelMaintenance(Hero hero) {
		for(int k = 0 ; k < hero.getTroopHandler().getTroopSize() ; k++) {
			Unit unit = hero.getTroopHandler().getTroop(k);
			if(movementTypeConsumesFuelDaily(unit.getMovementType())) {
				unit.getUnitSupply().useFuel(fuelMaintenancePerTurn); // TODO: add hero-effect for fuel
			}
		}
	}
	
	private boolean movementTypeConsumesFuelDaily(MovementType movementType) {
		return movementType == MovementType.SHIP
			|| movementType == MovementType.TRANSPORT
			|| movementType == MovementType.AIR;
	}
}