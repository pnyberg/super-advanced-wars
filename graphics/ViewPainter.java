package graphics;

import java.awt.Color;
import java.awt.Graphics;

import combat.AttackRangeHandler;
import gameObjects.Direction;
import gameObjects.MapDim;
import main.HeroHandler;
import map.area.Area;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import map.structures.BlackCannon;
import routing.RouteHandler;
import units.Unit;

public class ViewPainter {
	private MapViewType mapViewType;
	private CommanderView commanderView;
	private HeroHandler heroHandler;
	private MapDim mapDim;
	private Area[][] map;
	private RouteHandler routeHandler;
	private AttackRangeHandler attackRangeHandler;
	private BuildingHandler buildingGetter;
	
	public ViewPainter(CommanderView commanderView, HeroHandler heroHandler, MapDim mapDimension, Area[][] map, RouteHandler routeHandler, AttackRangeHandler attackRangeHandler, BuildingHandler buildingGetter) {
		mapViewType = MapViewType.MAIN_MAP_MENU_VIEW;
		this.commanderView = commanderView;
		this.heroHandler = heroHandler;
		this.mapDim = mapDimension;
		this.map = map;
		this.routeHandler = routeHandler;
		this.attackRangeHandler = attackRangeHandler;
		this.buildingGetter = buildingGetter;
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
		for (int x = 0 ; x < map.length ; x++) {
			for (int y = 0 ; y < map[0].length ; y++) {
				paintArea(g, x, y);
			}
		}
	}

	private void paintArea(Graphics g, int x, int y) {
		boolean movementAble = routeHandler.getMovementMap().isAcceptedMove(x, y);
		boolean rangeAble = attackRangeHandler.getRangeMap()[x][y];

		if (buildingGetter.getBuilding(x, y) != null && !rangeAble) {
			Building building = buildingGetter.getBuilding(x, y);
			building.paint(g, mapDim.tileSize);
		} else {
			map[x][y].paint(g, movementAble, rangeAble);
		}
		if (x == 4 && y == 2) {
			new BlackCannon(4 * mapDim.tileSize, 2 * mapDim.tileSize, Direction.SOUTH, Color.red, mapDim.tileSize).paint(g);
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