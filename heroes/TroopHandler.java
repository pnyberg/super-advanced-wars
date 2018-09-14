package heroes;

import java.util.ArrayList;

import units.Unit;

public class TroopHandler {
	private ArrayList<Unit> troops;

	public TroopHandler() {
		troops = new ArrayList<Unit>();
	}
	
	public void addTroop(Unit unit) {
		troops.add(unit);
	}

	public void removeTroop(Unit unit) {
		for (int i = 0 ; i < troops.size() ; i++) {
			if (troops.get(i) == unit) {
				troops.remove(i);
				break;
			}
		}
	}

	public Unit getTroop(int index) {
		return troops.get(index);
	}

	public int getTroopSize() {
		return troops.size();
	}
}