package domain;

import java.util.ArrayList;

public class AlchemyMarkerDeck {
    private ArrayList<AlchemyMarker> alchemyMarkerDeck = new ArrayList<AlchemyMarker>();
    public int chosen;

    public AlchemyMarkerDeck() {

        for (int i = 0; i < 8; i++) {
            AlchemyMarker marker = new AlchemyMarker(i, Molecules.molecules.get(i));
            alchemyMarkerDeck.add(marker);

        }

    }

    public AlchemyMarker getMarker(int id) {
        return alchemyMarkerDeck.get(id);
    }

    public ArrayList<AlchemyMarker> getDeck() {
        return this.alchemyMarkerDeck;
    }

    public void setChosen(int id) {
        this.chosen = id;
    }

    public AlchemyMarker getChosen() {
        return this.alchemyMarkerDeck.get(chosen);
    }

}
