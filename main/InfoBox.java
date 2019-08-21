package main;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.GraphicMetrics;
import gameObjects.GameProperties;
import gameObjects.GameState;
import point.Point;

public class InfoBox {
	private Point anchorPoint;
	private int width;
	private int height;
	private TerrainInfoBox terrainInfoBox;
	private UnitInfoBox unitInfoBox;
	private UnitContainedInfoBox unitContainedInfoBox; 
	
	public InfoBox(GameProperties gameProperties, GameState gameState) {
		GraphicMetrics infoBoxGraphicMetrics = gameProperties.getInfoBoxGraphicMetrics();
		this.anchorPoint = infoBoxGraphicMetrics.anchorPoint;
		this.width = infoBoxGraphicMetrics.width;
		this.height = infoBoxGraphicMetrics.height;

		GraphicMetrics terrainInfoBoxGraphicMetrics = gameProperties.getTerrainInfoBoxGraphicMetrics();
		terrainInfoBox = new TerrainInfoBox(terrainInfoBoxGraphicMetrics, gameProperties, gameState);
		GraphicMetrics unitInfoBoxGraphicMetrics = gameProperties.getUnitInfoBoxGraphicMetrics();
		unitInfoBox = new UnitInfoBox(unitInfoBoxGraphicMetrics, gameState);
		GraphicMetrics unitContainedInfoBoxGraphicMetrics = gameProperties.getUnitContainedInfoBoxGraphicMetrics();
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
		if (unitInfoBox.showUnitInfo()) {
			unitInfoBox.paint(g);
		}
		if (unitContainedInfoBox.showUnitContainedInfo()) {
			unitContainedInfoBox.paint(g);
		}
	}
}