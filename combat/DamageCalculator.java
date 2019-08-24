package combat;

import hero.Hero;
import hero.HeroHandler;
import map.GameMap;
import map.area.TerrainType;
import unitUtils.UnitType;
import units.Unit;

public class DamageCalculator {
	private HeroHandler heroHandler;
	private GameMap gameMap;
	private int[][][] damageMatrix;
	private WeaponIndexChooser weaponIndexChooser;
	private DefenceValueCalculator defenceValueCalculator;
	
	public DamageCalculator(HeroHandler heroHandler, GameMap gameMap) {
		this.heroHandler = heroHandler;
		this.gameMap = gameMap;
		damageMatrix = new DamageMatrixFactory().getDamageMatrix();
		weaponIndexChooser = new WeaponIndexChooser();
		defenceValueCalculator = new DefenceValueCalculator();
	}

	public int calculateNonRNGDamage(Unit attackingUnit, Unit defendingUnit) {
		TerrainType defendingTerrainType = gameMap.getTerrainTypeAtUnitsPosition(defendingUnit);
		int baseDamage = getBaseUnitDamage(attackingUnit, defendingUnit);
		int heroAttackValue = getHeroAttackValue(attackingUnit);
		int attackingHP = attackingUnit.getUnitHealth().getHP();
		
		int attackingAffect = attackingHP / 10 * ((baseDamage * heroAttackValue) / 100) / 10;
		int defendingAffect = getDefendingAffect(defendingUnit, defendingTerrainType);
		int damageValue = attackingAffect * defendingAffect / 10;
		return damageValue;
	}
	
	public int calculateRNGDamage(Unit attackingUnit, Unit defendingUnit) {
		TerrainType defendingTerrainType = gameMap.getTerrainTypeAtUnitsPosition(defendingUnit);
		int weaponIndex = weaponIndexChooser.getWeaponIndex(attackingUnit, defendingUnit); // 0 or 1
		if (weaponIndex == 0) {
			attackingUnit.getUnitSupply().useAmmo();
		}

		int baseDamage = getBaseUnitDamage(attackingUnit, defendingUnit);
		int heroAttackValue = getHeroAttackValue(attackingUnit);
		int attackingHP = attackingUnit.getUnitHealth().getHP();

		int rngNumber = ((int)(Math.random()*10)) % 10;		
		int attackingAffect = attackingHP / 10 * ((baseDamage * heroAttackValue) / 100 + rngNumber) / 10;
		int defendingAffect = getDefendingAffect(defendingUnit, defendingTerrainType);
		int damageValue = attackingAffect * defendingAffect / 10;
		
		return damageValue;
	}
	
	public int calculateStructureDamage(Unit attackingUnit, Hero attackingHero) {
		AttackValueCalculator attackValueCalculator = new AttackValueCalculator();
		int attackingUnitIndex = UnitType.getTypeFromUnit(attackingUnit);
		int baseDamage = getBaseStructureDamage(attackingUnit);
		int heroAttackValue = attackValueCalculator.calculateAttackValue(attackingHero, attackingUnitIndex);
		int attackingAffect = attackingUnit.getUnitHealth().getHP() / 10 * ((baseDamage * heroAttackValue) / 100) / 10;

		return attackingAffect;
	}
	
	public int getBaseDamageValue(int attackingUnitIndex, int defendingUnitIndex, int weaponIndex) {
		return damageMatrix[attackingUnitIndex][defendingUnitIndex][weaponIndex];
	}

	private int getBaseUnitDamage(Unit attackingUnit, Unit defendingUnit) {
		int attackingUnitIndex = UnitType.getTypeFromUnit(attackingUnit);
		int defendingUnitIndex = UnitType.getTypeFromUnit(defendingUnit);
		int weaponIndex = weaponIndexChooser.getWeaponIndex(attackingUnit, defendingUnit); // 0 or 1
		int baseDamage = damageMatrix[attackingUnitIndex][defendingUnitIndex][weaponIndex];
		return baseDamage;
	}
	
	private int getBaseStructureDamage(Unit attackingUnit) {
		int attackingUnitIndex = UnitType.getTypeFromUnit(attackingUnit);
		int weaponIndex = weaponIndexChooser.getWeaponIndexAgainstStructure(attackingUnit); // 0 or 1
		int baseDamage = damageMatrix[attackingUnitIndex][UnitType.NEOTANK.unitIndex()][weaponIndex];
		return baseDamage;
	}
	
	private int getHeroAttackValue(Unit attackingUnit) {
		AttackValueCalculator attackValueCalculator = new AttackValueCalculator();
		Hero attackingHero = heroHandler.getHeroFromUnit(attackingUnit);
		int attackingUnitIndex = UnitType.getTypeFromUnit(attackingUnit);
		int heroAttackValue = attackValueCalculator.calculateAttackValue(attackingHero, attackingUnitIndex);
		return heroAttackValue;
	}
	
	private int getDefendingAffect(Unit defendingUnit, TerrainType defTerrainType) {
		int heroDefenceValue = getHeroDefenceValue(defendingUnit);
		int areaDefenceValue = getAreaDefenceValue(defendingUnit, defTerrainType);
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defendingUnit.getUnitHealth().getHP() / 10)) / 10;
		return defendingAffect;
	}
	
	private int getHeroDefenceValue(Unit defendingUnit) {
		Hero defendingHero = heroHandler.getHeroFromUnit(defendingUnit); 
		int defendingUnitIndex = UnitType.getTypeFromUnit(defendingUnit);
		int heroDefenceValue = defenceValueCalculator.calculateDefenceValue(defendingHero, defendingUnitIndex);
		return heroDefenceValue;
	}
	
	private int getAreaDefenceValue(Unit defUnit, TerrainType defTerrainType) {
		int areaDefVal = defenceValueCalculator.getTerrainDefenceValue(defUnit.getMovementType(), defTerrainType);
		return areaDefVal;
	}
}