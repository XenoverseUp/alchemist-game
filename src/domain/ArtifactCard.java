package domain;


public class ArtifactCard {

	private String effectType;
	private String ability;
	private String name;


	public ArtifactCard(String effectType, String ability, String name) {
		this.effectType = effectType;
		this.ability = ability;
		this.name = name;

	}

	
	public String getEffectType() {
		return effectType;
	}
	public void setEffectType(String effectType) {
		this.effectType = effectType;
	}
	public String getAbility() {
		return ability;
	}
	public void setAbility(String ability) {
		this.ability = ability;
	}

	
}