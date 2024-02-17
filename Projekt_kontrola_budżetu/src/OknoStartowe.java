import com.univocity.parsers.csv.Csv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.format.TextStyle;
import java.util.Locale;

public class OknoStartowe {


    private static BudzetMiesieczny budzet;
    private static List<Uzytkownik> listaUzytkownikow;
    private static JPanel panelPrzyciskow;
    private static JPanel panelZarzadzania;
    private static JPanel panelWydatki;
    private static JPanel panelTransakcji;
    private static List<Transakcja> listaTransakcji = new ArrayList<Transakcja>();
    private static Uzytkownik uzytkownik;
    private static List<Transakcja> transakcjeUzytkownika = new ArrayList<Transakcja>();

    public static void main(String[] args) {


        List<Uzytkownik> listaUzytkownikow = new ArrayList<Uzytkownik>();
        listaUzytkownikow.add(new Uzytkownik("Adam", "adam", new BigDecimal(12480.42)));
        listaUzytkownikow.add(new Uzytkownik("Julia","julia", new BigDecimal(15410.35)));
        listaUzytkownikow.add(new Uzytkownik("Zofia", "zofia",new BigDecimal(410.74)));
        listaUzytkownikow.add(new Uzytkownik("Maciej", "maciej", new BigDecimal(310.11)));



        transakcjeUzytkownika = CsvManager.readTransactions("data2.csv");
        listaTransakcji = CsvManager.readTransactions("data2.csv");


        budzet = new BudzetMiesieczny(listaTransakcji, new BigDecimal(0));

        // Utwórz ramkę (okno)
        JFrame frame = new JFrame("Witaj w aplikacji");

        // Ustaw operację zamknięcia okna
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ustaw rozmiar okna
        frame.setSize(1100, 800);

        // Wycentruj okno na ekranie
        frame.setLocationRelativeTo(null);

        // Utwórz panel główny
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        // Dodaj etykietę z tekstem "Kontrola budżetu"
        JLabel budzetLabel = new JLabel("Kontrola budżetu");
        budzetLabel.setHorizontalAlignment(JLabel.CENTER);
        budzetLabel.setVerticalAlignment(JLabel.CENTER);
        budzetLabel.setFont(new Font("Arial", Font.BOLD, 40)); // Ustawienia czcionki


        // Ustawienie pozycji etykiety kontrola budżetu na środku
        mainPanel.add(budzetLabel, BorderLayout.CENTER);

        // Ustaw widoczność etykiet na true
        budzetLabel.setVisible(true);

        // Ustaw timer do ukrycia etykiety po 2 sekundach
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                budzetLabel.setVisible(false);
                pokazOknoDialogowe(frame, listaUzytkownikow);

            }
        });

        // Uruchom timer
        timer.setRepeats(false);
        timer.start();

        // Wyświetl okno
        frame.setVisible(true);
    }

    private static void pokazOknoDialogowe(JFrame parentFrame, List<Uzytkownik> listaUzytkownikow) {
        boolean zalogowanoPoprawnie = false;
        while (!zalogowanoPoprawnie) {
            // Utwórz panel z polami tekstowymi dla loginu i hasła
            JPanel panel = new JPanel(new GridLayout(2, 2));
            JTextField loginField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            panel.add(new JLabel("Login:"));
            panel.add(loginField);
            panel.add(new JLabel("Hasło:"));
            panel.add(passwordField);

            // Wyświetl okno dialogowe z panelem
            int result = JOptionPane.showConfirmDialog(
                    parentFrame, panel, "Podaj login i hasło",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            // Sprawdź, czy użytkownik nacisnął OK
            if (result == JOptionPane.OK_OPTION) {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());

                for(Uzytkownik elt: listaUzytkownikow){
                    if (elt.getNazwa().equals(login) && elt.getHaslo().equals(password)) {
                        uzytkownik = elt;
                        zalogowanoPoprawnie = true;
                        transakcjeUzytkownika = transakcjeUzytkownika.stream().filter(transakcja -> transakcja.getUzytkownik().equals(uzytkownik.getNazwa())).collect(Collectors.toList());


                        // Zmiana tytułu ramki po poprawnym zalogowaniu
                        parentFrame.setTitle("Kontrola budżetu. Zalogowano: " + login);

                        // Po poprawnym zalogowaniu
                        dodajPrzyciski(parentFrame);
                    }
                }
                if(!zalogowanoPoprawnie) {
                    JOptionPane.showMessageDialog(parentFrame, "Błędne dane", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Użytkownik nacisnął "Cancel" lub zamknął okno
                System.exit(0); // Zamknij program
            }
        }
    }

    // Po poprawnym zalogowaniu
    private static void dodajPrzyciski(JFrame parentFrame) {
        // Utwórz panel na przyciski
        panelPrzyciskow = new JPanel(new GridLayout(2, 3, 15, 15)); // 2 wiersze, 3 kolumny, z odstępem

        BigDecimal kwota1 = transakcjeUzytkownika.stream()
                .filter(transakcja -> transakcja.isWydatek())
                .map(Transakcja::getKwota) // mapowanie na wartość transakcji
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal kwota2 = transakcjeUzytkownika.stream()
                .filter(transakcja -> !transakcja.isWydatek())
                .map(Transakcja::getKwota) // mapowanie na wartość transakcji
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal bilans = kwota2.subtract(kwota1);

        String[] nazwyPrzyciskow = {
                "Wydatki miesięczne: " + kwota1 + " zł",
                "Przychody miesięczne: " + kwota2 + " zł",
                "Bilans ogólny: " + bilans + " zł",
                "Dodaj wydatek", "Dodaj przychód", "Analiza całego budżetu"
        };

        // Dodaj przyciski do panelu
        for (String nazwa : nazwyPrzyciskow) {
            JButton przycisk = new JButton(nazwa);
            przycisk.setFont(new Font("Arial", Font.BOLD, 16)); // Ustaw większą czcionkę

            if (nazwa.equals("Analiza całego budżetu")) {
                przycisk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pokazPanelAnalizy(parentFrame, "Analiza");
                    }
                });
            } else if (nazwa.equals("Dodaj wydatek")) {
                przycisk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dodajWydatek(parentFrame, "Wydatek");
                    }
                });
            } else if (nazwa.equals("Dodaj przychód")) {
                przycisk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dodajPrzychod(parentFrame);
                    }
                });
            } else if (nazwa.startsWith("Wydatki miesięczne: ")) {
                przycisk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pokazPanelAnalizy(parentFrame, "Wydatki");
                    }
                });
            } else if (nazwa.startsWith("Przychody miesięczne: ")) {
                przycisk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pokazPanelAnalizy(parentFrame, "Przychody");
                    }
                });
            } else if (nazwa.startsWith("Bilans ogólny: ")){
                przycisk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { pokazPanelAnalizy(parentFrame, "Bilans");}
                });
            }

            panelPrzyciskow.add(przycisk);
        }

        // Utwórz panel na sekcję lewą (dla tekstu "Zalogowany: abcdef")
        JPanel panelLewy = new JPanel(new BorderLayout());

        // Utwórz główny panel, który będzie zawierał panele lewy i centralny
        JPanel glownyPanel = new JPanel(new BorderLayout());
        glownyPanel.add(panelLewy, BorderLayout.WEST);
        glownyPanel.add(panelPrzyciskow, BorderLayout.CENTER);

        // Dodaj główny panel do ramki
        parentFrame.setContentPane(glownyPanel);

        // Odśwież układ
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    // Pokaż panel zarządzania
    private static void pokazPanelZarzadzania(JFrame parentFrame) {
        // Utwórz panel na przyciski zarządzania
        panelZarzadzania = new JPanel(new GridLayout(1, 3, 15, 15)); // 1 wiersz, 3 kolumny, z odstępem

        String[] przyciskiZarzadzania = {
                "Zamknij miesiąc", "Analiza całego budżetu", "Nadzór dzieci", "Transakcje cykliczne"
        };

        // Dodaj przyciski zarządzania do panelu
        for (String nazwa : przyciskiZarzadzania) {
            JButton przycisk = new JButton(nazwa);
            przycisk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obsługa akcji dla przycisków zarządzania
                    if (nazwa.equals("Zamknij miesiąc")) {
                        // Dodaj kod obsługi dla przycisku "Zamknij miesiąc"
                        JOptionPane.showMessageDialog(parentFrame, "Zamknij miesiąc - Obsługa w trakcie");
                    } else if (nazwa.equals("Analiza całego budżetu")) {
                        czyszczenieOkna(parentFrame, panelZarzadzania);
                        pokazPanelAnalizy(parentFrame, "Analiza");
                    } else if (nazwa.equals("Nadzór dzieci")) {
                        // Dodaj kod obsługi dla przycisku "Nadzór dzieci"
                        JOptionPane.showMessageDialog(parentFrame, "Nadzór dzieci - Obsługa w trakcie");
                    } else if (nazwa.equals("Transakcje cykliczne")) {
                        // Dodaj kod obsługi dla przycisku "Transakcje cykliczne"
                        czyszczenieOkna(parentFrame, panelZarzadzania);
                        pokazPanelTransakcji(parentFrame);

                    }
                }
            });
            panelZarzadzania.add(przycisk);
        }

        // Dodaj panel zarządzania do ramki
        parentFrame.setContentPane(panelZarzadzania);

        // Dodaj przycisk "Powrót" w prawym górnym rogu
        JButton powrotButton = new JButton("Powrót");
        powrotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obsługa akcji dla przycisku "Powrót"
                czyszczenieOkna(parentFrame, panelZarzadzania);
                powrotDoPaneluPrzyciskow(parentFrame);
            }
        });
        JPanel panelPowrot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelPowrot.add(powrotButton);
        parentFrame.add(panelPowrot, BorderLayout.NORTH);

        // Odśwież układ
        parentFrame.revalidate();
        parentFrame.repaint();
    }


    private static void czyszczenieOkna(JFrame parentFrame, JPanel panel) {

        // Usuń panel
        parentFrame.getContentPane().remove(panel);

        // Usuń przycisk "Powrót" i inne
        parentFrame.getContentPane().removeAll(); // Usunięcie wszystkich komponentów


    }

    // Powrót do panelu przycisków

    private static void powrotDoPaneluPrzyciskow(JFrame parentFrame) {

        // Dodaj ponownie panel przycisków
        dodajPrzyciski(parentFrame);

        // Odśwież układ
        parentFrame.revalidate();
        parentFrame.repaint();
    }


    private static void powrotDoPaneluZarzadzania(JFrame parentFrame) {

        pokazPanelZarzadzania(parentFrame);


        // Odśwież układ
        parentFrame.revalidate();
        parentFrame.repaint();
    }


    // Dodaj wydatek
    private static void dodajWydatek(JFrame parentFrame, String string) {
        JPanel panel = new JPanel(new GridLayout(4, 2)); // Increased rows to accommodate the checkbox
        JTextField wartoscField = new JTextField();
        JTextField tytulField = new JTextField();
        JComboBox<String> rodzajComboBox = new JComboBox<>(new String[]{
                "Wydatek Inwestycyjny", "Wydatek Nieprzewidziany", "Wydatek Niekonieczny", "Wydatek Codzienny"
        });



        JLabel cykliczny = new JLabel("Cykliczny");

        panel.add(new JLabel("Wartość wydatku w zł:"));
        panel.add(wartoscField);
        panel.add(new JLabel("Tytuł wydatku:"));
        panel.add(tytulField);
        panel.add(new JLabel("Rodzaj wydatku:"));

        if (!string.equals("Analiza")) {
            JCheckBox wydatekDoZwrotuCheckBox = new JCheckBox("");
            wydatekDoZwrotuCheckBox.setSelected(false); // niezaznaczony
            panel.add(rodzajComboBox);
            panel.add(new JLabel("Czy WydatekDoZwrotu?")); // Label for the checkbox
            panel.add(wydatekDoZwrotuCheckBox);
        } else {
            panel.add(cykliczny);
        }



        int result = JOptionPane.showConfirmDialog(
                parentFrame, panel, "Dodaj wydatek",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // Pobierz wartości z pól
            String wartoscText = wartoscField.getText();
            String tytul = tytulField.getText();
            String rodzaj = (String) rodzajComboBox.getSelectedItem();

            Typ typ;

            if (rodzaj.equals("Wydatek Inwestycyjny")) {
                typ = Typ.Inwestycyjny;
            } else if (rodzaj.equals("Wydatek Nieprzewidziany")) {
                typ = Typ.Nieprzewidziany;
            } else if (rodzaj.equals("Wydatek Niekonieczny")) {
                typ = Typ.Niekonieczny;
            } else {
                typ = Typ.Codzienny;
            }

            // Sprawdź, czy wartość jest liczbą zmiennoprzecinkową
            try {
                BigDecimal wartosc = new BigDecimal(wartoscText);
                wartosc = wartosc.setScale(2, RoundingMode.HALF_UP);

                // Sprawdź, czy tytuł wydatku został wpisany
                if (tytul.isEmpty()) {
                    throw new IllegalArgumentException("Nie wpisano tytułu wydatku");
                }


                listaTransakcji.add(new Transakcja(wartosc, LocalDate.now(), uzytkownik.getNazwa(), tytul, true, typ));
                budzet.dodajTransakcje(new Transakcja(wartosc, LocalDate.now(), uzytkownik.getNazwa(), tytul, true, typ));

                transakcjeUzytkownika.add(new Transakcja(wartosc, LocalDate.now(), uzytkownik.getNazwa(), tytul, true, typ));

                System.out.println("Dodano wydatek: Wartość = " + wartosc + ", Tytuł = " + tytul + ", Rodzaj = " + rodzaj);
                budzet.wyliczWydatki();

                CsvManager.appendCsvFile("data2.csv", new Transakcja(wartosc, LocalDate.now(), uzytkownik.nazwa, tytul, true, typ));

                parentFrame.revalidate();
                parentFrame.repaint();



            } catch (NumberFormatException e) {
                // Obsługa sytuacji, gdy wartość nie jest liczbą zmiennoprzecinkową
                JOptionPane.showMessageDialog(parentFrame, "Nieprawidłowe dane", "Błąd", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                // Obsługa sytuacji, gdy nie wpisano tytułu wydatku
                JOptionPane.showMessageDialog(parentFrame, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Dodaj przychód
    private static void dodajPrzychod(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField wartoscField = new JTextField();
        JTextField zrodloField = new JTextField();
        JComboBox<String> rodzajComboBox = new JComboBox<>(new String[]{
                "Wpływ Cykliczny", "Wpływ Nieregularny"
        });

        panel.add(new JLabel("Ile zł przychodu:"));
        panel.add(wartoscField);
        panel.add(new JLabel("Skąd przychód:"));
        panel.add(zrodloField);
        panel.add(new JLabel("Rodzaj przychodu:"));
        panel.add(rodzajComboBox);

        int result = JOptionPane.showConfirmDialog(
                parentFrame, panel, "Dodaj przychód",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // Pobierz wartości z pól
            String wartoscText = wartoscField.getText();
            String zrodlo = zrodloField.getText();
            String rodzaj = (String) rodzajComboBox.getSelectedItem();

            Typ typ;

            if (rodzaj.equals("Wydatek Inwestycyjny")) {
                typ = Typ.Inwestycyjny;
            } else if (rodzaj.equals("Wydatek Nieprzewidziany")) {
                typ = Typ.Nieprzewidziany;
            } else if (rodzaj.equals("Wydatek Niekonieczny")) {
                typ = Typ.Niekonieczny;
            } else {
                typ = Typ.Codzienny;
            }

            // Sprawdź, czy wartość jest liczbą zmiennoprzecinkową
            try {
                BigDecimal wartosc = new BigDecimal(wartoscText);
                wartosc = wartosc.setScale(2, RoundingMode.HALF_UP);

                // Sprawdź, czy źródło przychodu zostało wpisane
                if (zrodlo.isEmpty()) {
                    throw new IllegalArgumentException("Nie wpisano źródła przychodu");
                }

                listaTransakcji.add(new Transakcja(wartosc, LocalDate.now(), uzytkownik.getNazwa(), zrodlo, false, typ));
                transakcjeUzytkownika.add(new Transakcja(wartosc, LocalDate.now(), uzytkownik.getNazwa(), zrodlo, false, typ));

                System.out.println("Dodano przychód: Wartość = " + wartosc + ", Opis = " + zrodlo + ", Rodzaj = " + rodzaj);
                budzet.dodajTransakcje(new Transakcja(wartosc, LocalDate.now(), uzytkownik.getNazwa(), zrodlo, false, typ));

                budzet.wyliczWplywy();

                CsvManager.appendCsvFile("data2.csv", new Transakcja(wartosc, LocalDate.now(), uzytkownik.nazwa, zrodlo, false, typ));


                parentFrame.revalidate();
                parentFrame.repaint();


            } catch (NumberFormatException e) {
                // Obsługa sytuacji, gdy wartość nie jest liczbą zmiennoprzecinkową
                JOptionPane.showMessageDialog(parentFrame, "Nieprawidłowe dane", "Błąd", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                // Obsługa sytuacji, gdy nie wpisano źródła przychodu
                JOptionPane.showMessageDialog(parentFrame, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Pokaż panel Analizy
    private static void pokazPanelAnalizy(JFrame parentFrame, String string) {

        // Utwórz główny panel analizy
        panelWydatki = new JPanel(new BorderLayout());

        // Dodaj przycisk "Filtruj"
        JButton filtrujButton = new JButton("Filtruj");
        filtrujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obsługa akcji dla przycisku "Filtruj"
                if (string.equals("Analiza")) {
                    pokazPanelFiltrow(parentFrame, "Analiza");
                } else {
                    pokazPanelFiltrow(parentFrame, "");
                }
            }
        });

        // Ustaw szerokość przycisku "Filtruj" na czterokrotność standardowej szerokości
        Dimension btnSize = filtrujButton.getPreferredSize();
        btnSize.width *= 4;
        filtrujButton.setPreferredSize(btnSize);

        // Dodaj label "Historia"
        JLabel historiaLabel = new JLabel("Historia");

        // Dodaj puste okno historii z layout managerem, aby zajmowało całą długość
        JTextArea historiaTextArea = new JTextArea();

        List<Transakcja> przefiltrowanaLista = new ArrayList<>();
        if (string.equals("Wydatki")) {

            przefiltrowanaLista = transakcjeUzytkownika.stream()
                    .filter(transakcja -> transakcja.isWydatek()).
                    toList();

            for (Transakcja elt : przefiltrowanaLista) {
                historiaTextArea.append(elt.toString() + "\n");
            }
        }

        if (string.equals("Przychody")) {

                przefiltrowanaLista = transakcjeUzytkownika.stream()
                        .filter(transakcja -> !transakcja.isWydatek()).
                        toList();

                for (Transakcja elt : przefiltrowanaLista) {
                    historiaTextArea.append(elt.toString() + "\n");
                }
            }

        if (string.equals("Bilans")) {

            for (Transakcja elt : transakcjeUzytkownika) {
                historiaTextArea.append(elt.toString() + "\n");
            }
        }

        if (string.equals("Analiza")) {

            for (Transakcja elt : listaTransakcji) {
                historiaTextArea.append(elt.toString() + "\n");
            }

        }




            JScrollPane historiaScrollPane = new JScrollPane(historiaTextArea);
            historiaScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5)); // Dodaj margines prawy
            historiaScrollPane.setPreferredSize(new Dimension(0, 0)); // Ustaw szerokość na zero, aby zajęło całą dostępną długość
            // Utwórz panel po lewej stronie z przyciskiem "Filtruj" i historią
            JPanel panelLewy = new JPanel();
            panelLewy.setLayout(new BoxLayout(panelLewy, BoxLayout.Y_AXIS));
            panelLewy.add(filtrujButton);
            panelLewy.add(historiaLabel);
            panelLewy.add(historiaScrollPane);
            panelLewy.add(Box.createVerticalStrut(10)); // Odstęp między elementami

            // Dodaj panel po lewej stronie do głównego panelu analizy
            panelWydatki.add(panelLewy, BorderLayout.WEST);

            // Dodaj label "Wizualizacja"
        JLabel wizualizacjaLabel = new JLabel("Wizualizacja");

        JScrollPane wizualizacjaScrollPane;

        JTextArea wizualizacjaTextArea = new JTextArea();
        String curMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("pl"));
            // Utwórz panel po prawej stronie z label "Wizualizacja" i oknem wizualizacji

        String nazwa = uzytkownik.getNazwa();
        if (string.equals("Wydatki")) {
            MonthlyExpenseChart expenseChart = new MonthlyExpenseChart(nazwa, curMonth);
            wizualizacjaScrollPane = new JScrollPane(expenseChart);
        } else if (string.equals("Bilans")) {
            BudgetChartSwing budgetChartSwing = new BudgetChartSwing(nazwa, curMonth);
            wizualizacjaScrollPane = new JScrollPane(budgetChartSwing);
        } else if (string.equals("Analiza")) {

            BudgetChartSwing budgetChartSwing = new BudgetChartSwing("all", curMonth);
            wizualizacjaScrollPane = new JScrollPane(budgetChartSwing);

        }else {
            MonthlyIncomeChart incomeChart = new MonthlyIncomeChart(nazwa, curMonth);
            wizualizacjaScrollPane = new JScrollPane(incomeChart);
        }

        wizualizacjaScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); // Dodaj margines lewy

        JPanel panelPrawy = new JPanel(new BorderLayout());

        panelPrawy.add(wizualizacjaLabel, BorderLayout.NORTH);

        panelPrawy.add(wizualizacjaScrollPane, BorderLayout.CENTER);
        panelPrawy.add(wizualizacjaLabel, BorderLayout.NORTH);
        panelPrawy.add(wizualizacjaScrollPane, BorderLayout.CENTER);


        // Dodaj panel po prawej stronie do głównego panelu analizy
            panelWydatki.add(panelPrawy, BorderLayout.CENTER);

            // Dodaj przycisk "Powrót" w prawym górnym rogu
            JButton powrotButton = new JButton("Powrót");
            powrotButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obsługa akcji dla przycisku "Powrót"

                        czyszczenieOkna(parentFrame, panelWydatki);
                        powrotDoPaneluPrzyciskow(parentFrame);

                }
            });

            JPanel panelPowrot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelPowrot.add(powrotButton);
            panelWydatki.add(panelPowrot, BorderLayout.NORTH);

            // Odśwież układ
            parentFrame.setContentPane(panelWydatki);
            parentFrame.revalidate();
            parentFrame.repaint();
        }



        // Funkcja do pokazywania panelu z filtrami
        private static void pokazPanelFiltrow(JFrame parentFrame, String string){
            // Utwórz małe okno (dialog) z trzema przyciskami: "po dacie", "po rodzaju wydatku/przychodu", "po kwocie"
            JDialog filtrDialog = new JDialog(parentFrame, "Filtrowanie", true);

            filtrDialog.setLayout(new GridLayout(3, 1));
            filtrDialog.setSize(300, 150);


            // Dodaj przyciski do filtrDialog
            JButton poDacieButton = new JButton("Po dacie");
            JButton poRodzajuButton = new JButton("Po rodzaju wydatku/przychodu");
            JButton poKwocieButton = new JButton("Po kwocie");


            // Dodaj akcje do przycisków
            poDacieButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Tutaj możesz dodać kod do obsługi filtrowania po dacie
                    JOptionPane.showMessageDialog(filtrDialog, "Filtrowanie po dacie - Obsługa w trakcie");
                }
            });

            poRodzajuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Tutaj możesz dodać kod do obsługi filtrowania po rodzaju wydatku/przychodu
                    JOptionPane.showMessageDialog(filtrDialog, "Filtrowanie po rodzaju - Obsługa w trakcie");
                }
            });

            poKwocieButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Tutaj możesz dodać kod do obsługi filtrowania po kwocie
                    JOptionPane.showMessageDialog(filtrDialog, "Filtrowanie po kwocie - Obsługa w trakcie");
                }
            });

            // Dodaj przyciski do filtrDialog
            filtrDialog.add(poDacieButton);
            filtrDialog.add(poRodzajuButton);
            filtrDialog.add(poKwocieButton);

            if (string.equals("Analiza")) {
                filtrDialog.setLayout(new GridLayout(4, 1));
                JButton poOsobieButton = new JButton("Po osobie");

                poOsobieButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Tutaj możesz dodać kod do obsługi filtrowania po kwocie
                        JOptionPane.showMessageDialog(filtrDialog, "Filtrowanie po osobie - Obsługa w trakcie");
                    }
                });
                filtrDialog.add(poOsobieButton);
                filtrDialog.setSize(300, 200);
            }

            // Ustaw rozmiar, położenie i pokaż dialog
            filtrDialog.setLocationRelativeTo(parentFrame);
            filtrDialog.setVisible(true);
        }


    // Nowa funkcja do pokazywania panelu transakcji
    private static void pokazPanelTransakcji(JFrame parentFrame) {
        // Utwórz panel transakcji
        panelTransakcji = new JPanel(new BorderLayout());

        // Dodaj przycisk "Powrót" w prawym górnym rogu
        JButton powrotButton = new JButton("Powrót");
        powrotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obsługa akcji dla przycisku "Powrót"
                czyszczenieOkna(parentFrame, panelTransakcji);
                powrotDoPaneluZarzadzania(parentFrame);
            }
        });

        JPanel panelPowrot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelPowrot.add(powrotButton);
        panelTransakcji.add(panelPowrot, BorderLayout.NORTH);

        // Utwórz panel na przyciski "Dodaj wydatek cykliczny" i "Opłać wydatek cykliczny"
        JPanel panelPrzyciskow = new JPanel(new GridLayout(2, 1, 0, 10));

        JButton dodajWydatekButton = new JButton("Dodaj wydatek cykliczny");
        dodajWydatekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obsługa akcji dla przycisku "Dodaj wydatek cykliczny"
                dodajWydatek(parentFrame, "Analiza");
            }
        });

        JButton oplacWydatekButton = new JButton("Opłać wydatek cykliczny");
        oplacWydatekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obsługa akcji dla przycisku "Opłać wydatek cykliczny"
                JOptionPane.showMessageDialog(parentFrame, "Opłać wydatek cykliczny - Obsługa w trakcie");
            }
        });

        panelPrzyciskow.add(dodajWydatekButton);
        panelPrzyciskow.add(oplacWydatekButton);

        // Dodaj panel przycisków do panelu transakcji
        panelTransakcji.add(panelPrzyciskow, BorderLayout.CENTER);

        // Odśwież układ
        parentFrame.setContentPane(panelTransakcji);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}


