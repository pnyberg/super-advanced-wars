package main;

import java.awt.Color;
import java.awt.Graphics;

import cursors.Cursor;
import map.GameMap;
import map.UnitGetter;
import map.buildings.BuildingHandler;
import map.structures.StructureHandler;
import point.Point;

public class InfoBox {
	private Point point;
	private int width;
	private int height;
	private TerrainInfoBox terrainInfoBox;
	private UnitInfoBox unitInfoBox;
	private UnitContainedInfoBox unitContainedInfoBox; 
	
	// TODO: rewrite with fewer parameters
	public InfoBox(Point point, int width, int height, int tileSize, GameMap gameMap, Cursor cursor, UnitGetter unitGetter, BuildingHandler buildingHandler, StructureHandler structureHandler) {
		this.point = point;
		this.width = width;
		this.height = height;
		// TODO: rewrite code
		Point terrainInfoBoxPoint = new Point(point.getX() + tileSize / 4, point.getY() + tileSize / 8);
		terrainInfoBox = new TerrainInfoBox(terrainInfoBoxPoint, tileSize * 2, height - tileSize / 4, tileSize, gameMap, cursor, buildingHandler, structureHandler);
		Point unitInfoBoxPoint = new Point(point.getX() + tileSize * 2 + tileSize / 4 + 5, point.getY() + tileSize / 8);
		unitInfoBox = new UnitInfoBox(unitInfoBoxPoint, tileSize * 2, height - tileSize / 4, tileSize, cursor, unitGetter);
		Point unitContainedInfoBoxPoint = new Point(point.getX() + tileSize * 4 + tileSize / 4 + 10, point.getY() + tileSize / 8);
		unitContainedInfoBox = new UnitContainedInfoBox(unitContainedInfoBoxPoint, tileSize * 2, height - tileSize / 4, tileSize, cursor, unitGetter);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(point.getX(), point.getY(), width, height);
		g.setColor(Color.black);
		g.drawRect(point.getX(), point.getY(), width, height);
		
		terrainInfoBox.paint(g);
		unitInfoBox.paint(g);
		unitContainedInfoBox.paint(g);
	}
}