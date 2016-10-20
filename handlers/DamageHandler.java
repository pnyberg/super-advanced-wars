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
		NEOTANK = 5,
		APC = 6,
		ARTILLERY = 7,
		ROCKET = 8,
		A_AIR = 9,
//		MISSILES = 10,
		FIGHTER = 10, //11,
		BOMBER = 11, //12,
		BCOPTER = 12, //13,
//		TCOPTER = 14,
		BATTLESHIP = 13, // 15,
//		CRUISER = 16,
		LANDER = 14; //17,
//		SUB = 18;

	public static void init() {
		damageMatrix = new int[number][number][2]; // the last one is for secondary weapons

		implementDamageMatrix();
	}

	private static void implementDamageMatrix() {
		for (int i = 0 ; i < number ; i++) {
			damageMatrix[INFANTRY][i][0] = -1;
			damageMatrix[RECON][i][0] = -1;
			damageMatrix[APC][i][0] = -1;
			damageMatrix[APC][i][1] = -1;
			damageMatrix[A_AIR][i][1] = -1;

			damageMatrix[FIGHTER][i][1] = -1;
			damageMatrix[BOMBER][i][1] = -1;

			damageMatrix[LANDER][i][0] = -1;
			damageMatrix[LANDER][i][1] = -1;
		}

		// infantry
		damageMatrix[INFANTRY][INFANTRY][1] = 55;
		damageMatrix[INFANTRY][MECH][1] = 45;
		damageMatrix[INFANTRY][RECON][1] = 12;
		damageMatrix[INFANTRY][TANK][1] = 5;
		damageMatrix[INFANTRY][MDTANK][1] = 1;
		damageMatrix[INFANTRY][NEOTANK][1] = 1;
		damageMatrix[INFANTRY][APC][1] = 14;
		damageMatrix[INFANTRY][ARTILLERY][1] = 15;
		damageMatrix[INFANTRY][ROCKET][1] = 25;
		damageMatrix[INFANTRY][A_AIR][1] = 5;
//		damageMatrix[INFANTRY][MISSILES][1] = 25;
		damageMatrix[INFANTRY][FIGHTER][1] = -1;
		damageMatrix[INFANTRY][BOMBER][1] = -1;
		damageMatrix[INFANTRY][BCOPTER][1] = 7;
//		damageMatrix[INFANTRY][TCOPTER][1] = 30;
		damageMatrix[INFANTRY][BATTLESHIP][1] = -1;
//		damageMatrix[INFANTRY][CRUISER][1] = -1;
		damageMatrix[INFANTRY][LANDER][1] = -1;
//		damageMatrix[INFANTRY][SUB][1] = -1;

		// mech
		damageMatrix[MECH][INFANTRY][0] = -1;
		damageMatrix[MECH][INFANTRY][1] = 65;
		damageMatrix[MECH][MECH][0] = -1;
		damageMatrix[MECH][MECH][1] = 55;
		damageMatrix[MECH][RECON][0] = 85;
		damageMatrix[MECH][RECON][1] = 18;
		damageMatrix[MECH][TANK][0] = 55;
		damageMatrix[MECH][TANK][1] = 6;
		damageMatrix[MECH][MDTANK][0] = 15;
		damageMatrix[MECH][MDTANK][1] = 1;
		damageMatrix[MECH][NEOTANK][0] = 15;
		damageMatrix[MECH][NEOTANK][1] = 1;
		damageMatrix[MECH][APC][0] = 75;
		damageMatrix[MECH][APC][1] = 20;
		damageMatrix[MECH][ARTILLERY][0] = 70;
		damageMatrix[MECH][ARTILLERY][1] = 32;
		damageMatrix[MECH][ROCKET][0] = 85;
		damageMatrix[MECH][ROCKET][1] = 35;
		damageMatrix[MECH][A_AIR][0] = 65;
		damageMatrix[MECH][A_AIR][1] = 6;
//		damageMatrix[MECH][MISSILES][0] = 85;
//		damageMatrix[MECH][MISSILES][1] = 35;
		damageMatrix[MECH][FIGHTER][0] = -1;
		damageMatrix[MECH][FIGHTER][1] = -1;
		damageMatrix[MECH][BOMBER][0] = -1;
		damageMatrix[MECH][BOMBER][1] = -1;
		damageMatrix[MECH][BCOPTER][0] = -1;
		damageMatrix[MECH][BCOPTER][1] = 9;
//		damageMatrix[MECH][TCOPTER][0] = -1;
//		damageMatrix[MECH][TCOPTER][1] = 35;
		damageMatrix[MECH][BATTLESHIP][0] = -1;
		damageMatrix[MECH][BATTLESHIP][1] = -1;
//		damageMatrix[MECH][CRUISER][0] = -1;
//		damageMatrix[MECH][CRUISER][1] = -1;
		damageMatrix[MECH][LANDER][0] = -1;
		damageMatrix[MECH][LANDER][1] = -1;
//		damageMatrix[MECH][SUB][0] = -1;
//		damageMatrix[MECH][SUB][1] = -1;

		// recon
		damageMatrix[RECON][INFANTRY][1] = 70;
		damageMatrix[RECON][MECH][1] = 65;
		damageMatrix[RECON][RECON][1] = 35;
		damageMatrix[RECON][TANK][1] = 6;
		damageMatrix[RECON][MDTANK][1] = 1;
		damageMatrix[RECON][NEOTANK][1] = 1;
		damageMatrix[RECON][APC][1] = 45;
		damageMatrix[RECON][ARTILLERY][1] = 45;
		damageMatrix[RECON][ROCKET][1] = 55;
		damageMatrix[RECON][A_AIR][1] = 4;
//		damageMatrix[RECON][MISSILES][1] = 28;
		damageMatrix[RECON][FIGHTER][1] = -1;
		damageMatrix[RECON][BOMBER][1] = -1;
		damageMatrix[RECON][BCOPTER][1] = 10;
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
		damageMatrix[TANK][NEOTANK][0] = 15;
		damageMatrix[TANK][NEOTANK][1] = 1;
		damageMatrix[TANK][APC][0] = 75;
		damageMatrix[TANK][APC][1] = 45;
		damageMatrix[TANK][ARTILLERY][0] = 70;
		damageMatrix[TANK][ARTILLERY][1] = 45;
		damageMatrix[TANK][ROCKET][0] = 85;
		damageMatrix[TANK][ROCKET][1] = 55;
		damageMatrix[TANK][A_AIR][0] = 65;
		damageMatrix[TANK][A_AIR][1] = 5;
//		damageMatrix[TANK][MISSILES][0] = 85;
//		damageMatrix[TANK][MISSILES][1] = 30;
		damageMatrix[TANK][FIGHTER][0] = -1;
		damageMatrix[TANK][FIGHTER][1] = -1;
		damageMatrix[TANK][BOMBER][0] = -1;
		damageMatrix[TANK][BOMBER][1] = -1;
		damageMatrix[TANK][BCOPTER][0] = -1;
		damageMatrix[TANK][BCOPTER][1] = 10;
//		damageMatrix[TANK][TCOPTER][0] = -1;
//		damageMatrix[TANK][TCOPTER][1] = 40;
		damageMatrix[TANK][BATTLESHIP][0] = 1;
		damageMatrix[TANK][BATTLESHIP][1] = -1;
//		damageMatrix[TANK][CRUISER][0] = 5;
//		damageMatrix[TANK][CRUISER][1] = -1;
		damageMatrix[TANK][LANDER][0] = 10;
		damageMatrix[TANK][LANDER][1] = -1;
//		damageMatrix[TANK][SUB][0] = 1;
//		damageMatrix[TANK][SUB][1] = -1;

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
		damageMatrix[MDTANK][NEOTANK][0] = 45;
		damageMatrix[MDTANK][NEOTANK][1] = 1;
		damageMatrix[MDTANK][APC][0] = 105;
		damageMatrix[MDTANK][APC][1] = 45;
		damageMatrix[MDTANK][ARTILLERY][0] = 105;
		damageMatrix[MDTANK][ARTILLERY][1] = 45;
		damageMatrix[MDTANK][ROCKET][0] = 105;
		damageMatrix[MDTANK][ROCKET][1] = 55;
		damageMatrix[MDTANK][A_AIR][0] = 105;
		damageMatrix[MDTANK][A_AIR][1] = 7;
//		damageMatrix[MDTANK][MISSILES][0] = 105;
//		damageMatrix[MDTANK][MISSILES][1] = 35;
		damageMatrix[MDTANK][FIGHTER][0] = -1;
		damageMatrix[MDTANK][FIGHTER][1] = -1;
		damageMatrix[MDTANK][BOMBER][0] = -1;
		damageMatrix[MDTANK][BOMBER][1] = -1;
		damageMatrix[MDTANK][BCOPTER][0] = -1;
		damageMatrix[MDTANK][BCOPTER][1] = 12;
//		damageMatrix[MDTANK][TCOPTER][0] = -1;
//		damageMatrix[MDTANK][TCOPTER][1] = 45;
		damageMatrix[MDTANK][BATTLESHIP][0] = 10;
		damageMatrix[MDTANK][BATTLESHIP][1] = -1;
//		damageMatrix[MDTANK][CRUISER][0] = 45;
//		damageMatrix[MDTANK][CRUISER][1] = -1;
		damageMatrix[MDTANK][LANDER][0] = 35;
		damageMatrix[MDTANK][LANDER][1] = -1;
//		damageMatrix[MDTANK][SUB][0] = 10;
//		damageMatrix[MDTANK][SUB][1] = -1;

		// neotank
		damageMatrix[NEOTANK][INFANTRY][0] = 35; // will not be used
		damageMatrix[NEOTANK][INFANTRY][1] = 125;
		damageMatrix[NEOTANK][MECH][0] = 35; // will not be used
		damageMatrix[NEOTANK][MECH][1] = 115;
		damageMatrix[NEOTANK][RECON][0] = 125;
		damageMatrix[NEOTANK][RECON][1] = 65;
		damageMatrix[NEOTANK][TANK][0] = 105;
		damageMatrix[NEOTANK][TANK][1] = 10;
		damageMatrix[NEOTANK][MDTANK][0] = 75;
		damageMatrix[NEOTANK][MDTANK][1] = 1;
		damageMatrix[NEOTANK][NEOTANK][0] = 55;
		damageMatrix[NEOTANK][NEOTANK][1] = 1;
		damageMatrix[NEOTANK][APC][0] = 125;
		damageMatrix[NEOTANK][APC][1] = 65;
		damageMatrix[NEOTANK][ARTILLERY][0] = 115;
		damageMatrix[NEOTANK][ARTILLERY][1] = 65;
		damageMatrix[NEOTANK][ROCKET][0] = 125;
		damageMatrix[NEOTANK][ROCKET][1] = 75;
		damageMatrix[NEOTANK][A_AIR][0] = 115;
		damageMatrix[NEOTANK][A_AIR][1] = 17;
//		damageMatrix[NEOTANK][MISSILES][0] = 125;
//		damageMatrix[NEOTANK][MISSILES][1] = 55;
		damageMatrix[NEOTANK][FIGHTER][0] = -1;
		damageMatrix[NEOTANK][FIGHTER][1] = -1;
		damageMatrix[NEOTANK][BOMBER][0] = -1;
		damageMatrix[NEOTANK][BOMBER][1] = -1;
		damageMatrix[NEOTANK][BCOPTER][0] = -1;
		damageMatrix[NEOTANK][BCOPTER][1] = 22;
//		damageMatrix[NEOTANK][TCOPTER][0] = -1;
//		damageMatrix[NEOTANK][TCOPTER][1] = 55;
		damageMatrix[NEOTANK][BATTLESHIP][0] = 15;
		damageMatrix[NEOTANK][BATTLESHIP][1] = -1;
//		damageMatrix[NEOTANK][CRUISER][0] = 50;
//		damageMatrix[NEOTANK][CRUISER][1] = -1;
		damageMatrix[NEOTANK][LANDER][0] = 40;
		damageMatrix[NEOTANK][LANDER][1] = -1;
//		damageMatrix[NEOTANK][SUB][0] = 15;
//		damageMatrix[NEOTANK][SUB][1] = -1;

		// a-air
		damageMatrix[A_AIR][INFANTRY][0] = 105;
		damageMatrix[A_AIR][MECH][0] = 105;
		damageMatrix[A_AIR][RECON][0] = 60;
		damageMatrix[A_AIR][TANK][0] = 25;
		damageMatrix[A_AIR][MDTANK][0] = 10;
		damageMatrix[A_AIR][NEOTANK][0] = 5;
		damageMatrix[A_AIR][APC][0] = 50;
		damageMatrix[A_AIR][ARTILLERY][0] = 50;
		damageMatrix[A_AIR][ROCKET][0] = 55;
		damageMatrix[A_AIR][A_AIR][0] = 45;
//		damageMatrix[A_AIR][MISSILES][0] = 55;
		damageMatrix[A_AIR][FIGHTER][0] = 65;
		damageMatrix[A_AIR][BOMBER][0] = 75;
		damageMatrix[A_AIR][BCOPTER][0] = 120;
//		damageMatrix[A_AIR][TCOPTER][0] = 120;
		damageMatrix[A_AIR][BATTLESHIP][0] = -1;
//		damageMatrix[A_AIR][CRUISER][0] = -1;
		damageMatrix[A_AIR][LANDER][0] = -1;
//		damageMatrix[A_AIR][SUB][0] = -1;

		// fighter
		damageMatrix[FIGHTER][INFANTRY][0] = -1;
		damageMatrix[FIGHTER][MECH][0] = -1;
		damageMatrix[FIGHTER][RECON][0] = -1;
		damageMatrix[FIGHTER][TANK][0] = -1;
		damageMatrix[FIGHTER][MDTANK][0] = -1;
		damageMatrix[FIGHTER][NEOTANK][0] = -1;
		damageMatrix[FIGHTER][APC][0] = -1;
		damageMatrix[FIGHTER][ARTILLERY][0] = -1;
		damageMatrix[FIGHTER][ROCKET][0] = -1;
		damageMatrix[FIGHTER][A_AIR][0] = -1;
//		damageMatrix[FIGHTER][MISSILES][0] = -1;
		damageMatrix[FIGHTER][FIGHTER][0] = 55;
		damageMatrix[FIGHTER][BOMBER][0] = 100;
		damageMatrix[FIGHTER][BCOPTER][0] = 100;
//		damageMatrix[FIGHTER][TCOPTER][0] = 100;
		damageMatrix[FIGHTER][BATTLESHIP][0] = -1;
//		damageMatrix[FIGHTER][CRUISER][0] = -1;
		damageMatrix[FIGHTER][LANDER][0] = -1;
//		damageMatrix[FIGHTER][SUB][0] = -1;

		// bomber
		damageMatrix[BOMBER][INFANTRY][0] = 110;
		damageMatrix[BOMBER][MECH][0] = 110;
		damageMatrix[BOMBER][RECON][0] = 105;
		damageMatrix[BOMBER][TANK][0] = 105;
		damageMatrix[BOMBER][MDTANK][0] = 95;
		damageMatrix[BOMBER][NEOTANK][0] = 90;
		damageMatrix[BOMBER][APC][0] = 105;
		damageMatrix[BOMBER][ARTILLERY][0] = 105;
		damageMatrix[BOMBER][ROCKET][0] = 105;
		damageMatrix[BOMBER][A_AIR][0] = 95;
//		damageMatrix[BOMBER][MISSILES][0] = 105;
		damageMatrix[BOMBER][FIGHTER][0] = -1;
		damageMatrix[BOMBER][BOMBER][0] = -1;
		damageMatrix[BOMBER][BCOPTER][0] = -1;
//		damageMatrix[BOMBER][TCOPTER][0] = -1;
		damageMatrix[BOMBER][BATTLESHIP][0] = 75;
//		damageMatrix[BOMBER][CRUISER][0] = 85;
		damageMatrix[BOMBER][LANDER][0] = 95;
//		damageMatrix[BOMBER][SUB][0] = 95;

		// bcopter
		damageMatrix[BCOPTER][INFANTRY][0] = -1;
		damageMatrix[BCOPTER][INFANTRY][1] = 75;
		damageMatrix[BCOPTER][MECH][0] = -1;
		damageMatrix[BCOPTER][MECH][1] = 75;
		damageMatrix[BCOPTER][RECON][0] = 55;
		damageMatrix[BCOPTER][RECON][1] = 30;
		damageMatrix[BCOPTER][TANK][0] = 55;
		damageMatrix[BCOPTER][TANK][1] = 6;
		damageMatrix[BCOPTER][MDTANK][0] = 25;
		damageMatrix[BCOPTER][MDTANK][1] = 1;
		damageMatrix[BCOPTER][NEOTANK][0] = 20;
		damageMatrix[BCOPTER][NEOTANK][1] = 1;
		damageMatrix[BCOPTER][APC][0] = 60;
		damageMatrix[BCOPTER][APC][1] = 20;
		damageMatrix[BCOPTER][ARTILLERY][0] = 65;
		damageMatrix[BCOPTER][ARTILLERY][1] = 25;
		damageMatrix[BCOPTER][ROCKET][0] = 65;
		damageMatrix[BCOPTER][ROCKET][1] = 35;
		damageMatrix[BCOPTER][A_AIR][0] = 25;
		damageMatrix[BCOPTER][A_AIR][1] = 6;
//		damageMatrix[BCOPTER][MISSILES][0] = 65;
//		damageMatrix[BCOPTER][MISSILES][1] = 35;
		damageMatrix[BCOPTER][FIGHTER][0] = -1;
		damageMatrix[BCOPTER][FIGHTER][1] = -1;
		damageMatrix[BCOPTER][BOMBER][0] = -1;
		damageMatrix[BCOPTER][BOMBER][1] = -1;
		damageMatrix[BCOPTER][BCOPTER][0] = -1;
		damageMatrix[BCOPTER][BCOPTER][1] = 65;
//		damageMatrix[BCOPTER][TCOPTER][0] = -1;
//		damageMatrix[BCOPTER][TCOPTER][1] = 95;
		damageMatrix[BCOPTER][BATTLESHIP][0] = 25;
		damageMatrix[BCOPTER][BATTLESHIP][1] = -1;
//		damageMatrix[BCOPTER][CRUISER][0] = 55;
//		damageMatrix[BCOPTER][CRUISER][1] = -1;
		damageMatrix[BCOPTER][LANDER][0] = 25;
		damageMatrix[BCOPTER][LANDER][1] = -1;
//		damageMatrix[BCOPTER][SUB][0] = 25;
//		damageMatrix[BCOPTER][SUB][1] = -1;
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

		if (attacking instanceof IndirectUnit
			|| defending instanceof IndirectUnit
			|| defending instanceof APC
			|| defending instanceof Lander
			/*|| defending instanceof TCopter*/) {
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

		int weaponIndex = getWeaponIndex(attacker, defender); // 0 or 1

		int baseDamage = damageMatrix[attType][defType][weaponIndex];
		int heroAttackValue = attHero.getAttackValue(attType);
		int rngNumber = ((int)(Math.random()*10)) % 10;
		int heroDefenceValue = defHero.getDefenceValue(defType);
		int areaDefenceValue = MapHandler.getDefenceValue(defTerrainType);

		int attackingAffect = attacker.getHP() / 10 * ((baseDamage * heroAttackValue) / 100 + rngNumber) / 10;
		int defendingAffect = (200 - (heroDefenceValue + areaDefenceValue * defender.getHP() / 10)) / 10;
		int damageValue = attackingAffect * defendingAffect / 10;

		defender.takeDamage(damageValue);

		System.out.println("BaseDamage: " + baseDamage + " (" + attackingAffect + "*" + defendingAffect + ")");
		System.out.println("Damagevalue: " + damageValue + " of type: " + attType);
	}

	public static int getTypeFromUnit(Unit unit) {
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
		} else if (unit instanceof Neotank) {
			return NEOTANK;
		} else if (unit instanceof APC) {
			return APC;
		} else if (unit instanceof Artillery) {
			return ARTILLERY;
		} else if (unit instanceof Rocket) {
			return ROCKET;
		} else if (unit instanceof AAir) {
			return A_AIR;
/*		} else if (unit instanceof Missiles) {
			return MISSILES;*/
		} else if (unit instanceof Fighter) {
			return FIGHTER;
		} else if (unit instanceof Bomber) {
			return BOMBER;
		} else if (unit instanceof BCopter) {
			return BCOPTER;
/*		} else if (unit instanceof TCopter) {
			return TCOPTER;*/
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

	private static int getWeaponIndex(Unit attacker, Unit defender) {
		if (attacker instanceof Infantry) {
			return 1;
		} else if (attacker instanceof Mech) {
			if (defender instanceof Infantry 
				|| defender instanceof BCopter
				/*|| defender instanceof TCopter*/) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof Recon) {
			return 1;
		} else if (attacker instanceof Tank) {
			if (defender instanceof Infantry
				 || defender instanceof Mech 
				|| defender instanceof BCopter
				/*|| defender instanceof TCopter*/) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof MDTank) {
			if (defender instanceof Infantry
				 || defender instanceof Mech 
				|| defender instanceof BCopter
				/*|| defender instanceof TCopter*/) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof Neotank) {
			if (defender instanceof Infantry
				 || defender instanceof Mech 
				|| defender instanceof BCopter
				/*|| defender instanceof TCopter*/) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof APC) {
			// Do nothing
		} else if (attacker instanceof Artillery) {
			return 0;
		} else if (attacker instanceof Rocket) {
			return 0;
		} else if (attacker instanceof AAir) {
			return 0;
/*		} else if (attacker instanceof Missiles) {
			return 0;*/
		} else if (attacker instanceof Fighter) {
			return 0;
		} else if (attacker instanceof Bomber) {
			return 0;
		} else if (attacker instanceof BCopter) {
			if (defender instanceof Infantry
				|| defender instanceof Mech 
				|| defender instanceof BCopter
				/* || defender instanceof TCopter*/) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof Battleship) {
			return 0;
/*		} else if (attacker instanceof Cruiser) {
			if (defender instanceof Sub) {
				return 0;
			}
			return 1; */
		} else if (attacker instanceof Lander) {
			// Do nothing
/*		} else if (attacker instanceof Sub) {
			return 1;*/
		}

		return -1;
	}

	public static int getBaseDamageValue(int attType, int defType, int gunNumber) {
		return damageMatrix[attType][defType][gunNumber];
	}
}