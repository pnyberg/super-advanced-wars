package graphics;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.MapDim;
import main.HeroHandler;
import units.Unit;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.tireMoving.Recon;

public class CommanderView {
	private MapDim mapDim;
	private HeroHandler heroHandler;
	
	public CommanderView(MapDim mapDim, HeroHandler heroHandler) {
		this.mapDim = mapDim;
		this.heroHandler = heroHandler;
	}
	
	public void paintView(Graphics g) {
		Color heroColor = heroHandler.getCurrentHero().getColor();
		Infantry infantry = new Infantry(1, 1, heroColor);
		Mech mech = new Mech(1, 2, heroColor);
		Recon recon = new Recon(1, 3, heroColor);

		infantry.paint(g, mapDim.tileSize);
		paintPowerBar(g, infantry, 100);
		mech.paint(g, mapDim.tileSize);
		paintPowerBar(g, mech, 100);
		recon.paint(g, mapDim.tileSize);
		paintPowerBar(g, recon, 100);
	}
	
	private void paintPowerBar(Graphics g, Unit unit, int powerLevel) {
		int x = unit.getPoint().getX() + mapDim.tileSize;
		int y = unit.getPoint().getY() + 5;
		Color lightRed = Color.RED.brighter();
		g.setColor(lightRed);
		g.fillRect(x, y, 3 * mapDim.tileSize / 2, mapDim.tileSize / 2);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 3 * mapDim.tileSize / 2, mapDim.tileSize / 2);
	}
}