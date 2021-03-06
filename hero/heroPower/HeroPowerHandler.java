/**
 * TODO:
 *  - implement hero power-heal to teammates 
 *  - implement hero power-damage to teammates 
 */
package hero.heroPower;

import hero.HeroHandler;
import hero.heroAbilities.CommanderEffect;
import hero.heroAbilities.DamageAll;
import hero.heroAbilities.HealAll;
import units.Unit;

public class HeroPowerHandler {
	private HeroHandler heroHandler;
	
	public HeroPowerHandler(HeroHandler heroHandler) {
		this.heroHandler = heroHandler;
	}

	public void activatePower() {
		heroHandler.getCurrentHero().setPowerActive(true);
		for(CommanderEffect commanderEffect : heroHandler.getCurrentHero().getHeroPower().getPowerEffects()) {
			if (commanderEffect instanceof DamageAll) {
				handleDamageAll((DamageAll)commanderEffect);
			} else if (commanderEffect instanceof HealAll) {
				handleHealAll((HealAll)commanderEffect);
			}
		}
	}

	public void activateSuperPower() {
		heroHandler.getCurrentHero().setSuperPowerActive(true);
		for(CommanderEffect commanderEffect : heroHandler.getCurrentHero().getHeroPower().getSuperPowerEffects()) {
			if (commanderEffect instanceof DamageAll) {
				handleDamageAll((DamageAll)commanderEffect);
			} else if (commanderEffect instanceof HealAll) {
				handleHealAll((HealAll)commanderEffect);
			}
		}
	}
	
	private void handleDamageAll(DamageAll damageAll) {
		int friendlyDamage = damageAll.getDamageFriendlyUnits() * 10;
		if (friendlyDamage > 0) {
			int troopSize = heroHandler.getCurrentHero().getTroopHandler().getTroopSize();
			for (int unitIndex = 0 ; unitIndex < troopSize ; unitIndex++) {
				Unit friendlyUnit = heroHandler.getUnitFromCurrentHero(unitIndex);
				friendlyUnit.getUnitHealth().takeNonLethalDamage(friendlyDamage);
			}
		}
		// TODO: add damage-to-teammates
		int enemyDamage = damageAll.getDamageEnemyUnits() * 10;
		if (enemyDamage > 0) {
			for (int heroIndex = 0 ; heroIndex < heroHandler.getNumberOfHeroes() ; heroIndex++) {
				if (heroHandler.getHero(heroIndex) != heroHandler.getCurrentHero()) {
					dealDamageToHerosUnits(heroIndex, enemyDamage);
				}
			}
		}
	}
	
	private void dealDamageToHerosUnits(int heroIndex, int damage) {
		int troopSize = heroHandler.getTroopSize(heroIndex);
		for (int unitIndex = 0 ; unitIndex < troopSize ; unitIndex++) {
			Unit enemyUnit = heroHandler.getUnitFromHero(heroIndex, unitIndex);
			enemyUnit.getUnitHealth().takeNonLethalDamage(damage);
		}
	}
	
	private void handleHealAll(HealAll healAll) {
		int friendlyHeal = healAll.getHealFriendlyUnits() * 10;
		if (friendlyHeal > 0) {
			int troopSize = heroHandler.getCurrentHero().getTroopHandler().getTroopSize();
			for (int unitIndex = 0 ; unitIndex < troopSize ; unitIndex++) {
				Unit friendlyUnit = heroHandler.getUnitFromCurrentHero(unitIndex);
				friendlyUnit.getUnitHealth().heal(friendlyHeal);
			}
		}
		// TODO: add heal-teammates
		int enemyHeal = healAll.getHealEnemyUnits();
		if (enemyHeal > 0) {
			for (int heroIndex = 0 ; heroIndex < heroHandler.getNumberOfHeroes() ; heroIndex++) {
				if (heroHandler.getHero(heroIndex) != heroHandler.getCurrentHero()) {
					int troopSize = heroHandler.getTroopSize(heroIndex);
					for (int unitIndex = 0 ; unitIndex < troopSize ; unitIndex++) {
						Unit enemyUnit = heroHandler.getUnitFromHero(heroIndex, unitIndex);
						enemyUnit.getUnitHealth().heal(enemyHeal);
					}
				}
			}
		}
	}
}