package domain;

public class IngredientCard {

    private String name;
    private Molecule molecule;


	public IngredientCard(String name, Molecule molecule) {
		this.name = name;
		this.molecule = molecule;
	}

	public String getName() {
		return name;
	}

	public Molecule getMolecule() {
		return molecule;
	}
	
}
