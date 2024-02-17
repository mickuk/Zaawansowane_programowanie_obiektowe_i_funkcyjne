import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Utility class for reading and writing CSV files.
 */
public class CsvManager {

    // Delimiter used in CSV files
    private static final String COMMA_DELIMITER = ",";

    // New line separator for CSV files
    private static final String NEW_LINE_SEPARATOR = "\n";

    /**
     * Writes a list of objects implementing CsvAble interface to a CSV file.
     * @param fileName The name of the CSV file to be created.
     * @param objects List of CsvAble objects to be written to the CSV file.
     */
    public static void writeToCsv(String fileName, List<CsvAble> objects) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);
            for (CsvAble object : objects) {
                fileWriter.append(object.toCsvRow());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads a CSV file containing transactions and returns a list of Transakcja objects.
     * @param fileName The name of the CSV file to be read.
     * @return List of Transakcja objects read from the CSV file.
     */
    public static List<Transakcja> readTransactions(String fileName) {
        List<Transakcja> transakcje = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            line = br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                Transakcja transakcja = new Transakcja(new BigDecimal(values[0]), LocalDate.parse(values[1], formatter), values[2],
                        values[3], Boolean.valueOf(values[4]), Typ.valueOf(values[5]));
                transakcje.add(transakcja);
            }

            System.out.println("CSV file was read successfully !!!");

        } catch (IOException e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        }

        return transakcje;
    }

    /**
     * Appends a single CsvAble object to an existing CSV file.
     * @param fileName The name of the CSV file to be updated.
     * @param object CsvAble object to be appended to the CSV file.
     */
    public static void appendCsvFile(String fileName, CsvAble object) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName, true);

            fileWriter.append(object.toCsvRow());
            fileWriter.append(NEW_LINE_SEPARATOR);

            System.out.println("CSV file was updated successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
}
