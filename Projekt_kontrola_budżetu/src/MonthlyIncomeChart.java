import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Represents a JPanel that displays a bar chart of monthly income based on CSV data.
 */
public class MonthlyIncomeChart extends JPanel {

    // Fields

    /** The selected month for which the income data is displayed. */
    private String selectedMonth;

    /** The dataset for storing income data. */
    private DefaultCategoryDataset dataset;

    /** The panel containing the chart. */
    private ChartPanel chartPanel;

    /** The current user for whom the income data is displayed. */
    private String current_user;

    /** A map to store income data organized by month, day, and category. */
    private Map<String, Map<String, Map<String, BigDecimal>>> dataMap;

    /**
     * Constructs a MonthlyIncomeChart.
     *
     * @param user           The current user.
     * @param selectedMonth  The selected month for displaying income data.
     */
    public MonthlyIncomeChart(String user, String selectedMonth) {
        super(new BorderLayout());
        this.current_user = user;
        this.selectedMonth = selectedMonth;

        dataMap = new HashMap<>();
        createDataset();

        JPanel panel = new JPanel(new BorderLayout());
        chartPanel = createChartPanel();
        panel.add(chartPanel, BorderLayout.CENTER);

        add(panel);
    }

    // Methods

    /**
     * Creates an empty dataset for storing income data.
     */
    private void createDataset() {
        dataset = new DefaultCategoryDataset();
        loadDataFromCSV("data2.csv");
    }

    /**
     * Loads income data from a CSV file.
     *
     * @param csvFilePath The path to the CSV file.
     */
    private void loadDataFromCSV(String csvFilePath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> lines = reader.readAll();
            for (int i = 1; i < lines.size(); i++) {
                String[] line = lines.get(i);
                String user = line[2];
                boolean isExpense = Boolean.parseBoolean(line[4]);
                if (!isExpense && user.equals(current_user)) {
                    LocalDate date = LocalDate.parse(line[1], formatter);
                    String month = date.getMonth().getDisplayName(TextStyle.FULL, new Locale("pl"));

                    if (month.equals(selectedMonth)) {
                        String dayOfMonth = date.getDayOfMonth() + "";
                        String category = line[5];
                        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(line[0]));

                        if (!dataMap.containsKey(month)) {
                            dataMap.put(month, new HashMap<>());
                        }

                        Map<String, Map<String, BigDecimal>> monthData = dataMap.get(month);

                        if (!monthData.containsKey(dayOfMonth)) {
                            monthData.put(dayOfMonth, new HashMap<>());
                        }

                        Map<String, BigDecimal> dayData = monthData.get(dayOfMonth);
                        dayData.put(category, dayData.getOrDefault(category, new BigDecimal(0)).add(amount));
                    }
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        updateDataset();
    }

    /**
     * Updates the dataset with the loaded income data.
     */
    private void updateDataset() {
        dataset.clear();
        for (Map.Entry<String, Map<String, Map<String, BigDecimal>>> monthEntry : dataMap.entrySet()) {
            String month = monthEntry.getKey();
            Map<String, Map<String, BigDecimal>> monthData = monthEntry.getValue();

            for (Map.Entry<String, Map<String, BigDecimal>> dayEntry : monthData.entrySet()) {
                String day = dayEntry.getKey();
                Map<String, BigDecimal> dayData = dayEntry.getValue();

                for (Map.Entry<String, BigDecimal> categoryEntry : dayData.entrySet()) {
                    String category = categoryEntry.getKey();
                    BigDecimal amount = categoryEntry.getValue();

                    dataset.addValue(amount, category, day + " " + month);
                }
            }
        }
    }

    /**
     * Creates and configures a panel containing the bar chart.
     *
     * @return The configured ChartPanel.
     */
    private ChartPanel createChartPanel() {
        JFreeChart chart = ChartFactory.createBarChart(
                "Miesięczne wpływy",
                "Dzień",
                "Kwota",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemMargin(0.1);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryMargin(0.1);
        renderer.setBarPainter(new StandardBarPainter());
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        return chartPanel;
    }
}
