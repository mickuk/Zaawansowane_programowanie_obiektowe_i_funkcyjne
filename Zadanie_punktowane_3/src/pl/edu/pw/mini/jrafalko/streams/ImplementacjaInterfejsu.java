package pl.edu.pw.mini.jrafalko.streams;

import pl.edu.pw.mini.jrafalko.figures.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImplementacjaInterfejsu implements MetodyStrumieniowe {

    List<Figura2D> figury;

    public ImplementacjaInterfejsu(List<Figura2D> fig) {
        this.figury = fig;
    }

    /**
     * 1
     * @return Największa figura względem właściwości wysokosc
     * 0,5 pkt
     */
    @Override
    public Figura2D getNajwiekszaFigure() {
        return figury.stream().max(Comparator.comparing(f -> f.getWysokosc())).orElse(null);  // można zamienić na method reference, ale zostawiam lambda jak w poleceniu
    }

    /**
     * 2
     * @return Figura2D o najmniejszym polu powierzchni
     * 0,5 pkt
     */


    // zrozumiałem, że mają być tylko figury 2D brane pod uwagę (z programu wynika, że Figura3D też jest Figura2D, ale z matematycznego punktu widzenia biorę pod uwagę tylko figury na płaszczyźnie, jak kwadrat, koło i trójkąt)
    @Override
    public Figura2D getFigureONajmniejszymPolu() {
        return figury.stream()
                .filter(figura -> !(figura instanceof Figura3D)) // filtruję tylko Figura2D, ale nie Figura3D
                .min(java.util.Comparator.comparing(f -> f.polePowierzchni()))
                .orElse(null);
    }

    /**
     * 3
     * @return Najwyższa figura 3D
     * 0,5 pkt
     */
    @Override
    public Figura2D getNajwyzszaFigure3D() {
        return figury.stream()
                .filter(figura -> figura instanceof Figura3D)
                .map(figura -> (Figura3D) figura)  // rzutujemy na Figura3D
                .max(Comparator.comparing(f -> f.getWysokosc())) // (Figura3D::getWysokosc) -- pozamieniałem wszędzie na lambda, jak w poleceniu
                .orElse(null);
    }

    /**
     * 4
     * @return Najmniejszy sześcian względem objętości
     * 1 pkt
     */
    @Override
    public Figura2D getNajmniejszySzescian() {
        return figury.stream()
                .filter(figura -> figura instanceof Szescian)
                .map(figura -> (Szescian) figura)
                .min(Comparator.comparing(s -> s.objetosc()))
                .orElse(null);
    }

    /**
     * 5
     * @return Lista figur posortowanych względem pola powierzchni
     * 0,5 pkt
     */
    @Override
    public List<Figura2D> getPosortowaneWzgledemPowiezchni() {
        return figury.stream()
                .sorted(Comparator.comparing(f -> f.polePowierzchni()))  // z .reversed będzie malejąco
                .collect(Collectors.toList());
    }

    /**
     * 6
     * @return Figura z posortowanych malejaco względem obwodu,
     *         nr figury podany w argumencie,
     *         pomijając te, które obwodu nie mają
     * 1 pkt
     */
    @Override
    public Figura2D getFigureZPosortowanychMalejacoWgObwodu(int nr) {
        return figury.stream()
                .filter(figura -> figura.obwod() > 0)  // pomijamy figury bez obwodu, czyli te 3D
                .sorted((f1, f2) -> Double.compare(f2.obwod(), f1.obwod()))  // sortujemy malejąco względem obwodu
                .skip(nr - 1)  // pomijamy figury do numeru nr-1
                .findFirst()  // pobieramy pierwszą figurę po pominięciu nr-1 liczby figur
                .orElse(null);
    }

    /**
     * 7
     * @return Lista pierwszych figur posortowanych rosnąco względem pola powierzchni,
     *         o wysokości nie większej niż  i polu powierzchni nie mniejszym niż 10,
     *         ilość zwracanych figur w argumencie
     * 1 pkt
     */
    @Override
    public List<Figura2D> getPierwszePosortowaneRosnacoWgPowierzchni(int ilosc) {
        return figury.stream()
                .filter(figura -> figura.getWysokosc() <= 10 & figura.polePowierzchni() >= 10)
                .sorted(Comparator.comparing(f -> f.polePowierzchni()))
                .limit(ilosc)
                .collect(Collectors.toList());
    }

    /**
     * 8
     * @return Lista wszystkich sześcianów o długości boku nie większej niż bok
     * 1 pkt
     */
    @Override
    public List<Figura2D> getSzesciany(int bok) {
        return figury.stream()
                .filter(figura -> figura instanceof Szescian)
                .map(figura -> (Szescian) figura)
                .filter(figura -> figura.getWysokosc() <= bok)  // w konstruktorze sześcianu mamy wysokosc, która zastępuje bok
                .collect(Collectors.toList());
    }

    /**
     * 9
     * @return Koło o najmniejszym polu powierzchni
     * 0,5 pkt
     */
    @Override
    public Figura2D getNajmniejszeKolo() {
        return figury.stream()
                .filter(figura -> figura instanceof Kolo)
                .map(figura -> (Kolo) figura)
                .min(Comparator.comparing(k -> k.polePowierzchni()))
                .orElse(null);
    }

    /**
     * 10
     * @return Mapa figur względem ID
     * 1 pkt
     */
    @Override
    public Map<Integer, Figura2D> mapaWgId() {
        return figury.stream()
                .collect(Collectors.toMap(f -> f.getId(), figura -> figura));
    }

    /**
     * 11
     * @return Ilość figur o polu powierzchni nie większym niż pole
     * 0,5 pkt
     */
    @Override
    public int getiloscMalych(double pole) {
        return (int) figury.stream()
                .filter(figura -> figura.polePowierzchni() <= pole)
                .count();
    }

    /**
     * 12
     * @return Posortowany ciąg figur względem pola powierzchni zaczynając od podanej
     * 0,5 pkt
     */
    @Override
    public List<Figura2D> posortowaneWgPolaZaczynajacOd(int nr) {
        return figury.stream()
                .sorted(Comparator.comparing(f -> f.polePowierzchni()))  // (Figura2D::polePowierzchni) ponownie zastąpione lambdą
                .skip(nr - 1)
                .collect(Collectors.toList());
    }
}
