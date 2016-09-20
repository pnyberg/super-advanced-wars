import java.awt.Color;
import java.awt.Graphics;

public class HeroPortrait {
	private int cash;
	private int starPower;
	private int powerAmount, superPowerAmount;
	private Color color;

	public HeroPortrait(int heroIndex) {
		cash = 0;
		starPower = 0;

		initHero(heroIndex);
	}

	private void initHero(int heroIndex) {
		if (heroIndex == 0) {
			powerAmount = 3;
			superPowerAmount = 6;
			color = Color.magenta;
		}
	}

	public void paint(Graphics g) {
		paintLeftSide(g);
	}

	private void paintLeftSide(Graphics g) {
		int tileSize = MapHandler.tileSize;

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

		int borderThickness = 2;
		int cashBarHeight = 25;

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

		g.setColor(color);
		g.fillPolygon(outerBorderX, outerBorderY, 7);
		g.setColor(Color.white);
		g.fillPolygon(innerBorderX, innerBorderY, 7);

		g.drawString("$:", ox1 + 2, oy1 + 15);
		g.drawString("" + cash + "", ox2 - 20, oy1 + 15);
	}
}