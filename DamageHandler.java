public class DamageHandler {
	private static int[][][] damageMatrix;
	private static final int number = Unit.numberOfUnitTypes;

	private static final int
		INFANTRY = 0,
		MECH = 1,
		RECON = 2,
		TANK = 3,
//		MDTANK = 4,
//		NEOTANK = 5,
		APC = 4, // 6,
		ARTILLERY = 5, // 7,
		ROCKET = 6, // 8,
//		A_AIR = 9,
//		MISSILES = 10,
//		PLANE = 11,
//		BOMBER = 12,
//		BCOPTER = 13,
//		TCOPTER = 14,
		BSHIP = 7, // 15,
//		CRUISER = 16,
		LANDER = 8; //17,
//		SUB = 18;

	public static void init() {
		damageMatrix = new int[number][number][2]; // the last one is for secondary weapons

		implementDamageMatrix();
	}

	private static void implementDamageMatrix() {
		for (int i = 0 ; i < number ; i++) {
			damageMatrix[INFANTRY][i][0] = -1;
		}
		damageMatrix[INFANTRY][INFANTRY][1] = 55;
		damageMatrix[INFANTRY][MECH][1] = 45;
		damageMatrix[INFANTRY][RECON][1] = 12;
		damageMatrix[INFANTRY][TANK][1] = 5;
//		damageMatrix[INFANTRY][MDTANK][1] = 1;
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
	}

	public static void handleAttack(Unit attacking, Unit defending, boolean indirectAttack) {
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

		if (indirectAttack) {
			return;
		}

		int attackingTerrainType = MapHandler.map(attX, attY);
		// deal damage from defender to attacker (counterattack)
		damageCalculation(defending, defendingHero, attacking, attackingHero, attackingTerrainType);
	}


	private static void damageCalculation(Unit attacker, Hero attHero, Unit defender, Hero defHero, int defTerrainType) {
		// deal damage from A to B

		int attType = 0; // = attacker.getType();
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

		System.out.println("heroAttackValue: " + heroAttackValue + " baseDamage: " + baseDamage + " rng: " + rngNumber);
		System.out.println("heroDefenceValue: " + heroDefenceValue + " areaDefenceValue: " + areaDefenceValue);
		System.out.println("Attackaffect: " + attackingAffect + " Defendingaffect: " + defendingAffect);
		System.out.println(damageValue);

		/*
D%=[B*ACO/100+R]*(AHP/10)*[(200-(DCO+DTR*DHP))/100]

D = Actual damage expressed as a percentage

B = Base damage (in damage chart)

ACO = Attacking CO attack value (example:130 for Kanbei)

R = Random number 0-9

AHP = HP of attacker

DCO = Defending CO defense value (example:80 for Grimm)

DTR = Defending Terrain Stars (IE plain = 1, wood = 2)

DHP = HP of defender	 
		 */
	}
}