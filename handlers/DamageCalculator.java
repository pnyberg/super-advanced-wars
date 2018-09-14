package handlers;

import heroes.Hero;
import units.Unit;
import units.UnitTypes;

public class DamageCalculator {
	private static int[][][] damageMatrix;
	private static WeaponIndexChooser weaponIndexChooser;
	
	public DamageCalculator() {
		damageMatrix = new DamageMatrixFactory().getDamageMatrix();
		weaponIndexChooser = new WeaponIndexChooser();
	}

	public int getBaseDamageValue(int attType, int defType, int gunNumber) {
		return damageMatrix[attType][defType][gunNumber];
	}

	public int calculateNonRNGDamage(Unit attacker, Hero attHero, Unit defender, Hero defHero, int defTerrainType) {
		int attType = UnitTypes.getTypeFromUnit(attacker);
		int defType = UnitTypes.getTypeFromUnit(defender);
		int weaponIndex = weaponIndexChooser.getWeaponIndex(attacker, defender); // 0 or 1
		int baseDamage = damageMatrix[attType][defType][weaponIndex];
		int heroAttackValue = attHero.getAttackDefenceObject().getAttackValue(attType);
		int heroDefenceValue = defHero.getAttackDefenceObject().getDefenceValue(defType);
		int areaDefenceValue = (defender.getMovementType() == Unit.AIR ? 0 : MapHandler.getDefenceValue(defTerrainType));
		int attackingAffect = attacker.getHP() / 10 * ((baseDamage * heroAttackValue) / 100) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defender.getHP() / 10)) / 10;

		int damageValue = attackingAffect * defendingAffect / 10;
		return damageValue;
	}
	
	public int calculateRNGDamage(Unit attacker, Hero attHero, Unit defender, Hero defHero, int defTerrainType) {
		int attType = UnitTypes.getTypeFromUnit(attacker);
		int defType = UnitTypes.getTypeFromUnit(defender);

		int weaponIndex = weaponIndexChooser.getWeaponIndex(attacker, defender); // 0 or 1

		if (weaponIndex == 0) {
			attacker.useAmmo();
		}

		int baseDamage = getBaseDamageValue(attType, defType, weaponIndex);
		int heroAttackValue = attHero.getAttackDefenceObject().getAttackValue(attType);
		int rngNumber = ((int)(Math.random()*10)) % 10;
		int heroDefenceValue = defHero.getAttackDefenceObject().getDefenceValue(defType);
		int areaDefenceValue = (defender.getMovementType() == Unit.AIR ? 0 : MapHandler.getDefenceValue(defTerrainType));
		
		int attackingAffect = attacker.getHP() / 10 * ((baseDamage * heroAttackValue) / 100 + rngNumber) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defender.getHP() / 10)) / 10;
		int damageValue = attackingAffect * defendingAffect / 10;
		
		return damageValue;
	}
}
