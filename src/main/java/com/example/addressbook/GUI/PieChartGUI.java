package com.example.addressbook.GUI;
import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.ScreenTimeEntry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;
import javafx.geometry.Side;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * PieChartGUI class represents a pie chart displaying the screen time of different applications.
 */
public class PieChartGUI extends VBox {
    private TableView<ScreenTimeEntry> table = new TableView<>();
    private ObservableList<ScreenTimeEntry> tableData = FXCollections.observableArrayList();
    private PieChart pieChart;
    /**
     * Constructor for PieChartGUI class.
     * Initializes the pie chart and table view with data.
     * @param userId The user's ID.
     * @param screenTimeEntryDAO The DAO for screen time entries.
     * @throws SQLException If a database access error occurs.
     */
    public PieChartGUI(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) throws SQLException {
        setupPieChart(userId, screenTimeEntryDAO);
        setupTableView();
        setupRefreshTimer(userId, screenTimeEntryDAO);
    }
    /**
     * Sets up the pie chart with data.
     * @param userId The user's ID.
     * @param screenTimeEntryDAO The DAO for screen time entries.
     * @throws SQLException If a database access error occurs.
     */
    private void setupPieChart(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        List<ScreenTimeEntry> entries = screenTimeEntryDAO.getScreenTimeEntriesByUserIdAndDate(userId, LocalDate.now());
        entries.forEach(entry -> pieChartData.add(new PieChart.Data(entry.getApplicationName(), entry.getDuration())));
        pieChart = new PieChart(pieChartData);
        pieChart.setLegendVisible(true);
        pieChart.setLegendSide(Side.BOTTOM);
        this.getChildren().add(pieChart);
        tableData.setAll(entries);
    }
    /**
     * Sets up the table view.
     */
    private void setupTableView() {
        TableColumn<ScreenTimeEntry, String> appNameCol = new TableColumn<>("Application Name");
        appNameCol.setCellValueFactory(new PropertyValueFactory<>("applicationName"));
        TableColumn<ScreenTimeEntry, Long> durationCol = new TableColumn<>("Duration (seconds)");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        table.getColumns().addAll(appNameCol, durationCol);
        table.setItems(tableData);
        this.getChildren().add(table);
    }
    /**
     * Sets up a timer to refresh the data every 5 seconds.
     * @param userId The user's ID.
     * @param screenTimeEntryDAO The DAO for screen time entries.
     */

    //https://stackoverflow.com/questions/56688910/javafx-webview-auto-refresh
    private void setupRefreshTimer(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) {
        Timeline refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            refreshData(userId, screenTimeEntryDAO);
        }));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    /**
     * Refreshes the data in the pie chart and table view.
     * @param userId The user's ID.
     * @param screenTimeEntryDAO The DAO for screen time entries.
     */
    //https://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx
    private void refreshData(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) {
        Platform.runLater(() -> {
            try {
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                List<ScreenTimeEntry> entries = screenTimeEntryDAO.getScreenTimeEntriesByUserIdAndDate(userId, LocalDate.now());
                entries.forEach(entry -> pieChartData.add(new PieChart.Data(entry.getApplicationName(), entry.getDuration())));
                pieChart.setData(pieChartData);
                tableData.setAll(entries);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}



