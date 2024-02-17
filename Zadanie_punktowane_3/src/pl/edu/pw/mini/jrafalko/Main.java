package pl.edu.pw.mini.jrafalko;

import pl.edu.pw.mini.jrafalko.figures.Figura2D;
import pl.edu.pw.mini.jrafalko.streams.GeneratorFigur;
import pl.edu.pw.mini.jrafalko.streams.ImplementacjaInterfejsu;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ImplementacjaInterfejsu imp = new ImplementacjaInterfejsu(GeneratorFigur.generuj());

        System.out.println("1. Największa figura względem pola wysokosc: ");

        Figura2D najwiekszaFigura = imp.getNajwiekszaFigure();
        if (najwiekszaFigura != null) {
            System.out.println(najwiekszaFigura);
        } else {
            System.out.println("Brak figur.");
        }


        System.out.println("\n2. Figura2D o najmniejszym polu powierzchni: ");


        Figura2D najmPole = imp.getFigureONajmniejszymPolu();
        if (najmPole != null) {
            System.out.println(najmPole);
        } else {
            System.out.println("Brak figur.");
        }


        System.out.println("\n3. Najwyższa figura 3D: ");


        Figura2D najwyzsza3D = imp.getNajwyzszaFigure3D();
        if (najwyzsza3D != null) {
            System.out.println(najwyzsza3D);
        } else {
            System.out.println("\nBrak figur.");
        }


        System.out.println("\n4. Najmniejszy sześcian względem objętości: ");


        Figura2D najmSzescian = imp.getNajmniejszySzescian();
        if (najmSzescian != null) {
            System.out.println(najmSzescian);
        } else {
            System.out.println("Brak figur.");
        }


        System.out.println("\n5. Lista figur posortowanych względem pola powierzchni: ");


        List<Figura2D> posortowaneFigury = imp.getPosortowaneWzgledemPowiezchni();
        if (posortowaneFigury != null) {
            for (Figura2D figura : posortowaneFigury) {
                System.out.println(figura);
            }
        }
        else{
            System.out.println("Brak figur");
        }


        System.out.println("\n6. 3 figura z posortowanych malejaco względem obwodu: ");


        Figura2D figurazposortowanych = imp.getFigureZPosortowanychMalejacoWgObwodu(3);
        if (figurazposortowanych != null) {
            System.out.println(figurazposortowanych);
        } else {
            System.out.println("Nie ma figury o tym numerze.");
        }


        System.out.println("\n7. Lista 6 pierwszych figur posortowanych rosnąco względem pola powierzchni: ");



        List<Figura2D> listapierwrosnaco = imp.getPierwszePosortowaneRosnacoWgPowierzchni(6);
        int count = 0;
        if (listapierwrosnaco != null) {
            for (Figura2D figura : listapierwrosnaco) {
                System.out.println(figura);
                count++;
            }
        }
        else{
            System.out.println("\nBrak figur");
        }
        if(count < 6){
            System.out.println("Miałem wypisać jeszcze " + (6 - count) + " elementów, ale tyle nie ma.");
        }




        System.out.println("\n8. Lista wszystkich sześcianów o długości boku nie większej niż 9: ");


        List<Figura2D> listaSzescianow = imp.getSzesciany(9);
        if (listaSzescianow != null) {
            for (Figura2D figura : listaSzescianow) {
                System.out.println(figura);
            }
        }
        else{
            System.out.println("Brak figur");
        }


        System.out.println("\n9. Koło o najmniejszym polu powierzchni: ");


        Figura2D najmniejszeKolo = imp.getNajmniejszeKolo();
        if (najmniejszeKolo != null) {
            System.out.println(najmniejszeKolo);
        } else {
            System.out.println("Brak figur.");
        }


        System.out.println("\n10. Mapa figur względem ID: ");

        Map<Integer, Figura2D> mapaFigur = imp.mapaWgId();
        mapaFigur.forEach((id, figura) -> System.out.println("ID: " + id + ", Figura: " + figura));


        System.out.println("\n11. Ilość figur o polu powierzchni nie większym niż 200: ");


        int iloscmalych = imp.getiloscMalych(200);
        if (iloscmalych != 0) {
            System.out.println(iloscmalych);
        } else {
            System.out.println("\nBrak figur.");
        }


        System.out.println("\n12. Posortowany ciąg figur zaczynając od 5: ");


        List<Figura2D> posortowanewgpolaod = imp.posortowaneWgPolaZaczynajacOd(5);
        if (posortowanewgpolaod != null) {
            for (Figura2D figura : posortowanewgpolaod) {
                System.out.println(figura);
            }
        }
        else{
            System.out.println("\nBrak figur");
        }



    }
}
