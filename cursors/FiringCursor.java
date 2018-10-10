package cursors;

import java.awt.Color;
import java.awt.Graphics;

import combat.DamageHandler;
import gameObjects.MapDim;
import hero.Hero;
import hero.HeroPortrait;
import main.HeroHandler;
import map.UnitGetter;
import map.area.Area;
import map.area.TerrainType;
import units.Unit;

public class FiringCursor {
	private MapDim mapDim;
	private Area[][] map;
	private UnitGetter unitGetter;
	private HeroHandler heroHandler;
	private DamageHandler damageHandler;
	
	public FiringCursor(MapDim mapDimension, Area[][] map, UnitGetter unitGetter, HeroHandler heroHandler, DamageHandler damageHandler) {
		this.mapDim = mapDimension;
		this.map = map;
		this.unitGetter = unitGetter;
		this.heroHandler = heroHandler;
		this.damageHandler = damageHandler;
	}
	
	public void paint(Graphics g, Cursor cursor, Unit chosenUnit) {
		int tileSize = mapDim.tileSize;

		int x = cursor.getX() * tileSize;
		int y = cursor.getY() * tileSize;
		
		int xDiff = x - chosenUnit.getPoint().getX();
		int yDiff = y - chosenUnit.getPoint().getY();

		Unit targetUnit = unitGetter.getNonFriendlyUnit(x, y);

		Hero chosenHero = heroHandler.getCurrentHero();
		Hero targetHero = heroHandler.getHeroFromUnit(targetUnit);
		TerrainType terrainType = map[x / tileSize][y / tileSize].getTerrainType();
		int damage = damageHandler.getNonRNGDamageValue(chosenUnit, chosenHero, targetUnit, targetHero, terrainType);
		int damageFieldWidth = (damage <= 9 ? 3 * tileSize / 5 : 
									(damage <= 99 ? 4 * tileSize / 5
										: tileSize - 3));
		int damageFieldHeight = 3 * tileSize / 5;

		int paintX = x + 2;
		int paintY = y + 2;
		int dmgFieldX = x; // will be changed
		int dmgFieldY = y; // will be changed

		g.setColor(Color.black);
		g.drawOval(paintX, paintY, tileSize - 4, tileSize - 4);
		g.drawOval(paintX + 2, paintY + 2, tileSize - 8, tileSize - 8);

		g.setColor(Color.white);
		g.drawOval(paintX + 1, paintY + 1, tileSize - 6, tileSize - 6);

		if (yDiff == -1) {
			dmgFieldX += tileSize;
			dmgFieldY += -damageFieldHeight;
		} else if (xDiff == 1) {
			dmgFieldX += tileSize;
			dmgFieldY += tileSize;
		} else if (yDiff == 1) {
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