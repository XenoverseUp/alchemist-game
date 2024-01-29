package domain;

public class ArtifactCard {
	private final String name;
	private final String description;
	private final int price;
	private final int victoryPoints;
	private int activated;

	public ArtifactCard(String name, int price, int victoryPoints, String description) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.victoryPoints = victoryPoints;
		this.activated = 0;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public int getActivation() {
		return activated;
	}

	public void setActivation(int i) {
		activated = i;
	}
}