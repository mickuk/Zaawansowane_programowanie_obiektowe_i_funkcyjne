package pl.edu.pw.mini.jrafalko.censor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SetFields {
    enum produkt {LICZBA, PRODUKT}

    produkt value() default produkt.PRODUKT;
}