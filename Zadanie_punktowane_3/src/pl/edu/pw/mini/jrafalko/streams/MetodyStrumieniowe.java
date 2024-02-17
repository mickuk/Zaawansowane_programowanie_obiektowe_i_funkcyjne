package pl.edu.pw.mini.jrafalko.streams;

import pl.edu.pw.mini.jrafalko.figures.Figura2D;

import java.util.List;
import java.util.Map;

public interface MetodyStrumieniowe {

    Figura2D getNajwiekszaFigure(); //Względem właściwości(pole klasy) wysokość
    Figura2D getFigureONajmniejszymPolu(); //Pole powierzchni
    Figura2D getNajwyzszaFigure3D();
    Figura2D getNajmniejszySzescian();
    List<Figura2D> getPosortowaneWzgledemPowiezchni();
    Figura2D getFigureZPosortowanychMalejacoWgObwodu(int nr);
    List<Figura2D> getPierwszePosortowaneRosnacoWgPowierzchni(int ilosc);
    List<Figura2D> getSzesciany(int bok);
    Figura2D getNajmniejszeKolo();
    Map<Integer, Figura2D> mapaWgId();
    int getiloscMalych(double pole); //5 małych figur względem pola -- na zajęciach powiedziane, że bez tego komentarza
    List<Figura2D> posortowaneWgPolaZaczynajacOd(int nr);
}
