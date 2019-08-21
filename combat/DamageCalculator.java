package combat;

import hero.Hero;
import hero.HeroHandler;
import map.area.TerrainType;
import unitUtils.UnitType;
import units.Unit;

public class DamageCalculator {
	private int[][][] damageMatrix;
	private WeaponIndexChooser weaponIndexChooser;
	private AttackValueCalculator attackValueCalculator;
	private DefenceValueCalculator defenceValueCalculator;
	
	public DamageCalculator() {
		damageMatrix = new DamageMatrixFactory().getDamageMatrix();
		weaponIndexChooser = new WeaponIndexChooser();
		attackValueCalculator = new AttackValueCalculator();
		defenceValueCalculator = new DefenceValueCalculator();
	}

	public int getBaseDamageValue(int attType, int defType, int gunNumber) {
		return damageMatrix[attType][defType][gunNumber];
	}

	public int calculateNonRNGDamage(HeroHandler heroHandler, Unit attackingUnit, Unit defendingUnit, TerrainType defTerrainType) {
		Hero attackingHero = heroHandler.getHeroFromUnit(attackingUnit);
		Hero defendingHero = heroHandler.getHeroFromUnit(defendingUnit); 
		int attackingUnitIndex = UnitType.getTypeFromUnit(attackingUnit);
		int defendingUnitIndex = UnitType.getTypeFromUnit(defendingUnit);
		int weaponIndex = weaponIndexChooser.getWeaponIndex(attackingUnit, defendingUnit); // 0 or 1
		int baseDamage = damageMatrix[attackingUnitIndex][defendingUnitIndex][weaponIndex];

		int heroAttackValue = attackValueCalculator.calculateAttackValue(attackingHero, attackingUnitIndex);
		int heroDefenceValue = defenceValueCalculator.calculateDefenceValue(defendingHero, defendingUnitIndex);
		int areaDefenceValue = defenceValueCalculator.getTerrainDefenceValue(defendingUnit.getMovementType(), defTerrainType);
		int attackingAffect = attackingUnit.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defendingUnit.getUnitHealth().getHP() / 10)) / 10;

		int damageValue = attackingAffect * defendingAffect / 10;
		return damageValue;
	}
	
	public int calculateRNGDamage(HeroHandler heroHandler, Unit attackingUnit, Unit defendingUnit, TerrainType defTerrainType) {
		Hero attackingHero = heroHandler.getHeroFromUnit(attackingUnit);
		Hero defendingHero = heroHandler.getHeroFromUnit(defendingUnit); 
		int attackingUnitIndex = UnitType.getTypeFromUnit(attackingUnit);
		int defendingUnitIndex = UnitType.getTypeFromUnit(defendingUnit);

		int weaponIndex = weaponIndexChooser.getWeaponIndex(attackingUnit, defendingUnit); // 0 or 1

		if (weaponIndex == 0) {
			attackingUnit.getUnitSupply().useAmmo();
		}

		int baseDamage = getBaseDamageValue(attackingUnitIndex, defendingUnitIndex, weaponIndex);
		int heroAttackValue = attackValueCalculator.calculateAttackValue(attackingHero, attackingUnitIndex);
		int rngNumber = ((int)(Math.random()*10)) % 10;
		int heroDefenceValue = defenceValueCalculator.calculateDefenceValue(defendingHero, defendingUnitIndex);
		int areaDefenceValue = defenceValueCalculator.getTerrainDefenceValue(defendingUnit.getMovementType(), defTerrainType);
		
		int attackingAffect = attackingUnit.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100 + rngNumber) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defendingUnit.getUnitHealth().getHP() / 10)) / 10;
		int damageValue = attackingAffect * defendingAffect / 10;
		
		return damageValue;
	}
	
	public int calculateStructureDamage(Unit attackingUnit, Hero attackingHero) {
		int attackingUnitIndex = UnitType.getTypeFromUnit(attackingUnit);
		int weaponIndex = weaponIndexChooser.getWeaponIndexAgainstStructure(attackingUnit); // 0 or 1
		int baseDamage = damageMatrix[attackingUnitIndex][UnitType.NEOTANK.unitIndex()][weaponIndex];
		int heroAttackValue = attackValueCalculator.calculateAttackValue(attackingHero, attackingUnitIndex);
		int attackingAffect = attackingUnit.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100) / 10;

		return attackingAffect;
	}
}