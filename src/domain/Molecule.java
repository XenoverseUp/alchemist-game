package domain;

public class Molecule {
    public Atom red;
    public Atom green;
    public Atom blue;

    public Molecule(Atom r, Atom g, Atom b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public Atom getRed(){
        return red;
    }

    public Atom getGreen(){
        return green;
    }

    public Atom getBlue(){
        return blue;
    }

    public String toString(){
        return red.toString() + " " + green.toString() +" "+blue.toString();
    }

    
}
