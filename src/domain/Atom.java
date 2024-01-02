package domain;

public abstract class Atom implements Comparable<Atom> {
    protected char color;
    protected char size;
    protected char sign;

    public char getColor() {
        return color;
    }

    public char getSign() {
        return sign;
    }

    public char getSize() {
        return size;
    }

    @Override
    public String toString(){
        return "" + color + size + sign;
    }

    @Override
    public int compareTo(Atom a) {
        if (this.color == a.color && this.size == a.size && this.sign == a.sign) return 0;
        return -1;
    }
}



