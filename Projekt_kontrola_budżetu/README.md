# Konspekt projektu „Kontrola budżetu”

Projekt zakłada stworzenie aplikacji do zarządzania budżetem domowym, umożliwiającej śledzenie wydatków i podsumowywanie dostępnych środków na koniec każdego miesiąca.

## Klasy

### Klasa `Wydatek`

Podstawowa klasa reprezentująca wydatek, z atrybutami:
- `kwota` – ile zapłaciliśmy,
- `data` – kiedy zapłaciliśmy,
- `użytkownik` – kto zapłacił,
- `opis` – za co zapłaciliśmy.

### Klasy dziedziczące po `Wydatek`:

- `WydatekStały`
- `WydatekZmienny`
- `WydatekNieprzewidziany`
- `WydatekCodzienny`
- `WydatekNiekonieczny`
- `WydatekDoZwrotu` – dziedziczy po `WydatekNiekonieczny`
- `WydatekInwestycyjny`

### Klasa `Wpływy`

Klasa reprezentująca wpływy, z atrybutami:
- `kwota`
- `data`
- `użytkownik`
- `opis`

### Klasy dziedziczące po `Wpływy`:

- `WpływRegularny`
- `WpływNieregularny`
- `WpływZPoprzedniegoMiesiąca`

### Klasa `BudżetMiesięczny`

Klasa zawierająca:
- `bilans` - ile zostało nam pieniędzy pod koniec miesiąca,
- `listaWydatków`
- `listaWpływów`
- `sumaWydatków`
- `sumaWpływów`

#### Metody klasy `BudżetMiesięczny`:

- `skumulujWydatkiZDanejKategorii`
- `podsumujBudżet`
- `skumulujWydatkiUżytkownika`

### Klasa `Użytkownik`

Klasa reprezentująca użytkownika, z atrybutami:
- `nazwa`
- `oszczędności`

#### Metody klasy `Użytkownik`:

- `dodajWydatek`
- `usuńSwójWydatek`
- `modyfikujWydatek`
- `getWydatki` – wypisuje listę wydatków
- `podsumujWydatkiZaMiesiąc`

### Klasy dziedziczące po `Użytkownik`:

- `UżytkownikZarabiający` z dodatkowymi metodami:
  - `zróbOpłatę`
  - `zasilBudżet`

## Moduły

### I:
- `backend`
- `użytkownicy i wydatki`

### K:
- `GUI` – logowanie użytkownika, wyświetlanie, obsługa danych

### Z:
- `wizualizacja`

## Wstępna dokumentacja do backendu

Wszystkie wpływy i wydatki dziedziczą po głównej klasie `Transakcja`. Można w niej modyfikować kwotę, datę, opis i uwzględnianie w budżecie. Po nich dziedziczą `Wpływy` i `Wydatki`. `Wpływy` dzielą się na `Cykliczne` i `Nieregularne`. `Cykliczne` `Wpływy` i `Wydatki` implementują interfejs `Cykliczny` zawierający metody `realizuj`, `przypomnij` i `setZrealizowany`. Transakcje cykliczne znajdują się na oddzielnej liście w budżecie i dopiero po zrealizowaniu są przenoszone do listy z wszystkimi transakcjami, robi to metoda `realizuj`. `Przypomnij` będzie przy wejściu w aplikację przypominało o nadchodzących opłatach i wpływach. `setZrealizowany` obsługuje zmienną `zrealizowany`. `WydatekDoZwrotu` to rozszerzony `WydatekNieregularny`, który ma dodatkową zmienną `dłużny`, która przechowuje, który użytkownik ma oddać pieniądze temu, który wykonał transakcję. Można potem wysyłać powiadomienia (trzeba coś w tym wymyślić). Oraz metodą zapisującą datę oddania pieniędzy przez `dłużnego`. Po oddaniu pieniędzy transakcja przechodzi na `dłużnego` i jest przypisana do jego konta. `Wydatek` nieregularny ma na razie 4 typy jak z wcześniejszego punktu dokumentacji. Zmienna `uwzglednicWBudzecie` rozdziela transakcje na te prywatne dla danego użytkownika i te, które opisują wydatki w budżecie całej rodziny (głównie cykliczne wydatki oraz te niezbędne).

Transakcje dodaje się do budżetu za pomocą użytkownika. `UzytkownikZarzadzajacy` może dodatkowo zarządzać cyklicznymi transakcjami, otworzyć nowy miesiąc archiwizując stary, i generować analizę całego budżetu.

Budżet ma 2 listy: zwykłych transakcji i tych cyklicznych, reszta.

  
## Autorzy:
Michał Iwicki, Michał Kukla, Michał Zajączkowski
