package graphics;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.MapDim;
import hero.Hero;

public class HeroPortraitPainter {
	private MapDim mapDim;
	
	public HeroPortraitPainter(MapDim mapDim) {
		this.mapDim = mapDim;
	}

	// TODO: replace with an image
	public void paintLeftSide(Graphics g, Hero currentHero) {
		int borderThickness = 2;
		int cashBarHeight = 25;

		// remove and make the array with newlines after every element
		int ox1 = 0;
		int ox2 = mapDim.tileSize * 4;
		int ox3 = mapDim.tileSize * 4;
		int ox4 = (15 * mapDim.tileSize) / 4;
		int ox5 = (17 * mapDim.tileSize) / 5;
		int ox6 = (11 * mapDim.tileSize) / 4;
		int ox7 = 0;

		int oy1 = 0;
		int oy2 = 0;
		int oy3 = mapDim.tileSize;
		int oy4 = (4 * mapDim.tileSize) / 3;
		int oy5 = (5 * mapDim.tileSize) / 3;
		int oy6 = mapDim.tileSize * 2;
		int oy7 = mapDim.tileSize * 2;

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

		paintBorders(g, currentHero, outerBorderX, outerBorderY, innerBorderX, innerBorderY);
		paintHeroAttributes(g, currentHero, outerBorderX, outerBorderY);
	}

	// TODO: replace with an image
	public void paintRightSide(Graphics g, Hero currentHero) {
		int borderThickness = 2;
		int cashBarHeight = 25;

		int rightMapBorder = mapDim.getTileWidth() * mapDim.tileSize;
		int ox1 = rightMapBorder - (mapDim.tileSize * 4);
		int ox2 = rightMapBorder;
		int ox3 = rightMapBorder;
		int ox4 = rightMapBorder - mapDim.tileSize / 4;
		int ox5 = rightMapBorder - (3 * mapDim.tileSize) / 5;
		int ox6 = rightMapBorder - (5 * mapDim.tileSize) / 4;
		int ox7 = rightMapBorder - (mapDim.tileSize * 4);

		int oy1 = 0;
		int oy2 = 0;
		int oy3 = mapDim.tileSize;
		int oy4 = (4 * mapDim.tileSize) / 3;
		int oy5 = (5 * mapDim.tileSize) / 3;
		int oy6 = mapDim.tileSize * 2;
		int oy7 = mapDim.tileSize * 2;

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

		paintBorders(g, currentHero, outerBorderX, outerBorderY, innerBorderX, innerBorderY);
		paintHeroAttributes(g, currentHero, outerBorderX, outerBorderY);
	}
	
	// TODO: rewrite with fewer parameter
	private void paintBorders(Graphics g, Hero currentHero, int[] outerBorderX, int[] outerBorderY, int[] innerBorderX, int[] innerBorderY) {
		g.setColor(currentHero.getColor());
		g.fillPolygon(outerBorderX, outerBorderY, 7);
		g.setColor(Color.white);
		g.fillPolygon(innerBorderX, innerBorderY, 7);
	}
	
	private void paintHeroAttributes(Graphics g, Hero currentHero, int[] outerBorderX, int[] outerBorderY) {
		int cashAlign = 8 * (String.valueOf(currentHero.getCash()).length() - 1);

		g.drawString("$:", outerBorderX[0] + 2, outerBorderY[0] + 15);
		g.drawString("" + currentHero.getCash() + "", outerBorderX[1] - 20 - cashAlign, outerBorderY[1] + 15);

		currentHero.paint(g, 0, 0);
		if (currentHero.isPowerActive()) {
			drawPowerText(g, "POWER", outerBorderX[6], outerBorderY[6]);
		} else if (currentHero.isSuperPowerActive()) {
			drawPowerText(g, "SUPER", outerBorderX[6], outerBorderY[6]);
		} else {
			drawHeroPowerStars(g, currentHero, outerBorderX[6], outerBorderY[6]);
		}
	}

	private void drawHeroPowerStars(Graphics g, Hero currentHero, int x, int y) {
		int starX = x + 1;
		int starY = y - 8;
		int powerStars = currentHero.getHeroPower().getHeroPowerMeter().getRequiredPower();
		int superPowerStars = currentHero.getHeroPower().getHeroPowerMeter().getRequiredSuperPower();
		double currentPower = currentHero.getHeroPower().getHeroPowerMeter().getStarPower();

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
	
	private void drawPowerText(Graphics g, String text, int x, int y) {
		g.setColor(Color.black);
		g.drawString(text, x - 2, y + 10 - 2);
		g.drawString(text, x - 2, y + 10 + 2);
		g.drawString(text, x + 2, y + 10 - 2);
		g.drawString(text, x + 2, y + 10 + 2);
		g.setColor(Color.yellow);
		g.drawString(text, x, y + 10);
	}
}