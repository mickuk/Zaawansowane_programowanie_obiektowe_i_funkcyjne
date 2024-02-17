package pl.edu.pw.mini.owady;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Apis {

    private static ArrayList<Pszczola> pszczoly = new ArrayList<>();

    public int iloscPszczolWUlu() {return pszczoly.size();}
    public ArrayList<Pszczola> getPszczoly() {return pszczoly;}
    public void infoOUlu() {for (Pszczola p : pszczoly) System.out.println(p);}


    public Apis(String imie, int wiek, int silaAtaku, int iloscJaj) {
        pszczoly.add(new KrolowaMatka(imie, wiek, silaAtaku, iloscJaj));
        pszczoly.add(new Truten("T1", 1, 2));
        pszczoly.add(new Truten("T2", 3, 4));
        pszczoly.add(new Truten("T3", 5, 6));
        pszczoly.add(new Robotnica("R1", 7, 8));
        pszczoly.add(new Robotnica("R2", 9, 10));
        pszczoly.add(new Robotnica("R3", 11, 12));

    }

// klasa Pszczola jest u mnie abstrakcyjna, bo uznaję, że pszczoła jest albo królową albo trutniem lub robotnicą
    static abstract class Pszczola implements Runnable {
        protected String imie;
        protected int silaAtaku;
        protected int wiek;

        public Pszczola(String imie, int wiek, int silaAtaku) {
            this.imie = imie;
            this.wiek = wiek;
            this.silaAtaku = silaAtaku;
        }

        @Override
        public String toString() {
            return "Pszczola{}";
        }

        public String getImie() {
            return imie;
        }

        public int getSilaAtaku() {
            return silaAtaku;
        }

        public int getWiek() {
            return wiek;
        }

        @Override
        public void run() {

        }
    }//koniec Pszczoły


    public static class KrolowaMatka extends Pszczola {

        private static int iloscJaj;
        public KrolowaMatka(String imie, int wiek, int silaAtaku, int iloscJaj) {
            super(imie, wiek, silaAtaku);
            this.silaAtaku = 100;
            this.iloscJaj = iloscJaj;

        }

        public void zaplodnienie() {
            iloscJaj += 1000;
        }

        @Override
        public void run() {
            System.out.println("Lot godowy...");
        }

        public String getImie() {
            return imie;
        }

        public int getSilaAtaku() {
            return silaAtaku;
        }

        @Override
        public String toString() {
            return "Królowa " + this.imie + " (atak: " + this.silaAtaku + "), żyję " + this.wiek + " dni i będę matką dla " + this.iloscJaj + " młodych pszczółek";
        }
    }//koniec KrólowaMatka


    public static class Truten extends Pszczola {
        private boolean przydatny;

        public Truten(String imie, int wiek, int silaAtaku) {
            super(imie, wiek, silaAtaku);
            this.silaAtaku = 0;
            this.przydatny = true;
        }

        public void zaplodnienie(KrolowaMatka krolowa) {
            if (przydatny) {
                krolowa.zaplodnienie();
                this.przydatny = false;
                System.out.println("Można umierać...");
            } else {
                System.out.println("Już umarłem :_) ");
            }
        }


        private KrolowaMatka findKrolowa(ArrayList<Pszczola> lista) {
            KrolowaMatka krolowa = null;
            for (Pszczola pszczola : lista) {
                if (pszczola instanceof KrolowaMatka) {
                    return krolowa = (KrolowaMatka) pszczola;
                }
            }
            return krolowa;
        }

        @Override
        public void run() {
            if(Math.random() <= 0.5) {
                // Nie robi nic
            } else {
                KrolowaMatka krolowa = findKrolowa(pszczoly);
                if(krolowa != null){
                    this.zaplodnienie(krolowa);
                }
            }
        }

        public String getImie() {
            return imie;
        }

        public int getSilaAtaku() {
            return silaAtaku;
        }

        @Override
        public String toString() {
            return "Truteń " + this.imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni";

        }
    }//koniec Truteń




    public static class Robotnica extends Pszczola {
        private int iloscWyprodukowanegoMiodu;

        public Robotnica(String imie, int wiek, int silaAtaku) {
            super(imie, wiek, silaAtaku);
            this.iloscWyprodukowanegoMiodu = 0;
        }

        public void zbierajNektar(int ilosc) {
            iloscWyprodukowanegoMiodu += ilosc;
            System.out.println("Zbieram nektar");
        }

        @Override
        public void run() {
            Random random = new Random();
            int iloscNektaru = random.nextInt(0, 21); // Losowa ilość nektaru (od 0 do 20)
            this.zbierajNektar(iloscNektaru);
        }

        public String getImie() {
            return imie;
        }

        public int getSilaAtaku() {
            return silaAtaku;
        }

        @Override
        public String toString() {
            return "Robotnica " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni i zrobiłam " + iloscWyprodukowanegoMiodu + " baryłek miodu :)";
        }
    }//koniec Robotnica




    public void zyciePszczol() {
        Random random = new Random();

        KrolowaMatka krolowa = (KrolowaMatka) pszczoly.get(0);  // krolowa jest zawsze pierwsza

        Truten t1 = null;
        Truten t2 = null;
        Robotnica r1 = null;
        Robotnica r2 = null;
        Robotnica r3 = null;
        Robotnica r4 = null;

        Truten[] listaTr = {null, null};
        Robotnica[] listaRo = {null, null, null};

        int j = 0;

        for (Pszczola pszczola : pszczoly) {
            if (pszczola instanceof Truten) {
                listaTr[j] = (Truten) pszczola;
                j++;
                if (j == 2) {
                    break;
                }
            }
        }

        j = 0;

        for (Pszczola pszczola : pszczoly) {
            if (pszczola instanceof Robotnica) {
                listaRo[j] = (Robotnica) pszczola;
                j++;
                if (j == 3) {
                    break;
                }
            }
        }

        t1 = listaTr[0];
        t2 = listaTr[1];

        r1 = listaRo[0];
        r2 = listaRo[1];
        r3 = listaRo[2];

        // ul ma zawsze królową, 3 trutnie i 3 robotnice, zatem listaTr i listaRo nie posiada żadnego nulla

        t1.zaplodnienie(krolowa);
        t2.zaplodnienie(krolowa);
        r1.zbierajNektar(random.nextInt(0,200));
        r2.zbierajNektar(random.nextInt(0,200));
        r3.zbierajNektar(random.nextInt(0,200));

    }//koniec ŻyciePszczół

    public void sortujWgSilyIImienia() {
        pszczoly.sort(new Comparator<Pszczola>() {
            @Override
            public int compare(Pszczola p1, Pszczola p2) {
                int compareBySila = Integer.compare(p2.getSilaAtaku(), p1.getSilaAtaku());
                if (compareBySila == 0) {
                    return p1.getImie().compareTo(p2.getImie());
                }
                return compareBySila;
            }
        });
    }//koniec sortujWgSilyIImienia


    public static class PorownanieWieku implements Comparator<Pszczola>{
        @Override
        public int compare(Pszczola p1, Pszczola p2) {
            return p1.wiek - p2.wiek;
        }
    }//koniec PorowanieWieku

    public void dodajZolnierza(String imie, int wiek, int silaAtaku) {
        pszczoly.add(new Pszczola(imie, wiek, silaAtaku) {

            @Override
            public void run() {
                System.out.println("Walka to moje życie!!!");
            }

            @Override
            public String toString() {
                return "Żołnierz " + imie + " (atak: " + silaAtaku + "), żyję " + wiek + " dni i potrafię użądlić!";
            }
        });
    }//koniec dodajZolnierza


    public void dodajPszczole(Pszczola pszczola) {
        pszczoly.add(pszczola);
    }

    public void watkiPszczol() {
        for (Pszczola pszczola : pszczoly) {
            Thread thread = new Thread(pszczola);
            thread.start();
        }
    }


    // pomocnicza funkcja:
    public KrolowaMatka findKrolowa(ArrayList<Pszczola> lista)throws NullPointerException{
        KrolowaMatka krolowa = null;
        for (Pszczola pszczola: lista) {
            if (pszczola instanceof KrolowaMatka){
                krolowa = (KrolowaMatka) pszczola;
            }
        }
        return krolowa;
    }//koniec findKrolowa

}
