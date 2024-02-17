package pl.edu.pw.mini.jrafalko.cargo;

public class Barrel extends Cargo {

    private int capacity;
    private int content;

    public Barrel() {
        this.capacity = 100;
    }

    private Barrel(boolean big) {
        if(big) {
            this.capacity = 300;
        } else {
            this.capacity = 200;
        }
    }

    private void fillTheBarrel() {
        this.content = capacity;
    }

    private void pour() {
        if(content > 0) {
            content--;
        }
    }

    @Override
    public String toString() {
        return "Barrel{" +
                "capacity=" + capacity +
                ", content=" + content +
                '}';
    }
}
