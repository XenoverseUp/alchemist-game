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
	
	public void addGold (int amount) {
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
	}

	public int getGold() {
		return gold;
	}

	
	
}
