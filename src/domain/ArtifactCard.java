package domain;


public class ArtifactCard {
	private final String name;
	private final String description;
	private final int price;


	public ArtifactCard(String name, int price, String description) {
		this.name = name;
		this.description = description;
		this.price = price;
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
}