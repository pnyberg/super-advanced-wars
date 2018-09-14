package menus.building;

import units.airMoving.BCopter;
import units.airMoving.Bomber;
import units.airMoving.Fighter;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Battleship;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.tireMoving.Missiles;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.APC;
import units.treadMoving.Artillery;
import units.treadMoving.MDTank;
import units.treadMoving.Neotank;
import units.treadMoving.Tank;

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
				new BuildingItem(Cruiser.getTypeName(), Cruiser.getPrice())
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