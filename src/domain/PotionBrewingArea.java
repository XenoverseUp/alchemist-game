package domain;

import java.util.ArrayList;

public class PotionBrewingArea {


    private ArrayList<Potion> potions = new ArrayList<Potion>();


    public PotionBrewingArea() {
        ArrayList<IngredientCard> recipe1 = new ArrayList<IngredientCard>();
        recipe1.add(new Herb("ginger", 1, 3, "yellow"));
        recipe1.add(new Mineral("zinc", 9, 12, "grey"));
        potions.add(new Potion("Romictmce", 10, recipe1));

        ArrayList<IngredientCard> recipe2 = new ArrayList<IngredientCard>();
        recipe2.add(new Herb("sage", 5, 13, "lilac"));
        recipe2.add(new Mushroom("portobello", 11, 20, "brown"));
        potions.add(new Potion("Poigi-Joicie", 7, recipe2));

        ArrayList<IngredientCard> recipe3 = new ArrayList<IngredientCard>();
        recipe3.add(new Herb("dill", 4, 11, "green"));
        recipe3.add(new Mineral("quartz", 10, 3, "purple"));
        potions.add(new Potion("Bodion", 10, recipe3));

        ArrayList<IngredientCard> recipe4 = new ArrayList<IngredientCard>();
        recipe4.add(new Herb("cilantro", 3, 9, "green"));
        recipe4.add(new Mineral("fluorite", 8, 10, "white"));
        potions.add(new Potion("Plovee", 10, recipe4));


        ArrayList<IngredientCard> recipe5 = new ArrayList<IngredientCard>();
        recipe5.add(new Herb("sage", 5, 13, "lilac"));
        recipe5.add(new Mineral("copper", 6, 5, "grey"));
        potions.add(new Potion("Poticon", 10, recipe5));


        ArrayList<IngredientCard> recipe6 = new ArrayList<IngredientCard>();
        recipe6.add(new Herb("ginger", 1, 3, "yellow"));
        recipe6.add(new Mineral("copper", 6, 5, "grey"));
        potions.add(new Potion("Pacice", 9, recipe6));


        ArrayList<IngredientCard> recipe7 = new ArrayList<IngredientCard>();
        recipe7.add(new Herb("rosemary", 2, 5, "green"));
        recipe7.add(new Mineral("magnesium", 7, 9, "white"));
        potions.add(new Potion("Roin", 4, recipe7));


        ArrayList<IngredientCard> recipe8 = new ArrayList<IngredientCard>();
        recipe8.add(new Herb("cilantro", 3, 9, "green"));
        recipe8.add(new Mushroom("porcini", 12, 10, "yellow"));
        potions.add(new Potion("Rungton", 15, recipe8));


        ArrayList<IngredientCard> recipe9 = new ArrayList<IngredientCard>();
        recipe9.add(new Herb("sage", 5, 13, "lilac"));
        recipe9.add(new Mushroom("oyster", 13, 9, "white"));
        potions.add(new Potion("Potion", 5, recipe9));

        ArrayList<IngredientCard> recipe10 = new ArrayList<IngredientCard>();
        recipe10.add(new Herb("dill", 4, 11, "green"));
        recipe10.add(new Mushroom("hedgehog", 14, 2, "brown"));
        potions.add(new Potion("Potie", 11, recipe10));
	}

    public Potion combine(IngredientCard ingredientCard1, IngredientCard ingredientCard2) {
        for (int i = 0; i < potions.size(); i++) 
            if (potions.get(i).checkIngredient(ingredientCard1) && potions.get(i).checkIngredient(ingredientCard2)) 
                return potions.remove(i);
       
        return null;
    }

    
}
