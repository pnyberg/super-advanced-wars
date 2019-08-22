package main;

import hero.HeroHandler;
import map.buildings.Airport;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.buildings.City;
import map.buildings.Factory;
import map.buildings.Port;
import unitUtils.UnitWorthCalculator;
import units.Unit;

public class RepairHandler {
	private HeroHandler heroHandler;
	private BuildingHandler buildingHandler;
	
	public RepairHandler(HeroHandler heroHandler, BuildingHandler buildingHandler) {
		this.heroHandler = heroHandler;
		this.buildingHandler = buildingHandler;
	}

	public void repairUnits() {
		for (int i = 0 ; i < heroHandler.getCurrentHeroTroopSize() ; i++) {
			Unit unit = heroHandler.getCurrentHero().getTroopHandler().getTroop(i);
			if (unitCanBeRepaired(unit)) {
				repairUnit(unit);
				unit.getUnitSupply().replentish();
			}
		}
	}
	
	private boolean unitCanBeRepaired(Unit unit) {
		Building building = buildingHandler.getBuilding(unit.getPosition().getX(), unit.getPosition().getY());
		return building != null && building.getOwner() == heroHandler.getCurrentHero() 
				&& unitIsRepairableByBuilding(unit, building);
	}
	
	private boolean unitIsRepairableByBuilding(Unit unit, Building building) {
		if (unit.getMovementType().isLandMovementType() && landRepairableBuilding(building)) {
			return true;
		} else if (unit.getMovementType().isAirMovementType() && (building instanceof Airport)) {
			return true;
		} else if (unit.getMovementType().isSeaMovementType() && (building instanceof Port)) {
			return true;
		}
		return false;
	}
	
	private boolean landRepairableBuilding(Building building) {
		return building instanceof City || building instanceof Factory/* || building instanceof HQ*/;
	}

	private void repairUnit(Unit unit) {
		UnitWorthCalculator unitWorthCalculator = new UnitWorthCalculator();
		// restore hp to an even 10
		int restoreHp = 10 - unit.getUnitHealth().getHP() % 10;
		if (restoreHp < 10) {
			unit.getUnitHealth().heal(restoreHp);
		}
		// restore up to two "show-hp"
		int repairCost = unitWorthCalculator.getFullHealthUnitWorth(unit) / 10;
		for (int i = 0 ; i < 2 ; i++) {
			if (heroHandler.getCurrentHero().getCash() >= repairCost && !unit.getUnitHealth().atFullHealth()) {
				unit.getUnitHealth().heal(10);
				heroHandler.getCurrentHero().spendCash(repairCost);
			}
		}
	}
}