package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class IngredientCardDeck {
	private ArrayList<IngredientCard> ingredientCardDeck = new ArrayList<IngredientCard>();
	public Integer index = -1;
	public boolean shouldShuffle = true;
	private ArrayList<String> ingredientNames = new ArrayList<String>() {
		{
			add("mushroom");
			add("fern");
			add("warty toad");
			add("bird claw");
			add("moonshade");
			add("mandrake root");
			add("raven's feather");
			add("scorpion tail");
		}
	};

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
		incrIndex();
		IngredientCard iCard = ingredientCardDeck.get(index);
		return iCard;

	}

	public IngredientCard getByName(String name) {
		for (IngredientCard i : ingredientCardDeck) {
			if (i.getName().equals(name))
				return i;
		}

		return null;
	}

	public ArrayList<IngredientCard> getDeck() {
		return ingredientCardDeck;
	}

	public void addCard(IngredientCard card) {
		ingredientCardDeck.add(card);
	}

	private void incrIndex() {
		if (index == ingredientCardDeck.size() - 1) {
			if (shouldShuffle) {
				shuffle();
			}
			index = 0;
			shouldShuffle = true;
		} else {
			index++;
		}
	}


	public boolean repOk() {

		if (ingredientCardDeck == null || ingredientCardDeck.isEmpty()) {
			return false;
		}
	
		// Check if index is within the bounds of the deck
		if (index < -1 || index >= ingredientCardDeck.size()) {
			return false;
		}

		HashSet<IngredientCard> cardSet = new HashSet<>(ingredientCardDeck);
		if (cardSet.size() != ingredientCardDeck.size()) {
			return false;
		}
	
		
		return true; 
	}
	


}
