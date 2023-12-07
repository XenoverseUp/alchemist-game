package domain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class Potion {

    private String name;
    private int value;
    private ArrayList<IngredientCard> recipe;


    public Potion(String name, int value, ArrayList<IngredientCard> recipe) {
		this.name = name;
		this.value = value;
		this.recipe = recipe;

	}

  public boolean checkIngredient(IngredientCard card) {

    for (IngredientCard ingredientCard: recipe) {
      if (card==ingredientCard) {
        return true;
      }

  
    }

    return false;


  }

    
}
