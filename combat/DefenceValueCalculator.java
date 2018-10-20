package combat;

import hero.Hero;
import hero.heroAbilities.AttackIncrease;
import hero.heroAbilities.CommanderEffect;
import hero.heroAbilities.DefenceIncrease;
import map.area.TerrainType;
import units.MovementType;

public class DefenceValueCalculator {

	public DefenceValueCalculator() {
		
	}
	
	public int calculateDefenceValue(Hero defendingHero, int defendingUnitIndex) {
		int normalDefenceValue = defendingHero.getPassiveHeroAbilities().getDefenceValue(defendingUnitIndex);
		int powerDefenceValue = calculatePowerDefenceValue(defendingHero, defendingUnitIndex);
		int superPowerDefenceValue = calculateSuperPowerDefenceValue(defendingHero, defendingUnitIndex);;
		return normalDefenceValue + powerDefenceValue + superPowerDefenceValue;
	}
	
	private int calculatePowerDefenceValue(Hero hero, int unitIndex) {
		int defenceValue = 0;
		if (hero.isPowerActive()) {
			defenceValue = 10;
			for (CommanderEffect commanderEffect : hero.getHeroPower().getPowerEffects()) {
				if (commanderEffect instanceof DefenceIncrease) {
					defenceValue += ((DefenceIncrease)commanderEffect).getDefenceIncrease(unitIndex);
				}
			}
			
		}
		return defenceValue;
	}

	private int calculateSuperPowerDefenceValue(Hero hero, int unitIndex) {
		int defenceValue = 0;
		if (hero.isSuperPowerActive()) {
			defenceValue = 10;
			for (CommanderEffect commanderEffect : hero.getHeroPower().getSuperPowerEffects()) {
				if (commanderEffect instanceof DefenceIncrease) {
					defenceValue += ((DefenceIncrease)commanderEffect).getDefenceIncrease(unitIndex);
				}
			}
		}
		return defenceValue;
	}

	public int getTerrainDefenceValue(MovementType defendingMovementType, TerrainType terrainType) {
		if (defendingMovementType == MovementType.AIR) {
			return 0;
		}
		if (terrainType == TerrainType.ROAD ||
			terrainType == TerrainType.SEA ||
			terrainType == TerrainType.SHOAL) {
			return 0;
		} else if (terrainType == TerrainType.PLAIN ||
					terrainType == TerrainType.REEF) {
			return 1;
		} else if (terrainType == TerrainType.WOOD) {
			return 2;
		} else if (terrainType == TerrainType.CITY ||
					terrainType == TerrainType.FACTORY ||
					terrainType == TerrainType.AIRPORT ||
					terrainType == TerrainType.PORT) {
			return 3;
		} else if (terrainType == TerrainType.MOUNTAIN) {
			return 4;
		} 
		return -1;
	}
}