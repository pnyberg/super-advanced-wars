package main;

import hero.Hero;
import unitUtils.UnitWorthCalculator;
import units.Unit;

public class StarPowerCalculator {
	private UnitWorthCalculator unitWorthCalculator;
	
	public StarPowerCalculator() {
		unitWorthCalculator = new UnitWorthCalculator();
	}

	public void calculateStarPowerOpponent(Hero hero, Unit unit, int damage) {
		calculateStarPower(hero, unit, damage, 0.5);
	}

	public void calculateStarPowerSelf(Hero hero, Unit unit, int damage) {
		calculateStarPower(hero, unit, damage, 1);
	}
	
	private void calculateStarPower(Hero hero, Unit unit, int damage, double starMultiplier) {
		double worth = unitWorthCalculator.getFullHealthUnitWorth(unit);
		int starWorth = 18000; // how much money a star is "worth"
				
		worth = worth / starWorth * damage / 100 * starMultiplier;
		hero.getHeroPower().getHeroPowerMeter().addStarPower(worth);
	}
}