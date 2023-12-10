package domain;

import java.util.ArrayList;

public class Potion {
    private final String name;
    private final int value;
    private final ArrayList<IngredientCard> recipe;


    public Potion(String name, int value, ArrayList<IngredientCard> recipe) {
      this.name = name;
      this.value = value;
      this.recipe = recipe;
    }

  public boolean checkIngredient(IngredientCard card) {
    for (IngredientCard ingredientCard : recipe) {
      if (card == ingredientCard) return true;
    }

    return false;
  }

  public String getName() {
      return name;
  }

  public int getValue() {
      return value;
  }

    
}
