package combat;

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
import hero.*;
import main.HeroHandler;
import main.StarPowerCalculator;
import map.area.Area;
import map.area.TerrainType;

public class DamageHandler {
	private DamageCalculator damageCalculator;
	private StarPowerCalculator starPowerCalculator;
	private HeroHandler heroHandler;
	private Area[][] map;
	private int tileSize = 40;

	public DamageHandler(HeroHandler heroHandler, Area[][] map, AttackValueCalculator attackValueCalculator, DefenceValueCalculator defenceValueCalculator, UnitWorthCalculator unitWorthCalculator) {
		damageCalculator = new DamageCalculator(attackValueCalculator, defenceValueCalculator);
		starPowerCalculator = new StarPowerCalculator(unitWorthCalculator);
		this.heroHandler = heroHandler;
		this.map = map;
	}

	public void handleAttack(Unit attacking, Unit defending) {
		Hero attackingHero = heroHandler.getHeroFromUnit(attacking);
		Hero defendingHero = heroHandler.getHeroFromUnit(defending); 
		int attX = attacking.getPoint().getX() / tileSize;
		int attY = attacking.getPoint().getY() / tileSize;
		int defX = defending.getPoint().getX() / tileSize;
		int defY = defending.getPoint().getY() / tileSize;
		
		TerrainType defendingTerrainType = map[defX][defY].getTerrainType();

		// deal damage from attacker to defender
		performDamageCalculation(attacking, attackingHero, defending, defendingHero, defendingTerrainType);

		if (defending.getUnitHealth().getHP() <= 0) {
			return;
		}

		if (counterAttackAble(attacking, defending)) {
			TerrainType attackingTerrainType = map[attX][attY].getTerrainType();
			// deal damage from defender to attacker (counterattack)
			performDamageCalculation(defending, defendingHero, attacking, attackingHero, attackingTerrainType);
		}
	}

	private void performDamageCalculation(Unit attacker, Hero attHero, Unit defender, Hero defHero, TerrainType defTerrainType) {
		int damageValue = damageCalculator.calculateRNGDamage(attacker, attHero, defender, defHero, defTerrainType);
		defender.getUnitHealth().takeDamage(damageValue);

		starPowerCalculator.calculateStarPowerOpponent(attHero, defender, damageValue);
		starPowerCalculator.calculateStarPowerSelf(defHero, defender, damageValue);
	}
	
	private boolean counterAttackAble(Unit attacking, Unit defending) {
		return !(attacking instanceof IndirectUnit
				|| defending instanceof IndirectUnit
				|| defending instanceof APC
				|| defending instanceof Lander
				|| defending instanceof TCopter);
	}
	
	public int getNonRNGDamageValue(Unit attacker, Hero attHero, Unit defender, Hero defHero, TerrainType defTerrainType) {
		return damageCalculator.calculateNonRNGDamage(attacker, attHero, defender, defHero, defTerrainType);
	}

	public boolean validTarget(Unit attackingUnit, Unit targetUnit) {
		int attUnitType = UnitType.getTypeFromUnit(attackingUnit);
		int targetUnitType = UnitType.getTypeFromUnit(targetUnit);

		return (attackingUnit.getUnitSupply().hasAmmo() && getBaseDamageValue(attUnitType, targetUnitType, 0) > -1)
			|| getBaseDamageValue(attUnitType, targetUnitType, 1) > -1;
	}

	public int getBaseDamageValue(int attType, int defType, int gunNumber) {
		return damageCalculator.getBaseDamageValue(attType, defType, gunNumber);
	}
}