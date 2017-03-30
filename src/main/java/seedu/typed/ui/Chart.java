//@@author A0139392X
package seedu.typed.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

public class Chart extends UiPart<Region> {
    private static final String FXML = "Chart.fxml";

    @FXML
    private AnchorPane mainPane;

    @FXML
    private PieChart chart;

    @FXML
    private Circle blockOut;

    @FXML
    ObservableList<PieChart.Data> pieData;

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

        pieData = FXCollections.observableArrayList(
                new PieChart.Data("Completed", 10),
                new PieChart.Data("Pending", 13));

        chart.setData(pieData);
        chart.setStartAngle(90);
    }

    @FXML
void updateValue() {
//        for (Data d : pieData) {
//            if (d.getName().equals(name)) {
//                d.setPieValue(value);
//                return;
//            }
//        }
 }

}
//@@author
