package main;

import java.awt.Color;
import java.awt.Graphics;

import cursors.Cursor;
import gameObjects.GameState;
import gameObjects.GraphicMetrics;
import map.UnitGetter;
import point.Point;
import units.Unit;
import units.airMoving.TCopter;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class UnitContainedInfoBox {
	private Point anchorPoint;
	private int width;
	private int height;
	private int tileSize;
	private Cursor cursor;
	private UnitGetter unitGetter;
	
	public UnitContainedInfoBox(GraphicMetrics unitContainedInfoBoxGraphicMetrics, GameState gameState) {
		this.anchorPoint = unitContainedInfoBoxGraphicMetrics.anchorPoint;
		this.width = unitContainedInfoBoxGraphicMetrics.width;
		this.height = unitContainedInfoBoxGraphicMetrics.height;
		this.tileSize = unitContainedInfoBoxGraphicMetrics.tileSize;
		this.cursor = gameState.getCursor();
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
	}
	
	private boolean unitIsTransportingOtherUnit(Unit unit) {
		if (!unit.getUnitType().isTransportUnit()) {
			return false;
		}
		// TODO: make this more readable
		if (unit instanceof APC) {
			return ((APC)unit).isFull();
		} else if (unit.hasUnitContainer()) { // TCopter
			return unit.getUnitContainer().isFull();
		} else if (unit instanceof Lander) {
			return ((Lander)unit).getNumberOfContainedUnits() > 0;
		}
		return false;
	}
	
	// TODO: rewrite this to make it more readable
	public void paint(Graphics g) {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		if (unit != null && unitIsTransportingOtherUnit(unit)) {
			g.setColor(Color.lightGray);
			g.fillRect(anchorPoint.getX(), anchorPoint.getY(), width, height);
			if (unit instanceof APC) {
				Unit containedUnit = ((APC)unit).getContainedUnit();
				Color containedUnitColor = containedUnit.getColor();
				int containedUnitPosX = anchorPoint.getX() + (width-tileSize)/2;
				int containedUnitPosY = anchorPoint.getY() + 19;
				containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
				containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
			} else if (unit.hasUnitContainer() && !unit.getUnitContainer().isEmpty()) {
				Unit containedUnit = unit.getUnitContainer().getChosenUnit();
				Color containedUnitColor = containedUnit.getColor();
				int containedUnitPosX = anchorPoint.getX() + (width-tileSize)/2;
				int containedUnitPosY = anchorPoint.getY() + 19;
				containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
				containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
			} else if (unit instanceof Lander) {
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
			} else if (unit instanceof Cruiser) {
				// may hold two copters
			}
		}
	}	
}