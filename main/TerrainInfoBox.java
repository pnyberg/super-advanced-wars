package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.GraphicMetrics;
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
import units.Unit;

public class TerrainInfoBox {
	private Point anchorPoint;
	private int width;
	private int height;
	private int tileSize;
	private GameMap gameMap;
	private Cursor cursor;
	private BuildingHandler buildingHandler;
	private StructureHandler structureHandler;
	
	public TerrainInfoBox(GraphicMetrics terrainInfoBoxGraphicMetrics, GameProperties gameProperties, GameState gameState) {
		this.anchorPoint = terrainInfoBoxGraphicMetrics.anchorPoint;
		this.width = terrainInfoBoxGraphicMetrics.width;
		this.height = terrainInfoBoxGraphicMetrics.height;
		this.tileSize = terrainInfoBoxGraphicMetrics.tileSize;
		this.gameMap = gameProperties.getGameMap();
		this.cursor = gameState.getCursor();
		this.buildingHandler = new BuildingHandler(gameState);
		this.structureHandler = new StructureHandler(gameState, gameProperties.getMapDimension());
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
		if (area.getTerrainType().isBuilding()) {
			paintCaptValue(g, area);
		}
		if (area.getTerrainType().isStructure()) {
			paintStructureHealth(g, area);
		}
	}
	
	private void paintArea(Graphics g, Area area) {
		int buildingStructurePosX = anchorPoint.getX() + (width-tileSize)/2;
		int buildingStructurePosY = anchorPoint.getY() + 22;

		if (area.getTerrainType().isBuilding()) {
			Building building = buildingHandler.getBuilding(area.getPoint().getX(), area.getPoint().getY());
			Hero owner = building.getOwner();
			Color buildingColor = owner == null ? Color.white : owner.getColor();
			building.getBuildingImage().paint(g, buildingStructurePosX, buildingStructurePosY, buildingColor);
		} else if (area.getTerrainType().isStructure()) {
			Structure structure = structureHandler.getStructure(area.getPoint().getX(), area.getPoint().getY());
			Hero owner = structure.getOwner();
			Color cannonColor = owner == null ? Color.darkGray : owner.getColor();
			structure.getStructureImage().paint(g, buildingStructurePosX, buildingStructurePosY, cannonColor);
		} else {
			int areaPosX = anchorPoint.getX() + (width-tileSize)/2;
			int areaPosY = anchorPoint.getY() + 22;
			area.getAreaImage().paint(g, areaPosX, areaPosY, false, false);
		}
	}
	
	private void paintDefenceValue(Graphics g, Area area) {
		int starX = anchorPoint.getX() + (width-tileSize)/2 - 5;
		int starY = anchorPoint.getY() + tileSize + 26;
		PowerStar.paintNormal(g, starX, starY, 0);
		g.setColor(Color.black);
		Font currentFont = g.getFont();
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		g.drawString("" + area.getTerrainType().defenceValue() + "", anchorPoint.getX() + width/2 - 5 + 10, anchorPoint.getY() + 2 * tileSize + 3);
		g.setFont(currentFont);
	}
	
	private void paintCaptValue(Graphics g, Area area) {
		int captBoxPosX = anchorPoint.getX() + (width-tileSize)/2 - 5 + 1;
		int captBoxPosY = anchorPoint.getY() + tileSize + 26 + 20;

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
		int captValueTextPosX = anchorPoint.getX() + width/2 - 5 + xAdjust;
		int captValueTextPosY = anchorPoint.getY() + tileSize + 26 + 20 + 18;
		g.drawString("" + captValue + "", captValueTextPosX, captValueTextPosY);
		g.setFont(currentFont);
	}
	
	private void paintStructureHealth(Graphics g, Area area) {
		int heartX = anchorPoint.getX() + (width-tileSize)/2 - 7;
		int heartY = anchorPoint.getY() + 3 * tileSize/2 + 29;

		g.setColor(Color.black);
		Font currentFont = g.getFont();
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		Structure structure = structureHandler.getFiringStructure(area.getPoint().getX(), area.getPoint().getY());
		int healthValue = structure.getHP();
		int xAdjust = healthValue < 10 ? 10 : 0;
		int captValueTextPosX = anchorPoint.getX() + width/2 - 5 + xAdjust;
		int captValueTextPosY = anchorPoint.getY() + tileSize + 26 + 20 + 18;
		g.drawString("" + healthValue + "", captValueTextPosX, captValueTextPosY);
		g.setFont(currentFont);

		paintHeart(g, heartX, heartY);
	}

	private void paintHeart(Graphics g, int heartX, int heartY) {
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
	}
}