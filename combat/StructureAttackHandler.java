package combat;

import java.util.ArrayList;
import java.util.List;

import gameObjects.MapDim;
import hero.Hero;
import map.UnitGetter;
import map.structures.FiringStructure;
import units.Unit;

public class StructureAttackHandler {
	private MapDim mapDim;
	private UnitGetter unitGetter;
	
	public StructureAttackHandler(MapDim mapDim, UnitGetter unitGetter) {
		this.mapDim = mapDim;
		this.unitGetter = unitGetter;
	}

	public List<Unit> getPossibleTargets(FiringStructure firingStructure) {
		List<Unit> targetUnits = new ArrayList<>();
		Hero owningHero = firingStructure.getOwner(); 
		
		boolean[][] firingRangeMap = new boolean[mapDim.width][mapDim.height];
		firingStructure.fillRangeMap(firingRangeMap);
		
		for (int y = 0 ; y < mapDim.height ; y++) {
			for (int x = 0 ; x < mapDim.width ; x++) {
				if (firingRangeMap[x][y]) {
					Unit potentialTarget = unitGetter.getNonFriendlyUnit(x * mapDim.tileSize, y * mapDim.tileSize, owningHero);
					if (potentialTarget != null) {
						targetUnits.add(potentialTarget);
					}
				}
			}
		}
		
		return targetUnits;
	}
}