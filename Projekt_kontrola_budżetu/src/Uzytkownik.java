import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a user in the financial management system.
 */
public class Uzytkownik {

    protected String nazwa;
    protected BigDecimal oszczednosci;
    protected String haslo;

    /**
     * Constructs a new user with the provided details.
     *
     * @param nazwa      The name of the user.
     * @param haslo      The password associated with the user.
     * @param oszczednosci The savings amount of the user.
     */
    public Uzytkownik(String nazwa, String haslo, BigDecimal oszczednosci) {
        this.nazwa = nazwa;
        this.oszczednosci = oszczednosci;
        this.haslo = haslo;
    }

    /**
     * Gets the savings amount of the user.
     *
     * @return The savings amount of the user.
     */
    public BigDecimal getOszczednosci() {
        return oszczednosci;
    }

    /**
     * Sums up the expenses from the provided list of transactions.
     *
     * @param transakcjas The list of transactions.
     * @return The total expenses.
     */
    public BigDecimal sumujWydatki(List<Transakcja> transakcjas) {
        List<Transakcja> wydatki = transakcjas.stream().filter(transakcja -> transakcja.isWydatek).collect(Collectors.toList());
        BigDecimal suma = BigDecimal.ZERO;
        for (Transakcja wydatek : wydatki) {
            suma = suma.add(wydatek.getKwota());
        }
        return suma;
    }

    /**
     * Sums up the incomes from the provided list of transactions.
     *
     * @param transakcjas The list of transactions.
     * @return The total incomes.
     */
    public BigDecimal sumujWplywy(List<Transakcja> transakcjas) {
        List<Transakcja> wplywy = transakcjas.stream().filter(transakcja -> !transakcja.isWydatek).collect(Collectors.toList());
        BigDecimal suma = BigDecimal.ZERO;
        for (Transakcja wplyw : wplywy) {
            suma = suma.add(wplyw.getKwota());
        }
        return suma;
    }

    /**
     * Calculates the balance of the user based on the provided list of transactions.
     *
     * @param transakcjas The list of transactions.
     * @return The user's balance.
     */
    public BigDecimal getBilans(List<Transakcja> transakcjas) {
        return oszczednosci.add(sumujWplywy(transakcjas).subtract(sumujWydatki(transakcjas)));
    }

    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * Gets the password associated with the user.
     *
     * @return The password of the user.
     */
    public String getHaslo() {
        return haslo;
    }

    /**
     * Converts the user details to a CSV-formatted string.
     *
     * @return A CSV-formatted string representing the user details.
     */
    public String toCsvRow() {
        return nazwa + ',' + oszczednosci.toString() + ',' + haslo;
    }
}
