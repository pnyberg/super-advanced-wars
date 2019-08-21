package combat;

import units.*;
import units.airMoving.TCopter;
import units.seaMoving.Lander;
import units.treadMoving.APC;
import hero.*;
import main.StarPowerCalculator;
import map.GameMap;
import map.area.TerrainType;
import map.structures.Structure;
import unitUtils.UnitType;
import unitUtils.UnitWorthCalculator;

public class DamageHandler {
	private DamageCalculator damageCalculator;
	private StarPowerCalculator starPowerCalculator;
	private HeroHandler heroHandler;
	private GameMap gameMap;

	// TODO: rewrite code to make it fewer parameters
	public DamageHandler(HeroHandler heroHandler, GameMap gameMap) {
		damageCalculator = new DamageCalculator();
		starPowerCalculator = new StarPowerCalculator();
		this.heroHandler = heroHandler;
		this.gameMap = gameMap;
	}

	public void handleAttackingUnit(Unit attackingUnit, Unit defendingUnit) {
		// deal damage from attacker to defender
		performDamageCalculation(attackingUnit, defendingUnit);
		if (canCounterAttack(attackingUnit, defendingUnit)) {
			// deal counterattack-damage from defender to attacker (counterattack)
			performDamageCalculation(defendingUnit, attackingUnit);
		}
	}

	private void performDamageCalculation(Unit attackingUnit, Unit defendingUnit) {
		TerrainType defendingTerrainType = gameMap.getTerrainTypeAtUnitsPosition(defendingUnit);
		int damageValue = damageCalculator.calculateRNGDamage(heroHandler, attackingUnit, defendingUnit, defendingTerrainType);
		defendingUnit.getUnitHealth().takeDamage(damageValue);
		Hero attackingHero = heroHandler.getHeroFromUnit(attackingUnit);
		Hero defendingHero = heroHandler.getHeroFromUnit(defendingUnit); 
		starPowerCalculator.calculateStarPowerOpponent(attackingHero, defendingUnit, damageValue);
		starPowerCalculator.calculateStarPowerSelf(defendingHero, defendingUnit, damageValue);
	}
	
	private boolean canCounterAttack(Unit attacking, Unit defending) {
		return defending.getUnitHealth().getHP() > 0 && 
				!(attacking instanceof IndirectUnit
					|| defending instanceof IndirectUnit
					|| defending instanceof APC
					|| defending instanceof Lander
					|| defending instanceof TCopter);
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
		TerrainType defendingTerrainType = gameMap.getTerrainTypeAtUnitsPosition(defendingUnit);
		return damageCalculator.calculateNonRNGDamage(heroHandler, attackingUnit, defendingUnit, defendingTerrainType);
	}

	// TODO: rename method?
	public boolean validTarget(Unit attackingUnit, Unit targetUnit) {
		int attUnitType = UnitType.getTypeFromUnit(attackingUnit);
		int targetUnitType = UnitType.getTypeFromUnit(targetUnit);
		boolean canFireMainWeapon = attackingUnit.getUnitSupply().hasAmmo() && getBaseDamageValue(attUnitType, targetUnitType, 0) > -1;
		boolean canFireSecondaryWeapon = getBaseDamageValue(attUnitType, targetUnitType, 1) > -1;
		return canFireMainWeapon || canFireSecondaryWeapon;
	}

	public int getBaseDamageValue(int attType, int defType, int gunNumber) {
		return damageCalculator.getBaseDamageValue(attType, defType, gunNumber);
	}
	
	public int getStructureDamage(Unit attackingUnit, Hero attackingHero) {
		return damageCalculator.calculateStructureDamage(attackingUnit, attackingHero);
	}
}