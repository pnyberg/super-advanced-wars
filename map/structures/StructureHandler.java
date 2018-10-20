package map.structures;

import java.util.ArrayList;
import java.util.List;

import combat.StructureAttackHandler;
import units.Unit;
import units.UnitWorthCalculator;
import units.airMoving.BCopter;
import units.airMoving.Bomber;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Battleship;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.Artillery;
import units.treadMoving.MDTank;
import units.treadMoving.Neotank;
import units.treadMoving.Tank;

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
			if (moneyValue > highestMoneyValue || (moneyValue == highestMoneyValue && Math.random() > 0.5)) {
				targetUnit = possibleTarget;
				highestMoneyValue = moneyValue;
			}
		}
		if (targetUnit != null) {
			targetUnit.getUnitHealth().takeNonLethalDamage(miniCannon.getDamage());
		}
	}
	
	public void removeStructure(Structure structure) {
		structures.remove(structure);
	}
	
	public boolean unitCanAttackStructure(Unit unit, Structure structure) {
		return unit instanceof Infantry
			|| unit instanceof Mech
			|| unit instanceof Recon
			|| unit instanceof Tank
			|| unit instanceof MDTank
			|| unit instanceof Neotank
			|| unit instanceof Artillery
			|| unit instanceof Rocket
			|| unit instanceof AAir
			|| unit instanceof Bomber
			|| unit instanceof BCopter
			|| unit instanceof Battleship;
	}
	
	public Structure getStructure(int x, int y) {
		for (Structure structure : structures) {
			if(structure.getPoint().getX() == x && structure.getPoint().getY() == y) {
				return structure;
			}
		}
		return null;
	}

	public FiringStructure getFiringStructure(int x, int y) {
		for (Structure structure : structures) {
			if (structure instanceof FiringStructure && structure.getPoint().getX() == x && structure.getPoint().getY() == y) {
				return (FiringStructure)structure;
			}
		}

		return null;
	}
}