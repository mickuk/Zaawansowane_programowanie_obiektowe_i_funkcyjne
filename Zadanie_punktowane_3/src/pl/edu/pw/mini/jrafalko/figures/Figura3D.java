package pl.edu.pw.mini.jrafalko.figures;

public abstract class Figura3D extends Figura2D {

    protected Figura2D podstawa;

    public abstract double objetosc();

    public Figura3D() {}
    public Figura3D(Figura2D podstawa, int wysokosc) {
        super(wysokosc);
        this.podstawa = podstawa;
    }
}