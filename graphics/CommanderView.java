package graphics;

import java.awt.Color;
import java.awt.Graphics;

import combat.AttackValueCalculator;
import combat.DefenceValueCalculator;
import gameObjects.MapDim;
import main.HeroHandler;
import units.Unit;
import units.UnitType;
import units.airMoving.BCopter;
import units.airMoving.Bomber;
import units.airMoving.Fighter;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Battleship;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
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
	private MapDim mapDim;
	private HeroHandler heroHandler;
	private AttackValueCalculator attackValueCalculator;
	private DefenceValueCalculator defenceValueCalculator;
	private Unit[] unitCollection;
	
	public CommanderView(MapDim mapDim, HeroHandler heroHandler, AttackValueCalculator attackValueCalculator, DefenceValueCalculator defenceValueCalculator) {
		this.mapDim = mapDim;
		this.heroHandler = heroHandler;
		this.attackValueCalculator = attackValueCalculator;
		this.defenceValueCalculator = defenceValueCalculator;
		initUnitCollection();
	}
	
	private void initUnitCollection() {
		Color heroColor = heroHandler.getCurrentHero().getColor();
		Infantry infantry = new Infantry(1 * mapDim.tileSize, 3 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Mech mech = new Mech(5 * mapDim.tileSize, 3 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Recon recon = new Recon(9 * mapDim.tileSize, 3 * mapDim.tileSize, heroColor, mapDim.tileSize);
		APC apc = new APC(13 * mapDim.tileSize, 3 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Tank tank = new Tank(1 * mapDim.tileSize, 4 * mapDim.tileSize, heroColor, mapDim.tileSize);
		MDTank mdTank = new MDTank(5 * mapDim.tileSize, 4 * mapDim.tileSize, heroColor, mapDim.tileSize);
		AAir aair = new AAir(9 * mapDim.tileSize, 4 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Neotank neotank = new Neotank(13 * mapDim.tileSize, 4 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Artillery artillery = new Artillery(1 * mapDim.tileSize, 5 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Rocket rocket = new Rocket(5 * mapDim.tileSize, 5 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Missiles missiles = new Missiles(9 * mapDim.tileSize, 5 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Fighter fighter = new Fighter(1 * mapDim.tileSize, 6 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Bomber bomber = new Bomber(5 * mapDim.tileSize, 6 * mapDim.tileSize, heroColor, mapDim.tileSize);
		BCopter bCopter = new BCopter(9 * mapDim.tileSize, 6 * mapDim.tileSize, heroColor, mapDim.tileSize);
		TCopter tCopter = new TCopter(13 * mapDim.tileSize, 6 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Battleship bship = new Battleship(1 * mapDim.tileSize, 7 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Cruiser cruiser = new Cruiser(5 * mapDim.tileSize, 7 * mapDim.tileSize, heroColor, mapDim.tileSize);
		//Sub sub = new Sub(9 * mapDim.tileSize, 7 * mapDim.tileSize, heroColor, mapDim.tileSize);
		Lander lander = new Lander(13 * mapDim.tileSize, 7 * mapDim.tileSize, heroColor, mapDim.tileSize);
		
		unitCollection = new Unit[]{infantry, mech, recon, apc, tank, mdTank, aair, neotank, artillery, rocket, 
									missiles, fighter, bomber, bCopter, tCopter, bship, cruiser, 
									infantry/*sub*/, lander};
		
		for (Unit unit : unitCollection) {
			unit.regulateActive(true);
		}
	}
	
	public void paintView(Graphics g) {
		paintUnitWithPowerBar(g, UnitType.INFANTRY.unitIndex());
		paintUnitWithPowerBar(g, UnitType.MECH.unitIndex());
		paintUnitWithPowerBar(g, UnitType.RECON.unitIndex());
		paintUnitWithPowerBar(g, UnitType.APC_v.unitIndex());

		paintUnitWithPowerBar(g, UnitType.TANK.unitIndex());
		paintUnitWithPowerBar(g, UnitType.MDTANK.unitIndex());
		paintUnitWithPowerBar(g, UnitType.A_AIR.unitIndex());
		paintUnitWithPowerBar(g, UnitType.NEOTANK.unitIndex());

		paintUnitWithPowerBar(g, UnitType.ARTILLERY.unitIndex());
		paintUnitWithPowerBar(g, UnitType.ROCKET.unitIndex());
		paintUnitWithPowerBar(g, UnitType.MISSILES.unitIndex());

		paintUnitWithPowerBar(g, UnitType.FIGHTER.unitIndex());
		paintUnitWithPowerBar(g, UnitType.BOMBER.unitIndex());
		paintUnitWithPowerBar(g, UnitType.BCOPTER.unitIndex());
		paintUnitWithPowerBar(g, UnitType.TCOPTER.unitIndex());

		paintUnitWithPowerBar(g, UnitType.BATTLESHIP.unitIndex());
		paintUnitWithPowerBar(g, UnitType.CRUISER.unitIndex());
		//paintUnitWithPowerBar(g, UnitType.SUB.unitIndex());
		paintUnitWithPowerBar(g, UnitType.LANDER.unitIndex());
	}
	
	private void paintUnitWithPowerBar(Graphics g, int unitIndex) {
		int powerLevel = attackValueCalculator.calculateAttackValue(heroHandler.getCurrentHero(), unitIndex);
		unitCollection[unitIndex].paint(g, mapDim.tileSize);
		paintPowerBar(g, unitCollection[unitIndex], powerLevel);
	}
	
	private void paintPowerBar(Graphics g, Unit unit, int powerLevel) {
		int x = unit.getPoint().getX() + 3 * mapDim.tileSize / 2;
		int y = unit.getPoint().getY() + mapDim.tileSize / 3;
		int width = 5 * mapDim.tileSize / 3;
		int height = mapDim.tileSize / 3;
		Color lightRed = new Color(255, 102, 102); // lightish red
	
		if (powerLevel > 100) {
			int bonusPowerWidth = (powerLevel-100) * width / 100;
			int bonusPowerHeight = mapDim.tileSize / 4;
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
			
		} else {
			g.setColor(lightRed);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, 5 * mapDim.tileSize / 3, mapDim.tileSize / 3);
		}
	}
}