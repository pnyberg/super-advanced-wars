package graphics;

import java.awt.Graphics;

import combat.AttackRangeHandler;
import gameObjects.MapDimension;
import main.HeroHandler;
import map.BuildingStructureHandlerObject;
import map.GameMap;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.Structure;
import map.structures.StructureHandler;
import routing.RouteHandler;
import units.Unit;

public class ViewPainter {
	private MapViewType mapViewType;
	private CommanderView commanderView;
	private HeroHandler heroHandler;
	private MapDimension mapDim;
	private GameMap gameMap;
	private RouteHandler routeHandler;
	private AttackRangeHandler attackRangeHandler;
	private BuildingHandler buildingHandler;
	private StructureHandler structureHandler;
	
	// TODO: rewrite with fewer parameters
	public ViewPainter(CommanderView commanderView, HeroHandler heroHandler, MapDimension mapDimension, GameMap gameMap, RouteHandler routeHandler, AttackRangeHandler attackRangeHandler, BuildingStructureHandlerObject buildingStructureHandlerObject) {
		mapViewType = MapViewType.MAIN_MAP_MENU_VIEW;
		this.commanderView = commanderView;
		this.heroHandler = heroHandler;
		this.mapDim = mapDimension;
		this.gameMap = gameMap;
		this.routeHandler = routeHandler;
		this.attackRangeHandler = attackRangeHandler;
		this.buildingHandler = buildingStructureHandlerObject.buildingHandler;
		this.structureHandler = buildingStructureHandlerObject.structureHandler;
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
		Structure structure = structureHandler.getFiringStructure(tileX * mapDim.tileSize, tileY * mapDim.tileSize);
		Building building = buildingHandler.getBuilding(tileX * mapDim.tileSize, tileY * mapDim.tileSize);

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
							unit.paint(g, mapDim.tileSize);
						}
					}
				}
			}
		}
	}
}