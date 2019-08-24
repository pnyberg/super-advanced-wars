package gameObjects;

import map.buildings.Building;
import map.structures.FiringStructure;
import units.Unit;

public class ChosenObject {
	public Unit chosenUnit;
	public Unit rangeUnit;
	public Building selectedBuilding;
	public FiringStructure rangeStructure;

	public boolean rangeShooterChosen() {
		return (rangeUnit != null || rangeStructure != null);
	}
}