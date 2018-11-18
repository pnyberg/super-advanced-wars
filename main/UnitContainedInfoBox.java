package main;

import java.awt.Color;
import java.awt.Graphics;

import cursors.Cursor;
import map.UnitGetter;
import point.Point;
import units.Unit;
import units.airMoving.TCopter;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class UnitContainedInfoBox {
	private Point point;
	private int width;
	private int height;
	private int tileSize;
	private Cursor cursor;
	private UnitGetter unitGetter;
	
	public UnitContainedInfoBox(Point point, int width, int height, int tileSize, Cursor cursor, UnitGetter unitGetter) {
		this.point = point;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.cursor = cursor;
		this.unitGetter = unitGetter;
	}
	
	private boolean unitIsTransportingOtherUnit(Unit unit) {
		if (!unit.getUnitType().isTransportUnit()) {
			return false;
		}
		if (unit instanceof APC) {
			return ((APC)unit).isFull();
		} else if (unit.hasUnitContainer()) {
			return unit.getUnitContainer().isFull();
		} else if (unit instanceof Lander) {
			return ((Lander)unit).getNumberOfContainedUnits() > 0;
		}
		return false;
	}
	
	public void paint(Graphics g) {
		Unit unit = unitGetter.getAnyUnit(cursor.getX(), cursor.getY());
		if (unit != null && unitIsTransportingOtherUnit(unit)) {
			g.setColor(Color.lightGray);
			g.fillRect(point.getX(), point.getY(), width, height);
			if (unit instanceof APC) {
				Unit containedUnit = ((APC)unit).getContainedUnit();
				Color containedUnitColor = containedUnit.getColor();
				int containedUnitPosX = point.getX() + (width-tileSize)/2;
				int containedUnitPosY = point.getY() + 19;
				containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
				containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
			} else if (unit.hasUnitContainer() && !unit.getUnitContainer().isEmpty()) {
				Unit containedUnit = unit.getUnitContainer().getChosenUnit();
				Color containedUnitColor = containedUnit.getColor();
				int containedUnitPosX = point.getX() + (width-tileSize)/2;
				int containedUnitPosY = point.getY() + 19;
				containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
				containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
			} else if (unit instanceof Lander) {
				if (((Lander)unit).getNumberOfContainedUnits() > 1) {
					Unit containedUnit = ((Lander)unit).getUnit(0);
					Color containedUnitColor = containedUnit.getColor();
					int containedUnitPosX = point.getX() + (width-tileSize)/2;
					int containedUnitPosY = point.getY() + 15;
					containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
					containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
					containedUnit = ((Lander)unit).getUnit(1);
					containedUnitColor = containedUnit.getColor();

					containedUnitPosX = point.getX() + (width-tileSize)/2;
					containedUnitPosY = point.getY() + 20 + tileSize;
					containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
					containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
				} else if (((Lander)unit).getNumberOfContainedUnits() > 0) {
					Unit containedUnit = ((Lander)unit).getUnit(0);
					Color containedUnitColor = containedUnit.getColor();
					int containedUnitPosX = point.getX() + (width-tileSize)/2;
					int containedUnitPosY = point.getY() + 15 + tileSize / 2;
					containedUnit.getUnitImage().paint(g, containedUnitPosX, containedUnitPosY, containedUnitColor);
					containedUnit.getUnitHealth().paintHP(g, containedUnitPosX, containedUnitPosY);
				}
			} else if (unit instanceof Cruiser) {
				// may hold two copters
			}
		}
	}	
}