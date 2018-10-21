package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import cursors.Cursor;
import hero.Hero;
import map.GameMap;
import map.UnitGetter;
import map.area.TerrainType;
import map.buildings.Building;
import point.Point;
import units.Unit;
import units.UnitType;

public class UnitInfoBox {
	private Point point;
	private int width;
	private int height;
	private int tileSize;
	private GameMap gameMap;
	private Cursor cursor;
	private UnitGetter unitGetter;
	
	public UnitInfoBox(Point point, int width, int height, int tileSize, GameMap gameMap, Cursor cursor, UnitGetter unitGetter) {
		this.point = point;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.gameMap = gameMap;
		this.cursor = cursor;
		this.unitGetter = unitGetter;
	}

	public void paint(Graphics g) {
		Unit unit = unitGetter.getAnyUnit(cursor.getX() * tileSize, cursor.getY() * tileSize);
		
		if (unit != null) {
			g.setColor(Color.lightGray);
			g.fillRect(point.getX(), point.getY(), width, height);

			g.setColor(Color.black);
			Font currentFont = g.getFont();
			g.setFont(new Font("ComicSans", Font.BOLD, 20));
			UnitType unitType = unit.getUnitType();
			int xAdjust = (width - g.getFontMetrics().stringWidth(unitType.unitTypeShowName())) / 2;
			g.drawString(unitType.unitTypeShowName(), point.getX() + xAdjust, point.getY() + 20);
			g.setFont(currentFont);
			
			paintUnit(g, unit);
		}
	}
	
	public void paintUnit(Graphics g, Unit unit) {
		Color unitColor = unit.getColor();
		unit.getUnitImage().paint(g, point.getX() + (width-tileSize)/2, point.getY() + 22, unitColor);
	}
}