package pl.edu.pw.mini.jrafalko.cargo;

import pl.edu.pw.mini.jrafalko.Reflectionable;

public class Chest extends Cargo implements Reflectionable {

    private String material;
    private String label;

    public Chest(boolean metal) {
        if(metal) {
            this.material = "Metal";
        } else {
            this.material = "Drewno";
        }
    }

    private Chest() {
        this.material = "Plastyk";
    }

    private Chest(boolean metal, String label) {
        this(metal);
        this.label = label;
    }

}