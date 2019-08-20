package combat;

import java.util.ArrayList;
import java.util.List;

import gameObjects.MapDimension;
import hero.Hero;
import map.UnitGetter;
import map.structures.FiringStructure;
import units.Unit;

public class StructureAttackHandler {
	private MapDimension mapDim;
	private UnitGetter unitGetter;
	
	public StructureAttackHandler(MapDimension mapDim, UnitGetter unitGetter) {
		this.mapDim = mapDim;
		this.unitGetter = unitGetter;
	}

	// TODO: rewrite code to be more readable
	public List<Unit> getPossibleTargets(FiringStructure firingStructure) {
		List<Unit> targetUnits = new ArrayList<>();
		Hero owningHero = firingStructure.getOwner(); 
		
		boolean[][] firingRangeMap = new boolean[mapDim.getTileWidth()][mapDim.getTileHeight()];
		firingStructure.fillRangeMap(firingRangeMap);
		
		for (int tileY = 0 ; tileY < mapDim.getTileHeight() ; tileY++) {
			for (int tileX = 0 ; tileX < mapDim.getTileWidth() ; tileX++) {
				if (firingRangeMap[tileX][tileY]) {
					Unit potentialTarget = unitGetter.getNonFriendlyUnit(tileX * mapDim.tileSize, tileY * mapDim.tileSize, owningHero);
					if (potentialTarget != null) {
						targetUnits.add(potentialTarget);
					}
				}
			}
		}
		return targetUnits;
	}
}