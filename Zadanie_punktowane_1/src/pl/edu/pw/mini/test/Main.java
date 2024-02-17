package pl.edu.pw.mini.test;

import pl.edu.pw.mini.owady.Apis;

public class Main {

    /*
     * Klasa Main - 1.5p
     * Klasa Apis - 6.5p
     * Przejrzysty/czytelny kod - 1p
     */
    public static void main(String[] args) {

        Apis ul = new Apis("Alicja", 8, 0, 0);
        System.out.println("W ulu jest: " + ul.iloscPszczolWUlu() + " pszczół");
        ul.infoOUlu();
        //--------TODO-------------------------
        // Dodanie pszczół do listy
        //a)

        ul.dodajPszczole(new Apis.KrolowaMatka("Waleria", 7, 0, 10));
        ul.dodajPszczole(new Apis.Robotnica("Irmina", 5, 6));
        ul.dodajPszczole(new Apis.Truten("Michał", 6, 6));

        System.out.println();

        //-------------------------------------
        ul.zyciePszczol();
        System.out.println("\nW ulu jest: " + ul.iloscPszczolWUlu() + " pszczół");
        ul.infoOUlu();
        System.out.println("\nPszczoły posortowane wg siły i imienia:");
        ul.sortujWgSilyIImienia();
        ul.infoOUlu();
        System.out.println("\nPszczoły posortowane wg wieku:");
        //--------TODO-------------------------
        //Sortowanie listy pszczół za pomocą komparatora
        //b)
        ul.getPszczoly().sort(new Apis.PorownanieWieku());  // niestety nie wiem jak to zrobić, by klasa była prywatna

        //-------------------------------------
        ul.infoOUlu();
        System.out.println("\nŻołnierz:");
        //--------TODO-------------------------
        //Dodanie żołnierza
        //c)
        ul.dodajZolnierza("Helena", 10, 99);


        //-------------------------------------
        System.out.println((ul.getPszczoly().get(ul.getPszczoly().size() - 1)));
        System.out.println("\nWątki pszczół:");
        ul.watkiPszczol();
    }

}
