package main;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.GraphicMetrics;
import gameObjects.MapDimension;
import gameObjects.GameMapAndCursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import map.BuildingStructureHandlerObject;
import map.UnitGetter;
import point.Point;

public class InfoBox {
	private Point anchorPoint;
	private int width;
	private int height;
	private TerrainInfoBox terrainInfoBox;
	private UnitInfoBox unitInfoBox;
	private UnitContainedInfoBox unitContainedInfoBox; 
	
	// TODO: rewrite with fewer parameters
	public InfoBox(GameProperties gameProperties, GameState gameState) {
		GraphicMetrics infoBoxGraphicMetrics = gameProperties.getInfoBoxGraphicMetrics();
		this.anchorPoint = infoBoxGraphicMetrics.anchorPoint;
		this.width = infoBoxGraphicMetrics.width;
		this.height = infoBoxGraphicMetrics.height;
		int tileSize = infoBoxGraphicMetrics.tileSize;

		// terrain-infobox
		int terrainInfoBoxPosX = anchorPoint.getX() + tileSize / 4;
		int terrainInfoBoxPosY = anchorPoint.getY() + tileSize / 8;
		Point terrainInfoBoxPoint = new Point(terrainInfoBoxPosX, terrainInfoBoxPosY);
		int terrainInfoBoxWidth = tileSize * 2;
		int terrainInfoBoxHeight = height - tileSize / 4;
		GraphicMetrics terrainInfoBoxGraphicMetrics = new GraphicMetrics(terrainInfoBoxPoint, terrainInfoBoxWidth, terrainInfoBoxHeight, tileSize);
		terrainInfoBox = new TerrainInfoBox(terrainInfoBoxGraphicMetrics, gameProperties, gameState);

		// unit-infobox
		int unitInfoBoxPosX = anchorPoint.getX() + tileSize * 2 + tileSize / 4 + 5;
		int unitInfoBoxPosY = anchorPoint.getY() + tileSize / 8;
		Point unitInfoBoxPoint = new Point(unitInfoBoxPosX, unitInfoBoxPosY);
		int unitInfoBoxWidth = tileSize * 2;
		int unitInfoBoxHeight = height - tileSize / 4;
		GraphicMetrics unitInfoBoxGraphicMetrics = new GraphicMetrics(unitInfoBoxPoint, unitInfoBoxWidth, unitInfoBoxHeight, tileSize);
		unitInfoBox = new UnitInfoBox(unitInfoBoxGraphicMetrics, gameState);
		
		// unitContained-infobox
		int unitContainedInfoBoxPosX = anchorPoint.getX() + tileSize * 4 + tileSize / 4 + 10;
		int unitContainedInfoBoxPosY = anchorPoint.getY() + tileSize / 8;
		Point unitContainedInfoBoxPoint = new Point(unitContainedInfoBoxPosX, unitContainedInfoBoxPosY);
		int unitContainedInfoBoxWidht = tileSize * 2;
		int unitContainedInfoBoxHeight = height - tileSize / 4;
		GraphicMetrics unitContainedInfoBoxGraphicMetrics = new GraphicMetrics(unitContainedInfoBoxPoint, unitContainedInfoBoxWidht, unitContainedInfoBoxHeight, tileSize);
		unitContainedInfoBox = new UnitContainedInfoBox(unitContainedInfoBoxGraphicMetrics, gameState);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(anchorPoint.getX(), anchorPoint.getY(), width, height);
		g.setColor(Color.black);
		g.drawRect(anchorPoint.getX(), anchorPoint.getY(), width, height);
		
		terrainInfoBox.paint(g);
		unitInfoBox.paint(g);
		unitContainedInfoBox.paint(g);
	}
}