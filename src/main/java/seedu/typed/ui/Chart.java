//@@author A0139392X
package seedu.typed.ui;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import seedu.typed.commons.events.model.TaskManagerChangedEvent;
import seedu.typed.model.Model;

public class Chart extends UiPart<Region> {
    private static final String FXML = "Chart.fxml";

    @FXML
    private AnchorPane mainPane;

    @FXML
    private PieChart chart;

    @FXML
    private Circle blockOut;

    @FXML
    private Text percentage;

    private ObservableList<PieChart.Data> pieData;

    private Model model;

    /**
     * @param placeholder
     *            The AnchorPane where the BrowserPanel must be inserted
     */
    public Chart(AnchorPane holder, Model model) {
        super(FXML);
        this.model = model;
        holder.getChildren().add(chart);
        holder.getChildren().add(blockOut);
        holder.getChildren().add(percentage);

        initialize();
        registerAsAnEventHandler(this);
    }

    void initialize() {
        assert chart != null;

        int completed = model.getNumberCompletedTasks();
        int pending = model.getNumberUncompletedTasks();
        int total = model.getTotalTasks();

        System.out.println("Total: " + total);
        System.out.println("Pending: " + pending);
        System.out.println("Completed: " + completed);

        pieData = FXCollections.observableArrayList(
                new PieChart.Data("Completed", completed),
                new PieChart.Data("Pending", pending));

        percentage.setText((Math.abs((completed*100)/total)) + " %");

        chart.setData(pieData);
        chart.setStartAngle(90);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(TaskManagerChangedEvent event) {
        System.out.println("meow");
        int completed = model.getNumberCompletedTasks();
        int pending = model.getNumberUncompletedTasks();
        int total = model.getTotalTasks();

        System.out.println("Total: " + total);
        System.out.println("Pending: " + pending);
        System.out.println("Completed: " + completed);

        for (Data d : pieData) {
            if (d.getName().equals("Completed")) {
                d.setPieValue(completed);
            } else {
                d.setPieValue(pending);
            }
        }

        if (total != 0) {
            percentage.setText((Math.abs((completed*100)/total)) + " %");
        } else {
            percentage.setText(0 + " %");
        }
    }

}
//@@author
