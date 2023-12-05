package domain;

import java.util.ArrayList;

public class PotionBrewingArea {


    private ArrayList<Potion> potions = new ArrayList<Potion>();


    public PotionBrewingArea() {

        //ArrayList<IngredientCard> ingredients = IngredientCardDeck.getIngredientCardDeck();

        ArrayList<IngredientCard> recipe_1 = new ArrayList<IngredientCard>();
        recipe_1.add(new Herb("ginger", 1, 3, "yellow"));
        recipe_1.add(new Mineral("zinc", 9, 12, "grey"));
        recipe_1.add(new Mushroom("porcini", 12, 10, "yellow"));
		Potion potion_1 = new Potion("Romictmce", 10,recipe_1 );
        potions.add(potion_1);

        ArrayList<IngredientCard> recipe_2 = new ArrayList<IngredientCard>();
        recipe_2.add(new Herb("sage", 5, 13, "lilac"));
        recipe_2.add(new Mushroom("portobello", 11, 20, "brown"));
		Potion potion_2 = new Potion("Poigi-Joicie", 7,recipe_2 );
        potions.add(potion_2);


        ArrayList<IngredientCard> recipe_3 = new ArrayList<IngredientCard>();
        recipe_3.add(new Herb("dill", 4, 11, "green"));
        recipe_3.add(new Mineral("quartz", 10, 3, "purple"));
        recipe_3.add(new Mushroom("oyster", 13, 9, "white"));
		Potion potion_3= new Potion("Bodion", 10,recipe_3 );
        potions.add(potion_3);


        ArrayList<IngredientCard> recipe_4 = new ArrayList<IngredientCard>();
        recipe_4.add(new Herb("cilantro", 3, 9, "green"));
        recipe_4.add(new Mineral("fluorite", 8, 10, "white"));
		Potion potion_4 = new Potion("Plovee", 10,recipe_4 );
        potions.add(potion_4);


        ArrayList<IngredientCard> recipe_5 = new ArrayList<IngredientCard>();
        recipe_5.add(new Herb("sage", 5, 13, "lilac"));
        recipe_5.add(new Mineral("copper", 6, 5, "grey"));
		Potion potion_5 = new Potion("Poticon", 10,recipe_5 );
        potions.add(potion_5);


        ArrayList<IngredientCard> recipe_6 = new ArrayList<IngredientCard>();
        recipe_6.add(new Herb("ginger", 1, 3, "yellow"));
		Potion potion_6 = new Potion("Pacice", 9,recipe_6 );
        potions.add(potion_6);


        ArrayList<IngredientCard> recipe_7 = new ArrayList<IngredientCard>();
        recipe_7.add(new Herb("rosemary", 2, 5, "green"));
        recipe_7.add(new Mineral("magnesium", 7, 9, "white"));
        recipe_7.add(new Mushroom("portobello", 11, 20, "brown"));
		Potion potion_7 = new Potion("Roin", 4,recipe_7 );
        potions.add(potion_7);


        ArrayList<IngredientCard> recipe_8 = new ArrayList<IngredientCard>();
        recipe_8.add(new Herb("cilantro", 3, 9, "green"));
        recipe_8.add(new Mushroom("porcini", 12, 10, "yellow"));
		Potion potion_8 = new Potion("Rungton", 15,recipe_8 );
        potions.add(potion_8);


        ArrayList<IngredientCard> recipe_9 = new ArrayList<IngredientCard>();
        recipe_9.add(new Herb("sage", 5, 13, "lilac"));
        recipe_9.add(new Mushroom("oyster", 13, 9, "white"));
		Potion potion_9 = new Potion("Potion", 5,recipe_9 );
        potions.add(potion_9);


        ArrayList<IngredientCard> recipe_10 = new ArrayList<IngredientCard>();
        recipe_10.add(new Herb("dill", 4, 11, "green"));
        recipe_10.add(new Mushroom("hedgehog", 14, 2, "brown"));
		Potion potion_10 = new Potion("Potie", 11,recipe_10);
        potions.add(potion_10);
		

	}

    public Potion combine(IngredientCard ingredientCard1, IngredientCard ingredientCard2) {
        for (Potion potion : potions) {
            if (potion.checkIngredient(ingredientCard1) && potion.checkIngredient(ingredientCard2)) return potion;
        }

       
        return null;
    }

    
}
