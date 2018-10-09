package combat;

import hero.Hero;
import hero.heroAbilities.AttackIncrease;
import hero.heroAbilities.CommanderEffect;

public class AttackValueCalculator {

	public int calculateAttackValue(Hero hero, int unitIndex) { 
		int normalAttackValue = hero.getPassiveHeroAbilities().getAttackValue(unitIndex);
		int powerAttackValue = calculatePowerAttackValue(hero, unitIndex);
		int superPowerAttackValue = calculateSuperPowerAttackValue(hero, unitIndex);
		
		return normalAttackValue + powerAttackValue + superPowerAttackValue;
	}
	
	private int calculatePowerAttackValue(Hero hero, int unitIndex) {
		int attackValue = 0;
		if (hero.isPowerActive()) {
			for (CommanderEffect commanderEffect : hero.getHeroPower().getPowerEffects()) {
				if (commanderEffect instanceof AttackIncrease) {
					attackValue += ((AttackIncrease)commanderEffect).getAttackIncrease(unitIndex);
				}
			}
		}
		return attackValue;
	}

	private int calculateSuperPowerAttackValue(Hero hero, int unitIndex) {
		int attackValue = 0;
		if (hero.isSuperPowerActive()) {
			for (CommanderEffect commanderEffect : hero.getHeroPower().getSuperPowerEffects()) {
				if (commanderEffect instanceof AttackIncrease) {
					attackValue += ((AttackIncrease)commanderEffect).getAttackIncrease(unitIndex);
				}
			}
		}
		return attackValue;
	}
}