package handlers;

import area.TerrainType;
import heroes.Hero;
import units.MovementType;
import units.Unit;
import units.UnitType;

public class DamageCalculator {
	private int[][][] damageMatrix;
	private WeaponIndexChooser weaponIndexChooser;
	private DefenceValueCalculator defenceValueCalculator;
	
	public DamageCalculator() {
		damageMatrix = new DamageMatrixFactory().getDamageMatrix();
		weaponIndexChooser = new WeaponIndexChooser();
		defenceValueCalculator = new DefenceValueCalculator();
	}

	public int getBaseDamageValue(int attType, int defType, int gunNumber) {
		System.out.println(attType + " " + defType + " " + gunNumber);
		return damageMatrix[attType][defType][gunNumber];
	}

	public int calculateNonRNGDamage(Unit attacker, Hero attHero, Unit defender, Hero defHero, TerrainType defTerrainType) {
		int attType = UnitType.getTypeFromUnit(attacker);
		int defType = UnitType.getTypeFromUnit(defender);
		int weaponIndex = weaponIndexChooser.getWeaponIndex(attacker, defender); // 0 or 1
		int baseDamage = damageMatrix[attType][defType][weaponIndex];
		int heroAttackValue = attHero.getAttackDefenceObject().getAttackValue(attType);
		int heroDefenceValue = defHero.getAttackDefenceObject().getDefenceValue(defType);
		int areaDefenceValue = (defender.getMovementType() == MovementType.AIR ? 0 : defenceValueCalculator.getDefenceValue(defTerrainType));
		int attackingAffect = attacker.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defender.getUnitHealth().getHP() / 10)) / 10;

		int damageValue = attackingAffect * defendingAffect / 10;
		return damageValue;
	}
	
	public int calculateRNGDamage(Unit attacker, Hero attHero, Unit defender, Hero defHero, TerrainType defTerrainType) {
		int attType = UnitType.getTypeFromUnit(attacker);
		int defType = UnitType.getTypeFromUnit(defender);

		int weaponIndex = weaponIndexChooser.getWeaponIndex(attacker, defender); // 0 or 1

		if (weaponIndex == 0) {
			attacker.getUnitSupply().useAmmo();
		}

		int baseDamage = getBaseDamageValue(attType, defType, weaponIndex);
		int heroAttackValue = attHero.getAttackDefenceObject().getAttackValue(attType);
		int rngNumber = ((int)(Math.random()*10)) % 10;
		int heroDefenceValue = defHero.getAttackDefenceObject().getDefenceValue(defType);
		int areaDefenceValue = (defender.getMovementType() == MovementType.AIR ? 0 : defenceValueCalculator.getDefenceValue(defTerrainType));
		
		int attackingAffect = attacker.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100 + rngNumber) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defender.getUnitHealth().getHP() / 10)) / 10;
		int damageValue = attackingAffect * defendingAffect / 10;
		
		return damageValue;
	}
}