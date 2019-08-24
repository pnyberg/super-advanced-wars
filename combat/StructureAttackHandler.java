package combat;

import java.util.ArrayList;
import java.util.List;

import gameObjects.GameState;
import gameObjects.MapDimension;
import hero.Hero;
import map.UnitGetter;
import map.structures.FiringStructure;
import units.Unit;

public class StructureAttackHandler {
	private MapDimension mapDim;
	private UnitGetter unitGetter;
	
	public StructureAttackHandler(MapDimension mapDim, GameState gameState) {
		this.mapDim = mapDim;
		this.unitGetter = new UnitGetter(gameState.getHeroHandler());
	}

	public List<Unit> getPossibleTargets(FiringStructure firingStructure) {
		List<Unit> targetUnits = new ArrayList<>();
		Hero owningHero = firingStructure.getOwner();
		boolean[][] firingRangeMap = firingStructure.getFiringRangeMap(mapDim.getTileWidth(), mapDim.getTileHeight());
		for (int tileY = 0 ; tileY < mapDim.getTileHeight() ; tileY++) {
			for (int tileX = 0 ; tileX < mapDim.getTileWidth() ; tileX++) {
				if (firingRangeMap[tileX][tileY]) { // attackable location
					int x = tileX * mapDim.tileSize;
					int y = tileY * mapDim.tileSize;
					Unit potentialTarget = unitGetter.getNonFriendlyUnit(x, y, owningHero);
					if (potentialTarget != null) {
						targetUnits.add(potentialTarget);
					}
				}
			}
		}
		return targetUnits;
	}
}