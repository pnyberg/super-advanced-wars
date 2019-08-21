/**
 * TODO: needs repairing after refactoring
 */
package tests;

import main.HeroHandler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gameObjects.MapDimension;
import hero.*;

public class CashTester {
	private MapDimension mapDimension;
	private HeroPortrait portrait;
	private HeroFactory heroFactory;
	private Hero hero0, hero1;

	@Before
	public void init() {
		mapDimension = new MapDimension(0, 0, 0);
		//portrait = new HeroPortrait(mapDimension, new HeroHandler());
		heroFactory = new HeroFactory();
		hero0 = heroFactory.createHero(0);
		hero1 = heroFactory.createHero(1);

		/*
		portrait.getHeroHandler().addHero(hero0);
		portrait.getHeroHandler().addHero(hero1);
		*/
	}

	@Test
	public void testIncome() {
		// test to see if cities generate expected amount of income
	}

	@Test
	/**
	 * Test that the amount of cash is never over 999.999 and never under 0
	 */
	public void testAmounts() {
		int maxAmount = 999999;

		assertEquals(hero0.getCash(), 0);

		hero0.manageCash(maxAmount);
		assertEquals(hero0.getCash(), maxAmount);

		hero0.manageCash(1);
		assertEquals(hero0.getCash(), maxAmount);

		hero0.manageCash(-maxAmount);
		assertEquals(hero0.getCash(), 0);

		hero0.manageCash(-1);
		assertEquals(hero0.getCash(), -1);

		hero0.manageCash(12345 + 1);
		assertEquals(hero0.getCash(), 12345);

		hero0.manageCash(-12345);
		assertEquals(hero0.getCash(), 0);
	}
	
	@Test
	public void testSpending() {
		assertEquals(hero0.getCash(), 0);
		
		hero0.manageCash(1000);
	}
}
