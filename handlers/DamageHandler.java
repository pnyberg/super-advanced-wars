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
		BATTLESHIP = 8, // 15,
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
		damageMatrix[INFANTRY][BATTLESHIP][1] = -1;
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
		damageMatrix[MECH][BATTLESHIP][1] = -1;
//		damageMatrix[MECH][CRUISER][1] = -1;
		damageMatrix[MECH][LANDER][1] = -1;
//		damageMatrix[MECH][SUB][1] = -1;

		// recon
		damageMatrix[RECON][INFANTRY][1] = 70;
		damageMatrix[RECON][MECH][1] = 65;
		damageMatrix[RECON][RECON][1] = 35;
		damageMatrix[RECON][TANK][1] = 6;
		damageMatrix[RECON][MDTANK][1] = 1;
//		damageMatrix[RECON][NEOTANK][1] = 1;
		damageMatrix[RECON][APC][1] = 45;
		damageMatrix[RECON][ARTILLERY][1] = 45;
		damageMatrix[RECON][ROCKET][1] = 55;
//		damageMatrix[RECON][A_AIR][1] = 4;
//		damageMatrix[RECON][MISSILES][1] = 28;
//		damageMatrix[RECON][PLANE][1] = -1;
//		damageMatrix[RECON][BOMBER][1] = -1;
//		damageMatrix[RECON][BCOPTER][1] = 10;
//		damageMatrix[RECON][TCOPTER][1] = 35;
		damageMatrix[RECON][BATTLESHIP][1] = -1;
//		damageMatrix[RECON][CRUISER][1] = -1;
		damageMatrix[RECON][LANDER][1] = -1;
//		damageMatrix[RECON][SUB][1] = -1;

		// tank
		damageMatrix[TANK][INFANTRY][0] = 25; // will not be used
		damageMatrix[TANK][INFANTRY][1] = 75;
		damageMatrix[TANK][MECH][0] = 25; // will not be used
		damageMatrix[TANK][MECH][1] = 70;
		damageMatrix[TANK][RECON][0] = 85;
		damageMatrix[TANK][RECON][1] = 40;
		damageMatrix[TANK][TANK][0] = 55;
		damageMatrix[TANK][TANK][1] = 6;
		damageMatrix[TANK][MDTANK][0] = 15;
		damageMatrix[TANK][MDTANK][1] = 1;
//		damageMatrix[TANK][NEOTANK][0] = 15;
//		damageMatrix[TANK][NEOTANK][1] = 1;
		damageMatrix[TANK][APC][0] = 75;
		damageMatrix[TANK][APC][1] = 45;
		damageMatrix[TANK][ARTILLERY][0] = 70;
		damageMatrix[TANK][ARTILLERY][1] = 45;
		damageMatrix[TANK][ROCKET][0] = 85;
		damageMatrix[TANK][ROCKET][1] = 55;
//		damageMatrix[TANK][A_AIR][0] = 65;
//		damageMatrix[TANK][A_AIR][1] = 5;
//		damageMatrix[TANK][MISSILES][0] = 85;
//		damageMatrix[TANK][MISSILES][1] = 30;
//		damageMatrix[TANK][PLANE][1] = -1;
//		damageMatrix[TANK][BOMBER][1] = -1;
//		damageMatrix[TANK][BCOPTER][1] = 10;
//		damageMatrix[TANK][TCOPTER][1] = 40;
		damageMatrix[TANK][BATTLESHIP][1] = 1;
//		damageMatrix[TANK][CRUISER][1] = 5;
		damageMatrix[TANK][LANDER][1] = 10;
//		damageMatrix[TANK][SUB][1] = 1;

		// MDtank
		damageMatrix[MDTANK][INFANTRY][0] = 30; // will not be used
		damageMatrix[MDTANK][INFANTRY][1] = 105;
		damageMatrix[MDTANK][MECH][0] = 30; // will not be used
		damageMatrix[MDTANK][MECH][1] = 95;
		damageMatrix[MDTANK][RECON][0] = 105;
		damageMatrix[MDTANK][RECON][1] = 45;
		damageMatrix[MDTANK][TANK][0] = 85;
		damageMatrix[MDTANK][TANK][1] = 8;
		damageMatrix[MDTANK][MDTANK][0] = 55;
		damageMatrix[MDTANK][MDTANK][1] = 1;
//		damageMatrix[MDTANK][NEOTANK][0] = 45;
//		damageMatrix[MDTANK][NEOTANK][1] = 1;
		damageMatrix[MDTANK][APC][0] = 105;
		damageMatrix[MDTANK][APC][1] = 45;
		damageMatrix[MDTANK][ARTILLERY][0] = 105;
		damageMatrix[MDTANK][ARTILLERY][1] = 45;
		damageMatrix[MDTANK][ROCKET][0] = 105;
		damageMatrix[MDTANK][ROCKET][1] = 55;
//		damageMatrix[MDTANK][A_AIR][0] = 105;
//		damageMatrix[MDTANK][A_AIR][1] = 7;
//		damageMatrix[MDTANK][MISSILES][0] = 105;
//		damageMatrix[MDTANK][MISSILES][1] = 35;
//		damageMatrix[MDTANK][PLANE][1] = -1;
//		damageMatrix[MDTANK][BOMBER][1] = -1;
//		damageMatrix[MDTANK][BCOPTER][1] = 12;
//		damageMatrix[MDTANK][TCOPTER][1] = 45;
		damageMatrix[MDTANK][BATTLESHIP][1] = 10;
//		damageMatrix[MDTANK][CRUISER][1] = 45;
		damageMatrix[MDTANK][LANDER][1] = 35;
//		damageMatrix[MDTANK][SUB][1] = 10;
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

		int attType = getTypeFromUnit(attacker);
		int defType = getTypeFromUnit(defender);

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

	private static int getTypeFromUnit(Unit unit) {
		if (unit instanceof Infantry) {
			return INFANTRY;
		} else if (unit instanceof Mech) {
			return MECH;
		} else if (unit instanceof Recon) {
			return RECON;
		} else if (unit instanceof Tank) {
			return TANK;
		} else if (unit instanceof MDTank) {
			return MDTANK;
/*		} else if (unit instanceof Neotank) {
			return NEOTANK;*/
		} else if (unit instanceof APC) {
			return APC;
		} else if (unit instanceof Artillery) {
			return ARTILLERY;
		} else if (unit instanceof Rocket) {
			return ROCKET;
/*		} else if (unit instanceof AAir) {
			return A_AIR;
		} else if (unit instanceof Missiles) {
			return MISSILES;
		} else if (unit instanceof Plane) {
			return PLANE;
		} else if (unit instanceof Bomber) {
			return BOMBER;
		} else if (unit instanceof Bcopter) {
			return BCOPTER;*/
		} else if (unit instanceof Battleship) {
			return BATTLESHIP;
/*		} else if (unit instanceof Cruiser) {
			return CRUISER;*/
		} else if (unit instanceof Lander) {
			return LANDER;
/*		} else if (unit instanceof Sub) {
			return SUB;*/
		}
		return -1;
	}
}