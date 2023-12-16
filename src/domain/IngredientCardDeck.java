package domain;

import java.util.ArrayList;
import java.util.Collections;


public class IngredientCardDeck {
	private ArrayList<IngredientCard> ingredientCardDeck = new ArrayList<IngredientCard>();
	private ArrayList<String> ingredientNames = new ArrayList<>() {{
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
	
	public IngredientCard drawCard() {
		IngredientCard iCard = ingredientCardDeck.remove(ingredientCardDeck.size() - 1);
		return iCard;	
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
	
	
	
	

}
