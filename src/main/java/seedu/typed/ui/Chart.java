package seedu.typed.ui;

import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class Chart extends UiPart<Region> {
    private static final String FXML = "Chart.fxml";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private PieChart chart;

    /**
     * @param placeholder
     *            The AnchorPane where the BrowserPanel must be inserted
     */
    public Chart(AnchorPane holder) {
        super(FXML);
        holder.getChildren().add(chart);
    }

    @FXML
    void initialize() {
        assert chart != null;

        ObservableList<PieChart.Data> pieData =
                FXCollections.observableArrayList(new PieChart.Data("Completed", 13), new PieChart.Data("Pending", 10));

        chart.setData(pieData);
        chart.setStartAngle(90);
    }
}
