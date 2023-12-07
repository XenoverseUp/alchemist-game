package domain;

/**
 * AlchemyMarker
 */
public class AlchemyMarker {

    private int id;
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

}