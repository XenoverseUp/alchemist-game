package domain;

public class AlchemyMarker {

    private final int id;
    public boolean associated;
    private final Molecule molecule;
    private int markerAmount;
    private String imPath;

    public AlchemyMarker(int id, Molecule molecule) {
        this.id = id;
        this.associated = false;
        this.molecule = molecule;
        this.markerAmount = 8;
        this.imPath = "./src/resources/image/marker" + id + ".png";
    }

    public boolean associate() {
        this.associated = true;
        return associated;
    }

    public boolean dissociate() {
        this.associated = false;
        return associated;
    }

    public int getId() {
        return id;
    }

    public boolean checkAvailability() {
        return associated;
    }

    public Molecule getMolecule() {
        return this.molecule;
    }

    public int getAmount() {
        return markerAmount;
    }

    public void increaseAmount() {
        this.markerAmount++;
    }

    public void decreaseAmount() {
        this.markerAmount--;
    }

    public String getImagePath() {
        return imPath;
    }
}