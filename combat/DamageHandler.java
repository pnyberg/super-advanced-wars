package combat;

import units.*;
import units.airMoving.TCopter;
import units.seaMoving.Lander;
import units.treadMoving.APC;
import hero.*;
import main.HeroHandler;
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
	private int tileSize;

	public DamageHandler(HeroHandler heroHandler, GameMap gameMap, AttackValueCalculator attackValueCalculator, DefenceValueCalculator defenceValueCalculator, UnitWorthCalculator unitWorthCalculator, int tileSize) {
		damageCalculator = new DamageCalculator(attackValueCalculator, defenceValueCalculator);
		starPowerCalculator = new StarPowerCalculator(unitWorthCalculator);
		this.heroHandler = heroHandler;
		this.gameMap = gameMap;
		this.tileSize = tileSize;
	}

	public void handleAttackingUnit(Unit attacking, Unit defending) {
		Hero attackingHero = heroHandler.getHeroFromUnit(attacking);
		Hero defendingHero = heroHandler.getHeroFromUnit(defending); 
		int attackTileX = attacking.getPoint().getX() / tileSize;
		int attackTileY = attacking.getPoint().getY() / tileSize;
		int defendTileX = defending.getPoint().getX() / tileSize;
		int defendTileY = defending.getPoint().getY() / tileSize;
		
		TerrainType defendingTerrainType = gameMap.getMap()[defendTileX][defendTileY].getTerrainType();

		// deal damage from attacker to defender
		performDamageCalculation(attacking, attackingHero, defending, defendingHero, defendingTerrainType);

		if (defending.getUnitHealth().getHP() > 0 && counterAttackable(attacking, defending)) {
			TerrainType attackingTerrainType = gameMap.getMap()[attackTileX][attackTileY].getTerrainType();
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
	
	private boolean counterAttackable(Unit attacking, Unit defending) {
		return !(attacking instanceof IndirectUnit
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
	
	public int getStructureDamage(Unit attackingUnit, Hero attackingHero) {
		return damageCalculator.calculateStructureDamage(attackingUnit, attackingHero);
	}
}