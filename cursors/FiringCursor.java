package cursors;

import java.awt.Color;
import java.awt.Graphics;

import combat.DamageHandler;
import gameObjects.GameProperties;
import gameObjects.GameState;
import hero.HeroHandler;
import map.UnitGetter;
import map.structures.Structure;
import map.structures.StructureHandler;
import point.Point;
import units.Unit;

public class FiringCursor {
	private GameProperties gameProperties;
	private GameState gameState;

	public FiringCursor(GameProperties gameProperties, GameState gameState) {
		this.gameProperties	= gameProperties;
		this.gameState		= gameState;
	}
	
	public void paint(Graphics g, Cursor cursor, Unit chosenUnit) {
		paintTargetCircle(g, cursor);
		paintDamageField(g, cursor, chosenUnit);
	}
	
	private void paintTargetCircle(Graphics g, Cursor cursor) {
		int tileSize = gameProperties.getMapDimension().tileSize;
		int paintX = cursor.getX() + 2;
		int paintY = cursor.getY() + 2;

		g.setColor(Color.black);
		g.drawOval(paintX, paintY, tileSize - 4, tileSize - 4);
		g.drawOval(paintX + 2, paintY + 2, tileSize - 8, tileSize - 8);

		g.setColor(Color.white);
		g.drawOval(paintX + 1, paintY + 1, tileSize - 6, tileSize - 6);
	}
	
	private void paintDamageField(Graphics g, Cursor cursor, Unit chosenUnit) {
		int damage				= getDamageToBeShown(cursor, chosenUnit);
		int damageFieldWidth	= getDamageFieldWith(damage);
		int damageFieldHeight	= getDamageFieldHeight();

		Point damageFieldAnchorPoint	= getDamageFieldAnchorPoint(cursor, chosenUnit);
		int damageFieldX				= damageFieldAnchorPoint.getX();
		int damageFieldY				= damageFieldAnchorPoint.getY();

		int damageStringX	= damageFieldX + damageFieldWidth / 10;
		int damageStringY	= damageFieldY + 2 * damageFieldHeight / 3;

		g.setColor(Color.red);
		g.fillRect(damageFieldX, damageFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.black);
		g.drawRect(damageFieldX, damageFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.white);
		g.drawString("" + damage + "%", damageStringX, damageStringY);
	}

	// TODO: move to DamageHandler
	private int getDamageToBeShown(Cursor cursor, Unit chosenUnit) {
		HeroHandler heroHandler				= gameState.getHeroHandler();
		UnitGetter unitGetter				= new UnitGetter(heroHandler);
		DamageHandler damageHandler			= new DamageHandler(heroHandler, gameProperties.getGameMap());
		StructureHandler structureHandler	= new StructureHandler(gameState, gameProperties.getMapDimension());

		int damage = 0;
		Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY());
		Structure targetStructure = structureHandler.getStructure(cursor.getX(), cursor.getY());
		if(targetUnit != null) {
			damage = damageHandler.getNonRNGDamageValue(chosenUnit, targetUnit);
		} else if(targetStructure != null) {
			damage = damageHandler.getStructureDamage(chosenUnit, heroHandler.getCurrentHero());
		}
		return damage;
	}
	
	private int getDamageFieldWith(int damage) {
		int tileSize = gameProperties.getMapDimension().tileSize;

		if(damage <= 9) {
			return 3 * tileSize / 5;
		} else if(damage <= 99) {
			return 4 * tileSize / 5;
		}
		return tileSize - 3;
	}
	
	private int getDamageFieldHeight() {
		int tileSize = gameProperties.getMapDimension().tileSize;

		return 3 * tileSize / 5;
	}
	
	private Point getDamageFieldAnchorPoint(Cursor cursor, Unit chosenUnit) {
		int damage				= getDamageToBeShown(cursor, chosenUnit);
		int damageFieldWidth	= getDamageFieldWith(damage);
		int damageFieldHeight	= getDamageFieldHeight();
		int damageFieldX		= cursor.getX();
		int damageFieldY		= cursor.getY();
	
		int tileSize	= gameProperties.getMapDimension().tileSize;
		int tileWidth	= gameProperties.getMapDimension().getTileWidth();
		int tileHeight	= gameProperties.getMapDimension().getTileHeight();

		int xDiff	= cursor.getX() - chosenUnit.getPosition().getX();
		int yDiff	= cursor.getY() - chosenUnit.getPosition().getY();

		if(yDiff == -tileSize) {
			damageFieldX += tileSize;
			damageFieldY += -damageFieldHeight;
		} else if(xDiff == tileSize) {
			damageFieldX += tileSize;
			damageFieldY += tileSize;
		} else if(yDiff == tileSize) {
			damageFieldX += -damageFieldWidth;
			damageFieldY += tileSize;
		} else { // xDiff == -1
			damageFieldX += -damageFieldWidth;
			damageFieldY += -damageFieldHeight;
		}
		if(damageFieldY < 0) {
			damageFieldY = 0;
		} else if(damageFieldX >= tileWidth * tileSize) {
			damageFieldX = tileWidth * tileSize - damageFieldWidth;
		} else if(damageFieldY >= tileHeight * tileSize) {
			damageFieldY = tileHeight * tileSize - damageFieldHeight;
		} else if(damageFieldX < 0) {
			damageFieldX = 0;
		}
		return new Point(damageFieldX, damageFieldY);
	}
}