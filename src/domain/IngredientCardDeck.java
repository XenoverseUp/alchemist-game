package domain;

import java.util.ArrayList;
import java.util.Collections;


public class IngredientCardDeck {
	private ArrayList<IngredientCard> ingredientCardDeck = new ArrayList<IngredientCard>();
	private Integer index = -1;
	private ArrayList<String> ingredientNames = new ArrayList<String>() {{
		add("scorpion tail");
		add("bird claw");
		add("warty toad");
		add("mandrake root");
		add("raven's feather");
		add("mushroom");
		add("moonshade");
		add("fern");
	}};

	public IngredientCardDeck() {
		Collections.shuffle(Molecules.molecules);

		for (int i = 0; i < ingredientNames.size(); i++) 
			ingredientCardDeck.add(new IngredientCard(ingredientNames.get(i), Molecules.molecules.get(i)));
	}

	public void shuffle() {
		Collections.shuffle(ingredientCardDeck);		
	}





	/*-------------------------------------------------------------------------------------------------------------------------
	 * We keep an index to avoid getting the same ingredient card from the deck and the problem of having zero cards in the end.
	 * First we initialize it as zero as a private variable in the class.
	 * In the first card drawing we increment it and draw the 0th placed card in the ingredientCardDeck Array.
	 * If we reach at the end of the Array, then we reset the index, shuffle the deck and return the last card.
	 */


	public IngredientCard drawCard() {
		Integer indx = incrIndex();
		//IngredientCard iCard = ingredientCardDeck.remove(ingredientCardDeck.size() - 1);
		if (indx == ingredientCardDeck.size()){
			indx = -1;
			shuffle();
			IngredientCard iCard = ingredientCardDeck.get(ingredientCardDeck.size() - 1);
			return iCard;
		}
		else {
			incrIndex();
			IngredientCard iCard = ingredientCardDeck.get(indx);
			return iCard;	
		}
	}

	public IngredientCard getByName(String name) {
		for (IngredientCard i : ingredientCardDeck) {
			if (i.getName().equals(name)) return i;
		}
		
		return null;
	}

	
	public void addCard(IngredientCard card) {
		ingredientCardDeck.add(card);
	}
	
	public Integer incrIndex(){
		return index++;
	}
	


}
