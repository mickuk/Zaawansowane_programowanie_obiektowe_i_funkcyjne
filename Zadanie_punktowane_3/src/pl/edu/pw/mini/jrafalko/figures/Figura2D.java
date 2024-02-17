package pl.edu.pw.mini.jrafalko.figures;

public abstract class Figura2D {

    private static int licznikFigur;
    private final int id;
    protected int wysokosc;

    public abstract double polePowierzchni();
    public abstract double obwod();

    public Figura2D() {
        this.id = ++licznikFigur;
    }
    public Figura2D(int wysokosc) {
        this.wysokosc = wysokosc;
        this.id = ++licznikFigur;
    }

    public int getId() {
        return id;
    }

    public int getWysokosc() {
        return wysokosc;
    }
}
