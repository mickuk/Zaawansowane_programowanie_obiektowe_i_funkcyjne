package pl.edu.pw.mini.jrafalko;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class PortKosmiczny {

    private List<StatekKosmiczny> statkiKosmiczne;
    Random rand = new Random();

    public PortKosmiczny(int iloscSamolotow) {

        GenerujNazwe generujNazwe = () -> {
            int dlugosc = rand.nextInt(20) + 1;
            String nazwa = "";

            for(int i=0; i < dlugosc; i++){
                nazwa += (char) (rand.nextInt(26) + 97);
            }
            return nazwa;
        };



        this.statkiKosmiczne = new ArrayList<>();
        for (int i = 0; i < iloscSamolotow; i++) {
            int r = rand.nextInt(3);
            switch (r) {
                case 0:
                    String nazwa = generujNazwe.generuj();
                    int predkoscMax = 1000 + rand.nextInt(4001);
                    int maxIloscPasazerow = 100 + rand.nextInt(101);
                    statkiKosmiczne.add(new StatekKosmicznyPasazerski(nazwa, predkoscMax, maxIloscPasazerow));
                    break;
                case 1:
                    nazwa = generujNazwe.generuj();
                    predkoscMax = 500 + rand.nextInt(501);
                    int maxZaladunek = 100 + rand.nextInt(901);
                    statkiKosmiczne.add(new StatekKosmicznyTowarowy(nazwa, predkoscMax, maxZaladunek));
                    break;
                case 2:
                    nazwa = generujNazwe.generuj();
                    predkoscMax = 5000 + rand.nextInt(5001);
                    statkiKosmiczne.add(new Mysliwiec(nazwa, predkoscMax));
                    break;
            }
        }
    }//end konstruktor PortKosmiczny

    public void wypiszStatkiKosmiczne() {
        for (int i = 0; i < this.statkiKosmiczne.size(); i++) {
            System.out.println(this.statkiKosmiczne.get(i));
        }
    }

    public void startSststkowKosmicznych() {
        for (int i = 0; i < this.statkiKosmiczne.size(); i++) {
            statkiKosmiczne.get(i).start(1 + rand.nextInt(24));
        }
    }

    public void zaladunekStatkowKosmicznych() {
        for (int i = 0; i < this.statkiKosmiczne.size(); i++) {
            if (statkiKosmiczne.get(i) instanceof StatekKosmicznyPasazerski) {
                try {
                    statkiKosmiczne.get(i).zaladunek(rand.nextInt(401));
                } catch (WyjatekKosmiczny wyjatekLotniczy) {
                    System.out.println(wyjatekLotniczy.getMessage());
                }
            } else if (statkiKosmiczne.get(i) instanceof StatekKosmicznyTowarowy) {
                try {
                    statkiKosmiczne.get(i).zaladunek(rand.nextInt(201));
                } catch (WyjatekKosmiczny wyjatekLotniczy) {
                    System.out.println(wyjatekLotniczy.getMessage());
                }
            } else {
                statkiKosmiczne.get(i).zaladunek(rand.nextInt(11));
            }
        }
    }

    Consumer<StatekKosmiczny> wypiszStatek = (statekKosmiczny) -> {
        System.out.println(statekKosmiczny.toString());
    };

    public void dzialaniaKosmiczne() {

        statkiKosmiczne.forEach(wypiszStatek);

        statkiKosmiczne.forEach(StatekKosmiczny::laduj);

        statkiKosmiczne.forEach(wypiszStatek);


        statkiKosmiczne.forEach((statekKosmiczny) -> {
            try {
                statekKosmiczny.zaladunek(rand.nextInt(401));
            } catch (WyjatekEkonomiczny | WyjatekPrzeladowania e) {
                System.out.println(e.getMessage());
            }
        });

        statkiKosmiczne.forEach(wypiszStatek);


        statkiKosmiczne.forEach((statekKosmiczny) -> {
            statekKosmiczny.start(10);
        });


        statkiKosmiczne.forEach(wypiszStatek);


        statkiKosmiczne.forEach((statekKosmiczny) -> {
            if(statekKosmiczny instanceof Mysliwiec)
                ((Mysliwiec) statekKosmiczny).atak();
        });

    }

    public void sortowanieStatkowKosmicznych() {

        statkiKosmiczne.sort(Comparator.comparingInt((StatekKosmiczny s) -> s.predkoscMax));


        statkiKosmiczne.forEach(wypiszStatek);
        //statkiKosmiczne.forEach(System.out::println);


        statkiKosmiczne.sort((StatekKosmiczny s1, StatekKosmiczny s2) -> {
            if(s1.nazwa.length() > 5 && s2.nazwa.length() > 5)
                return s1.nazwa.compareTo(s2.nazwa);
            return 0;
        });

        statkiKosmiczne.forEach(wypiszStatek);
        //statkiKosmiczne.forEach(System.out::println);
    }

    public void sortowanieLosowe(){
        LosowyKomparator losowyKomparator = () -> {
            boolean opcja = rand.nextBoolean();
            if(opcja){
                return (StatekKosmiczny s1, StatekKosmiczny s2) -> s1.predkoscMax - s2.predkoscMax;
            }
            else {
                return (StatekKosmiczny s1, StatekKosmiczny s2) -> {
                    if(s1.nazwa.length() > 5 && s2.nazwa.length() > 5)
                        return s2.nazwa.compareTo(s1.nazwa);
                    return 0;
                };
            }
        };
        statkiKosmiczne.sort(losowyKomparator.generuj());
        statkiKosmiczne.forEach(wypiszStatek);
    }



    protected static abstract class StatekKosmiczny {
        protected String nazwa;
        protected int predkoscMax;
        protected int iloscGodzinWPowietrzu;
        protected boolean wPowietrzu;
        protected boolean poOdprawie;

        public StatekKosmiczny(String nazwa, int predkoscMax) {
            this.nazwa = nazwa;
            this.predkoscMax = predkoscMax;
        }

        public void start(int godziny) {
            if (poOdprawie) {
                iloscGodzinWPowietrzu += godziny;
                if (!wPowietrzu) {
                    wPowietrzu = true;
                    System.out.println("Startujemy...");
                } else {
                    System.out.println("Lecimy...");
                }
            } else {
                System.out.println("Nie możemy wystartować");
            }
        }

        public void laduj() {
            if (wPowietrzu) {
                wPowietrzu = false;
                poOdprawie = false;
                System.out.println("Lądujemy...");
            } else {
                System.out.println("I tak jesteśmy na ziemi");
            }
        }

        public abstract void zaladunek(int iloscZaladuku) throws WyjatekKosmiczny;
    }//end StatekKosmiczny



    private class StatekKosmicznyPasazerski extends StatekKosmiczny {
        private int iloscPasazerow;
        private int maxIloscPasazerow;

        public StatekKosmicznyPasazerski(String nazwa, int predkoscMax, int maxIloscPasazerow) {
            super(nazwa, predkoscMax);
            this.maxIloscPasazerow = maxIloscPasazerow;
        }

        @Override
        public void zaladunek(int iloscZaladuku) throws WyjatekEkonomiczny, WyjatekPrzeladowania {
            this.iloscPasazerow = iloscZaladuku;
            if(iloscZaladuku < maxIloscPasazerow/2){
                throw new WyjatekEkonomiczny("Za mało pasażerów nie opłaca się lecieć");
            } else if (iloscZaladuku > maxIloscPasazerow) {
                poOdprawie = true;
                throw new WyjatekPrzeladowania("Za dużo o " + (iloscZaladuku - maxIloscPasazerow) + " pasażerów");
            } else {
                poOdprawie = true;
            }
        }

        @Override
        public String toString() {
            return "Statek kosmiczny pasażerski " +
                    "o nazwie '" + nazwa + '\'' +
                    ". Predkość maksymalna " + predkoscMax +
                    ", w powietrzu spędził łącznie " + iloscGodzinWPowietrzu + " godzin" +
                    ", moze zabrac na pokład " + maxIloscPasazerow + " pasażerów. " +
                    (wPowietrzu ? "Obecnie leci z " + iloscPasazerow + " pasażerami na pokładzie." :
                            "Aktualnie uziemiony");
        }
    }//end StatekKosmicznyPasazerski

    private class StatekKosmicznyTowarowy extends StatekKosmiczny {
        private int maxZaladunek;
        private int ladunek;

        public StatekKosmicznyTowarowy(String nazwa, int predkoscMax, int maxZaladunek) {
            super(nazwa, predkoscMax);
            this.maxZaladunek = maxZaladunek;
        }

        @Override
        public void zaladunek(int iloscZaladuku) throws WyjatekEkonomiczny {
            this.ladunek = iloscZaladuku;
            if(iloscZaladuku < maxZaladunek/2){
                throw new WyjatekEkonomiczny("Zbyt mały ładunek, nie opłaca się lecieć");
            } else if (iloscZaladuku > maxZaladunek) {
                poOdprawie = true;
                throw new WyjatekPrzeladowania("Za dużo o " + (iloscZaladuku - maxZaladunek) + " ton ładunku");
            } else {
                poOdprawie = true;
            }
        }

        @Override
        public String toString() {
            return "Statek kosmiczny towarowy " +
                    "o nazwie '" + nazwa + '\'' +
                    ". Predkość maksymalna " + predkoscMax +
                    ", w powietrzu spędził łącznie " + iloscGodzinWPowietrzu + " godzin" +
                    ", moze zabrac na pokład " + maxZaladunek + " ton ładunku. " +
                    (wPowietrzu ? "Obecnie leci z " + ladunek + " t. ładunku." :
                            "Aktualnie uziemiony");
        }
    }//end StatekKosmicznyTowarowy

    protected static class Mysliwiec extends StatekKosmiczny {
        private int iloscRakiet;

        public Mysliwiec(String nazwa, int predkoscMax) {
            super(nazwa, predkoscMax);
        }

        @Override
        public void zaladunek(int iloscZaladuku) {
            this.poOdprawie = true;
            this.iloscRakiet = iloscZaladuku;
        }

        public void atak() {
            if(wPowietrzu && iloscRakiet > 0) {
                System.out.println("Ataaaaak!");
                iloscRakiet -= 1;
            }
            if(iloscRakiet == 0){
                laduj();
            }
        }

        @Override
        public String toString() {
            return "Myśliwiec " +
                    "o nazwie '" + nazwa + '\'' +
                    ". Predkość maksymalna " + predkoscMax +
                    ", w powietrzu spędził łącznie " + iloscGodzinWPowietrzu + " godzin. " +
                    (wPowietrzu ? "Obecnie leci, rakiet: " + iloscRakiet + "." :
                            "Aktualnie uziemiony.");
        }
    }//end Mysliwiec

    @FunctionalInterface
    private interface GenerujNazwe {
        String generuj();
    }


    private interface LosoweSortowanie extends Comparator<StatekKosmiczny>{
        @Override
        int compare(StatekKosmiczny o1, StatekKosmiczny o2);
    }


    private interface LosowyKomparator{
        Comparator<StatekKosmiczny> generuj();
    }


}//end PortKosmiczny