package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.GameState;
import gameObjects.GraphicMetrics;
import map.UnitGetter;
import point.Point;
import unitUtils.UnitType;
import units.Unit;

public class UnitInfoBox {
	private Point anchorPoint;
	private int width;
	private int height;
	private int tileSize;
	private Cursor cursor;
	private UnitGetter unitGetter;
	
	public UnitInfoBox(GraphicMetrics unitInfoBoxGraphicMetrics, GameState gameState) {
		this.anchorPoint = unitInfoBoxGraphicMetrics.anchorPoint;
		this.width = unitInfoBoxGraphicMetrics.width;
		this.height = unitInfoBoxGraphicMetrics.height;
		this.tileSize = unitInfoBoxGraphicMetrics.tileSize;
		this.cursor = gameState.getCursor();
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
	}
	
	public boolean showUnitInfo() {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		return unit != null;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(anchorPoint.getX(), anchorPoint.getY(), width, height);

		Font currentFont = g.getFont();
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		UnitType unitType = unit.getUnitType();
		g.setFont(new Font("ComicSans", Font.BOLD, 17));
		int xAdjust = (width - g.getFontMetrics().stringWidth(unitType.unitTypeShowName())) / 2;
		g.setColor(Color.black);
		g.drawString(unitType.unitTypeShowName(), anchorPoint.getX() + xAdjust, anchorPoint.getY() + 20);
		g.setFont(currentFont);

		paintUnit(g, unit);
		paintUnitHealth(g, unit);
		paintUnitFuel(g, unit);
		paintUnitAmmo(g, unit);
	}
	
	private void paintUnit(Graphics g, Unit unit) {
		Color unitColor = unit.getColor();
		int x = anchorPoint.getX() + (width-tileSize)/2;
		int y = anchorPoint.getY() + 19;
		unit.getUnitImage().paint(g, x, y, unitColor);
		unit.paintFlags(g, x, y, tileSize);
	}
	
	// TODO: replace code with image
	private void paintUnitHealth(Graphics g, Unit unit) {
		int healthValue = unit.getUnitHealth().getShowHP();
		
		int heartX = anchorPoint.getX() + (width-tileSize)/2 - 3; 
		int heartY = anchorPoint.getY() + 3 * tileSize/2 + 2;

		// heart-points
		int cx1 = heartX + 9;
		int cy1 = heartY + 3 + tileSize/4;

		int cx3 = heartX + 2;
		int cy3 = heartY + 5 + tileSize/4 - 9;
		int cx4 = heartX + 2;
		int cy4 = heartY + 5 + tileSize/4 - 12;
		int cx5 = heartX + 3;
		int cy5 = heartY;
		int cx6 = heartX + 6;
		int cy6 = heartY;
		int cx7 = heartX + 8;
		int cy7 = heartY + 1;
		
		int cx8 = heartX + 9;
		int cy8 = heartY + 3;

		int cx9 = heartX + 10;
		int cy9 = heartY + 1;
		int cx10 = heartX + 12;
		int cy10 = heartY;
		int cx11 = heartX + 15;
		int cy11 = heartY;
		int cx12 = heartX + 16;
		int cy12 = heartY + 5 + tileSize/4 - 12;
		int cx13 = heartX + 16;
		int cy13 = heartY + 5 + tileSize/4 - 9;

		// heart
		int[] cannonX = {cx1, cx3, cx4, cx5, cx6, cx7, cx8, cx9, cx10, cx11, cx12, cx13};
		int[] cannonY = {cy1, cy3, cy4, cy5, cy6, cy7, cy8, cy9, cy10, cy11, cy12, cy13};
		int npoints = cannonY.length;
		g.setColor(Color.red);
		g.fillPolygon(cannonX, cannonY, npoints);
		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);

		Font currentFont = g.getFont();
		g.setFont(new Font("ComicSans", Font.BOLD, 16));
		int xAdjust = healthValue < 10 ? 10 : 0;
		g.drawString("" + healthValue + "", heartX + tileSize/2 + xAdjust, heartY + tileSize / 3);
		g.setFont(currentFont);
	}
	
	private void paintUnitFuel(Graphics g, Unit unit) {
		int fuel = unit.getUnitSupply().getFuel();
		int fuelX = anchorPoint.getX() + (width-tileSize)/2 + 1;
		int fuelY = anchorPoint.getY() + 2 * tileSize - 3;
		
		int fuelWidth = tileSize / 4;
		int fuelHeight = tileSize / 3;
		g.setColor(new Color(252, 102, 0)); // orange
		g.fillRect(fuelX, fuelY, fuelWidth, fuelHeight);
		g.setColor(Color.black);
		g.drawRect(fuelX, fuelY, fuelWidth, fuelHeight);
		g.drawLine(fuelX + 2, fuelY + 2, fuelX + fuelWidth - 2, fuelY + fuelHeight - 2);
		g.drawLine(fuelX + 2, fuelY + fuelHeight - 2, fuelX + fuelWidth - 2, fuelY + 2);

		Font currentFont = g.getFont();
		g.setFont(new Font("ComicSans", Font.BOLD, 16));
		int xAdjust = fuel < 10 ? 9 : 0;
		g.drawString("" + fuel + "", fuelX + tileSize/2 - 4 + xAdjust, fuelY + tileSize / 3);
		g.setFont(currentFont);
	}
	
	private void paintUnitAmmo(Graphics g, Unit unit) {
		if (unit.getUnitSupply().getMaxAmmo() > 0) {
			int ammo = unit.getUnitSupply().getAmmo();
			int ammoX = anchorPoint.getX() + (width-tileSize)/2 + 1;
			int ammoY = anchorPoint.getY() + 9 * tileSize/4 + 2;
			
			int ammoWidth = tileSize / 4;
			int ammoHeight = tileSize / 8 + 2;
			g.setColor(new Color(139, 69, 19));
			g.fillRect(ammoX, ammoY + 3, ammoWidth, ammoHeight);
			g.setColor(Color.black);
			g.drawRect(ammoX, ammoY + 3, ammoWidth, ammoHeight);
			g.drawLine(ammoX + 2, ammoY + 3, ammoX + 2, ammoY + 3 + ammoHeight);

			Font currentFont = g.getFont();
			g.setFont(new Font("ComicSans", Font.BOLD, 16));
			int xAdjust = ammo < 10 ? 9 : 0;
			g.drawString("" + ammo + "", ammoX + tileSize/2 - 4 + xAdjust, ammoY + tileSize / 3);
			g.setFont(currentFont);
		}
	}
}