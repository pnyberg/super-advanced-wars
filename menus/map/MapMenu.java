package menus.map;

import java.awt.Graphics;

import hero.heroPower.HeroPowerMeter;
import main.HeroHandler;
import menus.Menu;

public class MapMenu extends Menu {
	private String[] menuTexts = {	"CO", 
									"Intel", 
									"Power", 
									"Super Power", 
									"Options", 
									"Save", 
									"End turn"
								};
	private HeroHandler heroHandler;

	public MapMenu(int tileSize, HeroHandler heroHandler) {
		super(tileSize);

		this.heroHandler = heroHandler;
	}

	public int getNumberOfRows() {
		HeroPowerMeter heroPowerMeter = heroHandler.getCurrentHero().getHeroPower().getHeroPowerMeter();
		return menuTexts.length + (heroPowerMeter.powerUsable() ? 0 : -1) + (heroPowerMeter.superPowerUsable() ? 0 : -1);
	}
	
	public boolean atCoRow() {
		return menuIndex == 0;
	}
	
	public boolean atPowerRow() {
		if (!heroHandler.getCurrentHero().getHeroPower().getHeroPowerMeter().powerUsable()) {
			return false;
		}
		return menuIndex == 2;
	}
	
	public boolean atSuperPowerRow() {
		if (!heroHandler.getCurrentHero().getHeroPower().getHeroPowerMeter().superPowerUsable()) {
			return false;
		}
		return menuIndex == 3;
	}

	public void paint(Graphics g) {
		paintMenuBackground(g);
		paintMenuForeground(g);
		paintArrow(g);
	}
	
	private void paintMenuForeground(Graphics g) {
		HeroPowerMeter heroPowerMeter = heroHandler.getCurrentHero().getHeroPower().getHeroPowerMeter();
		int xAlign = dimensionValues.getTileSize() / 2 + dimensionValues.getAlignX();
		int yAlign = dimensionValues.getTileSize() / 2 + dimensionValues.getAlignY();
		int rowHelpIndex = 1;
		for (int k = 0 ; k < menuTexts.length ; k++) {
			if (k == 2 && !heroPowerMeter.powerUsable()) {
				continue;
			}
			if (k == 3 && !heroPowerMeter.superPowerUsable()) {
				continue;
			}
			g.drawString(menuTexts[k], x + xAlign, y + yAlign + dimensionValues.getMenuRowHeight() * rowHelpIndex);
			rowHelpIndex++;
		}
	}
}