package graphics;

import java.awt.Graphics;

import combat.AttackRangeHandler;
import gameObjects.GameProperties;
import gameObjects.GameState;
import gameObjects.MapDimension;
import hero.HeroHandler;
import map.BuildingStructureHandlerObject;
import map.GameMap;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
import routing.MovementCostCalculator;
import routing.RouteHandler;
import units.Unit;

public class ViewPainter {
	private MapViewType mapViewType;
	private CommanderView commanderView;
	private HeroHandler heroHandler;
	private MapDimension mapDimension;
	private GameMap gameMap;
	private RouteHandler routeHandler;
	private AttackRangeHandler attackRangeHandler;
	private BuildingHandler buildingHandler;
	private StructureHandler structureHandler;
	
	// TODO: rewrite with fewer parameters
	public ViewPainter(GameProperties gameProperties, GameState gameState) {
		mapViewType = MapViewType.MAIN_MAP_MENU_VIEW;
		this.heroHandler = gameState.getHeroHandler();
		this.mapDimension = gameProperties.getMapDimension();
		this.commanderView = new CommanderView(mapDimension, gameState.getHeroHandler());
		this.gameMap = gameProperties.getGameMap();
		this.routeHandler = new RouteHandler(gameProperties, gameState);
		this.attackRangeHandler = new AttackRangeHandler(gameProperties, gameState);
		this.buildingHandler = new BuildingHandler(gameState);
		this.structureHandler = new StructureHandler(gameState, mapDimension);
	}
	
	public void setViewType(MapViewType mapViewType) {
		this.mapViewType = mapViewType;
	}
	
	public MapViewType getMapViewType() {
		return mapViewType;
	}
	
	public void paint(Graphics g) {
		if (mapViewType == MapViewType.MAIN_MAP_MENU_VIEW) {
			paintMap(g);
		} else if (mapViewType == MapViewType.CO_MAP_MENU_VIEW) {
			commanderView.paintView(g);
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
		if (mapViewType == MapViewType.MAIN_MAP_MENU_VIEW) {
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