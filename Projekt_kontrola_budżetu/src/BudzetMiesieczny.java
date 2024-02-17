import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a monthly budget with transactions and calculations for income, expenses, and overall balance.
 */
public class BudzetMiesieczny {

    // List to store transactions for the monthly budget
    public List<Transakcja> transakcje;

    // Variables to track the sum of incomes, expenses, and initial balance
    private BigDecimal sumaWplywow;
    private BigDecimal sumaWydatkow;
    private BigDecimal stanPoczatkowy;

    /**
     * Gets the list of transactions.
     * @return List of transactions for the monthly budget.
     */
    public List<Transakcja> getTransakcje() {
        return transakcje;
    }

    /**
     * Constructs a monthly budget with initial transactions and balance.
     * @param transakcje List of transactions for the monthly budget.
     * @param stanPoczatkowy Initial balance for the monthly budget.
     */
    public BudzetMiesieczny(List<Transakcja> transakcje, BigDecimal stanPoczatkowy) {
        this.transakcje = transakcje;
        this.sumaWplywow = new BigDecimal(0);
        this.sumaWydatkow = new BigDecimal(0);
        this.stanPoczatkowy = stanPoczatkowy;
    }

    /**
     * Calculates the sum of incomes from the transactions.
     */
    public void wyliczWplywy() {
        sumaWplywow = new BigDecimal(0);
        for (Transakcja transakcja : transakcje) {
            if (!transakcja.isWydatek) {
                sumaWplywow = sumaWplywow.add(transakcja.getKwota());
            }
        }
    }

    /**
     * Calculates the sum of expenses from the transactions.
     */
    public void wyliczWydatki() {
        sumaWydatkow = new BigDecimal(0);
        for (Transakcja transakcja : transakcje) {
            if (transakcja.isWydatek) {
                sumaWydatkow = sumaWydatkow.add(transakcja.getKwota());
            }
        }
    }

    /**
     * Calculates the overall budget by subtracting expenses from incomes and adding the initial balance.
     * @return Overall budget for the month.
     */
    public BigDecimal wyliczBudzet() {
        wyliczWydatki();
        wyliczWplywy();
        return stanPoczatkowy.add(sumaWplywow).subtract(sumaWydatkow);
    }

    /**
     * Sets the initial balance for the monthly budget.
     * @param stanPoczatkowy Initial balance to be set.
     */
    public void setStanPoczatkowy(BigDecimal stanPoczatkowy) {
        this.stanPoczatkowy = stanPoczatkowy;
    }

    /**
     * Clears the list of transactions for the monthly budget.
     */
    public void wyczyscTransakcje() {
        transakcje = new ArrayList<>();
    }

    /**
     * Adds a transaction to the list of transactions for the monthly budget.
     * @param transakcja Transaction to be added.
     */
    public void dodajTransakcje(Transakcja transakcja) {
        transakcje.add(transakcja);
    }

    /**
     * Gets the total sum of incomes.
     * @return Sum of incomes for the month.
     */
    public BigDecimal getSumaWplywow() {
        return sumaWplywow;
    }

    /**
     * Gets the total sum of expenses.
     * @return Sum of expenses for the month.
     */
    public BigDecimal getSumaWydatkow() {
        return sumaWydatkow;
    }
}
