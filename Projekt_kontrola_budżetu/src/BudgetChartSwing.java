import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

/**
 * A Swing application to display a budget chart based on CSV data.
 */
public class BudgetChartSwing extends JPanel {

    // Map to store date and corresponding total balance
    private Map<Date, Double> dataMap;

    // Panel to display the chart
    private ChartPanel chartPanel;

    // Total balance variable
    Double totalBalance = 0.0;

    // Selected user and month variables
    String selectedUser;
    String selectedMonth;

    /**
     * Constructor for BudgetChartSwing.
     * @param selectedUser The selected user for filtering data.
     * @param selectedMonth The selected month for filtering data.
     */
    public BudgetChartSwing(String selectedUser, String selectedMonth) {
        super(new BorderLayout());
        this.selectedUser = selectedUser;
        this.selectedMonth = selectedMonth;
        createDataset();

        JPanel panel = new JPanel(new BorderLayout());
        chartPanel = createChartPanel();
        panel.add(chartPanel, BorderLayout.CENTER);

        add(panel);
    }

    /**
     * Initialize the dataset by loading data from the CSV file.
     */
    private void createDataset() {
        dataMap = new HashMap<>();
        loadDataFromCSV("data2.csv");
    }

    /**
     * Load data from a CSV file and populate the dataMap.
     * @param csvFilePath The path to the CSV file.
     */
    private void loadDataFromCSV(String csvFilePath) {
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> lines = reader.readAll();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Check if a specific user is selected
            if (!selectedUser.equals("all")) {
                for (int i = 1; i < lines.size(); i++) {
                    String[] line = lines.get(i);
                    if (line.length >= 6) {
                        String dateString = line[1];
                        String month = LocalDate.parse(line[1], formatter).getMonth().getDisplayName(TextStyle.FULL, new Locale("pl"));

                        double amount = Double.parseDouble(line[0]);
                        String user = line[2];
                        boolean isExpense = Boolean.parseBoolean(line[4]);

                        // Filter data based on selected user and month
                        if (user.equals(selectedUser) && selectedMonth.equals(month)) {
                            try {
                                Date date = dateFormat.parse(dateString);
                                if (!isExpense) {
                                    totalBalance += amount;
                                } else if (isExpense) {
                                    totalBalance -= amount;
                                }

                                dataMap.put(date, totalBalance);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {  // If all users are selected
                for (int i = 1; i < lines.size(); i++) {
                    String[] line = lines.get(i);
                    if (line.length >= 6) {
                        String dateString = line[1];
                        String month = LocalDate.parse(line[1], formatter).getMonth().getDisplayName(TextStyle.FULL, new Locale("pl"));

                        double amount = Double.parseDouble(line[0]);
                        String user = line[2];
                        boolean isExpense = Boolean.parseBoolean(line[4]);

                        // Filter data based on selected month
                        if (selectedMonth.equals(month)) {
                            try {
                                Date date = dateFormat.parse(dateString);
                                if (!isExpense) {
                                    totalBalance += amount;
                                } else if (isExpense) {
                                    totalBalance -= amount;
                                }

                                dataMap.put(date, totalBalance);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an XYSeries from the dataMap.
     * @return XYSeries representing the total budget balance over time.
     */
    private XYSeries createXYSeriesFromDataMap() {
        XYSeries xySeries = new XYSeries("Total Budget Balance");
        for (Date date : dataMap.keySet()) {
            xySeries.add(date.getTime(), dataMap.get(date));
        }
        return xySeries;
    }

    /**
     * Create a ChartPanel to display the budget chart.
     * @return ChartPanel containing the budget chart.
     */
    private ChartPanel createChartPanel() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Total Balance Chart",
                "Date",
                "Total Balance",
                new XYSeriesCollection(createXYSeriesFromDataMap()),
                false,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        return chartPanel;
    }
}
