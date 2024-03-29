package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import combat.AttackHandler;
import combat.AttackRangeHandler;
import cursors.Cursor;
import cursors.FiringCursor;
import gameObjects.GameProperties;
import gameObjects.GameState;
import graphics.ViewPainter;
import graphics.ViewType;
import hero.HeroFactory;
import hero.HeroHandler;
import hero.HeroPortrait;
import loading.GameLoader;
import loading.GameLoadingObject;
import map.buildings.Building;
import menus.building.BuildingMenu;
import menus.map.MapMenu;
import menus.unit.UnitMenu;
import routing.RouteArrowPath;
import unitUtils.ContUnitHandler;
import units.Unit;

public class Gameboard extends JPanel implements KeyListener {
	private GameState gameState;
	private GameProperties gameProperties;
	private KeyListenerInputHandler keyListenerInputHandler;

	public Gameboard(int tileSize) {
		// Init heroes
		HeroHandler heroHandler	= new HeroHandler();
		HeroFactory heroFactory	= new HeroFactory();

		heroHandler.addHero(heroFactory.createHero(0));
		heroHandler.addHero(heroFactory.createHero(1));

		// Load game
		GameLoader gameLoader = new GameLoader(heroHandler, tileSize);

		GameLoadingObject gameLoadingObject = gameLoader.loadMap("map-files/test_map.txt");
		gameProperties = gameLoadingObject.getGameProperties();
		gameState = gameLoadingObject.getInitalGameState();
		
		keyListenerInputHandler = new KeyListenerInputHandler(gameProperties, gameState);
		addKeyListener(this);
		init();
	}
	
	private void init() {
		Building.init(gameProperties.initialMoneyPerBuilding);
		TurnHandler turnHandler = new TurnHandler(gameProperties, gameState);
		turnHandler.startTurnActions(); // setup
		updatePortraitSideChoice();
		repaint();
	}
	
	private void updatePortraitSideChoice() {
		Cursor cursor = gameState.getCursor();
		HeroPortrait heroPortrait = new HeroPortrait(gameProperties.getMapDimension(), gameState);
		heroPortrait.updateSideChoice(cursor);
	}
	
	public int getBoardWidth() {
		int mapTileWidth = gameProperties.getGameMap().getTileWidth();

		return mapTileWidth * gameProperties.getTileSize();
	}

	public int getBoardHeight() {
		int mapTileHeight = gameProperties.getGameMap().getTileHeight();
		int infoBoxHeight = gameProperties.getInfoBoxGraphicMetrics().height;

		return mapTileHeight * gameProperties.getTileSize() + infoBoxHeight;
	}

	/**
	 * KeyListener-methods
	 */
	public void keyPressed(KeyEvent e) {
System.out.println("pressed"); // TODO: checking for key-freezes
		keyListenerInputHandler.manageKeyPressedInput(e);
		updatePortraitSideChoice();
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		keyListenerInputHandler.manageKeyReleasedInput(e);
		repaint();
	}

	public void keyTyped(KeyEvent e) {}

	/**
	 * Paint-methods
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		ViewPainter mainViewPainter = new ViewPainter(gameProperties, gameState);
		Unit chosenUnit = gameState.getChosenUnit();
		mainViewPainter.paint(g);
		if (chosenUnit != null) {
			paintChosenUnitGraphics(g);
		}
		AttackRangeHandler attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		InfoBox infoBox = new InfoBox(gameProperties, gameState);
		HeroPortrait heroPortrait = new HeroPortrait(gameProperties.getMapDimension(), gameState);

		// paint general map-graphics
		attackRangeHandler.paintRange(g);
		mainViewPainter.paintUnits(g, chosenUnit);
		paintMenusAndCursors(g);
		if(gameState.getMapViewType() != ViewType.CO_VIEW) {
			heroPortrait.paint(g);
			infoBox.paint(g);
		}
	}
	
	private void paintChosenUnitGraphics(Graphics g) {
		Unit chosenUnit = gameState.getChosenUnit();
		if(showRoutePath()) {
			RouteArrowPath routeArrowPath = new RouteArrowPath(gameProperties, gameState);
			routeArrowPath.paintArrow(g);
		}
		chosenUnit.paint(g, gameProperties.getTileSize());
	}
	
	private boolean showRoutePath() {
		Unit chosenUnit = gameState.getChosenUnit();
		UnitMenu unitMenu = new UnitMenu(gameProperties.getTileSize(), gameState);
		ContUnitHandler contUnitHandler = new ContUnitHandler(gameProperties, gameState);
		AttackHandler attackHandler = new AttackHandler(gameProperties, gameState);

		return !unitMenu.isVisible()
				&& !contUnitHandler.unitIsDroppingOff() 
				&& !attackHandler.unitWantsToFire(chosenUnit);
	}
	
	private void paintMenusAndCursors(Graphics g) {
		MapMenu mapMenu = new MapMenu(gameProperties.getTileSize(), gameState);
		UnitMenu unitMenu = new UnitMenu(gameProperties.getTileSize(), gameState);
		BuildingMenu buildingMenu = new BuildingMenu(gameProperties, gameState);
		AttackHandler attackHandler = new AttackHandler(gameProperties, gameState);
		Unit chosenUnit = gameState.getChosenUnit();
		Cursor cursor = gameState.getCursor();

		if(gameState.getMapViewType() == ViewType.CO_VIEW) {
			// don't show anything
		} else if(gameState.getMapViewType() == ViewType.MAP_VIEW) {
			// when the mapMenu is open the cursor is hidden
			if(mapMenu.isVisible()) {
				mapMenu.paint(g);
			} else if(unitMenu.isVisible()) {
				unitMenu.paint(g);
			} else if(buildingMenu.isVisible()) {
				buildingMenu.paint(g);
			} else if(attackHandler.unitWantsToFire(chosenUnit)) {
				FiringCursor firingCursor = new FiringCursor(gameProperties, gameState);
				firingCursor.paint(g, cursor, chosenUnit);
			} else {
				gameState.getCursor().paint(g);
			}
		}
	}
}