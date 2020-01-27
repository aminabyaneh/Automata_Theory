package utils;

public class Phrase {

    public String string;
    public boolean hasStar;
    public Character nextOperation;
    public Character prevOperation;

    public Phrase () {
        super();
    }

    public Phrase (String string) {

        this.string = string;
        this.hasStar = false;
        this.nextOperation = Chars.none;
        this.prevOperation = Chars.none;
    }

    @Override
    public String toString() {
        return String.format("String: " + this.string + " Star: " +
                Boolean.toString(hasStar) + " N: " +
                this.nextOperation.toString() + " P: " +
                this.prevOperation.toString());
    }
}
