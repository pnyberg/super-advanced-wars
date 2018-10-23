package main;

import java.awt.Color;
import java.awt.Graphics;

import cursors.Cursor;
import map.GameMap;
import map.UnitGetter;
import point.Point;
import units.Unit;
import units.airMoving.TCopter;
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class UnitContainedInfoBox {
	private Point point;
	private int width;
	private int height;
	private int tileSize;
	private GameMap gameMap;
	private Cursor cursor;
	private UnitGetter unitGetter;
	
	public UnitContainedInfoBox(Point point, int width, int height, int tileSize, GameMap gameMap, Cursor cursor, UnitGetter unitGetter) {
		this.point = point;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.gameMap = gameMap;
		this.cursor = cursor;
		this.unitGetter = unitGetter;
	}
	
	private boolean unitIsTransportingOtherUnit(Unit unit) {
		if (!unit.getUnitType().isTransportUnit()) {
			return false;
		}
		if (unit instanceof APC) {
			return ((APC)unit).isFull();
		} else if (unit instanceof TCopter) {
			return ((TCopter)unit).isFull();
		} else if (unit instanceof Lander) {
			return ((Lander)unit).getNumberOfContainedUnits() > 0;
		}
		return false;
	}
	
	public void paint(Graphics g) {
		Unit unit = unitGetter.getAnyUnit(cursor.getX() * tileSize, cursor.getY() * tileSize);
		
		if (unit != null && unitIsTransportingOtherUnit(unit)) {
			g.setColor(Color.lightGray);
			g.fillRect(point.getX(), point.getY(), width, height);
		}
	}
}