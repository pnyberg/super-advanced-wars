package graphics;

import java.awt.Color;
import java.awt.Graphics;

import combat.AttackValueCalculator;
import combat.DefenceValueCalculator;
import gameObjects.MapDimension;
import hero.HeroHandler;
import unitUtils.UnitType;
import units.Unit;
import units.airMoving.BCopter;
import units.airMoving.Bomber;
import units.airMoving.Fighter;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Battleship;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.seaMoving.Sub;
import units.tireMoving.Missiles;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.APC;
import units.treadMoving.Artillery;
import units.treadMoving.MDTank;
import units.treadMoving.Neotank;
import units.treadMoving.Tank;

public class CommanderView {
	private MapDimension mapDimension;
	private HeroHandler heroHandler;
	private AttackValueCalculator attackValueCalculator;
	private DefenceValueCalculator defenceValueCalculator;
	private int[] unitColPos;
	private int[] unitRowPos;
	private Unit[] unitCollection;
	
	public CommanderView(MapDimension mapDimension, HeroHandler heroHandler) {
		this.mapDimension = mapDimension;
		this.heroHandler = heroHandler;
		attackValueCalculator = new AttackValueCalculator();
		defenceValueCalculator = new DefenceValueCalculator();
		unitColPos = new int[4]; // four rows
		unitRowPos = new int[5]; // five columns
		initUnitCollection();
	}
	
	private void initUnitCollection() {
		int tileSize = mapDimension.tileSize;
		Color heroColor = heroHandler.getCurrentHero().getColor();
		
		unitColPos[0] = tileSize;
		unitColPos[1] = 5 * tileSize;
		unitColPos[2] = 9 * tileSize;
		unitColPos[3] = 13 * tileSize;
		
		unitRowPos[0] = 5 * tileSize / 2;
		unitRowPos[1] = 8 * tileSize / 2;
		unitRowPos[2] = 11 * tileSize / 2;
		unitRowPos[3] = 14 * tileSize / 2;
		unitRowPos[4] = 17 * tileSize / 2;
		
		Infantry infantry = new Infantry(unitColPos[0], unitRowPos[0], heroColor, tileSize);
		Mech mech = new Mech(unitColPos[1], unitRowPos[0], heroColor, tileSize);
		Recon recon = new Recon(unitColPos[2], unitRowPos[0], heroColor, tileSize);
		APC apc = new APC(unitColPos[3], unitRowPos[0], heroColor, tileSize);
		Tank tank = new Tank(unitColPos[0], unitRowPos[1], heroColor, tileSize);
		MDTank mdTank = new MDTank(unitColPos[1], unitRowPos[1], heroColor, tileSize);
		AAir aair = new AAir(unitColPos[2], unitRowPos[1], heroColor, tileSize);
		Neotank neotank = new Neotank(unitColPos[3], unitRowPos[1], heroColor, tileSize);
		Artillery artillery = new Artillery(unitColPos[0], unitRowPos[2], heroColor, tileSize);
		Rocket rocket = new Rocket(unitColPos[1], unitRowPos[2], heroColor, tileSize);
		Missiles missiles = new Missiles(unitColPos[2], unitRowPos[2], heroColor, tileSize);
		Fighter fighter = new Fighter(unitColPos[0], unitRowPos[3], heroColor, tileSize);
		Bomber bomber = new Bomber(unitColPos[1], unitRowPos[3], heroColor, tileSize);
		BCopter bCopter = new BCopter(unitColPos[2], unitRowPos[3], heroColor, tileSize);
		TCopter tCopter = new TCopter(unitColPos[3], unitRowPos[3], heroColor, tileSize);
		Battleship bship = new Battleship(unitColPos[0], unitRowPos[4], heroColor, tileSize);
		Cruiser cruiser = new Cruiser(unitColPos[1], unitRowPos[4], heroColor, tileSize);
		Sub sub = new Sub(unitColPos[2], unitRowPos[4], heroColor, tileSize);
		Lander lander = new Lander(unitColPos[3], unitRowPos[4], heroColor, tileSize);

		unitCollection = new Unit[]{
				infantry, mech, recon, apc, 
				tank, mdTank, aair, neotank, 
				artillery, rocket, missiles, 
				fighter, bomber, bCopter, tCopter, 
				bship, cruiser, sub, lander
		};
		for(Unit unit : unitCollection) {
			unit.regulateActive(true);
		}
	}
	
	public void paintView(Graphics g) {
		paintLines(g);
		for(UnitType unitType : UnitType.allUnitTypes) {
			paintUnitWithPowerBar(g, unitType.unitIndex());
		}
	}
	
	private void paintLines(Graphics g) {
		int lineHeight = 7;
		int rowOffset = mapDimension.tileSize - lineHeight;
		Color lineColor = new Color(255, 168, 168); // lightcoral
		int lineStartX = unitColPos[0] - mapDimension.tileSize / 2;
		int lineStopX = unitColPos[unitColPos.length - 1] + 9 * mapDimension.tileSize / 2;
		int lineLength = lineStopX - lineStartX;
		g.setColor(lineColor);
		for(int rowY : unitRowPos) {
			g.fillRect(lineStartX, rowY + rowOffset, lineLength, lineHeight);
		}
	}
	
	private void paintUnitWithPowerBar(Graphics g, int unitIndex) {
		int powerLevel = attackValueCalculator.calculateAttackValue(heroHandler.getCurrentHero(), unitIndex);
		unitCollection[unitIndex].paint(g, mapDimension.tileSize);
		paintPowerBar(g, unitCollection[unitIndex], powerLevel);
	}
	
	private void paintPowerBar(Graphics g, Unit unit, int powerLevel) {
		int x = unit.getPosition().getX() + 3 * mapDimension.tileSize / 2;
		int y = unit.getPosition().getY() + mapDimension.tileSize / 8;
		int width = 5 * mapDimension.tileSize / 3;
		int height = mapDimension.tileSize / 3;
		Color lightRed = new Color(255, 102, 102); // lightish red
		int shadowOffset = 3;
	
		g.setColor(Color.gray);
		g.fillRect(x + shadowOffset, y + shadowOffset, width, height);
		if(powerLevel > 100) {
			int bonusPowerWidth = (powerLevel-100) * width / 100;
			int bonusPowerHeight = mapDimension.tileSize / 4;
			g.setColor(Color.yellow);
			g.fillRect(x, y-bonusPowerHeight, bonusPowerWidth, height + bonusPowerHeight);
			g.setColor(lightRed);
			g.fillRect(x+bonusPowerWidth, y, width-bonusPowerWidth, height);
			g.setColor(Color.black);
			g.drawLine(x, y-bonusPowerHeight, x+bonusPowerWidth, y-bonusPowerHeight);
			g.drawLine(x+bonusPowerWidth, y-bonusPowerHeight, x+bonusPowerWidth, y);
			g.drawLine(x+bonusPowerWidth, y, x+width, y);
			g.drawLine(x+width, y, x+width, y+height);
			g.drawLine(x+width, y+height, x, y+height);
			g.drawLine(x, y+height, x, y-bonusPowerHeight);
		} else if (powerLevel < 100) {
			// TODO: implement lower power-level
		} else {
			g.setColor(lightRed);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, 5 * mapDimension.tileSize / 3, mapDimension.tileSize / 3);
		}
	}
}