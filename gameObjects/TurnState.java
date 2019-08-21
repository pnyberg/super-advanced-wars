package gameObjects;

public class TurnState {
	private int day;
	private boolean firstHeroOfTheDay;

	public TurnState() {
		day = 1;
		firstHeroOfTheDay = false;
	}
	
	public void incrementDay() {
		day++;
	}
	
	public void setFirstHeroOfTheDay(boolean firstHeroOfTheDay) {
		this.firstHeroOfTheDay = firstHeroOfTheDay;
	}
	
	public int getDay() {
		return day;
	}
	
	public boolean isFirstHeroOfTheDay() {
		return firstHeroOfTheDay;
	}
}
