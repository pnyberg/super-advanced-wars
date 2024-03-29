package graphics;

import java.awt.Graphics;

import combat.AttackRangeHandler;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import hero.Hero;
import hero.HeroHandler;
import map.GameMap;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
import routing.RouteHandler;
import units.Unit;

public class ViewPainter {
	private GameState gameState;
	private CommanderView commanderView;
	private UnitInfoView unitInfoView;
	private HeroHandler heroHandler;
	private MapDimension mapDimension;
	private GameMap gameMap;
	private RouteHandler routeHandler;
	private AttackRangeHandler attackRangeHandler;
	private BuildingHandler buildingHandler;
	private StructureHandler structureHandler;
	
	public ViewPainter(GameProperties gameProperties, GameState gameState) {
		this.gameState = gameState;
		heroHandler = gameState.getHeroHandler();
		mapDimension = gameProperties.getMapDimension();
		commanderView = new CommanderView(gameProperties, gameState);
		unitInfoView = new UnitInfoView();
		gameMap = gameProperties.getGameMap();
		routeHandler = new RouteHandler(gameProperties, gameState);
		attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		buildingHandler = new BuildingHandler(gameState);
		structureHandler = new StructureHandler(gameState, mapDimension);
	}
	
	public void paint(Graphics g) {
		if(gameState.getMapViewType() == ViewType.MAP_VIEW) {
			paintMap(g);
		} else if(gameState.getMapViewType() == ViewType.CO_VIEW) {
			Hero coHero	= heroHandler.getCoViewHero();
			commanderView.paintView(g, coHero);
		} else if(gameState.getMapViewType() == ViewType.UNIT_INFO_VIEW) {
			paintMap(g);
			unitInfoView.paintView(g);
		} else if(gameState.getMapViewType() == ViewType.TERRAIN_INFO_VIEW) {
			paintMap(g);
			// TODO: add terrainInfoView
		}
	}
	
	private void paintMap(Graphics g) {
		for (int tileX = 0 ; tileX < gameMap.getTileWidth() ; tileX++) {
			for (int tileY = 0 ; tileY < gameMap.getTileHeight() ; tileY++) {
				paintArea(g, tileX, tileY);
			}
		}
	}

	private void paintArea(Graphics g, int tileX, int tileY) {
		boolean rangeAttackableLocation = attackRangeHandler.getRangeMap()[tileX][tileY];
		Structure structure = structureHandler.getFiringStructure(tileX * mapDimension.tileSize, tileY * mapDimension.tileSize);
		Building building = buildingHandler.getBuilding(tileX * mapDimension.tileSize, tileY * mapDimension.tileSize);

		if (structure != null && !rangeAttackableLocation) {
			structure.paint(g);
		} else if (building != null && !rangeAttackableLocation) {
			building.paint(g);
		} else {
			boolean movementAble = routeHandler.getMovementMap().isAcceptedMove(tileX, tileY);
			gameMap.getArea(tileX,tileY).paint(g, movementAble, rangeAttackableLocation);
		}
	}

	public void paintUnits(Graphics g, Unit chosenUnit) {
		if (gameState.getMapViewType() == ViewType.MAP_VIEW) {
			for (int heroIndex = 0 ; heroIndex < heroHandler.getNumberOfHeroes() ; heroIndex++) {
				for (int k = 0 ; k < heroHandler.getTroopSize(heroIndex) ; k++) {
					Unit unit = heroHandler.getUnitFromHero(heroIndex, k);
					if (unit != chosenUnit) {
						if (!unit.isHidden()) {
							unit.paint(g, mapDimension.tileSize);
						}
					}
				}
			}
		}
	}
}