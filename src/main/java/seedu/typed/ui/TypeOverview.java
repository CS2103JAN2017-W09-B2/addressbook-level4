package seedu.typed.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import seedu.typed.commons.events.model.TaskManagerChangedEvent;
import seedu.typed.model.Model;

//@@author A0139392X
public class TypeOverview extends UiPart<Region> {
    private static final String FXML = "TypeOverview.fxml";

    private Model model;

    @FXML
    private Text title;

    @FXML
    private Circle overdueCircle;
    @FXML
    private Text overdueTitle;
    @FXML
    private Text overdueNumber;

    @FXML
    private Circle deadlineCircle;
    @FXML
    private Text deadlineTitle;
    @FXML
    private Text deadlineNumber;

    @FXML
    private Circle durationCircle;
    @FXML
    private Text durationTitle;
    @FXML
    private Text durationNumber;

    @FXML
    private Circle floatingCircle;
    @FXML
    private Text floatingTitle;
    @FXML
    private Text floatingNumber;

    public TypeOverview(AnchorPane holder, Model model) {
        super(FXML);
        this.model = model;
        holder.getChildren().add(title);

        holder.getChildren().add(overdueCircle);
        holder.getChildren().add(overdueTitle);
        holder.getChildren().add(overdueNumber);

        holder.getChildren().add(deadlineCircle);
        holder.getChildren().add(deadlineTitle);
        holder.getChildren().add(deadlineNumber);

        holder.getChildren().add(durationCircle);
        holder.getChildren().add(durationTitle);
        holder.getChildren().add(durationNumber);

        holder.getChildren().add(floatingCircle);
        holder.getChildren().add(floatingTitle);
        holder.getChildren().add(floatingNumber);

        initialize();
        registerAsAnEventHandler(this);
    }

    void initialize() {
        int pendingFloating = model.getNumberUncompletedFloatingTasks();
        int pendingDeadline = model.getNumberUncompletedDeadlines();
        int pendingDuration = model.getNumberUncompletedEvents();
        int pendingOverdue = model.getNumberOverdue();

        floatingNumber.setText(pendingFloating + "");
        deadlineNumber.setText(pendingDeadline + "");
        durationNumber.setText(pendingDuration + "");

        overdueNumber.setText(pendingOverdue + "");
    }
    @Subscribe
    private void handleNewResultAvailableEvent(TaskManagerChangedEvent event) {
        int pendingFloating = model.getNumberUncompletedFloatingTasks();
        int pendingDeadline = model.getNumberUncompletedDeadlines();
        int pendingDuration = model.getNumberUncompletedEvents();
        int pendingOverdue = model.getNumberOverdue();

        floatingNumber.setText(pendingFloating + "");
        deadlineNumber.setText(pendingDeadline + "");
        durationNumber.setText(pendingDuration + "");
        overdueNumber.setText(pendingOverdue + "");
    }
}
//@@author
