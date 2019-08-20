package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.GameMapAndCursor;
import gameObjects.GraphicMetrics;
import graphics.PowerStar;
import hero.Hero;
import map.BuildingStructureHandlerObject;
import map.GameMap;
import map.area.Area;
import map.area.TerrainType;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
import point.Point;

public class TerrainInfoBox {
	private Point anchorPoint;
	private int width;
	private int height;
	private int tileSize;
	private GameMap gameMap;
	private Cursor cursor;
	private BuildingHandler buildingHandler;
	private StructureHandler structureHandler;
	
	public TerrainInfoBox(GraphicMetrics terrainInfoBoxGraphicMetrics, GameMapAndCursor gameMapAndCursor, BuildingStructureHandlerObject buildingStructureHandlerObject) {
		this.anchorPoint = terrainInfoBoxGraphicMetrics.anchorPoint;
		this.width = terrainInfoBoxGraphicMetrics.width;
		this.height = terrainInfoBoxGraphicMetrics.height;
		this.tileSize = terrainInfoBoxGraphicMetrics.tileSize;
		this.gameMap = gameMapAndCursor.gameMap;
		this.cursor = gameMapAndCursor.cursor;
		this.buildingHandler = buildingStructureHandlerObject.buildingHandler;
		this.structureHandler = buildingStructureHandlerObject.structureHandler;
	}
	
	private Area getAreaForCursor() {
		return gameMap.getArea(cursor.getX() / tileSize, cursor.getY() / tileSize);
	}
	
	public void paint(Graphics g) {
		Area area = getAreaForCursor();
		g.setColor(Color.lightGray);
		g.fillRect(anchorPoint.getX(), anchorPoint.getY(), width, height);
		g.setColor(Color.black);
		Font currentFont = g.getFont();
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		TerrainType terrainType = area.getTerrainType();
		int xAdjust = (width - g.getFontMetrics().stringWidth(terrainType.showName())) / 2;
		g.drawString(terrainType.showName(), anchorPoint.getX() + xAdjust, anchorPoint.getY() + 20);
		g.setFont(currentFont);
		
		paintArea(g, area);
		paintDefenceValue(g, area);
		paintCaptValue(g, area);
	}
	
	private void paintArea(Graphics g, Area area) {
		if (area.getTerrainType().isBuilding()) {
			Building building = buildingHandler.getBuilding(area.getPoint().getX(), area.getPoint().getY());
			Hero owner = building.getOwner();
			Color buildingColor = owner == null ? Color.white : owner.getColor();
			building.getBuildingImage().paint(g, anchorPoint.getX() + (width-tileSize)/2, anchorPoint.getY() + 22, buildingColor);
		} else if (area.getTerrainType().isStructure()) {
			Structure structure = structureHandler.getStructure(area.getPoint().getX(), area.getPoint().getY());
			Hero owner = structure.getOwner();
			Color cannonColor = owner == null ? Color.darkGray : owner.getColor();
			structure.getStructureImage().paint(g, anchorPoint.getX() + (width-tileSize)/2, anchorPoint.getY() + 22, cannonColor);
		} else {
			area.getAreaImage().paint(g, anchorPoint.getX() + (width-tileSize)/2, anchorPoint.getY() + 22, false, false);
		}
	}
	
	private void paintDefenceValue(Graphics g, Area area) {
		PowerStar.paintNormal(g, anchorPoint.getX() + (width-tileSize)/2 - 5, anchorPoint.getY() + tileSize + 26, 0);
		g.setColor(Color.black);
		Font currentFont = g.getFont();
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		g.drawString("" + area.getTerrainType().defenceValue() + "", anchorPoint.getX() + width/2 - 5 + 10, anchorPoint.getY() + 2 * tileSize + 3);
		g.setFont(currentFont);
	}
	
	private void paintCaptValue(Graphics g, Area area) {
		int captBoxPosX = anchorPoint.getX() + (width-tileSize)/2 - 5 + 1;
		int captBoxPosY = anchorPoint.getY() + tileSize + 26 + 20;
		if (area.getTerrainType().isBuilding()) {
			g.setColor(Color.white);
			g.fillRect(captBoxPosX, captBoxPosY, tileSize / 3, tileSize / 2);
			g.setColor(Color.black);
			g.drawRect(captBoxPosX, captBoxPosY, tileSize / 3, tileSize / 2);

			g.setColor(Color.black);
			Font currentFont = g.getFont();
			g.setFont(new Font("ComicSans", Font.BOLD, 20));
			Building building = buildingHandler.getBuilding(area.getPoint().getX(), area.getPoint().getY());
			int captValue = building.getCaptingValue();
			int xAdjust = captValue < 10 ? 10 : 0;
			g.drawString("" + captValue + "", anchorPoint.getX() + width/2 - 5 + xAdjust, anchorPoint.getY() + tileSize + 26 + 20 + 18);
			g.setFont(currentFont);
		}
	}
}