package domain;

public class AtomFactory {
    private char color;
    private char sign;
    private char size;


    public AtomFactory red() {
        this.color = 'r';
        return this;
    }

    public AtomFactory green() {
        this.color = 'g';
        return this;
    }

    public AtomFactory blue() {
        this.color = 'b';
        return this;
    }

    public AtomFactory plus() {
        this.sign = '+';
        return this;
    }
  
    public AtomFactory minus() {
        this.sign = '-';
        return this;
    }

    public AtomFactory big() {
        this.size = 'b';
        return this;
    }

    public AtomFactory small() {
        this.size = 's';
        return this;
    }

    public Atom create() {
        if (this.color == 'r')
            return new RedAtom(size, sign);
        else if (this.color == 'g')
            return new GreenAtom(size, sign);
        return new BlueAtom(size, sign);
    }

}
