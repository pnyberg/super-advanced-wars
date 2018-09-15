package heroes;

import java.awt.Color;

public class HeroFactory {
	public HeroFactory() {}
	
	public Hero createHero(int heroIndex) {
		if (heroIndex == 0) {
			AttackDefenceObject attackDefenceObject = new AttackDefenceObject(100, 100);
			HeroPowerMeter heroPowerMeter = new HeroPowerMeter(3, 6);
			return new Hero(Color.red, attackDefenceObject, heroPowerMeter);
		} else if (heroIndex == 1) {
			AttackDefenceObject attackDefenceObject = new AttackDefenceObject(110, 110);
			HeroPowerMeter heroPowerMeter = new HeroPowerMeter(4, 7);
			return new Hero(Color.orange, attackDefenceObject, heroPowerMeter);
		}
		return null;
	}
}