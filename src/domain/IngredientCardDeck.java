package domain;

import java.util.ArrayList;

public class IngredientCardDeck {
	
	private ArrayList<IngredientCard> ingredientCardDeck = new ArrayList<IngredientCard>();


	public IngredientCardDeck() {
	IngredientCard ingredientCard_1 = new Herb("ginger", 1, 3, "yellow");
	IngredientCard ingredientCard_2 = new Herb("rosemary", 2, 5, "green");
	IngredientCard ingredientCard_3 = new Herb("cilantro", 3, 9, "green");
	IngredientCard ingredientCard_4 = new Herb("dill", 4, 11, "green");
	IngredientCard ingredientCard_5 = new Herb("sage", 5, 13, "lilac");

	IngredientCard ingredientCard_6 = new Mineral("copper", 1, 5, "grey");
	IngredientCard ingredientCard_7 = new Mineral("magnesium", 2, 9, "white");
	IngredientCard ingredientCard_8 = new Mineral("fluorite", 3, 10, "white");
	IngredientCard ingredientCard_9 = new Mineral("zinc", 4, 12, "grey");
	IngredientCard ingredientCard_10 = new Mineral("quartz", 5, 3, "purple");

	IngredientCard ingredientCard_11 = new Mushroom("portobello", 1, 20, "brown");
	IngredientCard ingredientCard_12 = new Mushroom("porcini", 2, 10, "yellow");
	IngredientCard ingredientCard_13 = new Mushroom("oyster", 3, 9, "white");
	IngredientCard ingredientCard_14 = new Mushroom("hedgehog", 4, 2, "brown");

	ingredientCardDeck.add(ingredientCard_1);
	ingredientCardDeck.add(ingredientCard_2);
	ingredientCardDeck.add(ingredientCard_3);
	ingredientCardDeck.add(ingredientCard_4);
	ingredientCardDeck.add(ingredientCard_5);
	ingredientCardDeck.add(ingredientCard_6);
	ingredientCardDeck.add(ingredientCard_7);
	ingredientCardDeck.add(ingredientCard_8);
	ingredientCardDeck.add(ingredientCard_9);
	ingredientCardDeck.add(ingredientCard_10);
	ingredientCardDeck.add(ingredientCard_11);
	ingredientCardDeck.add(ingredientCard_12);
	ingredientCardDeck.add(ingredientCard_13);
	ingredientCardDeck.add(ingredientCard_14);

	}



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
