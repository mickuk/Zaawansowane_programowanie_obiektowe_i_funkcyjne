import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a financial transaction in the system, which can be either an expense or income.
 * Implements the CsvAble interface for CSV serialization.
 */
public class Transakcja implements CsvAble {

    protected BigDecimal kwota;
    protected LocalDate dataTransakcji;
    protected String uzytkownik;
    protected String opis;
    protected boolean isWydatek;
    protected Typ typ;

    /**
     * Constructs a new transaction with the provided details.
     *
     * @param kwota          The amount of the transaction.
     * @param dataTransakcji The date of the transaction.
     * @param uzytkownik     The user associated with the transaction.
     * @param opis           The description or title of the transaction.
     * @param isWydatek      A boolean indicating if the transaction is an expense.
     * @param typ            The type of the transaction.
     */
    public Transakcja(BigDecimal kwota, LocalDate dataTransakcji, String uzytkownik, String opis, boolean isWydatek, Typ typ) {
        this.kwota = kwota;
        this.dataTransakcji = dataTransakcji;
        this.uzytkownik = uzytkownik;
        this.opis = opis;
        this.isWydatek = isWydatek;
        this.typ = typ;
    }

    /**
     * Converts the transaction to a CSV-formatted string.
     *
     * @return A CSV-formatted string representing the transaction.
     */
    public String toCsvRow() {
        return kwota.toString() + ',' + dataTransakcji.toString() + ',' + uzytkownik + ',' + opis + ',' + isWydatek + ',' + typ.toString();
    }

    /**
     * Returns a string representation of the transaction.
     *
     * @return A string representation of the transaction.
     */
    @Override
    public String toString() {
        return kwota + " zł, " +
                "," + dataTransakcji +
                ", uzytkownik='" + uzytkownik + '\'' +
                ", opis='" + opis + '\'' +
                ", isWydatek=" + isWydatek +
                ", typ=" + typ;
    }

    /**
     * Gets the user associated with the transaction.
     *
     * @return The user associated with the transaction.
     */
    public String getUzytkownik() {
        return uzytkownik;
    }

    /**
     * Gets the date of the transaction.
     *
     * @return The date of the transaction.
     */
    public LocalDate getDataTransakcji() {
        return dataTransakcji;
    }

    /**
     * Gets the description or title of the transaction.
     *
     * @return The description or title of the transaction.
     */
    public String getOpis() {
        return opis;
    }

    /**
     * Gets the amount of the transaction.
     *
     * @return The amount of the transaction.
     */
    public BigDecimal getKwota() {
        return kwota;
    }

    /**
     * Checks if the transaction is an expense.
     *
     * @return True if the transaction is an expense, false if it is income.
     */
    public boolean isWydatek() {
        return isWydatek;
    }

    /**
     * Gets the type of the transaction.
     *
     * @return The type of the transaction.
     */
    public Typ getTyp() {
        return typ;
    }
}
