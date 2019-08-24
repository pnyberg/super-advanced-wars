package combat;

import units.*;
import hero.*;
import main.StarPowerCalculator;
import map.GameMap;
import map.structures.Structure;
import unitUtils.AttackType;
import unitUtils.UnitType;

public class DamageHandler {
	private DamageCalculator damageCalculator;
	private StarPowerCalculator starPowerCalculator;
	private HeroHandler heroHandler;

	public DamageHandler(HeroHandler heroHandler, GameMap gameMap) {
		damageCalculator = new DamageCalculator(heroHandler, gameMap);
		starPowerCalculator = new StarPowerCalculator();
		this.heroHandler = heroHandler;
	}

	public void handleAttackingUnit(Unit attackingUnit, Unit defendingUnit) {
		// deal damage from attacker to defender
		performDamageCalculations(attackingUnit, defendingUnit);
		if (canCounterAttack(attackingUnit, defendingUnit)) {
			// deal counter-attack-damage from defender to attacker (counter-attack)
			performDamageCalculations(defendingUnit, attackingUnit);
		}
	}

	private void performDamageCalculations(Unit attackingUnit, Unit defendingUnit) {
		int damageValue = damageCalculator.calculateRNGDamage(attackingUnit, defendingUnit);

		defendingUnit.getUnitHealth().takeDamage(damageValue);
		Hero attackingHero = heroHandler.getHeroFromUnit(attackingUnit);
		Hero defendingHero = heroHandler.getHeroFromUnit(defendingUnit);
		starPowerCalculator.calculateStarPowerOpponent(attackingHero, defendingUnit, damageValue);
		starPowerCalculator.calculateStarPowerSelf(defendingHero, defendingUnit, damageValue);
	}
	
	private boolean canCounterAttack(Unit attackingUnit, Unit defendingUnit) {
		if (defendingUnit.getUnitHealth().getHP() == 0) {
			return false;
		}
		if (attackingUnit instanceof IndirectUnit) {
			return false;
		}
		return defendingUnit.getAttackType() == AttackType.DIRECT_ATTACK;
	}
	
	/*
	 * "Pipe Seams have 99 hit points and the same defensive value as a 100/100 neotank on 
	 *   0 star terrain. Damage dealt to them is not impacted by luck, though normal CO 
	 *   attack bonuses and towers will impact damage output."
	 */
	public void handleAttackingStructure(Unit attackingUnit, Structure targetStructure) {
		Hero attackingHero = heroHandler.getCurrentHero();
		int damage = damageCalculator.calculateStructureDamage(attackingUnit, attackingHero);
		targetStructure.takeDamage(damage);
	}
	
	public int getNonRNGDamageValue(Unit attackingUnit, Unit defendingUnit) {
		return damageCalculator.calculateNonRNGDamage(attackingUnit, defendingUnit);
	}

	public boolean unitCanAttackTargetUnit(Unit attackingUnit, Unit targetUnit) {
		int attackingUnitType = UnitType.getTypeFromUnit(attackingUnit);
		int targetUnitType = UnitType.getTypeFromUnit(targetUnit);
		boolean canFireMainWeapon = canFireMainWeapon(attackingUnit, targetUnit);
		boolean canFireSecondaryWeapon = getBaseDamageValue(attackingUnitType, targetUnitType, 1) > -1;
		return canFireMainWeapon || canFireSecondaryWeapon;
	}
	
	private boolean canFireMainWeapon(Unit attackingUnit, Unit targetUnit) {
		int attackingUnitType = UnitType.getTypeFromUnit(attackingUnit);
		int targetUnitType = UnitType.getTypeFromUnit(targetUnit);
		if (!attackingUnit.getUnitSupply().hasAmmo()) {
			return false;
		}
		return getBaseDamageValue(attackingUnitType, targetUnitType, 0) > -1;
	}

	public int getBaseDamageValue(int attType, int defType, int gunNumber) {
		return damageCalculator.getBaseDamageValue(attType, defType, gunNumber);
	}
	
	public int getStructureDamage(Unit attackingUnit, Hero attackingHero) {
		return damageCalculator.calculateStructureDamage(attackingUnit, attackingHero);
	}
}