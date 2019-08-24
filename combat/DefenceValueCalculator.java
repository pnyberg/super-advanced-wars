package combat;

import hero.Hero;
import hero.heroAbilities.CommanderEffect;
import hero.heroAbilities.DefenceIncrease;
import map.area.TerrainType;
import unitUtils.MovementType;

public class DefenceValueCalculator {
	private int powerDefenceBoost;

	public DefenceValueCalculator() {
		powerDefenceBoost = 10;
	}
	
	public int calculateDefenceValue(Hero defendingHero, int defendingUnitIndex) {
		int normalDefenceValue = defendingHero.getStandardDefenceValue(defendingUnitIndex);
		if (defendingHero.isPowerActive()) {
			int powerDefenceValue = calculatePowerDefenceValue(defendingHero, defendingUnitIndex);
			return normalDefenceValue + powerDefenceValue;
		} else if (defendingHero.isSuperPowerActive()) {
			int superPowerDefenceValue = calculateSuperPowerDefenceValue(defendingHero, defendingUnitIndex);
			return normalDefenceValue + superPowerDefenceValue;
		}
		return normalDefenceValue;
	}
	
	private int calculatePowerDefenceValue(Hero defendingHero, int unitIndex) {
		int defenceValue = powerDefenceBoost;
		for (CommanderEffect commanderEffect : defendingHero.getHeroPower().getPowerEffects()) {
			if (commanderEffect instanceof DefenceIncrease) {
				defenceValue += ((DefenceIncrease)commanderEffect).getDefenceIncrease(unitIndex);
			}
		}
		return defenceValue;
	}

	private int calculateSuperPowerDefenceValue(Hero defendingHero, int unitIndex) {
		int defenceValue = powerDefenceBoost;
		for (CommanderEffect commanderEffect : defendingHero.getHeroPower().getSuperPowerEffects()) {
			if (commanderEffect instanceof DefenceIncrease) {
				defenceValue += ((DefenceIncrease)commanderEffect).getDefenceIncrease(unitIndex);
			}
		}
		return defenceValue;
	}

	public int getTerrainDefenceValue(MovementType defendingMovementType, TerrainType terrainType) {
		if (defendingMovementType == MovementType.AIR) {
			return 0;
		}
		return terrainType.defenceValue();
	}
}