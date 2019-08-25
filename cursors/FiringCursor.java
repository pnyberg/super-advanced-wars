package cursors;

import java.awt.Color;
import java.awt.Graphics;

import combat.DamageHandler;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import hero.HeroHandler;
import map.UnitGetter;
import map.structures.Structure;
import map.structures.StructureHandler;
import point.Point;
import units.Unit;

public class FiringCursor {
	private MapDimension mapDimension;
	private HeroHandler heroHandler;
	private UnitGetter unitGetter;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler;

	public FiringCursor(GameProperties gameProperties, GameState gameState) {
		this.mapDimension = gameProperties.getMapDimension();
		this.heroHandler = gameState.getHeroHandler();
		this.unitGetter = new UnitGetter(heroHandler);
		this.damageHandler = new DamageHandler(gameState.getHeroHandler(), gameProperties.getGameMap());
		this.structureHandler = new StructureHandler(gameState, gameProperties.getMapDimension());
	}
	
	public void paint(Graphics g, Cursor cursor, Unit chosenUnit) {
		paintTargetCircle(g, cursor);
		paintDamageField(g, cursor, chosenUnit);
	}
	
	private void paintTargetCircle(Graphics g, Cursor cursor) {
		int tileSize = mapDimension.tileSize;
		int paintX = cursor.getX() + 2;
		int paintY = cursor.getY() + 2;

		g.setColor(Color.black);
		g.drawOval(paintX, paintY, tileSize - 4, tileSize - 4);
		g.drawOval(paintX + 2, paintY + 2, tileSize - 8, tileSize - 8);

		g.setColor(Color.white);
		g.drawOval(paintX + 1, paintY + 1, tileSize - 6, tileSize - 6);
	}
	
	private void paintDamageField(Graphics g, Cursor cursor, Unit chosenUnit) {
		int tileSize = mapDimension.tileSize;
		int damage = getDamageToBeShown(cursor, chosenUnit);
		int damageFieldWidth = getDamageFieldWith(damage);
		int damageFieldHeight = 3 * tileSize / 5;
		Point damageFieldAnchorPoint = getDamageFieldAnchorPoint(cursor, chosenUnit, damageFieldWidth, damageFieldHeight);
		int damageFieldX = damageFieldAnchorPoint.getX();
		int damageFieldY = damageFieldAnchorPoint.getY();

		g.setColor(Color.red);
		g.fillRect(damageFieldX, damageFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.black);
		g.drawRect(damageFieldX, damageFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.white);
		g.drawString("" + damage + "%", damageFieldX + damageFieldWidth / 10, damageFieldY + 2 * damageFieldHeight / 3);
	}
	
	private int getDamageToBeShown(Cursor cursor, Unit chosenUnit) {
		int damage = 0;
		Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY());
		Structure targetStructure = structureHandler.getStructure(cursor.getX(), cursor.getY());
		if (targetUnit != null) {
			damage = damageHandler.getNonRNGDamageValue(chosenUnit, targetUnit);
		} else if (targetStructure != null) {
			damage = damageHandler.getStructureDamage(chosenUnit, heroHandler.getCurrentHero());
		}
		return damage;
	}
	
	private int getDamageFieldWith(int damage) {
		int tileSize = mapDimension.tileSize;
		if (damage <= 9) {
			return 3 * tileSize / 5;
		} else if (damage <= 99) {
			return 4 * tileSize / 5;
		}
		return tileSize - 3;
	}
	
	// TODO: rewrite this code to make it more readable
	private Point getDamageFieldAnchorPoint(Cursor cursor, Unit chosenUnit, int dmgFieldWidth, int dmgFieldHeight) {
		int damageFieldX = cursor.getX();
		int damageFieldY = cursor.getY();
		int tileSize = mapDimension.tileSize;
		int xDiff = cursor.getX() - chosenUnit.getPosition().getX();
		int yDiff = cursor.getY() - chosenUnit.getPosition().getY();

		if (yDiff == -tileSize) {
			damageFieldX += tileSize;
			damageFieldY += -dmgFieldHeight;
		} else if (xDiff == tileSize) {
			damageFieldX += tileSize;
			damageFieldY += tileSize;
		} else if (yDiff == tileSize) {
			damageFieldX += -dmgFieldWidth;
			damageFieldY += tileSize;
		} else { // xDiff == -1
			damageFieldX += -dmgFieldWidth;
			damageFieldY += -dmgFieldHeight;
		}
		if (damageFieldY < 0) {
			damageFieldY = 0;
		} else if (damageFieldX >= mapDimension.getTileWidth() * tileSize) {
			damageFieldX = mapDimension.getTileWidth() * tileSize - dmgFieldWidth;
		} else if (damageFieldY >= mapDimension.getTileHeight() * tileSize) {
			damageFieldY = mapDimension.getTileHeight() * tileSize - dmgFieldHeight;
		} else if (damageFieldX < 0) {
			damageFieldX = 0;
		}
		return new Point(damageFieldX, damageFieldY);
	}
}