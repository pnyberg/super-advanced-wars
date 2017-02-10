package tests;

import heroes.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CashTester {
	private HeroPortrait portrait;
	private Hero hero0, hero1;

	@Before
	public void init() {
		portrait = new HeroPortrait(-1);
		hero0 = new Hero(0);
		hero1 = new Hero(1);

		portrait.addHero(hero0);
		portrait.addHero(hero1);
	}

	@Test
	public void testIncome() {

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
		assertEquals(hero0.getCash(), 0);

		hero0.manageCash(12345);
		assertEquals(hero0.getCash(), 12345);

		hero0.manageCash(-12345);
	}
}
