/**
 * TODO:
 *  - rewrite code to be more readable
 */
package combat;

import hero.Hero;
import hero.heroAbilities.AttackIncrease;
import hero.heroAbilities.CommanderEffect;
import hero.heroAbilities.DefenceIncrease;
import map.area.TerrainType;
import unitUtils.MovementType;

public class DefenceValueCalculator {

	public DefenceValueCalculator() {}
	
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
		return terrainType.defenceValue();
	}
}