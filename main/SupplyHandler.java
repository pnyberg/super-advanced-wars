package main;

import gameObjects.GameState;
import map.UnitGetter;
import units.Unit;

public class SupplyHandler {
	private UnitGetter unitGetter;
	private int tileSize;

	public SupplyHandler(GameState gameState, int tileSize) {
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
		this.tileSize = tileSize;
	}
	
	public void replentishUnit(Unit unit) {
		unit.getUnitSupply().replentish();
	}

	public boolean apcMaySupply(Unit unit, int x, int y) {
		return unitGetter.getFriendlyUnitExceptSelf(unit, x + tileSize, y) != null 
				|| unitGetter.getFriendlyUnitExceptSelf(unit, x, y + tileSize) != null
				|| unitGetter.getFriendlyUnitExceptSelf(unit, x - tileSize, y) != null
				|| unitGetter.getFriendlyUnitExceptSelf(unit, x, y - tileSize) != null;
	}
}