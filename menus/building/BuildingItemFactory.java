package menus.building;

import units.airMoving.*;
import units.footMoving.*;
import units.seaMoving.*;
import units.tireMoving.*;
import units.treadMoving.*;

public class BuildingItemFactory {
	public BuildingItem[] getStandardFactoryItems() {
		return new BuildingItem[] {	
				new BuildingItem(Infantry.getTypeName(), Infantry.getPrice()),
				new BuildingItem(Mech.getTypeName(), Mech.getPrice()),
				new BuildingItem(Recon.getTypeName(), Recon.getPrice()),
				new BuildingItem(Tank.getTypeName(), Tank.getPrice()),
				new BuildingItem(MDTank.getTypeName(), MDTank.getPrice()),
				new BuildingItem(Neotank.getTypeName(), Neotank.getPrice()),
				new BuildingItem(APC.getTypeName(), APC.getPrice()),
				new BuildingItem(Artillery.getTypeName(), Artillery.getPrice()),
				new BuildingItem(Rocket.getTypeName(), Rocket.getPrice()),
				new BuildingItem(AAir.getTypeName(), AAir.getPrice()),
				new BuildingItem(Missiles.getTypeName(), Missiles.getPrice())
			};
	}

	public BuildingItem[] getStandardPortItems() {
		return new BuildingItem[] {	
				new BuildingItem(Battleship.getTypeName(), Battleship.getPrice()),
				new BuildingItem(Lander.getTypeName(), Lander.getPrice()),
				new BuildingItem(Cruiser.getTypeName(), Cruiser.getPrice()),
				new BuildingItem(Sub.getTypeName(), Sub.getPrice())
			};
	}

	public BuildingItem[] getStandardAirportItems() {
		return new BuildingItem[] {	
				new BuildingItem(Fighter.getTypeName(), Fighter.getPrice()),
				new BuildingItem(Bomber.getTypeName(), Bomber.getPrice()),
				new BuildingItem(BCopter.getTypeName(), BCopter.getPrice()),
				new BuildingItem(TCopter.getTypeName(), TCopter.getPrice())
			};
	}
}