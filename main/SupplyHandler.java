package main;

import gameObjects.GameState;
import map.GameMap;
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

	public boolean mayAPCSUpply(int x, int y) {
		return unitGetter.getFriendlyUnit(x + tileSize, y) != null 
				|| unitGetter.getFriendlyUnit(x, y + tileSize) != null
				|| unitGetter.getFriendlyUnit(x - tileSize, y) != null
				|| unitGetter.getFriendlyUnit(x, y - tileSize) != null;
	}
}