package pl.edu.pw.mini.jrafalko.workers;

import pl.edu.pw.mini.jrafalko.Pracownik;
import pl.edu.pw.mini.jrafalko.censor.ReplaceStringEnd;
import pl.edu.pw.mini.jrafalko.censor.ReplaceWithParameter;

public class Dyrektor extends Pracownik {

    private String ksywka;
    @ReplaceWithParameter("Władywostok")
    private String miastoUrodzenia;
    @ReplaceStringEnd
    private String charakterystykaOsobowosci;
    private int iloscPodwladnych;

    public Dyrektor(String imie, String nazwisko, int wiek, String ksywka,
                    String miastoUrodzenia, String charakterystykaOsobowosci,
                    int iloscPodwladnych) {
        super(imie, nazwisko, wiek);
        this.ksywka = ksywka;
        this.miastoUrodzenia = miastoUrodzenia;
        this.charakterystykaOsobowosci = charakterystykaOsobowosci;
        this.iloscPodwladnych = iloscPodwladnych;
    }

    @Override
    protected void zwiekszZysk() {
        wypracowanyZysk += 10;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", ksywka='" + ksywka + '\'' +
                ", miastoUrodzenia='" + miastoUrodzenia + '\'' +
                ", charakterystykaOsobowosci='" + charakterystykaOsobowosci + '\'' +
                ", iloscPodwladnych=" + iloscPodwladnych +
                ", dyrektor";
    }
}
