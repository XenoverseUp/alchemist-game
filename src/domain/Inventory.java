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
	
	public IngredientCard getIngredient(int ingredientId) {
		
		IngredientCard iCard = null;
		
		for (IngredientCard i: ingredientCards) {
			if (i.getId() == ingredientId) {
				
				iCard = this.ingredientCards.remove(ingredientCards.indexOf(i));
				
			}
		}
		
		return iCard;
	}
	
}
