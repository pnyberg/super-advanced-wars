package menus;

import units.*;
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

public class BuildingItem {
	private static BuildingItem[] factoryItems;
	private static BuildingItem[] portItems;
	private static BuildingItem[] airportItems;

	private String name;
	private int price;

	public BuildingItem(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public static void createBuildingItems() {
		BuildingItem[] newFactoryItems = {	
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

		BuildingItem[] newPortItems = {	
					new BuildingItem(Battleship.getTypeName(), Battleship.getPrice()),
					new BuildingItem(Lander.getTypeName(), Lander.getPrice()),
					new BuildingItem(Cruiser.getTypeName(), Cruiser.getPrice())
				};

		BuildingItem[] newAirportItems = {	
						new BuildingItem(Fighter.getTypeName(), Fighter.getPrice()),
						new BuildingItem(Bomber.getTypeName(), Bomber.getPrice()),
						new BuildingItem(BCopter.getTypeName(), BCopter.getPrice()),
						new BuildingItem(TCopter.getTypeName(), TCopter.getPrice())
					};

		factoryItems = newFactoryItems;
		portItems = newPortItems;
		airportItems = newAirportItems;
	}

	public static BuildingItem[] getFactoryItems() {
		return factoryItems;
	}

	public static BuildingItem[] getPortItems() {
		return portItems;
	}

	public static BuildingItem[] getAirportItems() {
		return airportItems;
	}


	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}