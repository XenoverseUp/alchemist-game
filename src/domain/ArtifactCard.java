package domain;


public class ArtifactCard {
	private final String name;
	private final String description;
	private final int price;
	private final int victoryPoints;


	public ArtifactCard(String name, int price, int victoryPoints, String description) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.victoryPoints = victoryPoints;
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
}