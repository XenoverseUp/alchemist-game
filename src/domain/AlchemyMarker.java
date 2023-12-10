package domain;

public class AlchemyMarker {

    private final int id;
    public boolean associated;

    public AlchemyMarker(int id) {
        this.id = id;
        this.associated = false;
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

}