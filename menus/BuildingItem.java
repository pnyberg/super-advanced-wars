package menus;

import units.*;

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
						new BuildingItem(Artillery.getTypeName(), Artillery.getPrice()),
						new BuildingItem(Rocket.getTypeName(), Rocket.getPrice())/*,
						new FactoryItem("Missiles", 12000)*/
					};

		BuildingItem[] newPortItems = {	
					new BuildingItem(Battleship.getTypeName(), Battleship.getPrice()),
					new BuildingItem(Lander.getTypeName(), Lander.getPrice())
				};

		BuildingItem[] newAirportItems = {	
//						new BuildingItem("B Copter", 9000),
						new BuildingItem(Fighter.getTypeName(), Fighter.getPrice()),
						new BuildingItem(Bomber.getTypeName(), Bomber.getPrice())
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