package domain;

public class IngredientCard {

	private int id;
    String name;
    private String color;
    private int value;


	public IngredientCard(String name, int id, int value, String color) {
		this.name = name;
		this.id = id;
		this.value = value;
		this.color = color;

	}


	
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


	public int getId() {
		return id;
	}
	
	public String getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
}
