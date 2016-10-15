package handlers;

import units.*;
import heroes.*;

public class DamageHandler {
	private static int[][][] damageMatrix;
	private static final int number = Unit.numberOfUnitTypes;

	private static final int
		INFANTRY = 0,
		MECH = 1,
		RECON = 2,
		TANK = 3,
		MDTANK = 4,
//		NEOTANK = 5,
		APC = 5, // 6,
		ARTILLERY = 6, // 7,
		ROCKET = 7, // 8,
//		A_AIR = 9,
//		MISSILES = 10,
//		PLANE = 11,
//		BOMBER = 12,
//		BCOPTER = 13,
//		TCOPTER = 14,
		BSHIP = 8, // 15,
//		CRUISER = 16,
		LANDER = 9; //17,
//		SUB = 18;

	public static void init() {
		damageMatrix = new int[number][number][2]; // the last one is for secondary weapons

		implementDamageMatrix();
	}

	private static void implementDamageMatrix() {
		for (int i = 0 ; i < number ; i++) {
			damageMatrix[INFANTRY][i][0] = -1;
			damageMatrix[MECH][i][0] = -1;
		}

		// infantry
		damageMatrix[INFANTRY][INFANTRY][1] = 55;
		damageMatrix[INFANTRY][MECH][1] = 45;
		damageMatrix[INFANTRY][RECON][1] = 12;
		damageMatrix[INFANTRY][TANK][1] = 5;
		damageMatrix[INFANTRY][MDTANK][1] = 1;
//		damageMatrix[INFANTRY][NEOTANK][1] = 1;
		damageMatrix[INFANTRY][APC][1] = 14;
		damageMatrix[INFANTRY][ARTILLERY][1] = 15;
		damageMatrix[INFANTRY][ROCKET][1] = 25;
//		damageMatrix[INFANTRY][A_AIR][1] = 5;
//		damageMatrix[INFANTRY][MISSILES][1] = 25;
//		damageMatrix[INFANTRY][PLANE][1] = -1;
//		damageMatrix[INFANTRY][BOMBER][1] = -1;
//		damageMatrix[INFANTRY][BCOPTER][1] = 7;
//		damageMatrix[INFANTRY][TCOPTER][1] = 30;
		damageMatrix[INFANTRY][BSHIP][1] = -1;
//		damageMatrix[INFANTRY][CRUISER][1] = -1;
		damageMatrix[INFANTRY][LANDER][1] = -1;
//		damageMatrix[INFANTRY][SUB][1] = -1;

		// mech
		damageMatrix[MECH][INFANTRY][1] = 65;
		damageMatrix[MECH][MECH][1] = 55;
		damageMatrix[MECH][RECON][0] = 85;
		damageMatrix[MECH][RECON][1] = 18;
		damageMatrix[MECH][TANK][0] = 55;
		damageMatrix[MECH][TANK][1] = 6;
		damageMatrix[MECH][MDTANK][0] = 15;
		damageMatrix[MECH][MDTANK][1] = 1;
//		damageMatrix[MECH][NEOTANK][0] = 15;
//		damageMatrix[MECH][NEOTANK][1] = 1;
		damageMatrix[MECH][APC][0] = 75;
		damageMatrix[MECH][APC][1] = 20;
		damageMatrix[MECH][ARTILLERY][0] = 70;
		damageMatrix[MECH][ARTILLERY][1] = 32;
		damageMatrix[MECH][ROCKET][0] = 85;
		damageMatrix[MECH][ROCKET][1] = 35;
//		damageMatrix[MECH][A_AIR][0] = 65;
//		damageMatrix[MECH][A_AIR][1] = 6;
//		damageMatrix[MECH][MISSILES][0] = 85;
//		damageMatrix[MECH][MISSILES][1] = 35;
//		damageMatrix[MECH][PLANE][1] = -1;
//		damageMatrix[MECH][BOMBER][1] = -1;
//		damageMatrix[MECH][BCOPTER][1] = 9;
//		damageMatrix[MECH][TCOPTER][1] = 35;
		damageMatrix[MECH][BSHIP][1] = -1;
//		damageMatrix[MECH][CRUISER][1] = -1;
		damageMatrix[MECH][LANDER][1] = -1;
//		damageMatrix[MECH][SUB][1] = -1;
	}

	public static void handleAttack(Unit attacking, Unit defending) {
		HeroPortrait portrait = MapHandler.getHeroPortrait();
		Hero attackingHero = portrait.getHeroFromUnit(attacking);
		Hero defendingHero = portrait.getHeroFromUnit(defending); 
		int attX = attacking.getX();
		int attY = attacking.getY();
		int defX = defending.getX();
		int defY = defending.getY();

		int defendingTerrainType = MapHandler.map(defX, defY);

		// deal damage from attacker to defender
		damageCalculation(attacking, attackingHero, defending, defendingHero, defendingTerrainType);

		if (defending.getHP() <= 0) {
			return;
		}

		if (attacking instanceof IndirectUnit || defending instanceof IndirectUnit) {
			return;
		}

		int attackingTerrainType = MapHandler.map(attX, attY);
		// deal damage from defender to attacker (counterattack)
		damageCalculation(defending, defendingHero, attacking, attackingHero, attackingTerrainType);
	}


	private static void damageCalculation(Unit attacker, Hero attHero, Unit defender, Hero defHero, int defTerrainType) {
		// deal damage from A to B

		int attType = 0; // = attacker.getType(); @TODO
		int defType = 0; // = defender.getType();

		int weaponIndex = 1; // 0 eller 1

		int baseDamage = damageMatrix[attType][defType][weaponIndex];
		int heroAttackValue = attHero.getAttackValue(attType);
		int rngNumber = ((int)(Math.random()*10)) % 10;
		int heroDefenceValue = defHero.getDefenceValue(defType);
		int areaDefenceValue = MapHandler.getDefenceValue(defTerrainType);

		int attackingAffect = attacker.getHP() / 10 * ((baseDamage * heroAttackValue) / 100 + rngNumber) / 10;
		int defendingAffect = (200 - (heroDefenceValue + defTerrainType * defender.getHP() / 10)) / 10;
		int damageValue = attackingAffect * defendingAffect / 10;

		defender.takeDamage(damageValue);

		System.out.println("Dammagevalue: " + damageValue);
	}
}