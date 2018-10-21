package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import cursors.Cursor;
import graphics.PowerStar;
import hero.Hero;
import map.GameMap;
import map.area.Area;
import map.area.TerrainType;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
import point.Point;

public class TerrainInfoBox {
	private Point point;
	private int width;
	private int height;
	private int tileSize;
	private GameMap gameMap;
	private Cursor cursor;
	private BuildingHandler buildingHandler;
	private StructureHandler structureHandler;
	
	public TerrainInfoBox(Point point, int width, int height, int tileSize, GameMap gameMap, Cursor cursor, BuildingHandler buildingHandler, StructureHandler structureHandler) {
		this.point = point;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.gameMap = gameMap;
		this.cursor = cursor;
		this.buildingHandler = buildingHandler;
		this.structureHandler = structureHandler;
	}
	
	public void paint(Graphics g) {
		Area area = gameMap.getMap()[cursor.getX()][cursor.getY()];
		g.setColor(Color.lightGray);
		g.fillRect(point.getX(), point.getY(), width, height);
		
		g.setColor(Color.black);
		Font currentFont = g.getFont();
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		TerrainType terrainType = area.getTerrainType();
		int xAdjust = (width - g.getFontMetrics().stringWidth(terrainType.showName())) / 2;
		g.drawString(terrainType.showName(), point.getX() + xAdjust, point.getY() + 20);
		g.setFont(currentFont);
		
		paintArea(g, area);
		
		paintDefenceValue(g, area);
		
		paintCaptValue(g, area);
	}
	
	private void paintArea(Graphics g, Area area) {
		if (area.getTerrainType().isBuilding()) {
			Building building = buildingHandler.getBuilding(area.getPoint().getX() / tileSize, area.getPoint().getY() / tileSize);
			Color buildingColor = null;
			Hero owner = building.getOwner();
			if (owner == null) {
				buildingColor = Color.white;
			} else {
				buildingColor = owner.getColor();
			}
			building.getBuildingImage().paint(g, point.getX() + (width-tileSize)/2, point.getY() + 22, buildingColor);
		} else if (area.getTerrainType().isStructure()) {
			Structure structure = structureHandler.getStructure(area.getPoint().getX(), area.getPoint().getY());
			Color cannonColor = null;
			Hero owner = structure.getOwner();
			if (owner == null) {
				cannonColor = Color.darkGray;
			} else {
				cannonColor = owner.getColor();
			}
			structure.getStructureImage().paint(g, point.getX() + (width-tileSize)/2, point.getY() + 22, cannonColor);
		} else {
			area.getAreaImage().paint(g, point.getX() + (width-tileSize)/2, point.getY() + 22, false, false);
		}
	}
	
	private void paintDefenceValue(Graphics g, Area area) {
		PowerStar.paintNormal(g, point.getX() + (width-tileSize)/2 - 5, point.getY() + tileSize + 26, 0);
		g.setColor(Color.black);
		Font currentFont = g.getFont();
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		g.drawString("" + area.getTerrainType().defenceValue() + "", point.getX() + width/2 - 5 + 10, point.getY() + 8 * tileSize / 4 + 3);
		g.setFont(currentFont);
	}
	
	private void paintCaptValue(Graphics g, Area area) {
		if (area.getTerrainType().isBuilding()) {
			g.setColor(Color.white);
			g.fillRect(point.getX() + (width-tileSize)/2 - 5 + 1, point.getY() + tileSize + 26 + 20, tileSize / 3, tileSize / 2);
			g.setColor(Color.black);
			g.drawRect(point.getX() + (width-tileSize)/2 - 5 + 1, point.getY() + tileSize + 26 + 20, tileSize / 3, tileSize / 2);

			g.setColor(Color.black);
			Font currentFont = g.getFont();
			g.setFont(new Font("ComicSans", Font.BOLD, 20));
			Building building = buildingHandler.getBuilding(area.getPoint().getX() / tileSize, area.getPoint().getY() / tileSize);
			int captValue = building.getCaptingValue();
			int xAdjust = captValue < 10 ? 10 : 0;
			g.drawString("" + captValue + "", point.getX() + width/2 - 5 + xAdjust, point.getY() + tileSize + 26 + 20 + 18);
			g.setFont(currentFont);
		}
	}
}