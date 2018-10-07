package hero.heroPower;

public class HeroPower {
	private HeroPowerMeter heroPowerMeter;
	
	public HeroPower(HeroPowerMeter heroPowerMeter) {
		this.heroPowerMeter = heroPowerMeter;
	}
	
	public HeroPowerMeter getHeroPowerMeter() {
		return heroPowerMeter;
	}
}