package menus.map;

import java.awt.Graphics;

import gameObjects.GameState;
import hero.HeroHandler;
import hero.heroPower.HeroPowerMeter;
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

	public MapMenu(int tileSize, GameState gameState) {
		super(gameState.getMapMenuState(), tileSize);
		this.heroHandler = gameState.getHeroHandler();
	}

	public int getNumberOfActiveRows() {
		HeroPowerMeter heroPowerMeter = heroHandler.getCurrentHero().getHeroPowerMeter();
		int numberOfActiveRows = menuTexts.length - 2; // all rows except the power-rows
		if (heroPowerMeter.powerUsable()) {
			numberOfActiveRows++;
		}
		if (heroPowerMeter.superPowerUsable()) {
			numberOfActiveRows++;
		}
		return numberOfActiveRows;
	}
	
	public boolean atCoRow() {
		return menuState.getMenuIndex() == 0;
	}
	
	public boolean atIntelRow() {
		return menuState.getMenuIndex() == 1;
	}
	
	public boolean atPowerRow() {
		if (!heroHandler.getCurrentHero().getHeroPowerMeter().powerUsable()) {
			return false;
		}
		return menuState.getMenuIndex() == 2;
	}
	
	public boolean atSuperPowerRow() {
		if (!heroHandler.getCurrentHero().getHeroPowerMeter().superPowerUsable()) {
			return false;
		}
		return menuState.getMenuIndex() == 3;
	}
	
	public boolean atOptionsRow() {
		return getMenuIndex() == (getNumberOfActiveRows() - 3);
	}

	public boolean atSaveRow() {
		return getMenuIndex() == (getNumberOfActiveRows() - 2);
	}

	public boolean atEndRow() {
		return getMenuIndex() == (getNumberOfActiveRows() - 1);
	}

	public void paint(Graphics g) {
		paintMenuBackground(g);
		paintMenuForeground(g);
		paintArrow(g);
	}
	
	private void paintMenuForeground(Graphics g) {
		HeroPowerMeter heroPowerMeter = heroHandler.getCurrentHero().getHeroPowerMeter();
		int x = menuState.getX();
		int y = menuState.getY();
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
			int textPosY = y + yAlign + dimensionValues.getMenuRowHeight() * rowHelpIndex;
			g.drawString(menuTexts[k], x + xAlign, textPosY);
			rowHelpIndex++;
		}
	}
}