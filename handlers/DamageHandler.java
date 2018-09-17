package handlers;

import units.*;
import units.airMoving.BCopter;
import units.airMoving.Bomber;
import units.airMoving.Fighter;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Battleship;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.tireMoving.Missiles;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.APC;
import units.treadMoving.Artillery;
import units.treadMoving.MDTank;
import units.treadMoving.Neotank;
import units.treadMoving.Tank;
import area.TerrainType;
import heroes.*;

public class DamageHandler {
	private DamageCalculator damageCalculator;
	private MapHandler mapHandler;
	private StarPowerCalculator starPowerCalculator;

	public DamageHandler(MapHandler mapHandler) {
		damageCalculator = new DamageCalculator(mapHandler);
		this.mapHandler = mapHandler;
		starPowerCalculator = new StarPowerCalculator();
	}

	public void handleAttack(Unit attacking, Unit defending) {
		HeroPortrait portrait = mapHandler.getHeroPortrait();
		Hero attackingHero = portrait.getHeroFromUnit(attacking);
		Hero defendingHero = portrait.getHeroFromUnit(defending); 
		int attX = attacking.getX();
		int attY = attacking.getY();
		int defX = defending.getX();
		int defY = defending.getY();

		TerrainType defendingTerrainType = mapHandler.map(defX, defY);

		// deal damage from attacker to defender
		performDamageCalculation(attacking, attackingHero, defending, defendingHero, defendingTerrainType);

		if (defending.getHP() <= 0) {
			return;
		}

		if (counterAttackAble(attacking, defending)) {
			TerrainType attackingTerrainType = mapHandler.map(attX, attY);
			// deal damage from defender to attacker (counterattack)
			performDamageCalculation(defending, defendingHero, attacking, attackingHero, attackingTerrainType);
		}
	}

	private void performDamageCalculation(Unit attacker, Hero attHero, Unit defender, Hero defHero, TerrainType defTerrainType) {
		int damageValue = damageCalculator.calculateRNGDamage(attacker, attHero, defender, defHero, defTerrainType);
		defender.takeDamage(damageValue);

		starPowerCalculator.calculateStarPowerOpponent(attHero, defender, damageValue);
		starPowerCalculator.calculateStarPowerSelf(defHero, defender, damageValue);
	}
	
	private boolean counterAttackAble(Unit attacking, Unit defending) {
		return attacking instanceof IndirectUnit
				|| defending instanceof IndirectUnit
				|| defending instanceof APC
				|| defending instanceof Lander
				|| defending instanceof TCopter;
	}
	
	public int getNonRNGDamageValue(Unit attacker, Hero attHero, Unit defender, Hero defHero, TerrainType defTerrainType) {
		return damageCalculator.calculateNonRNGDamage(attacker, attHero, defender, defHero, defTerrainType);
	}

	public boolean validTarget(Unit attackingUnit, Unit targetUnit) {
		int attUnitType = UnitTypes.getTypeFromUnit(attackingUnit);
		int targetUnitType = UnitTypes.getTypeFromUnit(targetUnit);

		return (attackingUnit.hasAmmo() && getBaseDamageValue(attUnitType, targetUnitType, 0) > -1)
			|| getBaseDamageValue(attUnitType, targetUnitType, 1) > -1;
	}

	public int getBaseDamageValue(int attType, int defType, int gunNumber) {
		return damageCalculator.getBaseDamageValue(attType, defType, gunNumber);
	}
}