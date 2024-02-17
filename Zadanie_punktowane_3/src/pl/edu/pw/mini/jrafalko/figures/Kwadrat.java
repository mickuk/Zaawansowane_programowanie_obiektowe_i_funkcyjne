package pl.edu.pw.mini.jrafalko.figures;

public class Kwadrat extends Figura2D {

    public Kwadrat(int bok) {
        super(bok);
    }

    @Override
    public double polePowierzchni() {
        return wysokosc * wysokosc;
    }

    @Override
    public double obwod() {
        return 4 * wysokosc;
    }

    @Override
    public String toString() {
        return "Kwadrat, " +
                "wysokość = " + wysokosc +
                ", pole = " + polePowierzchni() +
                ", obwód = " + obwod();
    }
}
