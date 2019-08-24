package combat;

import hero.Hero;
import hero.heroAbilities.AttackIncrease;
import hero.heroAbilities.CommanderEffect;

public class AttackValueCalculator {

	public int calculateAttackValue(Hero attackingHero, int unitIndex) { 
		int normalAttackValue = attackingHero.getStandardAttackValue(unitIndex);
		if (attackingHero.isPowerActive()) {
			int powerAttackValue = calculatePowerAttackValue(attackingHero, unitIndex);
			return normalAttackValue + powerAttackValue;
		} else if (attackingHero.isSuperPowerActive()) {
			int superPowerAttackValue = calculateSuperPowerAttackValue(attackingHero, unitIndex);
			return normalAttackValue + superPowerAttackValue;
		}
		return normalAttackValue;
	}
	
	private int calculatePowerAttackValue(Hero attackingHero, int unitIndex) {
		int attackValue = 0;
		for (CommanderEffect commanderEffect : attackingHero.getHeroPower().getPowerEffects()) {
			if (commanderEffect instanceof AttackIncrease) {
				attackValue += ((AttackIncrease)commanderEffect).getAttackIncrease(unitIndex);
			}
		}
		return attackValue;
	}

	private int calculateSuperPowerAttackValue(Hero attackingHero, int unitIndex) {
		int attackValue = 0;
		for (CommanderEffect commanderEffect : attackingHero.getHeroPower().getSuperPowerEffects()) {
			if (commanderEffect instanceof AttackIncrease) {
				attackValue += ((AttackIncrease)commanderEffect).getAttackIncrease(unitIndex);
			}
		}
		return attackValue;
	}
}