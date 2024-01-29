package domain;

public class Molecule implements Comparable<Molecule> {
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

    @Override
    public String toString(){
        return red.toString() + " " + green.toString() +" "+blue.toString();
    }

    @Override
    public int compareTo(Molecule m) {
        if (this.red == m.getRed() && this.green == m.getGreen() && this.blue == m.getBlue()) return 0;
        return -1;
    }
}
