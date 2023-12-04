package domain;

import java.util.ArrayList;

public class IngredientCardDeck {
	
	private ArrayList<IngredientCard> ingredientCardDeck = new ArrayList<>();
	
	public void shuffle() {

		
	}
	
	public IngredientCard drawCard() {
		
		IngredientCard iCard = ingredientCardDeck.remove(ingredientCardDeck.size() - 1);
		
		return iCard;	
	}
	
	
	public void removeIngredient(int ingredientId) {
		
		
	}
	
	public void addCard(IngredientCard card) {
		
		this.ingredientCardDeck.add(card);
	}
	
	
	
	

}
