package hero;

import java.awt.Color;

import hero.heroPower.HeroPower;
import hero.heroPower.HeroPowerMeter;

public class HeroFactory {
	public HeroFactory() {}
	
	public Hero createHero(int heroIndex) {
		if (heroIndex == 0) {
			AttackDefenceObject attackDefenceObject = new AttackDefenceObject(100, 100);
			HeroPowerMeter heroPowerMeter = new HeroPowerMeter(3, 6);
			HeroPower heroPower = new HeroPower(heroPowerMeter);
			return new Hero(Color.red, attackDefenceObject, heroPower);
		} else if (heroIndex == 1) {
			AttackDefenceObject attackDefenceObject = new AttackDefenceObject(110, 110);
			HeroPowerMeter heroPowerMeter = new HeroPowerMeter(4, 7);
			HeroPower heroPower = new HeroPower(heroPowerMeter);
			return new Hero(Color.orange, attackDefenceObject, heroPower);
		}
		return null;
	}
}