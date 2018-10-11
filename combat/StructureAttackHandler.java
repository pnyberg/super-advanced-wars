package combat;

import java.util.ArrayList;
import java.util.List;

import gameObjects.MapDim;
import main.HeroHandler;
import map.structures.FiringStructure;
import units.Unit;

public class StructureAttackHandler {
	private MapDim mapDim;
	private HeroHandler heroHandler;
	
	public StructureAttackHandler(MapDim mapDim, HeroHandler heroHandler) {
		this.mapDim = mapDim;
		this.heroHandler = heroHandler;
	}

	public List<Unit> getPossibleTargets(FiringStructure firingStructure) {
		List<Unit> targetUnits = new ArrayList<>();
		
		boolean[][] tempRangeMap = new boolean[mapDim.width][mapDim.height];
		
		return targetUnits;
	}
}