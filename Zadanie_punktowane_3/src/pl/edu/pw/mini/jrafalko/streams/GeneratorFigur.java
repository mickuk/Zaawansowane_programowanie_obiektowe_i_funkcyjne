package pl.edu.pw.mini.jrafalko.streams;

import pl.edu.pw.mini.jrafalko.figures.*;

import java.util.ArrayList;
import java.util.List;

public class GeneratorFigur {

    public static List<Figura2D> generuj() {
        List<Figura2D> figury = new ArrayList<>();

        figury.add(new Kwadrat(4));
        figury.add(new Kwadrat(11));
        figury.add(new Kwadrat(16));
        figury.add(new Kolo(5));
        figury.add(new Kolo(12));
        figury.add(new Kolo(9));
        figury.add(new TrojkatRownoboczny(4, 2));
        figury.add(new TrojkatRownoboczny(7, 8));
        figury.add(new TrojkatRownoboczny(15, 4));
        figury.add(new Szescian(6));
        figury.add(new Szescian(3));
        figury.add(new Szescian(12));
        figury.add(new Stozek(5, 5));
        figury.add(new Stozek(11, 8));
        figury.add(new Stozek(5, 6));

        return figury;
    }
}
