package domain;

public class IngredientCard {

	private int id;
    private String name;
    private String color;
    private int value;


	public IngredientCard(String name, int id, int value, String color) {
		this.name = name;
		this.id = id;
		this.value = value;
		this.color = color;

	}



	IngredientCard ingredientCard_1 = new Herb("ginger", 1, 3, "yellow");
	IngredientCard ingredientCard_2 = new Herb("rosemary", 2, 5, "green");
	IngredientCard ingredientCard_3 = new Herb("cilantro", 3, 9, "green");
	IngredientCard ingredientCard_4 = new Herb("dill", 4, 11, "green");
	IngredientCard ingredientCard_5 = new Herb("sage", 5, 13, "lilac");

	
	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

}
