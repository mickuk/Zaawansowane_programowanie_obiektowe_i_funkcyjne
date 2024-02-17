package pl.edu.pw.mini.jrafalko;

import pl.edu.pw.mini.jrafalko.censor.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Cenzura implements Censorable {
    @Override
    public List<Pracownik> cenzuruj(List<Pracownik> list) {

        // najpierw wyrzucam monterów, bo w poprzednich podpunktach ustawiam wiek na 0 --> wtedy każdy monter ma wiek 0, a więc mniej jak 30
        zadanie7(list);

        for (Pracownik pracownik : list) {
            zadanie1(pracownik);
            zadanie2(pracownik);
            zadanie3(pracownik);
            zadanie4(pracownik);
            zadanie5(pracownik);
            zadanie6(pracownik);
        }

        // sprawdzam czy wszystkie adnotacje są na miejscu
        //printAllAnnotations(list);

        return list;
    }

    private void zadanie1_resetStrings(Field field, Pracownik pracownik) {
        if (field.getType().isAssignableFrom(String.class)) {
            field.setAccessible(true);
            try {
                field.set(pracownik, "");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void zadanie1(Pracownik pracownik) {
        if (pracownik.getClass().isAnnotationPresent(ResetStrings.class)) {
            for (Field field : pracownik.getClass().getDeclaredFields()) {
                zadanie1_resetStrings(field, pracownik);
            }
            for (Field field : pracownik.getClass().getSuperclass().getDeclaredFields()) {
                zadanie1_resetStrings(field, pracownik);
            }
        }
    }

    private void zadanie2_replaceStringEnd(Field field, Pracownik pracownik) {
        try {
            field.setAccessible(true);
            String fieldValue = (String) field.get(pracownik);
            field.set(pracownik, fieldValue.substring(0, 3) + "___");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



    private void zadanie2(Pracownik pracownik) {
        for (Field field : pracownik.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(ReplaceStringEnd.class) &&
                    field.getType().isAssignableFrom(String.class))
                zadanie2_replaceStringEnd(field, pracownik);
        }
    }



    private void zadanie3_replaceWithParameter(Field field, Pracownik pracownik) {
        try {
            field.setAccessible(true);
            ReplaceWithParameter annotation = field.getAnnotation(ReplaceWithParameter.class);
            field.set(pracownik, annotation.value());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private void zadanie3(Pracownik pracownik) {
        for (Field field : pracownik.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ReplaceWithParameter.class) &&
                    field.getType().isAssignableFrom(String.class)) {
                zadanie3_replaceWithParameter(field, pracownik);
            }
        }
    }


    private void zadanie4_resetInt(Field field, Pracownik pracownik) {
        try {
            field.setAccessible(true);
            field.set(pracownik, 0);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private void zadanie4(Pracownik pracownik) {
        for (Field field : pracownik.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ResetInt.class) &&
                    field.getType().equals(Integer.TYPE)) {
                zadanie4_resetInt(field, pracownik);
            }
        }

        // robimy tak też dla klasy Pracownik
        for (Field field : pracownik.getClass().getSuperclass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ResetInt.class)) {
                zadanie4_resetInt(field, pracownik);
            }
        }
    }


    private void zadanie5_invokeMethod(Method method, Pracownik pracownik) {
        try {
            method.setAccessible(true);
            InvokeMethod annotation = method.getAnnotation(InvokeMethod.class);
            for(int i = 0; i < annotation.value(); i++) {
                method.invoke(pracownik);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private void zadanie5(Pracownik pracownik) {
        for (Method method : pracownik.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(InvokeMethod.class)) {
                zadanie5_invokeMethod(method, pracownik);
            }
        }
    }


    private void zadanie6_setFields(Pracownik pracownik, Field field, String className, Object o) {

        try {
            if(field.getType().isAssignableFrom(Class.forName(className))) {
                field.setAccessible(true);
                Object casted = Class.forName(className).cast(o);
                field.set(pracownik, casted);
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private void zadanie6(Pracownik pracownik) {
        if(pracownik.getClass().isAnnotationPresent(SetFields.class)){
            SetFields annotation = pracownik.getClass().getAnnotation(SetFields.class);

            // jak jest produkt, to zmieniamy na BOMBKI, a jak nie, to wszystko liczbowe na -1
            String type = (annotation.value() == SetFields.produkt.LICZBA) ? Number.class.getName() : Produkty.class.getName();
            Object toReplace = (annotation.value() == SetFields.produkt.LICZBA) ? -1 : Produkty.BOMBKI;
            for(Field  field : pracownik.getClass().getDeclaredFields())
                zadanie6_setFields(pracownik, field, type, toReplace);
            for(Field  field : pracownik.getClass().getSuperclass().getDeclaredFields())
                zadanie6_setFields(pracownik, field, type, toReplace);
        }
    }

    private void zadanie7(List<Pracownik> list) {
        list.removeIf(pracownik -> {
            if (pracownik.getClass().isAnnotationPresent(RemoveYounger.class)) {
                if (pracownik.wiek < 30) {
                    return true;
                }
            }
            return false;
        });
    }


    private void printAllAnnotations(List<Pracownik> list) {
        System.out.println();
        // dla całej klasy
        for (Pracownik pracownik : list) {
            if (pracownik.getClass().getAnnotations().length > 0 ){
                System.out.println(pracownik.getClass());
                for(Annotation annotation : pracownik.getClass().getAnnotations()){
                    System.out.println(annotation.toString());
                }
            }
            // dla pola
            for(Field field : pracownik.getClass().getDeclaredFields()){
                if (field.getAnnotations().length > 0 ){
                    System.out.println(field.getName());
                    for(Annotation annotation : field.getAnnotations()){
                        System.out.println(annotation.toString());
                    }
                }
            }
            // dla metody
            for(Method method : pracownik.getClass().getDeclaredMethods()){
                if (method.getAnnotations().length > 0 ){
                    System.out.println(method.getName());
                    for(Annotation annotation : method.getAnnotations()){
                        System.out.println(annotation.toString());
                    }
                }
            }
        }
    }
}

