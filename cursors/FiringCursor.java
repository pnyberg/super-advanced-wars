package cursors;

import java.awt.Color;
import java.awt.Graphics;

import combat.DamageHandler;
import gameObjects.MapDimension;
import main.HeroHandler;
import map.GameMap;
import map.UnitGetter;
import map.area.TerrainType;
import map.structures.Structure;
import map.structures.StructureHandler;
import units.Unit;

public class FiringCursor {
	private MapDimension mapDim;
	private UnitGetter unitGetter;
	private HeroHandler heroHandler;
	private DamageHandler damageHandler;
	private StructureHandler structureHandler;

	// TODO: to many parameters?
	public FiringCursor(MapDimension mapDim, HeroHandler heroHandler, DamageHandler damageHandler, StructureHandler structureHandler) {
		this.mapDim = mapDim;
		this.unitGetter = new UnitGetter(heroHandler);
		this.heroHandler = heroHandler;
		this.damageHandler = damageHandler;
		this.structureHandler = structureHandler;
	}
	
	// TODO: rewrite code to make it more readable
	public void paint(Graphics g, Cursor cursor, Unit chosenUnit) {
		int tileSize = mapDim.tileSize;

		int xDiff = cursor.getX() - chosenUnit.getPoint().getX();
		int yDiff = cursor.getY() - chosenUnit.getPoint().getY();

		Unit targetUnit = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY());
		Structure targetStructure = structureHandler.getStructure(cursor.getX(), cursor.getY());
		int damage = 0;
		if (targetUnit != null) {
			damage = damageHandler.getNonRNGDamageValue(chosenUnit, targetUnit);
		} else if (targetStructure != null) {
			damage = damageHandler.getStructureDamage(chosenUnit, heroHandler.getCurrentHero());
		}

		int damageFieldWidth = (damage <= 9 ? 3 * tileSize / 5 : 
									(damage <= 99 ? 4 * tileSize / 5
										: tileSize - 3));
		int damageFieldHeight = 3 * tileSize / 5;

		int paintX = cursor.getX() + 2;
		int paintY = cursor.getY() + 2;
		int dmgFieldX = cursor.getX(); // will be changed
		int dmgFieldY = cursor.getY(); // will be changed

		g.setColor(Color.black);
		g.drawOval(paintX, paintY, tileSize - 4, tileSize - 4);
		g.drawOval(paintX + 2, paintY + 2, tileSize - 8, tileSize - 8);

		g.setColor(Color.white);
		g.drawOval(paintX + 1, paintY + 1, tileSize - 6, tileSize - 6);

		if (yDiff == -tileSize) {
			dmgFieldX += tileSize;
			dmgFieldY += -damageFieldHeight;
		} else if (xDiff == tileSize) {
			dmgFieldX += tileSize;
			dmgFieldY += tileSize;
		} else if (yDiff == tileSize) {
			dmgFieldX += -damageFieldWidth;
			dmgFieldY += tileSize;
		} else { // xDiff == -1
			dmgFieldX += -damageFieldWidth;
			dmgFieldY += -damageFieldHeight;
		}

		g.setColor(Color.red);
		g.fillRect(dmgFieldX, dmgFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.black);
		g.drawRect(dmgFieldX, dmgFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.white);
		g.drawString("" + damage + "%", dmgFieldX + damageFieldWidth / 10, dmgFieldY + 2 * damageFieldHeight / 3);
	}
}