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
	
	

}
