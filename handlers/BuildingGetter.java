package handlers;

import java.util.ArrayList;

import area.buildings.Building;
import heroes.HeroPortrait;

public class BuildingGetter {
	private HeroPortrait portrait;
	private ArrayList<Building> buildings;
	
	public BuildingGetter(HeroPortrait portrait, ArrayList<Building> buildings) {
		this.buildings = buildings;
		this.portrait = portrait;
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

		if (building != null && building.getOwner() == portrait.getHeroHandler().getCurrentHero()) {
			return building;
		}

		return null;
	}
}
