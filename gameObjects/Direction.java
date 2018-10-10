package gameObjects;

public enum Direction {
	NORTH(0),
	EAST(1),
	SOUTH(2),
	WEST(3);
	
	private final int directionIndex;
	
	Direction(int directionIndex) {
		this.directionIndex = directionIndex;
	}
	
	public int directionIndex() {
		return directionIndex;
	}
}