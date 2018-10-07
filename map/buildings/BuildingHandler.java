package map.buildings;

import java.util.ArrayList;

import heroes.HeroPortrait;
import main.HeroHandler;

public class BuildingHandler {
	private HeroHandler heroHandler;
	private ArrayList<Building> buildings;
	
	public BuildingHandler(HeroHandler heroHandler, ArrayList<Building> buildings) {
		this.heroHandler = heroHandler;
		this.buildings = buildings;
	}
	
	public boolean isNonFriendlyBuilding(int x, int y) {
		Building building = getBuilding(x, y);
		if (building == null) {
			return false;
		}
		return building.getOwner() != heroHandler.getCurrentHero();
	}

	public Building getBuilding(int x, int y) {
		for (Building building : buildings) {
			if (building.getX() == x && building.getY() == y) {
				return building;
			}
		}

		return null;
	}

	public Building getFriendlyBuilding(int x, int y) {
		Building building = getBuilding(x, y);

		if (building != null && building.getOwner() == heroHandler.getCurrentHero()) {
			return building;
		}

		return null;
	}
}
