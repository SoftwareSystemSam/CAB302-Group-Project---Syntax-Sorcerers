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
 * This class is used to handle the pie chart GUI for MyHubController
 */
public class PieChartGUI extends VBox {
    private TableView<ScreenTimeEntry> table = new TableView<>();
    private ObservableList<ScreenTimeEntry> tableData = FXCollections.observableArrayList();
    private PieChart pieChart;

    /**
     * This function is used to create a new pie chart GUI
     * @param userId The user id
     * @param screenTimeEntryDAO The screen time entry DAO
     * @throws SQLException If an exception occurs
     */
    public PieChartGUI(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) throws SQLException {
        setupPieChart(userId, screenTimeEntryDAO);
        setupTableView();
        setupRefreshTimer(userId, screenTimeEntryDAO);
    }

    /**
     * This function is used to set up the pie chart
     * @param userId The user id
     * @param screenTimeEntryDAO The screen time entry DAO
     * @throws SQLException If an exception occurs
     */
    private void setupPieChart(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        List<ScreenTimeEntry> entries = screenTimeEntryDAO.getScreenTimeEntriesByUserIdAndDate(userId, LocalDate.now());
        // Sort entries by duration in descending order and limit to 20
        entries.stream()
                .sorted((e1, e2) -> Long.compare(e2.getDuration(), e1.getDuration())) // Sort by duration descending
                .limit(20) // Limit to top 20 entries
                .forEach(entry -> pieChartData.add(new PieChart.Data(entry.getApplicationName(), entry.getDuration())));
        pieChart = new PieChart(pieChartData);
        pieChart.setLegendVisible(true);
        pieChart.setLegendSide(Side.BOTTOM);
        this.getChildren().add(pieChart);
        tableData.setAll(entries);
    }

    /**
     * This function is used to set up the table view
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


    //https://stackoverflow.com/questions/56688910/javafx-webview-auto-refresh
    /**
     * This function is used to set up the refresh timer
     * @param userId The user id
     * @param screenTimeEntryDAO The screen time entry DAO
     */
    private void setupRefreshTimer(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) {
        Timeline refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            refreshData(userId, screenTimeEntryDAO);
        }));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }


    //https://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx
    /**
     * This function is used to refresh the data and only display top 20 entries
     * @param userId The user id
     * @param screenTimeEntryDAO The screen time entry DAO
     */
    private void refreshData(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) {
        Platform.runLater(() -> {
            try {
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                List<ScreenTimeEntry> entries = screenTimeEntryDAO.getScreenTimeEntriesByUserIdAndDate(userId, LocalDate.now());
                // Sort and limit entries as in setupPieChart
                entries.stream()
                        .sorted((e1, e2) -> Long.compare(e2.getDuration(), e1.getDuration()))
                        .limit(20)
                        .forEach(entry -> pieChartData.add(new PieChart.Data(entry.getApplicationName(), entry.getDuration())));
                pieChart.setData(pieChartData);
                tableData.setAll(entries);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}



