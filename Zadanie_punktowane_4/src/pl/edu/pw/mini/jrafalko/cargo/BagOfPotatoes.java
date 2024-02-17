package pl.edu.pw.mini.jrafalko.cargo;

public class BagOfPotatoes extends Cargo{

    private String label;
    private int numberOfPotatoes;

    private void addPotato() {
        numberOfPotatoes++;
    }

    private long bagWeight() {
        return Math.round(numberOfPotatoes * 0.1d);
    }

    protected void sayHello() {
        System.out.println("Hello!");
    }

}
