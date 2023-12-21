package domain;

public class PublicationCard {
	private IngredientCard ingredient;
	private AlchemyMarker marker = null;
	private Player player;
	private int id;

	public PublicationCard(IngredientCard ingredient, int id) {
		this.ingredient = ingredient;
		this.id = id;
	}

	public IngredientCard getIngredient() {
		return this.ingredient;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	public AlchemyMarker getAlchemyMarker() {
		return this.marker;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setAlchemyMarker(AlchemyMarker marker) {
		this.marker = marker;
	}

	public void removeAlchemyMarker() {
		this.marker = null;
	}

}
