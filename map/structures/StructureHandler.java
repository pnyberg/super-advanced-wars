package map.structures;

import java.util.ArrayList;
import java.util.List;

import combat.StructureAttackHandler;
import units.Unit;
import units.UnitWorthCalculator;

public class StructureHandler {
	private ArrayList<Structure> structures;
	private StructureAttackHandler structureAttackHandler;
	private UnitWorthCalculator unitWorthCalculator;	
	
	public StructureHandler(ArrayList<Structure> structures, StructureAttackHandler structureAttackHandler, UnitWorthCalculator unitWorthCalculator) {
		this.structures = structures;
		this.structureAttackHandler = structureAttackHandler;
		this.unitWorthCalculator = unitWorthCalculator;
	}
	
	public void doStructureActions() {
		for (Structure structure : structures) {
			if (structure instanceof MiniCannon) {
				fireMiniCannon((MiniCannon)structure);
			}
		}
	}
	
	private void fireMiniCannon(MiniCannon miniCannon) {
		List<Unit> possibleTargets = structureAttackHandler.getPossibleTargets(miniCannon);
		Unit targetUnit = null;
		double highestMoneyValue = 0;
		for (Unit possibleTarget : possibleTargets) {
			double moneyValue = unitWorthCalculator.getUnitWorth(possibleTarget);
			if (moneyValue > highestMoneyValue) {
				targetUnit = possibleTarget;
			}
		}
		targetUnit.getUnitHealth().takeDamage(miniCannon.getDamage());
	}

	public FiringStructure getFiringStructure(int x, int y) {
		for (Structure structure : structures) {
			if (structure instanceof FiringStructure && structure.getX() == x && structure.getY() == y) {
				return (FiringStructure)structure;
			}
		}

		return null;
	}
}