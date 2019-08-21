package menus;

import java.util.ArrayList;

import menus.unit.UnitMenuRowEntryBooleanHandler;
import units.Unit;

public class UnitMenuState extends MenuState {
	private UnitMenuRowEntryBooleanHandler unitMenuRowEntryBooleanHandler;
	private ArrayList<Unit> cargo;

	public UnitMenuState() {
		super();
		cargo = new ArrayList<Unit>();
		unitMenuRowEntryBooleanHandler = new UnitMenuRowEntryBooleanHandler();
	}
	
	public void addCargoRow(Unit containedUnit) {
		cargo.add(containedUnit);
	}
	
	public void resetRowEntries() {
		unitMenuRowEntryBooleanHandler.clear();
		cargo.clear();
	}
	
	public void setCaptOption(boolean capt) {
		unitMenuRowEntryBooleanHandler.capt = capt;
	}
	
	public void setDiveOption(boolean dive) {
		unitMenuRowEntryBooleanHandler.dive = dive;
	}
	
	public void setEmergeOption(boolean emerge) {
		unitMenuRowEntryBooleanHandler.emerge = emerge;
	}
	
	public void setEnterOption(boolean enter) {
		unitMenuRowEntryBooleanHandler.enter = enter;
	}
	
	public void setFireOption(boolean fire) {
		unitMenuRowEntryBooleanHandler.fire = fire;
	}
	
	public void setJoinOption(boolean join) {
		unitMenuRowEntryBooleanHandler.join = join;
	}
	
	public void setLaunchOption(boolean launch) {
		unitMenuRowEntryBooleanHandler.launch = launch;
	}
	
	public void setSupplyOption(boolean supply) {
		unitMenuRowEntryBooleanHandler.supply = supply;
	}
	
	public void setWaitOption(boolean wait) {
		unitMenuRowEntryBooleanHandler.wait = wait;
	}
	
	public int getNumberOfExistingRows() {
		int numberOfRows = unitMenuRowEntryBooleanHandler.getNumberOfExistingRows(); 
		if (cargo.size() > 0) {
			numberOfRows += cargo.size();
		}
		return numberOfRows;
	}
	
	public boolean[] getRowEntryShowingStatus() {
		return unitMenuRowEntryBooleanHandler.getAsBooleanArray();
	}
	
	public ArrayList<Unit> getCargo() {
		return cargo;
	}
	
	public boolean atUnitRow() {
		return getMenuIndex() < cargo.size();
	}
	
	public boolean atJoinRow() {
		return unitMenuRowEntryBooleanHandler.join == true;
	}
	
	public boolean atEnterRow() {
		if (!unitMenuRowEntryBooleanHandler.enter == true) {
			return false;
		}
		return getMenuIndex() == 0;
	}
	
	public boolean atFireRow() {
		if (!unitMenuRowEntryBooleanHandler.fire == true) {
			return false;
		}
		return getMenuIndex() == 0;
	}

	public boolean atCaptRow() {
		if (!unitMenuRowEntryBooleanHandler.capt == true) {
			return false;
		}
		if (unitMenuRowEntryBooleanHandler.fire == true) {
			return getMenuIndex() == 1;
		} else {
			return getMenuIndex() == 0;
		}
	}

	public boolean atSupplyRow() {
		if (!unitMenuRowEntryBooleanHandler.supply == true) {
			return false;
		}
		return getMenuIndex() == cargo.size();
	}
}