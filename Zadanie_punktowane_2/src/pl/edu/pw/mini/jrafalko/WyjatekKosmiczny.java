package pl.edu.pw.mini.jrafalko;

public class WyjatekKosmiczny extends RuntimeException {
    public WyjatekKosmiczny(String message) {
        super(message);
    }
}

class WyjatekEkonomiczny extends WyjatekKosmiczny {
    public WyjatekEkonomiczny(String message) {
        super(message);
    }
}

class WyjatekPrzeladowania extends WyjatekKosmiczny {
    public WyjatekPrzeladowania(String message) {
        super(message);
    }
}