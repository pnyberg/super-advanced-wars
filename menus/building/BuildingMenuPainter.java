package menus.building;

import java.awt.Color;
import java.awt.Graphics;

import heroes.HeroPortrait;
import menus.DimensionValues;

public class BuildingMenuPainter {
	private HeroPortrait heroPortrait;
	private DimensionValues dimensionValues;
	private int priceAlign;
	
	public BuildingMenuPainter(HeroPortrait heroPortrait, DimensionValues dimensionValues, int priceAlign) {
		this.heroPortrait = heroPortrait;
		this.dimensionValues = dimensionValues;
		this.priceAlign = priceAlign;
	}

	public void paint(Graphics g, int x, int menuY, BuildingItem[] items) {
		for (int k = 0 ; k < items.length ; k++) {
			paintMenuItem(g, x, menuY + dimensionValues.getMenuRowHeight() * (k + 1), items[k].getName(), items[k].getPrice());
		}
	}

	private void paintMenuItem(Graphics g, int x, int y, String text, int price) {
		int heroCash = heroPortrait.getCurrentHero().getCash();
		int menuX = x * dimensionValues.getTileSize() + dimensionValues.getTileSize() / 2 + dimensionValues.getAlignX();
		int extraPriceAlign = (price >= 10000 ? 0 : 8);

		if (heroCash < price) {
			g.setColor(Color.gray);
		} else {
			g.setColor(Color.black);
		}
		g.drawString(text, menuX, y);
		g.drawString("" + price + "", menuX + priceAlign + extraPriceAlign, y);
	}
}