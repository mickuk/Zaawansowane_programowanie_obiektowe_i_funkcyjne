package pl.edu.pw.mini.jrafalko;

public class Main {

    public static void main(String[] args) {

        PortKosmiczny portKosmiczny = new PortKosmiczny(5);

        System.out.println("Statki kosmiczne w porcie:");
        System.out.println("---------------------");
        portKosmiczny.wypiszStatkiKosmiczne();
        System.out.println("\nPróba odlotu:");
        System.out.println("----------------------");
        portKosmiczny.startSststkowKosmicznych();
        System.out.println("\nOdprawa:");
        System.out.println("----------");
        portKosmiczny.zaladunekStatkowKosmicznych();
        portKosmiczny.wypiszStatkiKosmiczne();
        System.out.println("\nOdlot:");
        System.out.println("--------");
        portKosmiczny.startSststkowKosmicznych();
        portKosmiczny.wypiszStatkiKosmiczne();
        System.out.println("\nDziałania w porcie:");
        System.out.println("-----------------------");
        portKosmiczny.dzialaniaKosmiczne();
        System.out.println("\nSortowanie ststków:");
        System.out.println("-----------------------");
        portKosmiczny.sortowanieStatkowKosmicznych();
        System.out.println("\nSortowanie losowe:");
        System.out.println("--------------------");
        portKosmiczny.sortowanieLosowe();
        System.out.println("\nOBCY ATAKUJĄ!!!");
        System.out.println("----------------------");
        PortKosmiczny.StatekKosmiczny ufo = new PortKosmiczny.Mysliwiec("UFO", 100000){
            @Override
            public String toString() {
                return "To nie obcy, to nowa technologia z przyszłosci";
            }
        };
        System.out.println(ufo);
    }
}
