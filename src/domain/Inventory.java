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

	public void addGold(int amount) {
		this.gold += amount;
	}

	public void removeGold(int amount) {
		this.gold -= amount;
	}

	public ArrayList<IngredientCard> getIngredientCards() {
		return ingredientCards;
	}

	public ArrayList<ArtifactCard> getArtifactCards() {
		return artifactCards;
	}

	public IngredientCard getIngredient(int ingredientId) {
		for (int i = 0; i < ingredientCards.size(); i++)
			if (ingredientCards.get(i).getId() == ingredientId)
				return ingredientCards.remove(i);

		return null;
		IngredientCard iCard = null;

		for (IngredientCard i : ingredientCards) {
			if (i.getId() == ingredientId) {
				iCard = this.ingredientCards.remove(ingredientCards.indexOf(i));
			}
		}

		return iCard;
	}

	public ArtifactCard removeArtifactCard(String name) {
		ArtifactCard aCard = null;

		for (ArtifactCard a : artifactCards) {
			if (a.getName().equals(name)) {
				aCard = this.artifactCards.remove(artifactCards.indexOf(a));
			}
		}

		return aCard;
	}

	public ArtifactCard getArtifactCard(String name) {
		ArtifactCard aCard = null;

		for (ArtifactCard a : artifactCards) {
			if (a.getName().equals(name)) {
				aCard = a;
			}
		}

		return aCard;
	}

	public int getGold() {
		return gold;
	}

}
