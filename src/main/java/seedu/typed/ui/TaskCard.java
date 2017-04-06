package seedu.typed.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.schedule.ScheduleElement;

//@@author A0139392X
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private final Image stampComplete = new Image("/images/doneInvert.png");
    private final Image checkbox = new Image("/images/checkbox.png");
    private final Image recurringLogo = new Image("/images/recurring.png");

    @FXML
    private AnchorPane cardPane;
    @FXML
    private Label name;
    @FXML
    private Text date;
    @FXML
    private FlowPane tags;
    @FXML
    private Text notes;
    @FXML
    private Circle taskType;
    @FXML
    private ImageView stamp;
    @FXML
    private ImageView recurring;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(displayedIndex + ". " + task.getName().getValue());

        ScheduleElement se = task.getSE();
        date.setText(se.toString() + se.teToString());

        if (task.isOverdue()) {
            taskType.setFill(Color.RED);
        } else if (task.isEvent()) {
            taskType.setFill(Color.SEAGREEN);
        } else if (task.isDeadline()) {
            taskType.setFill(Color.ORANGE);
        } else if (task.isFloating()) {
            taskType.setFill(Color.DODGERBLUE);
        }

        notes.setText(task.getNotes().toString());

        if (task.getIsCompleted()) {
            stamp.setImage(stampComplete);
        } else {
            stamp.setImage(checkbox);
        }

        if (task.isRecurring()) {
            recurring.setImage(recurringLogo);
        }

        initTags(task);

    }
    //@@author

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
