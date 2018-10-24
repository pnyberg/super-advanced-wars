package combat;

import hero.Hero;
import map.area.TerrainType;
import units.Unit;
import units.UnitType;

public class DamageCalculator {
	private int[][][] damageMatrix;
	private WeaponIndexChooser weaponIndexChooser;
	private AttackValueCalculator attackValueCalculator;
	private DefenceValueCalculator defenceValueCalculator;
	
	public DamageCalculator(AttackValueCalculator attackValueCalculator, DefenceValueCalculator defenceValueCalculator) {
		damageMatrix = new DamageMatrixFactory().getDamageMatrix();
		weaponIndexChooser = new WeaponIndexChooser();
		this.attackValueCalculator = attackValueCalculator;
		this.defenceValueCalculator = defenceValueCalculator;
	}

	public int getBaseDamageValue(int attType, int defType, int gunNumber) {
		return damageMatrix[attType][defType][gunNumber];
	}

	public int calculateNonRNGDamage(Unit attacker, Hero attHero, Unit defender, Hero defHero, TerrainType defTerrainType) {
		int attackingUnitIndex = UnitType.getTypeFromUnit(attacker);
		int defendingUnitIndex = UnitType.getTypeFromUnit(defender);
		int weaponIndex = weaponIndexChooser.getWeaponIndex(attacker, defender); // 0 or 1
		int baseDamage = damageMatrix[attackingUnitIndex][defendingUnitIndex][weaponIndex];
		int heroAttackValue = attackValueCalculator.calculateAttackValue(attHero, attackingUnitIndex);
		int heroDefenceValue = defenceValueCalculator.calculateDefenceValue(defHero, defendingUnitIndex);
		int areaDefenceValue = defenceValueCalculator.getTerrainDefenceValue(defender.getMovementType(), defTerrainType);
		int attackingAffect = attacker.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defender.getUnitHealth().getHP() / 10)) / 10;

		int damageValue = attackingAffect * defendingAffect / 10;
		return damageValue;
	}
	
	public int calculateRNGDamage(Unit attacker, Hero attHero, Unit defender, Hero defHero, TerrainType defTerrainType) {
		int attackingUnitIndex = UnitType.getTypeFromUnit(attacker);
		int defendingUnitIndex = UnitType.getTypeFromUnit(defender);

		int weaponIndex = weaponIndexChooser.getWeaponIndex(attacker, defender); // 0 or 1

		if (weaponIndex == 0) {
			attacker.getUnitSupply().useAmmo();
		}

		int baseDamage = getBaseDamageValue(attackingUnitIndex, defendingUnitIndex, weaponIndex);
		int heroAttackValue = attackValueCalculator.calculateAttackValue(attHero, attackingUnitIndex);
		int rngNumber = ((int)(Math.random()*10)) % 10;
		int heroDefenceValue = defenceValueCalculator.calculateDefenceValue(defHero, defendingUnitIndex);
		int areaDefenceValue = defenceValueCalculator.getTerrainDefenceValue(defender.getMovementType(), defTerrainType);
		
		int attackingAffect = attacker.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100 + rngNumber) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defender.getUnitHealth().getHP() / 10)) / 10;
		int damageValue = attackingAffect * defendingAffect / 10;
		
		return damageValue;
	}
	
	public int calculateStructureDamage(Unit attacker, Hero attHero) {
		int attackingUnitIndex = UnitType.getTypeFromUnit(attacker);
		int weaponIndex = weaponIndexChooser.getWeaponIndexAgainstStructure(attacker); // 0 or 1
		int baseDamage = damageMatrix[attackingUnitIndex][UnitType.NEOTANK.unitIndex()][weaponIndex];
		int heroAttackValue = attackValueCalculator.calculateAttackValue(attHero, attackingUnitIndex);
		int attackingAffect = attacker.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100) / 10;

		return attackingAffect;
	}
}