package pl.edu.pw.mini.jrafalko.workers;

import pl.edu.pw.mini.jrafalko.Pracownik;
import pl.edu.pw.mini.jrafalko.Produkty;
import pl.edu.pw.mini.jrafalko.censor.InvokeMethod;
import pl.edu.pw.mini.jrafalko.censor.RemoveYounger;
import pl.edu.pw.mini.jrafalko.censor.ResetInt;
import pl.edu.pw.mini.jrafalko.censor.SetFields;

@RemoveYounger
@SetFields
public class Monter extends Pracownik {
    @ResetInt
    private int iloscWyprodukowanychElementow;
    private Produkty produkowanyElement;

    public Monter(String imie, String nazwisko, int wiek,
                  int iloscWyprodukowanychElementow, Produkty produkowanyElement) {
        super(imie, nazwisko, wiek);
        this.iloscWyprodukowanychElementow = iloscWyprodukowanychElementow;
        this.produkowanyElement = produkowanyElement;
    }

    @Override
    @InvokeMethod(10)
    protected void zwiekszZysk() {
        wypracowanyZysk += 2;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", iloscWyprodukowanychElementow=" + iloscWyprodukowanychElementow +
                ", produkowanyElement=" + produkowanyElement +
                ", monter";
    }
}
