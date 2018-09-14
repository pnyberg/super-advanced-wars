package heroes;

import units.*;
import handlers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import graphics.PowerStar;

public class HeroPortrait {
	private ArrayList<Hero> heroes;
	private Hero currentHero;
	private int heroIndex;

	private boolean leftSide;
	private int mapWidth;

	public HeroPortrait(int mapWidth) {
		this.mapWidth = mapWidth;

		heroes = new ArrayList<Hero>();
		currentHero = null;
		heroIndex = 0;
		leftSide = true;
	}

	public void selectStartHero() {
		currentHero = heroes.get(heroIndex);
	}

	public void nextHero() {
		heroIndex = (heroIndex + 1) % heroes.size();

		currentHero = heroes.get(heroIndex);
	}

	public void addHero(Hero hero) {
		heroes.add(hero);
	}

	public void updateSideChoice(int cursorX, int cursorY) {
		if (cursorY >= 2) {
			return;
		}

		if (leftSide && cursorX < 4) {
			leftSide = false;
		} else if (!leftSide && cursorX >= 16) {
			leftSide = true;
		}
	}

	public int getNumberOfHeroes() {
		return heroes.size();
	}

	public Hero getHeroFromUnit(Unit testUnit) {
		for (int h = 0 ; h < getNumberOfHeroes() ; h++) {
			for (int k = 0 ; k < getHero(h).getTroopHandler().getTroopSize() ; k++) {
				Unit unit = heroes.get(h).getTroopHandler().getTroop(k);
				if (unit.getX() == testUnit.getX() && unit.getY() == testUnit.getY() && !unit.isHidden()) {
					return getHero(h);
				}
			}
		}

		return null;
	}

	public Hero getHero(int index) {
		return heroes.get(index);
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public void paint(Graphics g) {
		if (leftSide) {
			paintLeftSide(g);
		} else {
			paintRightSide(g);
		}
	}

	private void paintLeftSide(Graphics g) {
		int tileSize = MapHandler.tileSize;

		int borderThickness = 2;
		int cashBarHeight = 25;
		int cashAlign = 8 * (String.valueOf(currentHero.getCash()).length() - 1);

		// remove and make the array with newlines after every element
		int ox1 = 0;
		int ox2 = tileSize * 4;
		int ox3 = tileSize * 4;
		int ox4 = (15 * tileSize) / 4;
		int ox5 = (17 * tileSize) / 5;
		int ox6 = (11 * tileSize) / 4;
		int ox7 = 0;

		int oy1 = 0;
		int oy2 = 0;
		int oy3 = tileSize;
		int oy4 = (4 * tileSize) / 3;
		int oy5 = (10 * tileSize) / 6;
		int oy6 = tileSize * 2;
		int oy7 = tileSize * 2;

		int[] outerBorderX = {ox1, ox2, ox3, ox4, ox5, ox6, ox7};
		int[] outerBorderY = {oy1, oy2, oy3, oy4, oy5, oy6, oy7};

		int ix1 = ox1 + borderThickness;
		int ix2 = ox2 - borderThickness;
		int ix3 = ox3 - borderThickness;
		int ix4 = ox4 - borderThickness;
		int ix5 = ox5 - borderThickness;
		int ix6 = ox6 - borderThickness;
		int ix7 = ox7 + borderThickness;

		int iy1 = oy1 + borderThickness + cashBarHeight;
		int iy2 = oy2 + borderThickness + cashBarHeight;
		int iy3 = oy3 - borderThickness;
		int iy4 = oy4 - borderThickness;
		int iy5 = oy5 - borderThickness;
		int iy6 = oy6 - borderThickness;
		int iy7 = oy7 - borderThickness;

		int[] innerBorderX = {ix1, ix2, ix3, ix4, ix5, ix6, ix7};
		int[] innerBorderY = {iy1, iy2, iy3, iy4, iy5, iy6, iy7};

		g.setColor(currentHero.getColor());
		g.fillPolygon(outerBorderX, outerBorderY, 7);
		g.setColor(Color.white);
		g.fillPolygon(innerBorderX, innerBorderY, 7);

		g.drawString("$:", ox1 + 2, oy1 + 15);
		g.drawString("" + currentHero.getCash() + "", ox2 - 20 - cashAlign, oy1 + 15);

		currentHero.paint(g, 0, 0);
		drawHeroPowerStars(g, ox7, oy7);
	}

	private void paintRightSide(Graphics g) {
		int tileSize = MapHandler.tileSize;

		int borderThickness = 2;
		int cashBarHeight = 25;
		int cashAlign = 8 * (String.valueOf(currentHero.getCash()).length() - 1);

		int ox1 = mapWidth * tileSize - (tileSize * 4);
		int ox2 = mapWidth * tileSize;
		int ox3 = mapWidth * tileSize;
		int ox4 = mapWidth * tileSize - tileSize / 4;
		int ox5 = mapWidth * tileSize - (3 * tileSize) / 5;
		int ox6 = mapWidth * tileSize - (5 * tileSize) / 4;
		int ox7 = mapWidth * tileSize - (tileSize * 4);

		int oy1 = 0;
		int oy2 = 0;
		int oy3 = tileSize;
		int oy4 = (4 * tileSize) / 3;
		int oy5 = (10 * tileSize) / 6;
		int oy6 = tileSize * 2;
		int oy7 = tileSize * 2;

		int[] outerBorderX = {ox1, ox2, ox3, ox4, ox5, ox6, ox7};
		int[] outerBorderY = {oy1, oy2, oy3, oy4, oy5, oy6, oy7};

		int ix1 = ox1 + borderThickness;
		int ix2 = ox2 - borderThickness;
		int ix3 = ox3 - borderThickness;
		int ix4 = ox4 - borderThickness;
		int ix5 = ox5 - borderThickness;
		int ix6 = ox6 - borderThickness;
		int ix7 = ox7 + borderThickness;

		int iy1 = oy1 + borderThickness + cashBarHeight;
		int iy2 = oy2 + borderThickness + cashBarHeight;
		int iy3 = oy3 - borderThickness;
		int iy4 = oy4 - borderThickness;
		int iy5 = oy5 - borderThickness;
		int iy6 = oy6 - borderThickness;
		int iy7 = oy7 - borderThickness;

		int[] innerBorderX = {ix1, ix2, ix3, ix4, ix5, ix6, ix7};
		int[] innerBorderY = {iy1, iy2, iy3, iy4, iy5, iy6, iy7};

		g.setColor(currentHero.getColor());
		g.fillPolygon(outerBorderX, outerBorderY, 7);
		g.setColor(Color.white);
		g.fillPolygon(innerBorderX, innerBorderY, 7);

		g.drawString("$:", ox1 + 2, oy1 + 15);
		g.drawString("" + currentHero.getCash() + "", ox2 - 20 - cashAlign, oy1 + 15);

		currentHero.paint(g, 0, 0);
		
		drawHeroPowerStars(g, ox7, oy7);
	}
	
	private void drawHeroPowerStars(Graphics g, int x, int y) {
		int starX = x + 1;
		int starY = y - 8;

		int powerStars = currentHero.getHeroPowerMeter().getRequiredPower();
		int superPowerStars = currentHero.getHeroPowerMeter().getRequiredSuperPower();
		
		double currentPower = currentHero.getHeroPowerMeter().getStarPower();

		for (int i = 0 ; i < powerStars ; i++) {
			double amountFilled = Math.max(0, (currentPower - i));
			PowerStar.paintNormal(g, starX, starY, amountFilled);
			starX += PowerStar.smallSize;
		}

		starY -= PowerStar.bigSize - PowerStar.smallSize;

		for (int i = 0 ; i < (superPowerStars - powerStars) ; i++) {
			double amountFilled = Math.max(0, (currentPower - i - powerStars));
			PowerStar.paintSuper(g, starX, starY,  amountFilled);
			starX += PowerStar.bigSize;
		}
	}
}