package pl.edu.pw.mini.jrafalko;

import pl.edu.pw.mini.jrafalko.cargo.BagOfPotatoes;
import pl.edu.pw.mini.jrafalko.cargo.Barrel;
import pl.edu.pw.mini.jrafalko.cargo.Cargo;
import pl.edu.pw.mini.jrafalko.cargo.Chest;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Można to zrobić za pomocą metod, ale trzeba pamiętać,
        // że obiekty stworzone w poprzednich punktach
        // mają być wykorzystane w kolejnych

        //task1();
        //task2();


        //Lub można też zrobić wszystko bezpośrednio w mainie,
        //ale umieśćcie komentarze, który to punkt jest realizowany




        // ZADANIA wykonuję bezpośrednio w main. Rozdzieliłem polecenia funkcją sout i komentarzem "ZADANIE X---...".




        //ZADANIE 1-----------------------------------------------------------------------------------------------------
        System.out.println("\n1. Wypisz listę konstruktorów wraz z parametrami klasy Barrel korzystając z informacji możliwych do uzyskania z obiektu klasy. ");

        Class<?> barrelClass = Barrel.class;
        Constructor<?>[] constructors = barrelClass.getDeclaredConstructors();
        System.out.println("Konstruktory klasy Barrel:");
        for (Constructor<?> constructor : constructors) {
            System.out.println("Konstruktor: " + constructor.getName());
            Parameter[] parameters = constructor.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println("Parametry: " + parameter.getName() + ", Typ: " + parameter.getType().getName());
            }
        }







        //ZADANIE 2-----------------------------------------------------------------------------------------------------
        System.out.println("\n2. Ustal, czy klasa Chest posiada konstruktor o modyfikatorze protected, przyjmujący dwa parametry: boolean i String. Odpowiedź wypisz w konsoli.");

        Class<?> chestClass = Chest.class;
        Constructor<?>[] chestConstructors = chestClass.getDeclaredConstructors();
        boolean hasProtectedConstructor = false;
        for (Constructor<?> constructor : chestConstructors) {
            int modifiers = constructor.getModifiers();
            if (Modifier.isProtected(modifiers) && constructor.getParameterCount() == 2 &&
                    constructor.getParameterTypes()[0] == boolean.class && constructor.getParameterTypes()[1] == String.class) {
                hasProtectedConstructor = true;
                break;
            }
        }
        System.out.println("Klasa Chest posiada konstruktor o modyfikatorze protected, przyjmujący dwa parametry - boolean i String? : " + hasProtectedConstructor);







        //ZADANIE 3-----------------------------------------------------------------------------------------------------
        System.out.println("\n3. Uzyskaj informacje o nazwie pakietu klasy Cargo. Wypisz w konsoli.");

        Class<?> cargoClass = Cargo.class;
        Package cargoPackage = cargoClass.getPackage();
        System.out.println("Pakiet klasy Cargo: " + cargoPackage.getName());









        //ZADANIE 4-----------------------------------------------------------------------------------------------------
        System.out.println("\n4. Wylistuj prywatne metody klasy BagOfPotatoes wraz z parametrami.");

        Class<?> bagOfPotatoesClass = BagOfPotatoes.class;
        Method[] bagOfPotatoesMethods = bagOfPotatoesClass.getDeclaredMethods();
        System.out.println("Prywatne metody klasy BagOfPotatoes:");
        for (Method method : bagOfPotatoesMethods) {
            int modifiers = method.getModifiers();
            if (Modifier.isPrivate(modifiers)) {
                System.out.println("Metoda: " + method.getName());
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    System.out.println("Parametr: " + parameter.getName() + ", Typ: " + parameter.getType().getName());
                }
            }
        }









        //ZADANIE 5-----------------------------------------------------------------------------------------------------
        System.out.println("\n5. Ustal (pobierz) wartość pola sticker znajdującego się w klasie KoloOdCiezarowki i wypisz na konsoli.");

        try {
            Class<?> koloClass = Class.forName("pl.edu.pw.mini.jrafalko.truck.SpareWeel");
            Field stickerField = koloClass.getDeclaredField("sticker");
            stickerField.setAccessible(true);
            Object stickerValue = stickerField.get(null); // null, bo to pole jest statyczne
            System.out.println("Wartość pola sticker w klasie KoloOdCiezarowki: " + stickerValue);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }









        //ZADANIE 6-----------------------------------------------------------------------------------------------------
        System.out.println("\n6. Wypisz nazwę nadklasy klasy Barrel.");

        Class<?> barrelSuperclass = barrelClass.getSuperclass();
        System.out.println("Nadklasa klasy Barell to: " + barrelSuperclass.getName());









        //ZADANIE 7-----------------------------------------------------------------------------------------------------
        System.out.println("\n7. Wylistuj interfejsy implementowane przez klasę Cargo. Sprawdź, czy któryś z nich pochodzi z tego samego pakietu, co ta klasa.");

        Class<?> cargoInterfaces[] = cargoClass.getInterfaces();
        System.out.println("Interfejsy implementowane przez klasę Cargo:");
        for (Class<?> iface : cargoInterfaces) {
            System.out.println("Interfejs: " + iface.getName());
            if (iface.getPackage().equals(cargoPackage)) {
                System.out.println("Ten interfejs pochodzi z tego samego pakietu, co klasa Cargo.");
            }
            else{
                System.out.println("Ten interfejs nie pochodzi z tego samego pakietu, co klasa Cargo");
            }
        }









        //ZADANIE 8-----------------------------------------------------------------------------------------------------
        System.out.println("\n8. Stwórz obiekt klasy KoloOdCiezarowki i sprawdź wartość pola tireSize na tym obiekcie.");

        try {
            Class<?> koloClass = Class.forName("pl.edu.pw.mini.jrafalko.truck.SpareWeel");
            Object koloObject = koloClass.newInstance();
            Field tireSizeField = koloClass.getDeclaredField("tireSize");
            tireSizeField.setAccessible(true);
            Object tireSizeValue = tireSizeField.get(koloObject);
            System.out.println("Wartość pola tireSize na tym obiekcie: " + tireSizeValue);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }









        //ZADANIE 9-----------------------------------------------------------------------------------------------------
        System.out.println("\n9. Utwórz kolekcję beczek, skrzyń i worków z ziemniakami używając każdego z konstruktorów w tych klasach.");

        List<Object> containers = new ArrayList<>();
        try {
            barrelClass = Class.forName("pl.edu.pw.mini.jrafalko.cargo.Barrel");
            chestClass = Class.forName("pl.edu.pw.mini.jrafalko.cargo.Chest");
            bagOfPotatoesClass = Class.forName("pl.edu.pw.mini.jrafalko.cargo.BagOfPotatoes");

            // Utwórz beczkę bez parametru
            Constructor<?> barrelConstructor0 = barrelClass.getDeclaredConstructor();
            Object barrelObject0 = barrelConstructor0.newInstance();
            containers.add(barrelObject0);

            //Utwórz beczkę z jednym parametrem
            Constructor<?> barrelConstructor1 = barrelClass.getDeclaredConstructor(boolean.class);
            barrelConstructor1.setAccessible(true);
            Object barrelObject1 = barrelConstructor1.newInstance(true);
            containers.add(barrelObject1);

            //Utwórz pierwszą skrzynię z jednym parametrem
            Constructor<?> chestConstructor0 = chestClass.getDeclaredConstructor(boolean.class);
            Object chestObject0 = chestConstructor0.newInstance(true);
            containers.add(chestObject0);

            // Utwórz drugą skrzynię z dwoma parametrami
            Constructor<?> chestConstructor = chestClass.getDeclaredConstructor(boolean.class, String.class);
            chestConstructor.setAccessible(true);
            Object chestObject = chestConstructor.newInstance(true, "Label");
            containers.add(chestObject);

            // Utwórz trzecią skrzynię bezparametrowym konstruktorem
            Constructor<?> chestConstructor2 = chestClass.getDeclaredConstructor();
            chestConstructor2.setAccessible(true);
            Object chestObject2 = chestConstructor2.newInstance();
            containers.add(chestObject2);

            // Utwórz worek z ziemniakami
            Constructor<?> bagOfPotatoesConstructor = bagOfPotatoesClass.getDeclaredConstructor();
            Object bagOfPotatoesObject = bagOfPotatoesConstructor.newInstance();
            containers.add(bagOfPotatoesObject);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("Created containers: ");
        for(Object elt : containers) {
            System.out.println(elt);  // brak metod toString, zatem nie zawsze wyświetla się ładnie
        }







        // kolejne zadania są w jednym try-catch




        //ZADANIE 10-----------------------------------------------------------------------------------------------------
        System.out.println("\n10. Utwórz obiekt klasy Truck.");

        Object truckObject = null;
        try {
            Class<?> truckClass = Class.forName("pl.edu.pw.mini.jrafalko.truck.Truck");
            Constructor<?> truckConstructor = truckClass.getDeclaredConstructor();
            truckObject = truckConstructor.newInstance();
            System.out.println("" + truckObject);









            //ZADANIE 11------------------------------------------------------------------------------------------------
            System.out.println("\n11. Uzyskaj dostęp do pola loadingBody w utworzonym wcześniej obiekcie i wywołaj na nim metodę addCargo, dodając obiekty utworzone w punkcie 9-tym.");

            Class<?> loadingBodyClass = Class.forName("pl.edu.pw.mini.jrafalko.truck.Truck$LoadingBody");
            Field loadingBodyField = truckObject.getClass().getDeclaredField("loadingBody");
            loadingBodyField.setAccessible(true);
            Object loadingBodyObject = loadingBodyField.get(truckObject);
            System.out.println("" + loadingBodyObject);

            Method addCargoMethod = loadingBodyClass.getDeclaredMethod("addTowar", Cargo.class);
            addCargoMethod.setAccessible(true);
            for (Object container : containers) {
                addCargoMethod.invoke(loadingBodyObject, container);
                System.out.println("Dodaję obiekt");
            }
            System.out.println("Obiekty zostały dodane");










            //ZADANIE 12------------------------------------------------------------------------------------------------
            System.out.println("\n12. Przypisz polom (instancji klasy Truck) spareWeel i fuelTank instancje stworzonych obiektów klas SpareWeel i FuelTank(napełniony). Informację o typach zaczerpnij z klas tych pól.");

            Class<?> spareWeelClass = Class.forName("pl.edu.pw.mini.jrafalko.truck.SpareWeel");
            Class<?> fuelTankClass = Class.forName("pl.edu.pw.mini.jrafalko.truck.Truck$FuelTank");

            // znajduję konstruktor z parametrem boolean w Truck$FuelTank
            Constructor<?> fuelTankConstructor = fuelTankClass.getDeclaredConstructor(truckClass, boolean.class);

            Object truckInstance = truckClass.newInstance();

            Object fuelTankObject = fuelTankConstructor.newInstance(truckInstance,true);

            Field spareWeelField = truckObject.getClass().getDeclaredField("spareWeel");
            spareWeelField.setAccessible(true);
            spareWeelField.set(truckObject, spareWeelClass.newInstance());

            Field fuelTankField = truckObject.getClass().getDeclaredField("fuelTank");
            fuelTankField.setAccessible(true);
            fuelTankField.set(truckObject, fuelTankObject);

            Field[] truckFields = truckClass.getDeclaredFields();

            for (Field field : truckFields) {
                field.setAccessible(true);
                System.out.println(field.getName() + ": " + field.get(truckObject));
            } // nie ma funkcji toString, stąd wypisuje domyślną reprezentację tekstową

            System.out.println("Obiekt klasy truck: " + truckObject);










            //ZADANIE 13------------------------------------------------------------------------------------------------
            System.out.println("\n13. Uzyskaj dostęp do pola: driversCabin i sprawdź czy elementy tego pola mają wartość null. Jeśli tak, to wstaw tam utworzone obiekty odpowiedniego typu. Informację o typach zaczerpnij z klas tych pól.");

            Class<?> driversCabinClass = Class.forName("pl.edu.pw.mini.jrafalko.truck.Truck$DriversCabin");
            Field driversCabinField = truckObject.getClass().getDeclaredField("driversCabin");
            driversCabinField.setAccessible(true);
            Object driversCabinObject = driversCabinField.get(truckObject);

            Field driverField = driversCabinClass.getDeclaredField("driver");
            driverField.setAccessible(true);
            Object driverObject = driverField.get(driversCabinObject);

            if(driverObject == null) {
                Constructor<?> driverConstructor = driversCabinClass.getDeclaredClasses()[0].getDeclaredConstructor(driversCabinClass);
                driverConstructor.setAccessible(true);
                Object newDriverObject = driverConstructor.newInstance(driversCabinObject);
                driverField.set(driversCabinObject, newDriverObject);
                System.out.println("driver is set");
            }
            Field ignitionSwitchField = driversCabinClass.getDeclaredField("ignitionSwitch");
            ignitionSwitchField.setAccessible(true);
            Object ignitionSwitchObject = ignitionSwitchField.get(driversCabinObject);

            if (ignitionSwitchObject == null) {
                Constructor<?> ignitionSwitchConstructor = driversCabinClass.getDeclaredClasses()[1].getDeclaredConstructor(driversCabinClass, boolean.class);
                ignitionSwitchConstructor.setAccessible(true);
                Object newIgnitionSwitchObject = ignitionSwitchConstructor.newInstance(driversCabinObject, true);
                ignitionSwitchField.set(driversCabinObject, newIgnitionSwitchObject);
                System.out.println("ignitionswitchobject is set");
            }









            //ZADANIE 14------------------------------------------------------------------------------------------------
            System.out.println("\n14. Wywołaj metodę drive zdefiniowaną dla kierowcy.");

            System.out.println();
            Method driveMethod = driverObject.getClass().getDeclaredMethod("drive");
            driveMethod.setAccessible(true);
            driveMethod.invoke(driverObject);

        }
        catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }









        //ZADANIE 15----------------------------------------------------------------------------------------------------
        System.out.println("\n15. Stwórz adnotację TireCompany działającą podczas wykonania programu zawierającą dwie informacje będące ciągami znaków: nazwa producenta oraz rozmiar opony. 'Ozdób' nią klasę spareWeel. Wstaw dowolne wartości.");

        try {
            Class<?> spareWeelClass = Class.forName("pl.edu.pw.mini.jrafalko.truck.SpareWeel");
            Field[] fields = spareWeelClass.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(TireCompany.class)) {
                    TireCompany tireCompanyAnnotation = field.getAnnotation(TireCompany.class);
                    field.setAccessible(true);
                    System.out.println("Manufacturer: " + tireCompanyAnnotation.manufacturer());
                    System.out.println("Size: " + tireCompanyAnnotation.size());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    // Klasa adnotacji
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface TireCompany {
        String manufacturer();
        String size();
    }


}
