package hero;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import hero.heroAbilities.AttackIncrease;
import hero.heroAbilities.CommanderEffect;
import hero.heroAbilities.DamageAll;
import hero.heroAbilities.HealAll;
import hero.heroPower.HeroPower;
import hero.heroPower.HeroPowerMeter;

public class HeroFactory {
	public HeroFactory() {}
	
	public Hero createHero(int heroIndex) {
		if (heroIndex == 0) {
			PassiveHeroAbilities passiveHeroAbilities = new PassiveHeroAbilities(100, 100);
			HeroPowerMeter heroPowerMeter = new HeroPowerMeter(3, 6);
			int[] powerAttackPercents = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 
											30, 30, 30};
			List<CommanderEffect> powerList = new ArrayList<>();
			powerList.add(new AttackIncrease(powerAttackPercents));
			ArrayList<CommanderEffect> superPowerList = new ArrayList<>();
			int[] superAttackPercents = {40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 
											40, 40, 40};
			superPowerList.add(new AttackIncrease(superAttackPercents));
			superPowerList.add(new DamageAll(0, 0, 2));
			HeroPower heroPower = new HeroPower(heroPowerMeter, powerList, superPowerList);
			return new Hero(Color.red, passiveHeroAbilities, heroPower);
		} else if (heroIndex == 1) {
			PassiveHeroAbilities passiveHeroAbilities = new PassiveHeroAbilities(110, 110);
			HeroPowerMeter heroPowerMeter = new HeroPowerMeter(4, 7);
			int[] powerAttackPercents = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 
									20, 20, 20};
			List<CommanderEffect> powerList = new ArrayList<>();
			powerList.add(new AttackIncrease(powerAttackPercents));
			ArrayList<CommanderEffect> superPowerList = new ArrayList<>();
			int[] superAttackPercents = {40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 
											40, 40, 40};
			superPowerList.add(new AttackIncrease(superAttackPercents));
			superPowerList.add(new HealAll(1, 0, 0));
			HeroPower heroPower = new HeroPower(heroPowerMeter, powerList, superPowerList);
			return new Hero(Color.orange, passiveHeroAbilities, heroPower);
		}
		return null;
	}
}