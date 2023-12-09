package domain;

public class ArtifactCard {

	private final String effectType;
	private final String ability;
	private final String name;
	private final int price;
	private boolean activated = false;

	public ArtifactCard(String effectType, String ability, String name, int price) {
		this.effectType = effectType;
		this.ability = ability;
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getEffectType() {
		return effectType;
	}

	public String getAbility() {
		return ability;
	}

	public void use() {
		activated = true;
	}

	public boolean getActivated() {
		return activated;
	}
}