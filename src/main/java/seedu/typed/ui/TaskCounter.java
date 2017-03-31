package seedu.typed.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.typed.commons.events.ui.NewResultAvailableEvent;
import seedu.typed.model.Model;

public class TaskCounter extends UiPart<Region> {
    private static final String FXML = "TaskCounter.fxml";

    private Model model;

    @FXML
    private Text completedCount, pendingCount;

    @FXML
    private Text Completed, Pending;

    public TaskCounter(AnchorPane taskCounterPlaceholder, Model model) {
        super(FXML);
        this.model = model;

        taskCounterPlaceholder.getChildren().add(Completed);
        taskCounterPlaceholder.getChildren().add(Pending);
        taskCounterPlaceholder.getChildren().add(completedCount);
        taskCounterPlaceholder.getChildren().add(pendingCount);

        completedCount.setText(model.getNumberCompletedTasks() + "");
        pendingCount.setText(model.getNumberUncompletedTasks() + "");
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        completedCount.setText(model.getNumberCompletedTasks() + "");
        pendingCount.setText(model.getNumberUncompletedTasks() + "");
    }

}
