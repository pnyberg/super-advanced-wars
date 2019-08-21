package menus.building;

import java.awt.Color;
import java.awt.Graphics;

import hero.HeroHandler;
import menus.DimensionValues;

public class BuildingMenuPainter {
	private HeroHandler heroHandler;
	private DimensionValues dimensionValues;
	private int priceAlign;
	
	public BuildingMenuPainter(HeroHandler heroHandler, DimensionValues dimensionValues, int priceAlign) {
		this.heroHandler = heroHandler;
		this.dimensionValues = dimensionValues;
		this.priceAlign = priceAlign;
	}

	public void paint(Graphics g, int x, int y, BuildingItem[] items) {
		for (int k = 0 ; k < items.length ; k++) {
			paintMenuItem(g, x, y + dimensionValues.getMenuRowHeight() * (k + 1), items[k].getName(), items[k].getPrice());
		}
	}

	private void paintMenuItem(Graphics g, int x, int y, String text, int price) {
		int heroCash = heroHandler.getCurrentHero().getCash();
		int xAdjust = dimensionValues.getTileSize() / 2 + dimensionValues.getAlignX();
		int extraPriceAlign = (price < 10000 ? 8 : 0);

		if (heroCash < price) {
			g.setColor(Color.gray);
		} else {
			g.setColor(Color.black);
		}
		g.drawString(text, x + xAdjust, y);
		g.drawString("" + price + "", x + xAdjust + priceAlign + extraPriceAlign, y);
	}
}