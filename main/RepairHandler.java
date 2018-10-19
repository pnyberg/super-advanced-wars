package main;

import gameObjects.MapDim;
import map.buildings.Airport;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.buildings.City;
import map.buildings.Factory;
import map.buildings.Port;
import units.Unit;
import units.UnitWorthCalculator;

public class RepairHandler {
	private MapDim mapDim;
	private HeroHandler heroHandler;
	private BuildingHandler buildingHandler;
	private UnitWorthCalculator unitWorthCalculator;
	
	public RepairHandler(MapDim mapDim, HeroHandler heroHandler, BuildingHandler buildingHandler, UnitWorthCalculator unitWorthCalculator) {
		this.mapDim = mapDim;
		this.heroHandler = heroHandler;
		this.buildingHandler = buildingHandler;
		this.unitWorthCalculator = unitWorthCalculator;
	}

	public void repairUnits() {
		for (int i = 0 ; i < heroHandler.getCurrentHeroTroopSize() ; i++) {
			Unit unit = heroHandler.getCurrentHero().getTroopHandler().getTroop(i);
			int x = unit.getPoint().getX();
			int y = unit.getPoint().getY();
			Building building = buildingHandler.getBuilding(x / mapDim.tileSize, y / mapDim.tileSize);
			if (building != null && building.getOwner() == heroHandler.getCurrentHero() 
					&& unitIsRepairable(unit, building)) {
				repairUnit(unit);
				unit.getUnitSupply().replentish();
			}
		}
	}
	
	private boolean unitIsRepairable(Unit unit, Building building) {
		if (unit.getMovementType().isLandMovementType() && (building instanceof City || building instanceof Factory/* || building instanceof HQ*/)) {
			return true;
		} else if (unit.getMovementType().isAirMovementType() && (building instanceof Airport)) {
			return true;
		} else if (unit.getMovementType().isSeaMovementType() && (building instanceof Port)) {
			return true;
		}
		return false;
	}

	private void repairUnit(Unit unit) {
		int restoreHp = 10 - unit.getUnitHealth().getHP() % 10;
		if (restoreHp < 10) {
			unit.getUnitHealth().heal(restoreHp);
		}
		int repairCost = unitWorthCalculator.getFullHealthUnitWorth(unit) / 10;
		for (int i = 0 ; i < 2 ; i++) {
			if (heroHandler.getCurrentHero().getCash() >= repairCost && !unit.getUnitHealth().atFullHealth()) {
				unit.getUnitHealth().heal(10);
				heroHandler.getCurrentHero().manageCash(-repairCost);
			}
		}
	}
}