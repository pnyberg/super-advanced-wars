package main;

import java.awt.Color;
import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.GameState;
import gameObjects.GraphicMetrics;
import map.UnitGetter;
import point.Point;
import units.Unit;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class UnitContainedInfoBox {
	private GraphicMetrics unitContainedInfoBoxGraphicMetrics;
	private Cursor cursor;
	private UnitGetter unitGetter;
	
	public UnitContainedInfoBox(GraphicMetrics unitContainedInfoBoxGraphicMetrics, GameState gameState) {
		this.unitContainedInfoBoxGraphicMetrics = unitContainedInfoBoxGraphicMetrics;
		this.cursor = gameState.getCursor();
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
	}
	
	private boolean unitIsTransportingOtherUnit(Unit unit) {
		if (!unit.getUnitType().isTransportUnit()) {
			return false;
		}
		if (unit.hasUnitContainer()) { // APC / TCopter / Lander
			return !unit.getUnitContainer().isEmpty();
		}
		return false;
	}
	
	public boolean showUnitContainedInfo() {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		return unit != null && unitIsTransportingOtherUnit(unit);
	}

	public void paint(Graphics g) {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		Point anchorPoint = unitContainedInfoBoxGraphicMetrics.anchorPoint;
		int width = unitContainedInfoBoxGraphicMetrics.width;
		int height = unitContainedInfoBoxGraphicMetrics.height;

		g.setColor(Color.lightGray);
		g.fillRect(anchorPoint.getX(), anchorPoint.getY(), width, height);

		if (unit instanceof APC) {
			paintAPCInfo(g);
		} else if (unit.hasUnitContainer() && !unit.getUnitContainer().isEmpty()) {
			paintContainerInfo(g);
		} else if (unit instanceof Lander) {
			paintLanderInfo(g);
		} else if (unit instanceof Cruiser) {
			// TODO: write code
			// may hold two copters
		}
	}
	
	private void paintAPCInfo(Graphics g) {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		Point anchorPoint = unitContainedInfoBoxGraphicMetrics.anchorPoint;
		int width = unitContainedInfoBoxGraphicMetrics.width;
		int tileSize = unitContainedInfoBoxGraphicMetrics.tileSize;
		Unit containedUnit = ((APC)unit).getContainedUnit();
		Color containedUnitColor = containedUnit.getColor();
		int containedUnitPosX = anchorPoint.getX() + (width-tileSize)/2;
		int containedUnitPosY = anchorPoint.getY() + 19;

		containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
		containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
	}
	
	private void paintContainerInfo(Graphics g) {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		Point anchorPoint = unitContainedInfoBoxGraphicMetrics.anchorPoint;
		int width = unitContainedInfoBoxGraphicMetrics.width;
		int tileSize = unitContainedInfoBoxGraphicMetrics.tileSize;
		Unit containedUnit = unit.getUnitContainer().getChosenUnit();
		Color containedUnitColor = containedUnit.getColor();
		int containedUnitPosX = anchorPoint.getX() + (width-tileSize)/2;
		int containedUnitPosY = anchorPoint.getY() + 19;

		containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
		containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
	}
	
	private void paintLanderInfo(Graphics g) {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		Point anchorPoint = unitContainedInfoBoxGraphicMetrics.anchorPoint;
		int width = unitContainedInfoBoxGraphicMetrics.width;
		int tileSize = unitContainedInfoBoxGraphicMetrics.tileSize;

		if (((Lander)unit).getNumberOfContainedUnits() > 1) {
			Unit containedUnit = ((Lander)unit).getUnit(0);
			Color containedUnitColor = containedUnit.getColor();
			int containedUnitPosX = anchorPoint.getX() + (width-tileSize)/2;
			int containedUnitPosY = anchorPoint.getY() + 15;
			containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
			containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
			containedUnit = ((Lander)unit).getUnit(1);
			containedUnitColor = containedUnit.getColor();

			containedUnitPosX = anchorPoint.getX() + (width-tileSize)/2;
			containedUnitPosY = anchorPoint.getY() + 20 + tileSize;
			containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
			containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
		} else if (((Lander)unit).getNumberOfContainedUnits() > 0) {
			Unit containedUnit = ((Lander)unit).getUnit(0);
			Color containedUnitColor = containedUnit.getColor();
			int containedUnitPosX = anchorPoint.getX() + (width-tileSize)/2;
			int containedUnitPosY = anchorPoint.getY() + 15 + tileSize / 2;
			containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
			containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
		}
	}
}