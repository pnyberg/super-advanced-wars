package main;

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
	private UnitWorthCalculator unitWorthCalculator;
	
	public RepairHandler(HeroHandler heroHandler, BuildingHandler buildingHandler) {
		this.heroHandler = heroHandler;
		this.buildingHandler = buildingHandler;
		unitWorthCalculator = new UnitWorthCalculator();
	}

	public void repairUnits() {
		for (int i = 0 ; i < heroHandler.getCurrentHeroTroopSize() ; i++) {
			Unit unit = heroHandler.getCurrentHero().getTroopHandler().getTroop(i);
			Building building = buildingHandler.getBuilding(unit.getPoint().getX(), unit.getPoint().getY());
			if (building != null && building.getOwner() == heroHandler.getCurrentHero() && unitIsRepairable(unit, building)) {
				repairUnit(unit);
				unit.getUnitSupply().replentish();
			}
		}
	}
	
	private boolean unitIsRepairable(Unit unit, Building building) {
		if (unit.getMovementType().isLandMovementType() && 
				(building instanceof City || building instanceof Factory/* || building instanceof HQ*/)) {
			return true;
		} else if (unit.getMovementType().isAirMovementType() && (building instanceof Airport)) {
			return true;
		} else if (unit.getMovementType().isSeaMovementType() && (building instanceof Port)) {
			return true;
		}
		return false;
	}

	private void repairUnit(Unit unit) {
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
				heroHandler.getCurrentHero().manageCash(-repairCost);
			}
		}
	}
}