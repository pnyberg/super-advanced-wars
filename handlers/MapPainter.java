package handlers;

import java.awt.Graphics;

import area.Area;
import area.buildings.Building;
import heroes.HeroPortrait;
import units.Unit;

public class MapPainter {
	private HeroPortrait heroPortrait;
	private MapDimension mapDimension;
	private Area[][] map;
	private RouteHandler routeHandler;
	private BuildingGetter buildingGetter;
	
	public MapPainter(HeroPortrait heroPortrait, MapDimension mapDimension, Area[][] map, RouteHandler routeHandler, BuildingGetter buildingGetter) {
		this.heroPortrait = heroPortrait;
		this.mapDimension = mapDimension;
		this.map = map;
		this.routeHandler = routeHandler;
		this.buildingGetter = buildingGetter;
	}

	public void paintArea(Graphics g, int x, int y, boolean rangeAble) {
		Area area = map[x][y];
		boolean movementAble = routeHandler.getMovementMap().isAcceptedMove(x, y);

		if (buildingGetter.getBuilding(x, y) != null && !rangeAble) {
			Building building = buildingGetter.getBuilding(x, y);
			building.paint(g, mapDimension.tileSize);
			return;
		}

		area.paint(g, movementAble, rangeAble);
	}

	public void paintUnits(Graphics g, Unit chosenUnit) {
		for (int t = 0 ; t < 2 ; t++) {
			for (int k = 0 ; k < heroPortrait.getHeroHandler().getTroopSize(t) ; k++) {
				Unit unit = heroPortrait.getHeroHandler().getUnitFromHero(t, k);
				if (unit != chosenUnit) {
					if (!unit.isHidden()) {
						unit.paint(g, mapDimension.tileSize);
					}
				}
			}
		}
	}

	public void paintPortrait(Graphics g) {
		heroPortrait.paint(g);
	}
}
