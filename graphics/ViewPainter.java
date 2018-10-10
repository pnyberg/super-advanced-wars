package graphics;

import java.awt.Graphics;

import combat.AttackRangeHandler;
import gameObjects.MapDim;
import main.HeroHandler;
import map.area.Area;
import map.buildings.Building;
import map.buildings.BuildingHandler;
import routing.RouteHandler;
import units.Unit;

public class ViewPainter {
	private MapViewType mapViewType;
	private CommanderView commanderView;
	private HeroHandler heroHandler;
	private MapDim mapDimension;
	private Area[][] map;
	private RouteHandler routeHandler;
	private AttackRangeHandler attackRangeHandler;
	private BuildingHandler buildingGetter;
	
	public ViewPainter(HeroHandler heroHandler, MapDim mapDimension, Area[][] map, RouteHandler routeHandler, AttackRangeHandler attackRangeHandler, BuildingHandler buildingGetter) {
		mapViewType = MapViewType.MAIN_MAP_MENU_VIEW;
		commanderView = new CommanderView(mapDimension, heroHandler);
		this.heroHandler = heroHandler;
		this.mapDimension = mapDimension;
		this.map = map;
		this.routeHandler = routeHandler;
		this.attackRangeHandler = attackRangeHandler;
		this.buildingGetter = buildingGetter;
	}
	
	public void setViewType(MapViewType mapViewType) {
		this.mapViewType = mapViewType;
	}
	
	public MapViewType geMapViewType() {
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
			building.paint(g, mapDimension.tileSize);
		} else {
			map[x][y].paint(g, movementAble, rangeAble);
		}
	}

	public void paintUnits(Graphics g, Unit chosenUnit) {
		for (int t = 0 ; t < 2 ; t++) {
			for (int k = 0 ; k < heroHandler.getTroopSize(t) ; k++) {
				Unit unit = heroHandler.getUnitFromHero(t, k);
				if (unit != chosenUnit) {
					if (!unit.isHidden()) {
						unit.paint(g, mapDimension.tileSize);
					}
				}
			}
		}
	}
}