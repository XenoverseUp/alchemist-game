package domain;

import java.util.ArrayList;

public class Inventory {
	private int gold = 0;
	private ArrayList<ArtifactCard> artifactCards = new ArrayList<>();
	private ArrayList<IngredientCard> ingredientCards = new ArrayList<>();

	public void addIngredientCard(IngredientCard ingredient) {
		this.ingredientCards.add(ingredient);
	}

	public void addArtifactCard(ArtifactCard artifact) {
		this.artifactCards.add(artifact);
	}

	/**
	 * Discards any artifact card in return of the half of the price of card.
	 * Useful when player buys same persistent card more than once.
	 * 
	 * @param name - Name of the artifact card.
	 */
	public void discardArtifactCard(String name) {
		ArtifactCard card = this.artifactCards
				.stream()
				.filter(c -> c.getName().equals(name))
				.findFirst()
				.get();

		this.gold += (int) (card.getPrice() / 2);
		this.artifactCards.remove(card);
	}

	public boolean isEmpty() {
		return this.ingredientCards.size() == 0;
	}

	public void addGold(int amount) {
		this.gold += amount;
	}

	public void spendGold(int amount) {
		if (amount <= this.gold) {
			this.gold -= amount;
		}
	}

	public ArrayList<IngredientCard> getIngredientCards() {
		return ingredientCards;
	}

	public ArrayList<ArtifactCard> getArtifactCards() {
		return artifactCards;
	}

	public IngredientCard getIngredient(String name) {
		for (int i = 0; i < ingredientCards.size(); i++)
			if (ingredientCards.get(i).getName().equals(name))
				return ingredientCards.remove(i);

		return null;
	}

	public Boolean hasIngredient(String name) {
		for (int i = 0; i < ingredientCards.size(); i++)
			if (ingredientCards.get(i).getName().equals(name))
				return true;

		return false;
	}

	public Boolean hasArtifactCard(String name) {
		ArrayList<ArtifactCard> artifactCards = getArtifactCards();
		String cardToFind = name;
		boolean isCardFound = false;
		int cardActivated = 0;
		for (ArtifactCard card : artifactCards) {
			if (card.getName().equalsIgnoreCase(cardToFind)) {
				isCardFound = true;
				cardActivated = card.getActivation();
				if (cardActivated == 1) {
					return true;
				}
			}
		}
		return false;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int amount) {
		this.gold = amount;
	}

	public void activateArtifact(String name) {
		ArtifactCard card = this.artifactCards
				.stream()
				.filter(c -> c.getName().equals(name))
				.findFirst()
				.get();

		if (card.getActivation() == 1) {
			card.setActivation(0);
		}
		if (card.getActivation() == 0) {
			card.setActivation(1);
		}
	}

	public void removeArtifactCardAfterUsing(String name) {
		ArtifactCard card = this.artifactCards
				.stream()
				.filter(c -> c.getName().equals(name))
				.findFirst()
				.get();

		this.artifactCards.remove(card);
	}
}
