package hero.heroPower;

import java.util.List;

import hero.heroAbilities.CommanderEffect;

public class HeroPower {
	private HeroPowerMeter heroPowerMeter;
	private List<CommanderEffect> powerEffects;
	private List<CommanderEffect> superPowerEffects;
	
	public HeroPower(HeroPowerMeter heroPowerMeter, List<CommanderEffect> powerEffects, List<CommanderEffect> superPowerEffects) {
		this.heroPowerMeter = heroPowerMeter;
		this.powerEffects = powerEffects;
		this.superPowerEffects = superPowerEffects;
	}
	
	public HeroPowerMeter getHeroPowerMeter() {
		return heroPowerMeter;
	}
	
	public List<CommanderEffect> getPowerEffects() {
		return powerEffects;
	}
	
	public List<CommanderEffect> getSuperPowerEffects() {
		return superPowerEffects;
	}
}