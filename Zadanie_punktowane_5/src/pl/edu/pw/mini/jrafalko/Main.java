package pl.edu.pw.mini.jrafalko;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Pracownik> pracownicy = FabrykaTajnePrzezPoufne.tworzZalogeFabryki();

        // wykonuję, by sprawdzić, jak wyglądały dane pełne
        pracownicy.forEach(System.out::println);
        System.out.println();

        // dane ocenzurowane
        Cenzura cenzura = new Cenzura();
        for(Pracownik p : cenzura.cenzuruj(pracownicy)){
            System.out.println(p);
        }
    }
}
